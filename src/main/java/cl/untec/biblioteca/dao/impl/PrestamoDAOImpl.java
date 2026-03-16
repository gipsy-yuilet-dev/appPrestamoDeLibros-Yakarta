package cl.untec.biblioteca.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cl.untec.biblioteca.dao.PrestamoDAO;
import cl.untec.biblioteca.model.Libro;
import cl.untec.biblioteca.model.Prestamo;
import cl.untec.biblioteca.model.Prestamo.EstadoPrestamo;
import cl.untec.biblioteca.model.Usuario;
import cl.untec.biblioteca.util.DatabaseConnection;

/**
 * Implementación de PrestamoDAO usando JDBC.
 * 
 * <p>Esta clase proporciona acceso a datos de préstamos usando JDBC puro,
 * siguiendo buenas prácticas como PreparedStatements, try-with-resources
 * y manejo adecuado de excepciones.</p>
 * 
 * <h2>Características especiales:</h2>
 * <ul>
 *   <li>Transacciones para operaciones críticas (devolver libro)</li>
 *   <li>JOINs para obtener información completa de usuario y libro</li>
 *   <li>Validaciones de integridad referencial</li>
 *   <li>Actualización automática de disponibilidad de libros</li>
 * </ul>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class PrestamoDAOImpl implements PrestamoDAO {
    
    // ========== Constantes SQL ==========
    
    private static final String SQL_INSERT =
            "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo, fecha_devolucion_esperada, " +
            "fecha_devolucion_real, estado, observaciones) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM prestamos WHERE id = ?";
    
    private static final String SQL_SELECT_BY_ID_CON_DETALLES =
            "SELECT p.*, " +
            "u.id as usuario_id, u.rut, u.nombre as usuario_nombre, u.email, u.tipo_usuario, " +
            "l.id as libro_id, l.isbn, l.titulo, l.autor, l.editorial, l.categoria, " +
            "l.cantidad_total, l.cantidad_disponible " +
            "FROM prestamos p " +
            "INNER JOIN usuarios u ON p.id_usuario = u.id " +
            "INNER JOIN libros l ON p.id_libro = l.id " +
            "WHERE p.id = ?";
    
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM prestamos ORDER BY fecha_prestamo DESC";
    
    private static final String SQL_SELECT_BY_USUARIO =
            "SELECT * FROM prestamos WHERE id_usuario = ? ORDER BY fecha_prestamo DESC";
    
    private static final String SQL_SELECT_BY_LIBRO =
            "SELECT * FROM prestamos WHERE id_libro = ? ORDER BY fecha_prestamo DESC";
    
    private static final String SQL_SELECT_ACTIVOS =
            "SELECT * FROM prestamos WHERE estado = 'ACTIVO' AND fecha_devolucion_real IS NULL " +
            "ORDER BY fecha_devolucion_esperada";
    
    private static final String SQL_SELECT_ACTIVOS_CON_DETALLES =
            "SELECT p.*, " +
            "u.id as usuario_id, u.rut, u.nombre as usuario_nombre, u.email, u.tipo_usuario, " +
            "l.id as libro_id, l.isbn, l.titulo, l.autor, l.editorial, l.categoria, " +
            "l.cantidad_total, l.cantidad_disponible " +
            "FROM prestamos p " +
            "INNER JOIN usuarios u ON p.id_usuario = u.id " +
            "INNER JOIN libros l ON p.id_libro = l.id " +
            "WHERE p.estado = 'ACTIVO' AND p.fecha_devolucion_real IS NULL " +
            "ORDER BY p.fecha_devolucion_esperada";
    
    private static final String SQL_SELECT_VENCIDOS =
            "SELECT * FROM prestamos " +
            "WHERE fecha_devolucion_esperada < CURRENT_DATE " +
            "AND fecha_devolucion_real IS NULL " +
            "AND estado IN ('ACTIVO', 'VENCIDO') " +
            "ORDER BY fecha_devolucion_esperada";
    
    private static final String SQL_SELECT_BY_ESTADO =
            "SELECT * FROM prestamos WHERE estado = ? ORDER BY fecha_prestamo DESC";
    
    private static final String SQL_SELECT_BY_RANGO_VENCIMIENTO =
            "SELECT * FROM prestamos " +
            "WHERE fecha_devolucion_esperada BETWEEN ? AND ? " +
            "AND fecha_devolucion_real IS NULL " +
            "ORDER BY fecha_devolucion_esperada";
    
    private static final String SQL_UPDATE_DEVOLUCION =
            "UPDATE prestamos SET fecha_devolucion_real = ?, estado = 'DEVUELTO', observaciones = ? " +
            "WHERE id = ? AND fecha_devolucion_real IS NULL";
    
    private static final String SQL_UPDATE_ESTADO =
            "UPDATE prestamos SET estado = ? WHERE id = ?";
    
    private static final String SQL_UPDATE =
            "UPDATE prestamos SET id_usuario = ?, id_libro = ?, fecha_devolucion_esperada = ?, " +
            "fecha_devolucion_real = ?, estado = ?, observaciones = ? WHERE id = ?";
    
    private static final String SQL_DELETE =
            "DELETE FROM prestamos WHERE id = ?";
    
    private static final String SQL_COUNT =
            "SELECT COUNT(*) FROM prestamos";
    
    private static final String SQL_COUNT_ACTIVOS_POR_USUARIO =
            "SELECT COUNT(*) FROM prestamos WHERE id_usuario = ? AND estado = 'ACTIVO' " +
            "AND fecha_devolucion_real IS NULL";
    
    private static final String SQL_COUNT_ACTIVOS_POR_LIBRO =
            "SELECT COUNT(*) FROM prestamos WHERE id_libro = ? AND estado = 'ACTIVO' " +
            "AND fecha_devolucion_real IS NULL";
    
    private static final String SQL_HAS_VENCIDOS =
            "SELECT COUNT(*) FROM prestamos WHERE id_usuario = ? " +
            "AND fecha_devolucion_esperada < CURRENT_DATE " +
            "AND fecha_devolucion_real IS NULL";
    
    private static final String SQL_INCREMENT_LIBRO_DISPONIBLE =
            "UPDATE libros SET cantidad_disponible = cantidad_disponible + 1 WHERE id = ?";
    
    private static final String SQL_DECREMENT_LIBRO_DISPONIBLE =
            "UPDATE libros SET cantidad_disponible = cantidad_disponible - 1 " +
            "WHERE id = ? AND cantidad_disponible > 0";
    
    // ========== Implementación de métodos CRUD ==========
    
    @Override
    public Long crear(Prestamo prestamo) {
        validarPrestamo(prestamo);
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            // Iniciar transacción
            conn.setAutoCommit(false);
            
            // 1. Insertar préstamo
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            setPreparedStatementFromPrestamo(ps, prestamo);
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No se pudo crear el préstamo");
            }
            
            Long prestamoId = null;
            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                prestamoId = generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("No se pudo obtener el ID generado");
            }
            
            // 2. Decrementar cantidad disponible del libro
            ps.close();
            ps = conn.prepareStatement(SQL_DECREMENT_LIBRO_DISPONIBLE);
            ps.setLong(1, prestamo.getIdLibro());
            int updated = ps.executeUpdate();
            
            if (updated == 0) {
                // No hay copias disponibles
                throw new RuntimeException("No hay copias disponibles del libro");
            }
            
            // Commit transacción
            conn.commit();
            return prestamoId;
            
        } catch (SQLException e) {
            // Rollback en caso de error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Error al hacer rollback", ex);
                }
            }
            throw new RuntimeException("Error al crear préstamo: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, ps, generatedKeys);
        }
    }
    
    @Override
    public Prestamo obtenerPorId(Long id) {
        validarId(id);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrestamo(rs);
                }
                return null;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamo por ID: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Prestamo obtenerPorIdConDetalles(Long id) {
        validarId(id);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID_CON_DETALLES)) {
            
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrestamoConDetalles(rs);
                }
                return null;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamo con detalles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerTodos() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            
            return mapResultSetToPrestamoList(rs);
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los préstamos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerPorUsuario(Long idUsuario) {
        validarId(idUsuario);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_USUARIO)) {
            
            ps.setLong(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                return mapResultSetToPrestamoList(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos por usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerPorLibro(Long idLibro) {
        validarId(idLibro);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_LIBRO)) {
            
            ps.setLong(1, idLibro);
            
            try (ResultSet rs = ps.executeQuery()) {
                return mapResultSetToPrestamoList(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos por libro: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerActivos() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ACTIVOS);
             ResultSet rs = ps.executeQuery()) {
            
            return mapResultSetToPrestamoList(rs);
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos activos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerActivosConDetalles() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ACTIVOS_CON_DETALLES);
             ResultSet rs = ps.executeQuery()) {
            
            List<Prestamo> prestamos = new ArrayList<>();
            while (rs.next()) {
                prestamos.add(mapResultSetToPrestamoConDetalles(rs));
            }
            return prestamos;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos activos con detalles: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerVencidos() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_VENCIDOS);
             ResultSet rs = ps.executeQuery()) {
            
            return mapResultSetToPrestamoList(rs);
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos vencidos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerPorEstado(EstadoPrestamo estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ESTADO)) {
            
            ps.setString(1, estado.name());
            
            try (ResultSet rs = ps.executeQuery()) {
                return mapResultSetToPrestamoList(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos por estado: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Prestamo> obtenerPorRangoVencimiento(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser null");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_RANGO_VENCIMIENTO)) {
            
            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            
            try (ResultSet rs = ps.executeQuery()) {
                return mapResultSetToPrestamoList(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener préstamos por rango: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean devolverLibro(Long idPrestamo) {
        return devolverLibro(idPrestamo, null);
    }
    
    @Override
    public boolean devolverLibro(Long idPrestamo, String observaciones) {
        validarId(idPrestamo);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            // Iniciar transacción
            conn.setAutoCommit(false);
            
            // 1. Obtener información del préstamo
            Prestamo prestamo = obtenerPorId(idPrestamo);
            if (prestamo == null) {
                return false;
            }
            
            // 2. Actualizar préstamo como devuelto
            ps = conn.prepareStatement(SQL_UPDATE_DEVOLUCION);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, observaciones);
            ps.setLong(3, idPrestamo);
            
            int updated = ps.executeUpdate();
            if (updated == 0) {
                conn.rollback();
                return false;
            }
            
            // 3. Incrementar cantidad disponible del libro
            ps.close();
            ps = conn.prepareStatement(SQL_INCREMENT_LIBRO_DISPONIBLE);
            ps.setLong(1, prestamo.getIdLibro());
            ps.executeUpdate();
            
            // Commit transacción
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            // Rollback en caso de error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Error al hacer rollback", ex);
                }
            }
            throw new RuntimeException("Error al devolver libro: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, ps, null);
        }
    }
    
    @Override
    public boolean actualizarEstado(Long idPrestamo, EstadoPrestamo nuevoEstado) {
        validarId(idPrestamo);
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser null");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ESTADO)) {
            
            ps.setString(1, nuevoEstado.name());
            ps.setLong(2, idPrestamo);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean actualizar(Prestamo prestamo) {
        validarPrestamo(prestamo);
        
        if (prestamo.getId() == null || prestamo.getId() <= 0) {
            throw new IllegalArgumentException("El préstamo debe tener un ID válido para actualizar");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setLong(1, prestamo.getIdUsuario());
            ps.setLong(2, prestamo.getIdLibro());
            ps.setDate(3, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
            ps.setTimestamp(4, prestamo.getFechaDevolucionReal() != null ? 
                            Timestamp.valueOf(prestamo.getFechaDevolucionReal()) : null);
            ps.setString(5, prestamo.getEstado().name());
            ps.setString(6, prestamo.getObservaciones());
            ps.setLong(7, prestamo.getId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar préstamo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminar(Long id) {
        validarId(id);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar préstamo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int contarTotal() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar préstamos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int contarActivosPorUsuario(Long idUsuario) {
        validarId(idUsuario);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_ACTIVOS_POR_USUARIO)) {
            
            ps.setLong(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar préstamos activos por usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int contarActivosPorLibro(Long idLibro) {
        validarId(idLibro);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_ACTIVOS_POR_LIBRO)) {
            
            ps.setLong(1, idLibro);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar préstamos activos por libro: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean tieneVencidos(Long idUsuario) {
        validarId(idUsuario);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_HAS_VENCIDOS)) {
            
            ps.setLong(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar vencidos: " + e.getMessage(), e);
        }
    }
    
    // ========== Métodos helper privados ==========
    
    /**
     * Mapea un ResultSet a un objeto Prestamo.
     */
    private Prestamo mapResultSetToPrestamo(ResultSet rs) throws SQLException {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(rs.getLong("id"));
        prestamo.setIdUsuario(rs.getLong("id_usuario"));
        prestamo.setIdLibro(rs.getLong("id_libro"));
        
        Timestamp fechaPrestamo = rs.getTimestamp("fecha_prestamo");
        if (fechaPrestamo != null) {
            prestamo.setFechaPrestamo(fechaPrestamo.toLocalDateTime());
        }
        
        Date fechaDevEsperada = rs.getDate("fecha_devolucion_esperada");
        if (fechaDevEsperada != null) {
            prestamo.setFechaDevolucionEsperada(fechaDevEsperada.toLocalDate());
        }
        
        Timestamp fechaDevReal = rs.getTimestamp("fecha_devolucion_real");
        if (fechaDevReal != null) {
            prestamo.setFechaDevolucionReal(fechaDevReal.toLocalDateTime());
        }
        
        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            prestamo.setEstado(EstadoPrestamo.valueOf(estadoStr));
        }
        
        prestamo.setObservaciones(rs.getString("observaciones"));
        
        return prestamo;
    }
    
    /**
     * Mapea un ResultSet con JOIN a un objeto Prestamo con detalles de Usuario y Libro.
     */
    private Prestamo mapResultSetToPrestamoConDetalles(ResultSet rs) throws SQLException {
        // Mapear préstamo base
        Prestamo prestamo = new Prestamo();
        prestamo.setId(rs.getLong("id"));
        prestamo.setIdUsuario(rs.getLong("id_usuario"));
        prestamo.setIdLibro(rs.getLong("id_libro"));
        
        Timestamp fechaPrestamo = rs.getTimestamp("fecha_prestamo");
        if (fechaPrestamo != null) {
            prestamo.setFechaPrestamo(fechaPrestamo.toLocalDateTime());
        }
        
        Date fechaDevEsperada = rs.getDate("fecha_devolucion_esperada");
        if (fechaDevEsperada != null) {
            prestamo.setFechaDevolucionEsperada(fechaDevEsperada.toLocalDate());
        }
        
        Timestamp fechaDevReal = rs.getTimestamp("fecha_devolucion_real");
        if (fechaDevReal != null) {
            prestamo.setFechaDevolucionReal(fechaDevReal.toLocalDateTime());
        }
        
        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            prestamo.setEstado(EstadoPrestamo.valueOf(estadoStr));
        }
        
        prestamo.setObservaciones(rs.getString("observaciones"));
        
        // Mapear usuario
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("usuario_id"));
        usuario.setRut(rs.getString("rut"));
        usuario.setNombre(rs.getString("usuario_nombre"));
        usuario.setEmail(rs.getString("email"));
        String tipoUsuario = rs.getString("tipo_usuario");
        if (tipoUsuario != null) {
            usuario.setTipoUsuario(Usuario.TipoUsuario.valueOf(tipoUsuario));
        }
        prestamo.setUsuario(usuario);
        
        // Mapear libro
        Libro libro = new Libro();
        libro.setId(rs.getLong("libro_id"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setCategoria(rs.getString("categoria"));
        libro.setCantidadTotal(rs.getInt("cantidad_total"));
        libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        prestamo.setLibro(libro);
        
        return prestamo;
    }
    
    /**
     * Mapea un ResultSet completo a una lista de préstamos.
     */
    private List<Prestamo> mapResultSetToPrestamoList(ResultSet rs) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        while (rs.next()) {
            prestamos.add(mapResultSetToPrestamo(rs));
        }
        return prestamos;
    }
    
    /**
     * Setea los parámetros de un PreparedStatement desde un objeto Prestamo.
     */
    private void setPreparedStatementFromPrestamo(PreparedStatement ps, Prestamo prestamo) 
            throws SQLException {
        ps.setLong(1, prestamo.getIdUsuario());
        ps.setLong(2, prestamo.getIdLibro());
        ps.setTimestamp(3, Timestamp.valueOf(prestamo.getFechaPrestamo() != null ? 
                        prestamo.getFechaPrestamo() : LocalDateTime.now()));
        ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
        ps.setTimestamp(5, prestamo.getFechaDevolucionReal() != null ? 
                        Timestamp.valueOf(prestamo.getFechaDevolucionReal()) : null);
        ps.setString(6, prestamo.getEstado().name());
        ps.setString(7, prestamo.getObservaciones());
    }
    
    /**
     * Valida que un préstamo no sea null y tenga los campos obligatorios.
     */
    private void validarPrestamo(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no puede ser null");
        }
        if (prestamo.getIdUsuario() == null || prestamo.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser válido");
        }
        if (prestamo.getIdLibro() == null || prestamo.getIdLibro() <= 0) {
            throw new IllegalArgumentException("El ID de libro debe ser válido");
        }
        if (prestamo.getFechaDevolucionEsperada() == null) {
            throw new IllegalArgumentException("La fecha de devolución esperada no puede ser null");
        }
        if (prestamo.getEstado() == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
    }
    
    /**
     * Valida que un ID sea válido.
     */
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
    }
    
    /**
     * Cierra recursos JDBC de forma segura.
     */
    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar auto-commit
                conn.close();
            }
        } catch (SQLException e) {
            // Log pero no lanzar excepción
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
