package cl.untec.biblioteca.dao;

import java.util.List;

import cl.untec.biblioteca.model.Libro;

/**
 * Interfaz para operaciones de acceso a datos de libros.
 * 
 * <p>Define el contrato para todas las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con la entidad Libro. Esta interfaz sigue el patrón DAO
 * (Data Access Object) para abstraer y encapsular todo el acceso a la fuente de datos.</p>
 * 
 * <h2>Patrón DAO:</h2>
 * <ul>
 *   <li>Separa la lógica de persistencia de la lógica de negocio</li>
 *   <li>Facilita el cambio de tecnología de base de datos</li>
 *   <li>Hace el código más testeable</li>
 *   <li>Centraliza las consultas SQL</li>
 * </ul>
 * 
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * LibroDAO libroDAO = DAOFactory.getInstance().getLibroDAO();
 * List&lt;Libro&gt; libros = libroDAO.obtenerTodos();
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 * @see Libro
 * @see cl.untec.biblioteca.dao.impl.LibroDAOImpl
 */
public interface LibroDAO {
    
    /**
     * Crea un nuevo libro en la base de datos.
     * 
     * @param libro el libro a crear (no puede ser null)
     * @return el ID generado para el libro
     * @throws IllegalArgumentException si el libro es null o tiene datos inválidos
     * @throws RuntimeException si ocurre un error de base de datos
     */
    Long crear(Libro libro);
    
    /**
     * Obtiene un libro por su ID.
     * 
     * @param id el ID del libro a buscar
     * @return el libro encontrado, o null si no existe
     * @throws IllegalArgumentException si el ID es null o menor a 1
     */
    Libro obtenerPorId(Long id);
    
    /**
     * Obtiene un libro por su ISBN.
     * 
     * @param isbn el código ISBN del libro
     * @return el libro encontrado, o null si no existe
     * @throws IllegalArgumentException si el ISBN es null o vacío
     */
    Libro obtenerPorIsbn(String isbn);
    
    /**
     * Obtiene todos los libros activos en la base de datos.
     * 
     * @return lista de libros (nunca null, puede estar vacía)
     */
    List<Libro> obtenerTodos();
    
    /**
     * Busca libros por título (búsqueda parcial, case-insensitive).
     * 
     * @param titulo el título o parte del título a buscar
     * @return lista de libros que coinciden (nunca null)
     */
    List<Libro> buscarPorTitulo(String titulo);
    
    /**
     * Busca libros por autor (búsqueda parcial, case-insensitive).
     * 
     * @param autor el nombre del autor o parte de él
     * @return lista de libros que coinciden (nunca null)
     */
    List<Libro> buscarPorAutor(String autor);
    
    /**
     * Busca libros por categoría.
     * 
     * @param categoria la categoría de libros
     * @return lista de libros de esa categoría (nunca null)
     */
    List<Libro> buscarPorCategoria(String categoria);
    
    /**
     * Obtiene todos los libros disponibles para préstamo.
     * 
     * @return lista de libros con cantidad disponible mayor a 0 (nunca null)
     */
    List<Libro> obtenerDisponibles();
    
    /**
     * Actualiza un libro existente en la base de datos.
     * 
     * @param libro el libro con los datos actualizados
     * @return true si se actualizó correctamente, false si no existe
     * @throws IllegalArgumentException si el libro es null o no tiene ID
     */
    boolean actualizar(Libro libro);
    
    /**
     * Elimina un libro de la base de datos (borrado lógico).
     * Marca el libro como inactivo en lugar de eliminarlo físicamente.
     * 
     * @param id el ID del libro a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    boolean eliminar(Long id);
    
    /**
     * Cuenta el total de libros activos en la base de datos.
     * 
     * @return número total de libros activos
     */
    int contarTotal();
}
