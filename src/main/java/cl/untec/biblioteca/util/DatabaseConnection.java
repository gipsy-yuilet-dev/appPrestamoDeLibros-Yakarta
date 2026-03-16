package cl.untec.biblioteca.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar conexiones a la base de datos.
 * 
 * <p>Esta clase implementa el patrón Singleton para proporcionar una única
 * instancia que gestiona las conexiones a la base de datos. Utiliza JDBC
 * para conectarse tanto a H2 (desarrollo) como a MySQL (producción).</p>
 * 
 * <h2>Características:</h2>
 * <ul>
 *   <li>Gestión centralizada de conexiones</li>
 *   <li>Soporte para múltiples motores de base de datos</li>
 *   <li>Configuración mediante variables o archivo de properties</li>
 *   <li>Manejo de errores robusto</li>
 * </ul>
 * 
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * try (Connection conn = DatabaseConnection.getConnection()) {
 *     // Usar la conexión para ejecutar consultas
 *     PreparedStatement ps = conn.prepareStatement("SELECT * FROM libros");
 *     ResultSet rs = ps.executeQuery();
 *     // Procesar resultados...
 * } catch (SQLException e) {
 *     e.printStackTrace();
 * }
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class DatabaseConnection {
    
    // ========== Constantes de configuración ==========
    
    /**
     * Driver JDBC para H2 Database (desarrollo).
     */
    private static final String H2_DRIVER = "org.h2.Driver";
    
    /**
     * Driver JDBC para MySQL (producción).
     */
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * URL de conexión por defecto para H2.
     * El archivo de base de datos se crea en el directorio home del usuario.
     */
    private static final String H2_URL = "jdbc:h2:~/biblioteca-untec;AUTO_SERVER=TRUE";
    
    /**
     * Usuario por defecto para H2.
     */
    private static final String H2_USER = "sa";
    
    /**
     * Contraseña por defecto para H2 (vacía).
     */
    private static final String H2_PASSWORD = "";
    
    /**
     * URL de conexión para MySQL (producción).
     * Cambiar según la configuración del servidor.
     */
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/biblioteca_untec";
    
    /**
     * Usuario de MySQL.
     */
    private static final String MYSQL_USER = "biblioteca_user";
    
    /**
     * Contraseña de MySQL.
     */
    private static final String MYSQL_PASSWORD = "biblioteca_pass";
    
    // ========== Variables de configuración ==========
    
    /**
     * Modo de base de datos actual (H2 o MYSQL).
     * Por defecto usa H2 para facilitar el desarrollo.
     */
    private static String dbMode = "H2"; // Opciones: "H2" o "MYSQL"
    
    /**
     * Instancia singleton de la clase.
     */
    private static DatabaseConnection instance;
    
    // ========== Constructor privado (Singleton) ==========
    
    /**
     * Constructor privado para evitar instanciación directa.
     * Carga el driver JDBC correspondiente.
     */
    private DatabaseConnection() {
        try {
            if ("MYSQL".equalsIgnoreCase(dbMode)) {
                Class.forName(MYSQL_DRIVER);
            } else {
                Class.forName(H2_DRIVER);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la instancia singleton de DatabaseConnection.
     * 
     * @return la instancia única de DatabaseConnection
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    // ========== Métodos públicos ==========
    
    /**
     * Obtiene una conexión a la base de datos.
     * 
     * <p><b>IMPORTANTE:</b> El llamador es responsable de cerrar la conexión
     * usando try-with-resources o llamando a close() explícitamente.</p>
     * 
     * @return una conexión JDBC activa
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        getInstance(); // Asegurar que el driver esté cargado
        
        String url, user, password;
        
        if ("MYSQL".equalsIgnoreCase(dbMode)) {
            url = MYSQL_URL;
            user = MYSQL_USER;
            password = MYSQL_PASSWORD;
        } else {
            url = H2_URL;
            user = H2_USER;
            password = H2_PASSWORD;
        }
        
        return DriverManager.getConnection(url, user, password);
    }
    
    /**
     * Prueba la conexión a la base de datos.
     * 
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar conexión: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Establece el modo de base de datos (H2 o MYSQL).
     * 
     * @param mode el modo de base de datos ("H2" o "MYSQL")
     * @throws IllegalArgumentException si el modo no es válido
     */
    public static void setDatabaseMode(String mode) {
        if (!"H2".equalsIgnoreCase(mode) && !"MYSQL".equalsIgnoreCase(mode)) {
            throw new IllegalArgumentException("Modo de BD inválido. Use 'H2' o 'MYSQL'");
        }
        dbMode = mode.toUpperCase();
        instance = null; // Reiniciar instancia para recargar driver
    }
    
    /**
     * Obtiene el modo actual de base de datos.
     * 
     * @return el modo de base de datos actual ("H2" o "MYSQL")
     */
    public static String getDatabaseMode() {
        return dbMode;
    }
    
    /**
     * Cierra una conexión de forma segura.
     * 
     * @param conn la conexión a cerrar (puede ser null)
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Obtiene información sobre la conexión actual.
     * 
     * @return String con información de la conexión
     */
    public static String getConnectionInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Modo de Base de Datos: ").append(dbMode).append("\n");
        
        try (Connection conn = getConnection()) {
            info.append("Driver: ").append(conn.getMetaData().getDriverName()).append("\n");
            info.append("Versión Driver: ").append(conn.getMetaData().getDriverVersion()).append("\n");
            info.append("URL: ").append(conn.getMetaData().getURL()).append("\n");
            info.append("Usuario: ").append(conn.getMetaData().getUserName()).append("\n");
        } catch (SQLException e) {
            info.append("Error al obtener información: ").append(e.getMessage());
        }
        
        return info.toString();
    }
}
