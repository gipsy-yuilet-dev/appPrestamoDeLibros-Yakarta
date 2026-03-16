package cl.untec.biblioteca.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cl.untec.biblioteca.dao.LibroDAO;
import cl.untec.biblioteca.model.Libro;
import cl.untec.biblioteca.util.DatabaseConnection;

/**
 * Implementación de LibroDAO usando JDBC.
 * 
 * <p>Esta clase proporciona acceso a datos de libros usando JDBC puro,
 * siguiendo buenas prácticas como PreparedStatements, try-with-resources
 * y manejo adecuado de excepciones.</p>
 * 
 * <h2>Características:</h2>
 * <ul>
 *   <li>Usa PreparedStatements para prevenir SQL Injection</li>
 *   <li>try-with-resources para gestión automática de recursos</li>
 *   <li>Métodos helper para reutilización de código</li>
 *   <li>Manejo centralizado de excepciones</li>
 * </ul>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class LibroDAOImpl implements LibroDAO {
    
    // ========== Constantes SQL ==========
    
    private static final String SQL_INSERT =
            "INSERT INTO libros (isbn, titulo, autor, editorial, anio_publicacion, " +
            "categoria, descripcion, cantidad_total, cantidad_disponible, ubicacion, especialidad, codigo_categoria, nivel_recomendado, activo) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM libros WHERE id = ? AND activo = TRUE";
    
    private static final String SQL_SELECT_BY_ISBN =
            "SELECT * FROM libros WHERE isbn = ? AND activo = TRUE";
    
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM libros WHERE activo = TRUE ORDER BY titulo";
    
    private static final String SQL_SELECT_BY_TITULO =
            "SELECT * FROM libros WHERE LOWER(titulo) LIKE LOWER(?) AND activo = TRUE ORDER BY titulo";
    
    private static final String SQL_SELECT_BY_AUTOR =
            "SELECT * FROM libros WHERE LOWER(autor) LIKE LOWER(?) AND activo = TRUE ORDER BY autor, titulo";
    
    private static final String SQL_SELECT_BY_CATEGORIA =
            "SELECT * FROM libros WHERE categoria = ? AND activo = TRUE ORDER BY titulo";
    
    private static final String SQL_SELECT_BY_ESPECIALIDAD =
            "SELECT * FROM libros WHERE especialidad = ? AND activo = TRUE ORDER BY titulo";
    
    private static final String SQL_SELECT_DISPONIBLES =
            "SELECT * FROM libros WHERE cantidad_disponible > 0 AND activo = TRUE ORDER BY titulo";
    
    private static final String SQL_UPDATE =
            "UPDATE libros SET isbn = ?, titulo = ?, autor = ?, editorial = ?, " +
            "anio_publicacion = ?, categoria = ?, descripcion = ?, cantidad_total = ?, " +
            "cantidad_disponible = ?, ubicacion = ?, especialidad = ?, codigo_categoria = ?, nivel_recomendado = ? WHERE id = ?";
    
    private static final String SQL_DELETE =
            "UPDATE libros SET activo = FALSE WHERE id = ?";
    
    private static final String SQL_COUNT =
            "SELECT COUNT(*) FROM libros WHERE activo = TRUE";
    
    // ========== Implementación de métodos ==========
    
    @Override
    public Long crear(Libro libro) {
        validarLibro(libro);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            setPreparedStatementFromLibro(ps, libro);
            ps.setBoolean(14, true); // activo
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new RuntimeException("No se pudo crear el libro");
            }
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new RuntimeException("No se pudo obtener el ID generado");
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear libro: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Libro obtenerPorId(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLibro(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener libro: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public Libro obtenerPorIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN inválido");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ISBN)) {
            
            ps.setString(1, isbn.trim());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLibro(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener libro por ISBN: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                libros.add(mapResultSetToLibro(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener libros: " + e.getMessage(), e);
        }
        
        return libros;
    }
    
    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        return buscarPorCampo(SQL_SELECT_BY_TITULO, "%" + titulo + "%");
    }
    
    @Override
   public List<Libro> buscarPorAutor(String autor) {
        return buscarPorCampo(SQL_SELECT_BY_AUTOR, "%" + autor + "%");
    }
    
    @Override
    public List<Libro> buscarPorCategoria(String categoria) {
        return buscarPorCampo(SQL_SELECT_BY_CATEGORIA, categoria);
    }
    
    @Override
    public List<Libro> obtenerDisponibles() {
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_DISPONIBLES);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                libros.add(mapResultSetToLibro(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener libros disponibles: " + e.getMessage(), e);
        }
        
        return libros;
    }
    
    @Override
    public boolean actualizar(Libro libro) {
        if (libro == null || libro.getId() == null) {
            throw new IllegalArgumentException("Libro o ID inválido");
        }
        
        validarLibro(libro);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            setPreparedStatementFromLibro(ps, libro);
            ps.setLong(14, libro.getId());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminar(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setLong(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar libro: " + e.getMessage(), e);
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
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar libros: " + e.getMessage(), e);
        }
        
        return 0;
    }
    
    // ========== Métodos Helper (privados) para evitar duplicación ==========
    
    /**
     * Mapea un ResultSet a un objeto Libro.
     * Extrae todos los campos del ResultSet y crea una instancia de Libro.
     */
    private Libro mapResultSetToLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getLong("id"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setCategoria(rs.getString("categoria"));
        libro.setDescripcion(rs.getString("descripcion"));
        libro.setCantidadTotal(rs.getInt("cantidad_total"));
        libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        libro.setUbicacion(rs.getString("ubicacion"));
        
        Timestamp timestamp = rs.getTimestamp("fecha_registro");
        if (timestamp != null) {
            libro.setFechaRegistro(timestamp.toLocalDateTime());
        }
        
        libro.setActivo(rs.getBoolean("activo"));
        
        // Nuevos campos
        String especialidad = rs.getString("especialidad");
        if (especialidad != null && !especialidad.isEmpty()) {
            libro.setEspecialidad(Libro.Especialidad.valueOf(especialidad));
        }
        libro.setCodigoCategoria(rs.getString("codigo_categoria"));
        
        String nivelRecomendado = rs.getString("nivel_recomendado");
        if (nivelRecomendado != null && !nivelRecomendado.isEmpty()) {
            libro.setNivelRecomendado(Libro.NivelRecomendado.valueOf(nivelRecomendado));
        }
        
        return libro;
    }
    
    /**
     * Establece los parámetros del PreparedStatement desde un objeto Libro.
     * Centraliza la lógica para evitar duplicación en insert/update.
     */
    private void setPreparedStatementFromLibro(PreparedStatement ps, Libro libro) throws SQLException {
        ps.setString(1, libro.getIsbn());
        ps.setString(2, libro.getTitulo());
        ps.setString(3, libro.getAutor());
        ps.setString(4, libro.getEditorial());
        ps.setObject(5, libro.getAnioPublicacion());
        ps.setString(6, libro.getCategoria());
        ps.setString(7, libro.getDescripcion());
        ps.setInt(8, libro.getCantidadTotal());
        ps.setInt(9, libro.getCantidadDisponible());
        ps.setString(10, libro.getUbicacion());
        ps.setString(11, libro.getEspecialidad() != null ? libro.getEspecialidad().name() : null);
        ps.setString(12, libro.getCodigoCategoria());
        ps.setString(13, libro.getNivelRecomendado() != null ? libro.getNivelRecomendado().name() : null);
    }
    
    /**
     * Valida que un libro tenga los datos mínimos requeridos.
     */
    private void validarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN es obligatorio");
        }
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor es obligatorio");
        }
    }
    
    /**
     * Método genérico para búsquedas por un campo específico.
     * Evita duplicación de código en buscarPorTitulo, buscarPorAutor, etc.
     */
    private List<Libro> buscarPorCampo(String sql, String valor) {
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, valor);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapResultSetToLibro(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en búsqueda: " + e.getMessage(), e);
        }
        
        return libros;
    }
}
