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
            "  activo BOOLEAN DEFAULT TRUE," +
            "  facultad VARCHAR(100)," +
            "  carrera VARCHAR(150)," +
            "  anio_actual INT," +
            "  tiene_multa BOOLEAN DEFAULT FALSE," +
            "  monto_multa DECIMAL(10,2) DEFAULT 0" +
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
            "  especialidad VARCHAR(30)," +
            "  codigo_categoria VARCHAR(20)," +
            "  nivel_recomendado VARCHAR(20)," +
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
            "CREATE INDEX IF NOT EXISTS idx_facultad ON usuarios(facultad)",
            "CREATE INDEX IF NOT EXISTS idx_multa ON usuarios(tiene_multa)",
            "CREATE INDEX IF NOT EXISTS idx_titulo ON libros(titulo)",
            "CREATE INDEX IF NOT EXISTS idx_autor ON libros(autor)",
            "CREATE INDEX IF NOT EXISTS idx_isbn ON libros(isbn)",
            "CREATE INDEX IF NOT EXISTS idx_categoria ON libros(categoria)",
            "CREATE INDEX IF NOT EXISTS idx_especialidad ON libros(especialidad)",
            "CREATE INDEX IF NOT EXISTS idx_nivel ON libros(nivel_recomendado)",
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
                // Usuarios de prueba (14 usuarios con carreras variadas)
                stmt.executeUpdate(
                    "INSERT INTO usuarios (rut, nombre, email, password, tipo_usuario, facultad, carrera, anio_actual, tiene_multa, monto_multa) VALUES " +
                    "('12345678-9', 'Administrador Sistema', 'admin@untec.cl', 'admin123', 'ADMIN', NULL, NULL, NULL, FALSE, 0)," +
                    "('98765432-1', 'Juan Pérez González', 'juan.perez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería en Informática', 2, FALSE, 0)," +
                    "('87654321-0', 'Carlos Morales López', 'carlos.morales@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería Civil', 3, FALSE, 0)," +
                    "('76543210-9', 'Roberto Sánchez Vargas', 'roberto.sanchez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería Industrial', 1, TRUE, 45.50)," +
                    "('65432109-8', 'Miguel Rodríguez Flores', 'miguel.rodriguez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería en Informática', 4, FALSE, 0)," +
                    "('54321098-7', 'Ana García Martínez', 'ana.garcia@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Educación', 'Pedagogía en Matemática', 2, FALSE, 0)," +
                    "('43210987-6', 'Laura Fernández Silva', 'laura.fernandez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Educación', 'Pedagogía en Lenguaje y Comunicación', 3, FALSE, 0)," +
                    "('32109876-5', 'Patricia Núñez Díaz', 'patricia.nunez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Educación', 'Pedagogía en Historia', 1, FALSE, 0)," +
                    "('21098765-4', 'Dr. Fernando Castillo Méndez', 'fernando.castillo@untec.cl', 'profesional123', 'ESTUDIANTE', 'Medicina', 'Medicina', 5, FALSE, 0)," +
                    "('10987654-3', 'Verónica López Rojas', 'veronica.lopez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Veterinaria', 'Medicina Veterinaria', 2, FALSE, 0)," +
                    "('19876543-2', 'Jorge Ramírez Contreras', 'jorge.ramirez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ciencias Comerciales', 'Administración de Empresas', 3, FALSE, 0)," +
                    "('28765432-1', 'Sandra Pacheco González', 'sandra.pacheco@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ciencias Comerciales', 'Contabilidad Auditoría', 4, FALSE, 0)," +
                    "('37654321-0', 'Marcelo Quispe Álvarez', 'marcelo.quispe@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Turismo', 'Administración de Turismo Sostenible', 2, FALSE, 0)," +
                    "('46543210-9', 'Rodrigo Vera Acuña', 'rodrigo.vera@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Derecho', 'Derecho', 3, FALSE, 0)," +
                    "('11223344-5', 'María Silva Torres', 'maria.silva@untec.cl', 'profesor123', 'PROFESOR', 'Ingeniería', 'Ingeniería en Informática', NULL, FALSE, 0)," +
                    "('55667788-9', 'Dr. Andrés González Riquelme', 'andres.gonzalez@untec.cl', 'profesor123', 'PROFESOR', 'Medicina', 'Medicina', NULL, FALSE, 0)"
                );
                
                // Libros de prueba (26 libros por especialidad)
                stmt.executeUpdate(
                    "INSERT INTO libros (isbn, titulo, autor, editorial, anio_publicacion, categoria, descripcion, cantidad_total, cantidad_disponible, ubicacion, especialidad, codigo_categoria, nivel_recomendado) VALUES " +
                    "('9780134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Programación', 'Guía esencial para programadores Java', 3, 3, 'Estante A1', 'INGENIERIA', 'ING-001', 'INTERMEDIO')," +
                    "('9780596009205', 'Head First Design Patterns', 'Eric Freeman, Elisabeth Robson', 'O''Reilly Media', 2004, 'Programación', 'Introducción visual a patrones de diseño', 2, 2, 'Estante A2', 'INGENIERIA', 'ING-002', 'BASICO')," +
                    "('9780132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 'Programación', 'Manual de buenas prácticas', 5, 5, 'Estante A3', 'INGENIERIA', 'ING-003', 'INTERMEDIO')," +
                    "('9781617294945', 'Spring in Action', 'Craig Walls', 'Manning Publications', 2018, 'Frameworks', 'Desarrollo de aplicaciones empresariales', 2, 2, 'Estante B1', 'INGENIERIA', 'ING-004', 'AVANZADO')," +
                    "('9781491950296', 'Learning SQL', 'Alan Beaulieu', 'O''Reilly Media', 2020, 'Bases de Datos', 'Introducción completa al lenguaje SQL', 4, 4, 'Estante C1', 'INGENIERIA', 'ING-005', 'BASICO')," +
                    "('9780073352724', 'Estática y Dinámica de Estructuras', 'R.C. Hibbeler', 'Pearson', 2016, 'Ingeniería Civil', 'Análisis de estructuras', 3, 3, 'Estante D1', 'INGENIERIA', 'ING-006', 'INTERMEDIO')," +
                    "('9780470619957', 'Principios de Ingeniería Industrial', 'Benjamin Niebel', 'Wiley', 2014, 'Ingeniería Industrial', 'Optimización de procesos', 2, 2, 'Estante D2', 'INGENIERIA', 'ING-007', 'AVANZADO')," +
                    "('9788495007529', 'Pedagogía Diferenciada', 'Michel Legrand', 'Narcea', 2012, 'Educación', 'Estrategias de enseñanza adaptadas', 3, 3, 'Estante E1', 'EDUCACION', 'EDU-001', 'BASICO')," +
                    "('9788427712430', 'Didáctica de las Matemáticas', 'Chamorro, M.C.', 'Pearson', 2003, 'Matemáticas', 'Metodologías de enseñanza', 4, 3, 'Estante E2', 'EDUCACION', 'EDU-002', 'INTERMEDIO')," +
                    "('9788428325790', 'Historia de la Educación', 'Antonio Viñao Frago', 'Sourcebook', 2010, 'Historia Educativa', 'Evolución de sistemas educativos', 2, 2, 'Estante E3', 'EDUCACION', 'EDU-003', 'BASICO')," +
                    "('9781496355379', 'Anatomía Humana de Gray', 'Richard Drake, A. Wayne Vogl, Adam Mitchell', 'Elsevier', 2020, 'Medicina', 'Referencia de anatomía humana', 2, 2, 'Estante F1', 'MEDICINA', 'MED-001', 'INTERMEDIO')," +
                    "('9781455728572', 'Fisiología Médica de Guyton y Hall', 'John E. Hall', 'Elsevier', 2016, 'Medicina', 'Funcionamiento del cuerpo humano', 1, 1, 'Estante F2', 'MEDICINA', 'MED-002', 'AVANZADO')," +
                    "('9786073215435', 'Fundamentos de Nutrición', 'Margaret McWilliams', 'Cengage', 2018, 'Nutrición', 'Estudio de nutrientes', 3, 3, 'Estante F3', 'MEDICINA', 'MED-003', 'BASICO')," +
                    "('9788485732296', 'Anatomía Veterinaria', 'König, Klaus-Dieter', 'Medica Panamericana', 2005, 'Veterinaria', 'Estructura anatómica de animales', 2, 2, 'Estante G1', 'VETERINARIA', 'VET-001', 'INTERMEDIO')," +
                    "('9789706558939', 'Patología Veterinaria', 'McGavin, Carlton L.', 'Mosby Elsevier', 2007, 'Veterinaria', 'Estudio de enfermedades animales', 1, 1, 'Estante G2', 'VETERINARIA', 'VET-002', 'AVANZADO')," +
                    "('9788448197742', 'Administración Estratégica', 'Michael A. Hitt, R. Duane Ireland, Robert E. Hoskisson', 'Cengage', 2015, 'Administración', 'Gestión integral de empresas', 3, 3, 'Estante H1', 'CIENCIAS_COMERCIALES', 'COM-001', 'INTERMEDIO')," +
                    "('9788497324670', 'Contabilidad Financiera', 'Pedro Flores Estigarribia', 'Paraninfo', 2014, 'Contabilidad', 'Fundamentos de contabilidad', 4, 3, 'Estante H2', 'CIENCIAS_COMERCIALES', 'COM-002', 'BASICO')," +
                    "('9788416771127', 'Marketing Digital', 'Javier Alonso Rivas', 'Esic Editorial', 2019, 'Marketing', 'Estrategias modernas en marketing', 2, 2, 'Estante H3', 'CIENCIAS_COMERCIALES', 'COM-003', 'INTERMEDIO')," +
                    "('9780367364625', 'Gestión del Turismo Sostenible', 'David Weaver', 'Routledge', 2019, 'Turismo', 'Turismo responsable y sostenible', 2, 2, 'Estante I1', 'TURISMO', 'TUR-001', 'INTERMEDIO')," +
                    "('9788490522134', 'Geografía del Turismo', 'David Harrison', 'Síntesis', 2012, 'Turismo', 'Impacto del turismo mundial', 1, 1, 'Estante I2', 'TURISMO', 'TUR-002', 'BASICO')," +
                    "('9789568365561', 'Derecho Constitucional', 'Raúl Bertelsen Repetto', 'Jurídica', 2005, 'Derecho', 'Derecho constitucional chileno', 2, 2, 'Estante J1', 'DERECHO', 'DER-001', 'INTERMEDIO')," +
                    "('9789569254886', 'Derecho Civil Patrimonial', 'Hernán Corral Talciani', 'Thomson Reuters', 2015, 'Derecho', 'Teoría del patrimonio y obligaciones', 3, 2, 'Estante J2', 'DERECHO', 'DER-002', 'AVANZADO')," +
                    "('9788499083452', 'Sapiens', 'Yuval Noah Harari', 'Debate', 2014, 'Historia', 'Historia de la humanidad', 5, 4, 'Estante K1', 'GENERAL', 'GEN-001', 'BASICO')," +
                    "('9789501278331', 'El Poder del Hábito', 'Charles Duhigg', 'Vergara', 2012, 'Autoayuda', 'Cómo formar hábitos efectivos', 3, 3, 'Estante K2', 'GENERAL', 'GEN-002', 'BASICO')"
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

