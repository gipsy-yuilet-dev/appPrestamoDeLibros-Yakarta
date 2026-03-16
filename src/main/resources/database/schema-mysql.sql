-- ============================================
-- Schema SQL para MySQL 8.0+ (Producción)
-- Biblioteca Digital UNTEC
-- ============================================
-- Este script se debe ejecutar ANTES de desplegar a MySQL
-- en ambientes de producción (Azure, AWS, etc)

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_db 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca_db;

-- ============================================
-- TABLA: USUARIOS
-- ============================================
DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS libros;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL COMMENT 'Email único del usuario',
    password VARCHAR(255) NOT NULL COMMENT 'Password hash (preferiblemente bcrypt)',
    nombre VARCHAR(150) NOT NULL COMMENT 'Nombre completo del usuario',
    tipo_usuario ENUM('ESTUDIANTE', 'PROFESOR', 'ADMIN') NOT NULL COMMENT 'Rol del usuario',
    activo BOOLEAN DEFAULT TRUE COMMENT 'Usuario activo o desactivado',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de registro',
    
    INDEX idx_email (email),
    INDEX idx_tipo (tipo_usuario),
    INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Usuarios del sistema (estudiantes, profesores, admin)';

-- ============================================
-- TABLA: LIBROS
-- ============================================
CREATE TABLE libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE NOT NULL COMMENT 'ISBN del libro',
    titulo VARCHAR(255) NOT NULL COMMENT 'Título del libro',
    autor VARCHAR(150) NOT NULL COMMENT 'Autor principal',
    editorial VARCHAR(100) COMMENT 'Editorial/Imprenta',
    anio_publicacion INT COMMENT 'Año de publicación',
    categoria VARCHAR(50) COMMENT 'Categoría/Género (Ficción, Técnico, etc)',
    descripcion LONGTEXT COMMENT 'Descripción detallada del libro',
    cantidad_total INT DEFAULT 1 COMMENT 'Total de copias disponibles',
    cantidad_disponible INT DEFAULT 1 COMMENT 'Copias disponibles para prestar',
    ubicacion VARCHAR(100) COMMENT 'Ubicación física en biblioteca (Estante, etc)',
    activo BOOLEAN DEFAULT TRUE COMMENT 'Libro activo en catálogo',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de ingreso al sistema',
    
    INDEX idx_isbn (isbn),
    INDEX idx_titulo (titulo),
    INDEX idx_autor (autor),
    INDEX idx_categoria (categoria),
    INDEX idx_activo (activo),
    FULLTEXT idx_busqueda (titulo, autor, descripcion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Catálogo de libros';

-- ============================================
-- TABLA: PRESTAMOS
-- ============================================
CREATE TABLE prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL COMMENT 'ID del usuario que solicita préstamo',
    id_libro BIGINT NOT NULL COMMENT 'ID del libro prestado',
    fecha_prestamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha/hora de préstamo',
    fecha_devolucion_esperada DATE NOT NULL COMMENT 'Fecha esperada de devolución',
    fecha_devolucion_real TIMESTAMP NULL COMMENT 'Fecha/hora real de devolución (NULL si no devuelto)',
    estado ENUM('ACTIVO', 'DEVUELTO', 'VENCIDO') DEFAULT 'ACTIVO' COMMENT 'Estado del préstamo',
    observaciones LONGTEXT COMMENT 'Observaciones sobre devolución (daños, estado, etc)',
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_libro) REFERENCES libros(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    
    INDEX idx_usuario (id_usuario),
    INDEX idx_libro (id_libro),
    INDEX idx_estado (estado),
    INDEX idx_fecha_prestamo (fecha_prestamo),
    INDEX idx_fecha_devolucion_esperada (fecha_devolucion_esperada),
    INDEX idx_usuario_estado (id_usuario, estado),
    INDEX idx_libro_estado (id_libro, estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Registro de préstamos y devoluciones';

-- ============================================
-- DATOS DE PRUEBA (para desarrollo en cloud)
-- ============================================

-- Insertar usuarios de prueba
-- Password: admin123 y estudiante123 (DEBES CAMBIAR ESTOS EN PRODUCCIÓN)
INSERT INTO usuarios (email, password, nombre, tipo_usuario, activo) VALUES
('admin@untec.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/1Ja', 'Administrador Sistema', 'ADMIN', TRUE),
('juan.perez@untec.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/1Ja', 'Juan Pérez González', 'ESTUDIANTE', TRUE),
('maria.garcia@untec.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/1Ja', 'María García López', 'ESTUDIANTE', TRUE),
('profesor.lopez@untec.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/1Ja', 'Dr. Roberto López', 'PROFESOR', TRUE);

-- Insertar libros de prueba
INSERT INTO libros (isbn, titulo, autor, editorial, anio_publicacion, categoria, descripcion, cantidad_total, cantidad_disponible, ubicacion, activo) VALUES
('978-0-596-00712-6', 'Head First Java', 'Kathy Sierra, Bert Bates', 'O\'Reilly Media', 2005, 'Programación', 'Guía introductoria a Java con enfoque visual', 3, 2, 'Estante A1', TRUE),
('978-0-13-468599-1', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2017, 'Programación', 'Colección de mejores prácticas para programadores Java', 2, 2, 'Estante A2', TRUE),
('978-0-201-63361-0', 'Design Patterns', 'Gang of Four', 'Addison-Wesley', 1994, 'Arquitectura', 'Patrones reutilizables para diseño de software', 1, 1, 'Estante B1', TRUE),
('978-0-13-110362-7', 'The C Programming Language', 'Brian W. Kernighan, Dennis M. Ritchie', 'Prentice Hall', 1988, 'Programación', 'Manual de referencia para el lenguaje C', 2, 2, 'Estante C1', TRUE),
('978-1491927281', 'Building Microservices', 'Sam Newman', 'O\'Reilly Media', 2015, 'Arquitectura', 'Construyendo arquitecturas con microservicios', 1, 1, 'Estante D1', TRUE);

-- ============================================
-- VISTAS ÚTILES (Opcional)
-- ============================================

-- Vista: Préstamos activos con detalles
CREATE VIEW v_prestamos_activos AS
SELECT 
    p.id as id_prestamo,
    u.nombre as usuario,
    u.email,
    l.titulo as libro,
    l.isbn,
    p.fecha_prestamo,
    p.fecha_devolucion_esperada,
    DATEDIFF(p.fecha_devolucion_esperada, CURDATE()) as dias_restantes,
    p.estado
FROM prestamos p
INNER JOIN usuarios u ON p.id_usuario = u.id
INNER JOIN libros l ON p.id_libro = l.id
WHERE p.estado IN ('ACTIVO', 'VENCIDO')
  AND p.fecha_devolucion_real IS NULL
ORDER BY p.fecha_devolucion_esperada ASC;

-- Vista: Estadísticas de préstamos por usuario
CREATE VIEW v_estadisticas_usuario AS
SELECT 
    u.id,
    u.nombre,
    u.email,
    COUNT(CASE WHEN p.estado = 'ACTIVO' THEN 1 END) as activos,
    COUNT(CASE WHEN p.estado = 'DEVUELTO' THEN 1 END) as devueltos,
    COUNT(CASE WHEN p.estado = 'VENCIDO' THEN 1 END) as vencidos,
    COUNT(p.id) as total_prestamos
FROM usuarios u
LEFT JOIN prestamos p ON u.id = p.id_usuario
GROUP BY u.id, u.nombre, u.email;

-- ============================================
-- PROCEDIMIENTOS ALMACENADOS (Opcional)
-- ============================================

-- Procedimiento: Marcar préstamo como devuelto
DELIMITER //
CREATE PROCEDURE sp_devolver_libro(
    IN p_id_prestamo BIGINT,
    IN p_observaciones LONGTEXT
)
BEGIN
    DECLARE v_id_libro BIGINT;
    START TRANSACTION;
    
    -- Obtener ID del libro
    SELECT id_libro INTO v_id_libro 
    FROM prestamos 
    WHERE id = p_id_prestamo;
    
    -- Actualizar préstamo
    UPDATE prestamos 
    SET estado = CASE 
            WHEN DATE(CURDATE()) > fecha_devolucion_esperada THEN 'VENCIDO'
            ELSE 'DEVUELTO'
        END,
        fecha_devolucion_real = NOW(),
        observaciones = p_observaciones
    WHERE id = p_id_prestamo;
    
    -- Incrementar cantidad disponible del libro
    UPDATE libros 
    SET cantidad_disponible = cantidad_disponible + 1 
    WHERE id = v_id_libro;
    
    COMMIT;
END //
DELIMITER ;

-- ============================================
-- NOTAS IMPORTANTES
-- ============================================
/*
1. SEGURIDAD:
   - Cambiar passwords de prueba en producción
   - Usar bcrypt para hashear passwords (NO plaintext)
   - Configurar SSL/TLS para conexiones

2. PERFORMANCE:
   - Los índices están optimizados para queries comunes
   - FULLTEXT INDEX para búsqueda de libros
   - Índices compuestos para filtros frecuentes

3. BACKUP:
   - Realizar backups diarios automáticos
   - Probar restauración regularmente
   - Almacenar backups en múltiples ubicaciones

4. MANTENIMIENTO:
   - Ejecutar ANALYZE TABLE mensualmente
   - Verificar integridad de datos (CHECKSUM)
   - Monitorear uso de espacio

5. MIGRACIÓN DESDE H2:
   - Usar herramientas como SQLyog o MySQL Workbench
   - Verificar tipos de datos compatibles
   - Validar constraints y relaciones
*/
