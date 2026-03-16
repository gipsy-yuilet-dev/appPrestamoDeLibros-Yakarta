package cl.untec.biblioteca.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cl.untec.biblioteca.dao.UsuarioDAO;
import cl.untec.biblioteca.model.Usuario;
import cl.untec.biblioteca.util.DatabaseConnection;

/**
 * Implementación de UsuarioDAO usando JDBC.
 * 
 * <p>Proporciona acceso a datos de usuarios incluyendo autenticación básica.
 * <b>NOTA IMPORTANTE:</b> Este código es educativo y usa contraseñas en texto plano.
 * En producción deberías usar encriptación (BCrypt, PBKDF2, etc.).</p>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    // ========== Constantes SQL ==========
    
    private static final String SQL_INSERT =
            "INSERT INTO usuarios (rut, nombre, email, password, tipo_usuario, activo) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM usuarios WHERE id = ? AND activo = TRUE";
    
    private static final String SQL_SELECT_BY_RUT =
            "SELECT * FROM usuarios WHERE rut = ? AND activo = TRUE";
    
    private static final String SQL_SELECT_BY_EMAIL =
            "SELECT * FROM usuarios WHERE email = ? AND activo = TRUE";
    
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM usuarios WHERE activo = TRUE ORDER BY nombre";
    
    private static final String SQL_SELECT_BY_TIPO =
            "SELECT * FROM usuarios WHERE tipo_usuario = ? AND activo = TRUE ORDER BY nombre";
    
    private static final String SQL_AUTHENTICATE =
            "SELECT * FROM usuarios WHERE email = ? AND password = ? AND activo = TRUE";
    
    private static final String SQL_UPDATE =
            "UPDATE usuarios SET rut = ?, nombre = ?, email = ?, password = ?, " +
            "tipo_usuario = ? WHERE id = ?";
    
    private static final String SQL_DELETE =
            "UPDATE usuarios SET activo = FALSE WHERE id = ?";
    
    private static final String SQL_EXISTS_EMAIL =
            "SELECT COUNT(*) FROM usuarios WHERE email = ? AND activo = TRUE";
    
    private static final String SQL_EXISTS_RUT =
            "SELECT COUNT(*) FROM usuarios WHERE rut = ? AND activo = TRUE";
    
    // ========== Implementación de métodos ==========
    
    @Override
    public Long crear(Usuario usuario) {
        validarUsuario(usuario);
        
        // Verificar que email y RUT no existan
        if (existeEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (existeRut(usuario.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            setPreparedStatementFromUsuario(ps, usuario);
            ps.setBoolean(6, true); // activo
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new RuntimeException("No se pudo crear el usuario");
            }
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new RuntimeException("No se pudo obtener el ID generado");
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Usuario obtenerPorId(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public Usuario obtenerPorRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("RUT inválido");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_RUT)) {
            
            ps.setString(1, rut.trim());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por RUT: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public Usuario obtenerPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            
            ps.setString(1, email.trim().toLowerCase());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por email: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuarios: " + e.getMessage(), e);
        }
        
        return usuarios;
    }
    
    @Override
    public List<Usuario> obtenerPorTipo(Usuario.TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("Tipo de usuario inválido");
        }
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_TIPO)) {
            
            ps.setString(1, tipoUsuario.name());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapResultSetToUsuario(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuarios por tipo: " + e.getMessage(), e);
        }
        
        return usuarios;
    }
    
    @Override
    public Usuario autenticar(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.isEmpty()) {
            return null;
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_AUTHENTICATE)) {
            
            ps.setString(1, email.trim().toLowerCase());
            ps.setString(2, password); // En producción: comparar hash
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al autenticar usuario: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public boolean actualizar(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Usuario o ID inválido");
        }
        
        validarUsuario(usuario);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            setPreparedStatementFromUsuario(ps, usuario);
            ps.setLong(6, usuario.getId());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
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
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean existeEmail(String email) {
        return existe(SQL_EXISTS_EMAIL, email);
    }
    
    @Override
    public boolean existeRut(String rut) {
        return existe(SQL_EXISTS_RUT, rut);
    }
    
    // ========== Métodos Helper ==========
    
    /**
     * Mapea un ResultSet a un objeto Usuario.
     */
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setRut(rs.getString("rut"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPassword(rs.getString("password")); // En producción: no exponer
        usuario.setTipoUsuario(Usuario.TipoUsuario.valueOf(rs.getString("tipo_usuario")));
        
        Timestamp timestamp = rs.getTimestamp("fecha_registro");
        if (timestamp != null) {
            usuario.setFechaRegistro(timestamp.toLocalDateTime());
        }
        
        usuario.setActivo(rs.getBoolean("activo"));
        
        return usuario;
    }
    
    /**
     * Establece los parámetros del PreparedStatement desde un objeto Usuario.
     */
    private void setPreparedStatementFromUsuario(PreparedStatement ps, Usuario usuario) throws SQLException {
        ps.setString(1, usuario.getRut());
        ps.setString(2, usuario.getNombre());
        ps.setString(3, usuario.getEmail().toLowerCase());
        ps.setString(4, usuario.getPassword()); // En producción: hash
        ps.setString(5, usuario.getTipoUsuario().name());
    }
    
    /**
     * Valida que un usuario tenga los datos mínimos requeridos.
     */
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        if (usuario.getRut() == null || usuario.getRut().trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT es obligatorio");
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (usuario.getTipoUsuario() == null) {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio");
        }
    }
    
    /**
     * Método genérico para verificar existencia de un valor.
     */
    private boolean existe(String sql, String valor) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, valor);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia: " + e.getMessage(), e);
        }
        
        return false;
    }
}
