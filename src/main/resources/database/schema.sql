-- =====================================================
-- Script de creación de Base de Datos
-- Biblioteca Digital UNTEC
-- =====================================================
-- Compatible con H2 y MySQL
-- Autor: Equipo Biblioteca Digital UNTEC
-- Versión: 1.0
-- Fecha: Marzo 2026
-- =====================================================

-- -----------------------------------------------------
-- Tabla: usuarios
-- Descripción: Almacena información de usuarios del sistema
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL COMMENT 'RUT del usuario sin puntos con guión',
    nombre VARCHAR(100) NOT NULL COMMENT 'Nombre completo del usuario',
    email VARCHAR(100) UNIQUE NOT NULL COMMENT 'Correo electrónico institucional',
    password VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada',
    tipo_usuario VARCHAR(20) NOT NULL COMMENT 'ESTUDIANTE, PROFESOR, ADMIN',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE COMMENT 'Usuario activo o inactivo',
    
    -- Índices para mejorar rendimiento
    INDEX idx_email (email),
    INDEX idx_rut (rut),
    INDEX idx_tipo (tipo_usuario)
) COMMENT='Usuarios del sistema de biblioteca';

-- -----------------------------------------------------
-- Tabla: libros
-- Descripción: Catálogo de libros de la biblioteca
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(13) UNIQUE NOT NULL COMMENT 'ISBN-13 del libro',
    titulo VARCHAR(200) NOT NULL COMMENT 'Título del libro',
    autor VARCHAR(150) NOT NULL COMMENT 'Autor principal del libro',
    editorial VARCHAR(100) COMMENT 'Editorial que publica el libro',
    anio_publicacion INT COMMENT 'Año de publicación',
    categoria VARCHAR(50) COMMENT 'Categoría o género del libro',
    descripcion TEXT COMMENT 'Descripción o sinopsis del libro',
    cantidad_total INT NOT NULL DEFAULT 1 COMMENT 'Cantidad total de ejemplares',
    cantidad_disponible INT NOT NULL DEFAULT 1 COMMENT 'Ejemplares disponibles para préstamo',
    ubicacion VARCHAR(50) COMMENT 'Ubicación física en biblioteca',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE COMMENT 'Libro activo en catálogo',
    
    -- Restricciones
    CHECK (cantidad_disponible >= 0),
    CHECK (cantidad_disponible <= cantidad_total),
    CHECK (anio_publicacion > 1000 AND anio_publicacion <= YEAR(CURRENT_DATE) + 1),
    
    -- Índices
    INDEX idx_titulo (titulo),
    INDEX idx_autor (autor),
    INDEX idx_isbn (isbn),
    INDEX idx_categoria (categoria)
) COMMENT='Catálogo de libros de la biblioteca';

-- -----------------------------------------------------
-- Tabla: prestamos
-- Descripción: Registro de préstamos de libros
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL COMMENT 'ID del usuario que solicita el préstamo',
    id_libro BIGINT NOT NULL COMMENT 'ID del libro prestado',
    fecha_prestamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha del préstamo',
    fecha_devolucion_esperada DATE NOT NULL COMMENT 'Fecha esperada de devolución',
    fecha_devolucion_real TIMESTAMP NULL COMMENT 'Fecha real de devolución (NULL si no devuelto)',
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' COMMENT 'ACTIVO, DEVUELTO, VENCIDO',
    observaciones TEXT COMMENT 'Observaciones del préstamo',
    
    -- Claves foráneas
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_libro) REFERENCES libros(id) ON DELETE RESTRICT,
    
    -- Índices
    INDEX idx_usuario (id_usuario),
    INDEX idx_libro (id_libro),
    INDEX idx_estado (estado),
    INDEX idx_fecha_prestamo (fecha_prestamo)
) COMMENT='Registro de préstamos de libros';

-- =====================================================
-- Datos de prueba (Seed Data)
-- =====================================================

-- Insertar usuario administrador por defecto
-- Password: admin123 (debe cambiarse en producción)
INSERT INTO usuarios (rut, nombre, email, password, tipo_usuario, activo) VALUES
('12345678-9', 'Administrador Sistema', 'admin@untec.cl', 'admin123', 'ADMIN', TRUE),
('98765432-1', 'Juan Pérez González', 'juan.perez@untec.cl', 'estudiante123', 'ESTUDIANTE', TRUE),
('11223344-5', 'María Silva Torres', 'maria.silva@untec.cl', 'profesor123', 'PROFESOR', TRUE);

-- Insertar libros de ejemplo
INSERT INTO libros (isbn, titulo, autor, editorial, anio_publicacion, categoria, descripcion, cantidad_total, cantidad_disponible, ubicacion) VALUES
('9780134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Programación', 'Guía esencial para programadores Java con las mejores prácticas actualizadas', 3, 3, 'Estante A1'),
('9780596009205', 'Head First Design Patterns', 'Eric Freeman, Elisabeth Robson', 'O''Reilly Media', 2004, 'Programación', 'Introducción visual a patrones de diseño en programación orientada a objetos', 2, 2, 'Estante A2'),
('9780132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 'Programación', 'Manual de buenas prácticas para escribir código limpio y mantenible', 5, 5, 'Estante A3'),
('9781617294945', 'Spring in Action', 'Craig Walls', 'Manning Publications', 2018, 'Frameworks', 'Desarrollo de aplicaciones empresariales con Spring Framework', 2, 2, 'Estante B1'),
('9781491950296', 'Learning SQL', 'Alan Beaulieu', 'O''Reilly Media', 2020, 'Bases de Datos', 'Introducción completa al lenguaje SQL y diseño de bases de datos', 4, 4, 'Estante C1');

-- Insertar un préstamo de ejemplo
INSERT INTO prestamos (id_usuario, id_libro, fecha_devolucion_esperada, estado) VALUES
(2, 1, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY), 'ACTIVO');

-- Actualizar cantidad disponible después del préstamo
UPDATE libros SET cantidad_disponible = cantidad_disponible - 1 WHERE id = 1;

-- =====================================================
-- Vistas útiles para consultas
-- =====================================================

-- Vista: Préstamos activos con información completa
CREATE OR REPLACE VIEW v_prestamos_activos AS
SELECT 
    p.id AS prestamo_id,
    p.fecha_prestamo,
    p.fecha_devolucion_esperada,
    p.estado,
    u.rut AS usuario_rut,
    u.nombre AS usuario_nombre,
    u.email AS usuario_email,
    l.isbn AS libro_isbn,
    l.titulo AS libro_titulo,
    l.autor AS libro_autor,
    DATEDIFF(p.fecha_devolucion_esperada, CURRENT_DATE) AS dias_restantes
FROM prestamos p
INNER JOIN usuarios u ON p.id_usuario = u.id
INNER JOIN libros l ON p.id_libro = l.id
WHERE p.estado = 'ACTIVO' AND p.fecha_devolucion_real IS NULL;

-- Vista: Disponibilidad de libros
CREATE OR REPLACE VIEW v_disponibilidad_libros AS
SELECT 
    l.id,
    l.isbn,
    l.titulo,
    l.autor,
    l.categoria,
    l.cantidad_total,
    l.cantidad_disponible,
    (l.cantidad_total - l.cantidad_disponible) AS cantidad_prestada,
    CASE 
        WHEN l.cantidad_disponible > 0 THEN 'DISPONIBLE'
        ELSE 'NO DISPONIBLE'
    END AS estado_disponibilidad
FROM libros l
WHERE l.activo = TRUE;

-- =====================================================
-- Procedimientos almacenados (opcional - depende del SGBD)
-- =====================================================
-- Nota: Los procedimientos almacenados varían entre H2 y MySQL
-- Descomentar según el motor de base de datos usado

-- =====================================================
-- Fin del script
-- =====================================================
