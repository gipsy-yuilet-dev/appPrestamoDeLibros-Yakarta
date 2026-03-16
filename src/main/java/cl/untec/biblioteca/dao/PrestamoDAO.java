package cl.untec.biblioteca.dao;

import java.time.LocalDate;
import java.util.List;

import cl.untec.biblioteca.model.Prestamo;

/**
 * Interfaz para operaciones de acceso a datos de préstamos.
 * 
 * <p>Define el contrato para todas las operaciones CRUD (Create, Read, Update, Delete)
 * relacionadas con la entidad Prestamo. Esta interfaz sigue el patrón DAO
 * (Data Access Object) para abstraer y encapsular todo el acceso a la fuente de datos.</p>
 * 
 * <h2>Operaciones principales:</h2>
 * <ul>
 *   <li><b>Crear préstamo:</b> Registra un nuevo préstamo de libro</li>
 *   <li><b>Consultar préstamos:</b> Por ID, usuario, libro, estado</li>
 *   <li><b>Devolver libro:</b> Registra la devolución de un libro prestado</li>
 *   <li><b>Actualizar estado:</b> Cambia el estado de un préstamo (ACTIVO, VENCIDO, DEVUELTO)</li>
 * </ul>
 * 
 * <h2>Ejemplo de uso - Crear préstamo:</h2>
 * <pre>
 * PrestamoDAO dao = DAOFactory.getInstance().getPrestamoDAO();
 * 
 * Prestamo prestamo = new Prestamo();
 * prestamo.setIdUsuario(1L);
 * prestamo.setIdLibro(5L);
 * prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(14));
 * prestamo.setEstado(Prestamo.EstadoPrestamo.ACTIVO);
 * 
 * Long id = dao.crear(prestamo);
 * </pre>
 * 
 * <h2>Ejemplo de uso - Devolver libro:</h2>
 * <pre>
 * PrestamoDAO dao = DAOFactory.getInstance().getPrestamoDAO();
 * boolean devuelto = dao.devolverLibro(prestamoId);
 * if (devuelto) {
 *     System.out.println("Libro devuelto correctamente");
 * }
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 * @see Prestamo
 * @see cl.untec.biblioteca.dao.impl.PrestamoDAOImpl
 */
public interface PrestamoDAO {
    
    /**
     * Crea un nuevo préstamo en la base de datos.
     * 
     * <p>Al crear el préstamo, automáticamente:</p>
     * <ul>
     *   <li>Se establece la fecha de préstamo (ahora)</li>
     *   <li>Se calcula la fecha de devolución esperada</li>
     *   <li>Se marca como ACTIVO</li>
     *   <li>Se decrementa la cantidad disponible del libro</li>
     * </ul>
     * 
     * @param prestamo el préstamo a crear (no puede ser null)
     * @return el ID generado para el préstamo
     * @throws IllegalArgumentException si el préstamo es null o tiene datos inválidos
     * @throws RuntimeException si ocurre un error de base de datos
     */
    Long crear(Prestamo prestamo);
    
    /**
     * Obtiene un préstamo por su ID.
     * 
     * @param id el ID del préstamo a buscar
     * @return el préstamo encontrado, o null si no existe
     * @throws IllegalArgumentException si el ID es null o menor a 1
     */
    Prestamo obtenerPorId(Long id);
    
    /**
     * Obtiene un préstamo por su ID con información completa del usuario y libro.
     * 
     * <p>Esta versión hace JOIN con las tablas usuarios y libros para obtener
     * toda la información relacionada en una sola consulta.</p>
     * 
     * @param id el ID del préstamo a buscar
     * @return el préstamo con objetos Usuario y Libro completos, o null si no existe
     * @throws IllegalArgumentException si el ID es null o menor a 1
     */
    Prestamo obtenerPorIdConDetalles(Long id);
    
    /**
     * Obtiene todos los préstamos registrados en el sistema.
     * 
     * @return lista de todos los préstamos (nunca null, puede estar vacía)
     */
    List<Prestamo> obtenerTodos();
    
    /**
     * Obtiene todos los préstamos de un usuario específico.
     * 
     * @param idUsuario el ID del usuario
     * @return lista de préstamos del usuario (nunca null)
     * @throws IllegalArgumentException si el ID de usuario es null o inválido
     */
    List<Prestamo> obtenerPorUsuario(Long idUsuario);
    
    /**
     * Obtiene todos los préstamos de un libro específico (historial).
     * 
     * @param idLibro el ID del libro
     * @return lista de préstamos del libro (nunca null)
     * @throws IllegalArgumentException si el ID de libro es null o inválido
     */
    List<Prestamo> obtenerPorLibro(Long idLibro);
    
    /**
     * Obtiene todos los préstamos activos (no devueltos).
     * 
     * <p>Un préstamo está activo si:</p>
     * <ul>
     *   <li>Estado = ACTIVO</li>
     *   <li>fecha_devolucion_real es NULL</li>
     * </ul>
     * 
     * @return lista de préstamos activos (nunca null)
     */
    List<Prestamo> obtenerActivos();
    
    /**
     * Obtiene todos los préstamos activos con información completa (JOIN).
     * 
     * <p>Incluye información del usuario y del libro en cada préstamo.</p>
     * 
     * @return lista de préstamos activos con detalles completos
     */
    List<Prestamo> obtenerActivosConDetalles();
    
    /**
     * Obtiene todos los préstamos vencidos (no devueltos después de la fecha esperada).
     * 
     * <p>Un préstamo está vencido si:</p>
     * <ul>
     *   <li>fecha_devolucion_esperada &lt; fecha actual</li>
     *   <li>fecha_devolucion_real es NULL</li>
     *   <li>Estado = ACTIVO o VENCIDO</li>
     * </ul>
     * 
     * @return lista de préstamos vencidos (nunca null)
     */
    List<Prestamo> obtenerVencidos();
    
    /**
     * Obtiene préstamos por estado específico.
     * 
     * @param estado el estado a filtrar (ACTIVO, DEVUELTO, VENCIDO)
     * @return lista de préstamos con ese estado (nunca null)
     * @throws IllegalArgumentException si el estado es null
     */
    List<Prestamo> obtenerPorEstado(Prestamo.EstadoPrestamo estado);
    
    /**
     * Obtiene préstamos que vencen entre dos fechas.
     * 
     * @param fechaInicio fecha inicial del rango
     * @param fechaFin fecha final del rango
     * @return lista de préstamos que vencen en ese rango
     * @throws IllegalArgumentException si alguna fecha es null
     */
    List<Prestamo> obtenerPorRangoVencimiento(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Registra la devolución de un libro prestado.
     * 
     * <p>Esta operación:</p>
     * <ul>
     *   <li>Establece fecha_devolucion_real = ahora</li>
     *   <li>Cambia estado a DEVUELTO</li>
     *   <li>Incrementa cantidad_disponible del libro</li>
     * </ul>
     * 
     * @param idPrestamo el ID del préstamo a devolver
     * @return true si se devolvió correctamente, false si no existe o ya estaba devuelto
     * @throws IllegalArgumentException si el ID es null o inválido
     */
    boolean devolverLibro(Long idPrestamo);
    
    /**
     * Registra la devolución de un libro con observaciones.
     * 
     * @param idPrestamo el ID del préstamo
     * @param observaciones notas sobre la devolución (estado del libro, etc.)
     * @return true si se devolvió correctamente
     * @throws IllegalArgumentException si el ID es null o inválido
     */
    boolean devolverLibro(Long idPrestamo, String observaciones);
    
    /**
     * Actualiza el estado de un préstamo (por ejemplo, de ACTIVO a VENCIDO).
     * 
     * @param idPrestamo el ID del préstamo
     * @param nuevoEstado el nuevo estado
     * @return true si se actualizó correctamente, false si no existe
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    boolean actualizarEstado(Long idPrestamo, Prestamo.EstadoPrestamo nuevoEstado);
    
    /**
     * Actualiza un préstamo existente en la base de datos.
     * 
     * <p><b>Importante:</b> Este método NO debe usarse para devolver libros.
     * Use {@link #devolverLibro(Long)} en su lugar.</p>
     * 
     * @param prestamo el préstamo con los datos actualizados
     * @return true si se actualizó correctamente, false si no existe
     * @throws IllegalArgumentException si el préstamo es null o no tiene ID
     */
    boolean actualizar(Prestamo prestamo);
    
    /**
     * Elimina un préstamo de la base de datos (eliminación física).
     * 
     * <p><b>Advertencia:</b> Esta operación es irreversible. Se recomienda
     * usar actualizaciones de estado en lugar de eliminar.</p>
     * 
     * @param id el ID del préstamo a eliminar
     * @return true si se eliminó correctamente, false si no existe
     * @throws RuntimeException si hay restricciones de clave foránea
     */
    boolean eliminar(Long id);
    
    /**
     * Cuenta el total de préstamos registrados en el sistema.
     * 
     * @return número total de préstamos
     */
    int contarTotal();
    
    /**
     * Cuenta préstamos activos de un usuario específico.
     * 
     * <p>Útil para verificar límites de préstamos por usuario.</p>
     * 
     * @param idUsuario el ID del usuario
     * @return número de préstamos activos del usuario
     * @throws IllegalArgumentException si el ID es null o inválido
     */
    int contarActivosPorUsuario(Long idUsuario);
    
    /**
     * Cuenta préstamos activos de un libro específico.
     * 
     * @param idLibro el ID del libro
     * @return número de copias actualmente prestadas
     * @throws IllegalArgumentException si el ID es null o inválido
     */
    int contarActivosPorLibro(Long idLibro);
    
    /**
     * Verifica si un usuario tiene préstamos vencidos.
     * 
     * <p>Útil para políticas de préstamo (no prestar si tiene vencidos).</p>
     * 
     * @param idUsuario el ID del usuario
     * @return true si el usuario tiene al menos un préstamo vencido
     * @throws IllegalArgumentException si el ID es null o inválido
     */
    boolean tieneVencidos(Long idUsuario);
}
