package cl.untec.biblioteca.dao;

import java.util.List;

import cl.untec.biblioteca.model.Usuario;

/**
 * Interfaz para operaciones de acceso a datos de usuarios.
 * 
 * <p>Define el contrato para todas las operaciones CRUD y de autenticación
 * relacionadas con la entidad Usuario.</p>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 * @see Usuario
 */
public interface UsuarioDAO {
    
    /**
     * Crea un nuevo usuario en la base de datos.
     * 
     * @param usuario el usuario a crear (no puede ser null)
     * @return el ID generado para el usuario
     * @throws IllegalArgumentException si el usuario es null o tiene datos inválidos
     * @throws RuntimeException si el RUT o email ya existen
     */
    Long crear(Usuario usuario);
    
    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id el ID del usuario a buscar
     * @return el usuario encontrado, o null si no existe
     */
    Usuario obtenerPorId(Long id);
    
    /**
     * Obtiene un usuario por su RUT.
     * 
     * @param rut el RUT del usuario
     * @return el usuario encontrado, o null si no existe
     */
    Usuario obtenerPorRut(String rut);
    
    /**
     * Obtiene un usuario por su email.
     * 
     * @param email el email del usuario
     * @return el usuario encontrado, o null si no existe
     */
    Usuario obtenerPorEmail(String email);
    
    /**
     * Obtiene todos los usuarios activos.
     * 
     * @return lista de usuarios (nunca null)
     */
    List<Usuario> obtenerTodos();
    
    /**
     * Obtiene usuarios por tipo.
     * 
     * @param tipoUsuario el tipo de usuario (ESTUDIANTE, PROFESOR, ADMIN)
     * @return lista de usuarios de ese tipo (nunca null)
     */
    List<Usuario> obtenerPorTipo(Usuario.TipoUsuario tipoUsuario);
    
    /**
     * Autentica un usuario por email y contraseña.
     * 
     * <p><b>NOTA:</b> En producción, la contraseña debe estar encriptada.
     * Este método es educativo y usa texto plano.</p>
     * 
     * @param email el email del usuario
     * @param password la contraseña del usuario
     * @return el usuario si las credenciales son correctas, null en caso contrario
     */
    Usuario autenticar(String email, String password);
    
    /**
     * Actualiza un usuario existente.
     * 
     * @param usuario el usuario con los datos actualizados
     * @return true si se actualizó correctamente, false si no existe
     */
    boolean actualizar(Usuario usuario);
    
    /**
     * Elimina un usuario (borrado lógico).
     * 
     * @param id el ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    boolean eliminar(Long id);
    
    /**
     * Verifica si un email ya existe en la base de datos.
     * 
     * @param email el email a verificar
     * @return true si el email existe, false en caso contrario
     */
    boolean existeEmail(String email);
    
    /**
     * Verifica si un RUT ya existe en la base de datos.
     * 
     * @param rut el RUT a verificar
     * @return true si el RUT existe, false en caso contrario
     */
    boolean existeRut(String rut);
}
