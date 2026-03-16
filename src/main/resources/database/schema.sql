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
    facultad VARCHAR(100) COMMENT 'Facultad a la que pertenece (Ingeniería, Educación, Medicina, etc.)',
    carrera VARCHAR(150) COMMENT 'Carrera académica del usuario',
    anio_actual INT COMMENT 'Año de estudio actual (1-5)',
    tiene_multa BOOLEAN DEFAULT FALSE COMMENT 'Indica si tiene multa activa',
    monto_multa DECIMAL(10,2) DEFAULT 0 COMMENT 'Monto de la multa en USD',
    
    -- Índices para mejorar rendimiento
    INDEX idx_email (email),
    INDEX idx_rut (rut),
    INDEX idx_tipo (tipo_usuario),
    INDEX idx_facultad (facultad),
    INDEX idx_tiene_multa (tiene_multa)
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
    especialidad VARCHAR(30) COMMENT 'Especialidad: INGENIERIA, EDUCACION, MEDICINA, VETERINARIA, CIENCIAS_COMERCIALES, TURISMO, DERECHO, GENERAL',
    codigo_categoria VARCHAR(20) COMMENT 'Código de categoría según especialidad (ej: ING-001, MED-005)',
    nivel_recomendado VARCHAR(20) COMMENT 'Nivel académico recomendado: BASICO, INTERMEDIO, AVANZADO',
    
    -- Restricciones
    CHECK (cantidad_disponible >= 0),
    CHECK (cantidad_disponible <= cantidad_total),
    CHECK (anio_publicacion > 1000 AND anio_publicacion <= YEAR(CURRENT_DATE) + 1),
    
    -- Índices
    INDEX idx_titulo (titulo),
    INDEX idx_autor (autor),
    INDEX idx_isbn (isbn),
    INDEX idx_categoria (categoria),
    INDEX idx_especialidad (especialidad),
    INDEX idx_nivel (nivel_recomendado)
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

-- USUARIOS: Diversidad de carreras y años
INSERT INTO usuarios (rut, nombre, email, password, tipo_usuario, facultad, carrera, anio_actual, tiene_multa, monto_multa, activo) VALUES
-- ADMINISTRATIVOS
('12345678-9', 'Administrador Sistema', 'admin@untec.cl', 'admin123', 'ADMIN', NULL, NULL, NULL, FALSE, 0, TRUE),

-- FACULTAD DE INGENIERÍA
('98765432-1', 'Juan Pérez González', 'juan.perez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería en Informática', 2, FALSE, 0, TRUE),
('87654321-0', 'Carlos Morales López', 'carlos.morales@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería Civil', 3, FALSE, 0, TRUE),
('76543210-9', 'Roberto Sánchez Vargas', 'roberto.sanchez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería Industrial', 1, TRUE, 45.50, TRUE),
('65432109-8', 'Miguel Rodríguez Flores', 'miguel.rodriguez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ingeniería', 'Ingeniería en Informática', 4, FALSE, 0, TRUE),

-- FACULTAD DE EDUCACIÓN
('54321098-7', 'Ana García Martínez', 'ana.garcia@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Educación', 'Pedagogía en Matemática', 2, FALSE, 0, TRUE),
('43210987-6', 'Laura Fernández Silva', 'laura.fernandez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Educación', 'Pedagogía en Lenguaje y Comunicación', 3, FALSE, 0, TRUE),
('32109876-5', 'Patricia Núñez Díaz', 'patricia.nunez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Educación', 'Pedagogía en Historia', 1, FALSE, 0, TRUE),

-- FACULTAD DE MEDICINA Y VETERINARIA
('21098765-4', 'Dr. Fernando Castillo Méndez', 'fernando.castillo@untec.cl', 'profesional123', 'ESTUDIANTE', 'Medicina', 'Medicina', 5, FALSE, 0, TRUE),
('10987654-3', 'Verónica López Rojas', 'veronica.lopez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Veterinaria', 'Medicina Veterinaria', 2, FALSE, 0, TRUE),

-- FACULTAD DE CIENCIAS COMERCIALES
('19876543-2', 'Jorge Ramírez Contreras', 'jorge.ramirez@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ciencias Comerciales', 'Administración de Empresas', 3, FALSE, 0, TRUE),
('28765432-1', 'Sandra Pacheco González', 'sandra.pacheco@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Ciencias Comerciales', 'Contabilidad Auditoría', 4, FALSE, 0, TRUE),

-- FACULTAD DE TURISMO Y DERECHO
('37654321-0', 'Marcelo Quispe Álvarez', 'marcelo.quispe@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Turismo', 'Administración de Turismo Sostenible', 2, FALSE, 0, TRUE),
('46543210-9', 'Rodrigo Vera Acuña', 'rodrigo.vera@untec.cl', 'estudiante123', 'ESTUDIANTE', 'Derecho', 'Derecho', 3, FALSE, 0, TRUE),

-- PROFESORES
('11223344-5', 'María Silva Torres', 'maria.silva@untec.cl', 'profesor123', 'PROFESOR', 'Ingeniería', 'Ingeniería en Informática', NULL, FALSE, 0, TRUE),
('55667788-9', 'Dr. Andrés González Riquelme', 'andres.gonzalez@untec.cl', 'profesor123', 'PROFESOR', 'Medicina', 'Medicina', NULL, FALSE, 0, TRUE);

-- LIBROS: Por especialidad con códigos y niveles diversos
INSERT INTO libros (isbn, titulo, autor, editorial, anio_publicacion, categoria, descripcion, cantidad_total, cantidad_disponible, ubicacion, especialidad, codigo_categoria, nivel_recomendado) VALUES
-- INGENIERÍA - PROGRAMACIÓN Y DESARROLLO
('9780134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Programación', 'Guía esencial para programadores Java con las mejores prácticas actualizadas', 3, 3, 'Estante A1', 'INGENIERIA', 'ING-001', 'INTERMEDIO'),
('9780596009205', 'Head First Design Patterns', 'Eric Freeman, Elisabeth Robson', 'O''Reilly Media', 2004, 'Programación', 'Introducción visual a patrones de diseño en programación orientada a objetos', 2, 2, 'Estante A2', 'INGENIERIA', 'ING-002', 'BASICO'),
('9780132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 'Programación', 'Manual de buenas prácticas para escribir código limpio y mantenible', 5, 5, 'Estante A3', 'INGENIERIA', 'ING-003', 'INTERMEDIO'),
('9781617294945', 'Spring in Action', 'Craig Walls', 'Manning Publications', 2018, 'Frameworks', 'Desarrollo de aplicaciones empresariales con Spring Framework', 2, 2, 'Estante B1', 'INGENIERIA', 'ING-004', 'AVANZADO'),
('9781491950296', 'Learning SQL', 'Alan Beaulieu', 'O''Reilly Media', 2020, 'Bases de Datos', 'Introducción completa al lenguaje SQL y diseño de bases de datos', 4, 4, 'Estante C1', 'INGENIERIA', 'ING-005', 'BASICO'),

-- INGENIERÍA - PARA INGENIERÍA CIVIL E INDUSTRIAL
('9780073352724', 'Estática y Dinámica de Estructuras', 'R.C. Hibbeler', 'Pearson', 2016, 'Ingeniería Civil', 'Análisis completo de estructuras y fuerzas construcción', 3, 3, 'Estante D1', 'INGENIERIA', 'ING-006', 'INTERMEDIO'),
('9780470619957', 'Principios de Ingeniería Industrial', 'Benjamin Niebel', 'Wiley', 2014, 'Ingeniería Industrial', 'Optimización de procesos y recursos en sistemas de manufactura', 2, 2, 'Estante D2', 'INGENIERIA', 'ING-007', 'AVANZADO'),

-- EDUCACIÓN - PEDAGOGÍA Y DIDÁCTICA
('9788495007529', 'Pedagogía Diferenciada', 'Michel Legrand', 'Narcea', 2012, 'Educación', 'Estrategias de enseñanza adaptadas a diferentes estilos de aprendizaje', 3, 3, 'Estante E1', 'EDUCACION', 'EDU-001', 'BASICO'),
('9788427712430', 'Didáctica de las Matemáticas', 'Chamorro, M.C.', 'Pearson', 2003, 'Matemáticas', 'Metodologías efectivas para la enseñanza de matemática', 4, 3, 'Estante E2', 'EDUCACION', 'EDU-002', 'INTERMEDIO'),
('9788428325790', 'Historia de la Educación', 'Antonio Viñao Frago', 'Sourcebook', 2010, 'Historia Educativa', 'Evolución de sistemas educativos a nivel mundial', 2, 2, 'Estante E3', 'EDUCACION', 'EDU-003', 'BASICO'),

-- MEDICINA Y CIENCIAS DE LA SALUD
('9781496355379', 'Anatomía Humana de Gray', 'Richard Drake, A. Wayne Vogl, Adam Mitchell', 'Elsevier', 2020, 'Medicina', 'Referencia autorizada de anatomía humana con ilustraciones detalladas', 2, 2, 'Estante F1', 'MEDICINA', 'MED-001', 'INTERMEDIO'),
('9781455728572', 'Fisiología Médica de Guyton y Hall', 'John E. Hall', 'Elsevier', 2016, 'Medicina', 'Comprensión integral del funcionamiento del cuerpo humano', 1, 1, 'Estante F2', 'MEDICINA', 'MED-002', 'AVANZADO'),
('9786073215435', 'Fundamentos de Nutrición', 'Margaret McWilliams', 'Cengage', 2018, 'Nutrición', 'Estudio de nutrientes y su impacto en la salud humana', 3, 3, 'Estante F3', 'MEDICINA', 'MED-003', 'BASICO'),

-- VETERINARIA
('9788485732296', 'Anatomía Veterinaria', 'König, Klaus-Dieter', 'Medica Panamericana', 2005, 'Veterinaria', 'Estructura anatómica de animales domésticos', 2, 2, 'Estante G1', 'VETERINARIA', 'VET-001', 'INTERMEDIO'),
('9789706558939', 'Patología Veterinaria', 'McGavin, Carlton L.', 'Mosby Elsevier', 2007, 'Veterinaria', 'Estudio de enfermedades en animales', 1, 1, 'Estante G2', 'VETERINARIA', 'VET-002', 'AVANZADO'),

-- CIENCIAS COMERCIALES
('9788448197742', 'Administración Estratégica', 'Michael A. Hitt, R. Duane Ireland, Robert E. Hoskisson', 'Cengage', 2015, 'Administración', 'Gestión integral y visión estratégica de empresas', 3, 3, 'Estante H1', 'CIENCIAS_COMERCIALES', 'COM-001', 'INTERMEDIO'),
('9788497324670', 'Contabilidad Financiera', 'Pedro Flores Estigarribia', 'Paraninfo', 2014, 'Contabilidad', 'Fundamentos de contabilidad y análisis financiero', 4, 3, 'Estante H2', 'CIENCIAS_COMERCIALES', 'COM-002', 'BASICO'),
('9788416771127', 'Marketing Digital', 'Javier Alonso Rivas', 'Esic Editorial', 2019, 'Marketing', 'Estrategias modernas en marketing y publicidad digital', 2, 2, 'Estante H3', 'CIENCIAS_COMERCIALES', 'COM-003', 'INTERMEDIO'),

-- TURISMO
('9780367364625', 'Gestión del Turismo Sostenible', 'David Weaver', 'Routledge', 2019, 'Turismo', 'Turismo responsable y sostenible con comunidades locales', 2, 2, 'Estante I1', 'TURISMO', 'TUR-001', 'INTERMEDIO'),
('9788490522134', 'Geografía del Turismo', 'David Harrison', 'Síntesis', 2012, 'Turismo', 'Impacto geográfico y cultural del turismo mundial', 1, 1, 'Estante I2', 'TURISMO', 'TUR-002', 'BASICO'),

-- DERECHO
('9789568365561', 'Derecho Constitucional', 'Raúl Bertelsen Repetto', 'Jurídica', 2005, 'Derecho', 'Teoría y práctica del derecho constitucional chileno', 2, 2, 'Estante J1', 'DERECHO', 'DER-001', 'INTERMEDIO'),
('9789569254886', 'Derecho Civil Patrimonial', 'Hernán Corral Talciani', 'Thomson Reuters', 2015, 'Derecho', 'Teoría del patrimonio y obligaciones civiles', 3, 2, 'Estante J2', 'DERECHO', 'DER-002', 'AVANZADO'),

-- LIBROS GENERALES DE INTERÉS
('9788499083452', 'Sapiens', 'Yuval Noah Harari', 'Debate', 2014, 'Historia', 'Historia de la humanidad desde perspectiva evolucionaria y antropológica', 5, 4, 'Estante K1', 'GENERAL', 'GEN-001', 'BASICO'),
('9789501278331', 'El Poder del Hábito', 'Charles Duhigg', 'Vergara', 2012, 'Autoayuda', 'Cómo formar hábitos efectivos para mejorar vida personal y laboral', 3, 3, 'Estante K2', 'GENERAL', 'GEN-002', 'BASICO');

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
