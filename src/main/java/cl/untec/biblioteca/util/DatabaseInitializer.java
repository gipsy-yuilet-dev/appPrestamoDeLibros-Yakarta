package cl.untec.biblioteca.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Listener que se ejecuta al iniciar la aplicación.
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Verificar si las tablas de BD existen</li>
 *   <li>Si no existen, crearlas e insertar datos de prueba</li>
 *   <li>Registrar estado en logs de aplicación</li>
 * </ul>
 * 
 * <p>Esto asegura que la aplicación esté lista para funcionar
 * sin pasos de configuración manual.</p>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 */
@WebListener
public class DatabaseInitializer implements ServletContextListener {
    
    /**
     * Se ejecuta cuando la aplicación inicia.
     * Verifica e inicializa la base de datos si es necesario.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String contextName = sce.getServletContext().getServletContextName();
        sce.getServletContext().log("========================================");
        sce.getServletContext().log("🚀 Inicializando aplicación: " + contextName);
        sce.getServletContext().log("========================================");
        
        try {
            // 1. Verificar conexión a BD
            if (!DatabaseConnection.testConnection()) {
                sce.getServletContext().log("❌ CRÍTICO: No se puede conectar a la base de datos");
                sce.getServletContext().log("   Verifica las credenciales en web.xml o DatabaseConnection.java");
                return;
            }
            sce.getServletContext().log("✓ Conexión a base de datos: OK");
            
            // 2. Verificar si las tablas existen
            if (!tablasExisten()) {
                sce.getServletContext().log("⚠ Tablas no encontradas. Inicializando schema...");
                inicializarSchema();
                sce.getServletContext().log("✓ Base de datos inicializada correctamente");
            } else {
                sce.getServletContext().log("✓ Tablas de BD ya existen. Saltando inicialización.");
            }
            
            // 3. Verificar datos de prueba
            int cantidadLibros = obtenerCantidadLibros();
            sce.getServletContext().log("✓ Total de libros en catálogo: " + cantidadLibros);
            
            int cantidadUsuarios = obtenerCantidadUsuarios();
            sce.getServletContext().log("✓ Total de usuarios registrados: " + cantidadUsuarios);
            
            sce.getServletContext().log("========================================");
            sce.getServletContext().log("✓ APLICACIÓN LISTA PARA USAR");
            sce.getServletContext().log("  Accede a: http://localhost:8080" + sce.getServletContext().getContextPath());
            sce.getServletContext().log("========================================");
            
        } catch (SQLException | RuntimeException e) {
            sce.getServletContext().log("❌ Error durante la inicialización de BD:", e);
        }
    }
    
    /**
     * Verifica si las tablas de BD existen.
     */
    private boolean tablasExisten() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            
            // Intentar obtener la tabla "usuarios"
            try (ResultSet rs = metaData.getTables(null, null, "USUARIOS", null)) {
                return rs.next();
            }
        }
    }
    
    /**
     * Inicializa el schema de BD leyendo y ejecutando schema.sql.
     * 
     * <p>NOTA: En una aplicación real, esto se haría con migrations
     * (Flyway, Liquibase). Para este proyecto educativo es suficiente.</p>
     */
    private void inicializarSchema() throws SQLException {
        // SQL embebido (versión simplificada de schema.sql)
        String[] sqlStatements = {
            // Tabla usuarios
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "  id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "  rut VARCHAR(12) UNIQUE NOT NULL," +
            "  nombre VARCHAR(100) NOT NULL," +
            "  email VARCHAR(100) UNIQUE NOT NULL," +
            "  password VARCHAR(255) NOT NULL," +
            "  tipo_usuario VARCHAR(20) NOT NULL," +
            "  fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "  activo BOOLEAN DEFAULT TRUE" +
            ")",
            
            // Tabla libros
            "CREATE TABLE IF NOT EXISTS libros (" +
            "  id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "  isbn VARCHAR(13) UNIQUE NOT NULL," +
            "  titulo VARCHAR(200) NOT NULL," +
            "  autor VARCHAR(150) NOT NULL," +
            "  editorial VARCHAR(100)," +
            "  anio_publicacion INT," +
            "  categoria VARCHAR(50)," +
            "  descripcion TEXT," +
            "  cantidad_total INT NOT NULL DEFAULT 1," +
            "  cantidad_disponible INT NOT NULL DEFAULT 1," +
            "  ubicacion VARCHAR(50)," +
            "  fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "  activo BOOLEAN DEFAULT TRUE," +
            "  CHECK (cantidad_disponible >= 0)," +
            "  CHECK (cantidad_disponible <= cantidad_total)" +
            ")",
            
            // Tabla prestamos
            "CREATE TABLE IF NOT EXISTS prestamos (" +
            "  id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "  id_usuario BIGINT NOT NULL," +
            "  id_libro BIGINT NOT NULL," +
            "  fecha_prestamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "  fecha_devolucion_esperada DATE NOT NULL," +
            "  fecha_devolucion_real TIMESTAMP NULL," +
            "  estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO'," +
            "  observaciones TEXT," +
            "  FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE RESTRICT," +
            "  FOREIGN KEY (id_libro) REFERENCES libros(id) ON DELETE RESTRICT" +
            ")"
            ,
            "CREATE INDEX IF NOT EXISTS idx_email ON usuarios(email)",
            "CREATE INDEX IF NOT EXISTS idx_rut ON usuarios(rut)",
            "CREATE INDEX IF NOT EXISTS idx_tipo ON usuarios(tipo_usuario)",
            "CREATE INDEX IF NOT EXISTS idx_titulo ON libros(titulo)",
            "CREATE INDEX IF NOT EXISTS idx_autor ON libros(autor)",
            "CREATE INDEX IF NOT EXISTS idx_isbn ON libros(isbn)",
            "CREATE INDEX IF NOT EXISTS idx_categoria ON libros(categoria)",
            "CREATE INDEX IF NOT EXISTS idx_usuario ON prestamos(id_usuario)",
            "CREATE INDEX IF NOT EXISTS idx_libro ON prestamos(id_libro)",
            "CREATE INDEX IF NOT EXISTS idx_estado ON prestamos(estado)",
            "CREATE INDEX IF NOT EXISTS idx_fecha_prestamo ON prestamos(fecha_prestamo)"
        };
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Desactivar auto-commit para transaction
            conn.setAutoCommit(false);
            
            try (Statement stmt = conn.createStatement()) {
                for (String sql : sqlStatements) {
                    stmt.execute(sql);
                }
            }
            
            // Insertar datos de prueba
            try (Statement stmt = conn.createStatement()) {
                // Usuarios de prueba (idénticos a schema.sql)
                stmt.executeUpdate(
                    "INSERT INTO usuarios (rut, nombre, email, password, tipo_usuario, activo) VALUES " +
                    "('12345678-9', 'Administrador Sistema', 'admin@untec.cl', 'admin123', 'ADMIN', TRUE)," +
                    "('98765432-1', 'Juan Pérez González', 'juan.perez@untec.cl', 'estudiante123', 'ESTUDIANTE', TRUE)," +
                    "('11223344-5', 'María Silva Torres', 'maria.silva@untec.cl', 'profesor123', 'PROFESOR', TRUE)"
                );
                
                // Libros de prueba
                stmt.executeUpdate(
                    "INSERT INTO libros (isbn, titulo, autor, editorial, anio_publicacion, categoria, descripcion, cantidad_total, cantidad_disponible, ubicacion) VALUES " +
                    "('9780134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Programación', 'Guía esencial para programadores Java', 3, 3, 'Estante A1')," +
                    "('9780596009205', 'Head First Design Patterns', 'Eric Freeman, Elisabeth Robson', 'O''Reilly Media', 2004, 'Programación', 'Introducción visual a patrones de diseño', 2, 2, 'Estante A2')," +
                    "('9780132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 'Programación', 'Manual de buenas prácticas', 5, 5, 'Estante A3')," +
                    "('9781617294945', 'Spring in Action', 'Craig Walls', 'Manning Publications', 2018, 'Frameworks', 'Desarrollo de aplicaciones empresariales', 2, 2, 'Estante B1')," +
                    "('9781491950296', 'Learning SQL', 'Alan Beaulieu', 'O''Reilly Media', 2020, 'Bases de Datos', 'Introducción completa al lenguaje SQL', 4, 4, 'Estante C1')"
                );
            }
            
            conn.commit();
            
        } catch (SQLException e) {
            throw new SQLException("Error al inicializar schema: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la cantidad de libros en la BD.
     */
    private int obtenerCantidadLibros() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM libros WHERE activo = TRUE")) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            // Tabla aún no existe
            return 0;
        }
        return 0;
    }
    
    /**
     * Obtiene la cantidad de usuarios en la BD.
     */
    private int obtenerCantidadUsuarios() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM usuarios WHERE activo = TRUE")) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            // Tabla aún no existe
            return 0;
        }
        return 0;
    }
    
    /**
     * Se ejecuta al detener la aplicación.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("========================================");
        sce.getServletContext().log("🛑 Aplicación detenida");
        sce.getServletContext().log("========================================");
    }
}

