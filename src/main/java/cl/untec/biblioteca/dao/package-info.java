/**
 * Paquete DAO (Data Access Object).
 * 
 * <p>Implementa el patrón DAO para abstraer y encapsular todo el acceso 
 * a la fuente de datos. Proporciona una interfaz común para realizar 
 * operaciones CRUD (Create, Read, Update, Delete) sin exponer detalles 
 * de la implementación con JDBC.</p>
 * 
 * <h2>Beneficios del patrón DAO:</h2>
 * <ul>
 *   <li>Separación de responsabilidades</li>
 *   <li>Facilita el cambio de tecnología de persistencia</li>
 *   <li>Código más limpio y mantenible</li>
 *   <li>Facilita las pruebas unitarias</li>
 * </ul>
 * 
 * <h2>Estructura típica:</h2>
 * <pre>
 * public interface LibroDAO {
 *     void crear(Libro libro);
 *     Libro obtenerPorId(Long id);
 *     List&lt;Libro&gt; obtenerTodos();
 *     void actualizar(Libro libro);
 *     void eliminar(Long id);
 * }
 * </pre>
 * 
 * @since 1.0
 */
package cl.untec.biblioteca.dao;
