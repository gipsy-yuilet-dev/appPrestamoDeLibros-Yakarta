package cl.untec.biblioteca.dao;

import cl.untec.biblioteca.dao.impl.LibroDAOImpl;
import cl.untec.biblioteca.dao.impl.PrestamoDAOImpl;
import cl.untec.biblioteca.dao.impl.UsuarioDAOImpl;

/**
 * Factory para crear instancias de DAOs.
 * 
 * <p>Implementa el patrón Factory y Singleton para centralizar la creación
 * de objetos DAO. Esto facilita el cambio de implementación sin afectar
 * el resto del código.</p>
 * 
 * <h2>Ventajas del patrón Factory:</h2>
 * <ul>
 *   <li>Centraliza la creación de objetos</li>
 *   <li>Facilita el cambio de implementación</li>
 *   <li>Permite inyección de dependencias futura</li>
 *   <li>Hace el código más testeable</li>
 * </ul>
 * 
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * DAOFactory factory = DAOFactory.getInstance();
 * LibroDAO libroDAO = factory.getLibroDAO();
 * List&lt;Libro&gt; libros = libroDAO.obtenerTodos();
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class DAOFactory {
    
    /**
     * Instancia única de la factory (Singleton).
     */
    private static DAOFactory instance;
    
    /**
     * Constructor privado para evitar instanciación directa (Singleton).
     */
    private DAOFactory() {
        // Constructor privado
    }
    
    /**
     * Obtiene la instancia única de DAOFactory.
     * Thread-safe usando sincronización doble.
     * 
     * @return la instancia única de DAOFactory
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
    
    /**
     * Crea y retorna una nueva instancia de LibroDAO.
     * 
     * @return implementación de LibroDAO
     */
    public LibroDAO getLibroDAO() {
        return new LibroDAOImpl();
    }
    
    /**
     * Crea y retorna una nueva instancia de UsuarioDAO.
     * 
     * @return implementación de UsuarioDAO
     */
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
    
    /**
     * Crea y retorna una nueva instancia de PrestamoDAO.
     * 
     * @return implementación de PrestamoDAO
     */
    public PrestamoDAO getPrestamoDAO() {
        return new PrestamoDAOImpl();
    }
}
