/**
 * Paquete de modelos (Entidades de dominio).
 * 
 * <p>Contiene las clases que representan las entidades del dominio 
 * de la aplicación. Estas clases son POJOs (Plain Old Java Objects) 
 * que mapean directamente a las tablas de la base de datos.</p>
 * 
 * <h2>Entidades principales:</h2>
 * <ul>
 *   <li><b>Libro</b>: Representa un libro en el catálogo de la biblioteca</li>
 *   <li><b>Usuario</b>: Representa un usuario del sistema (estudiante/profesor)</li>
 *   <li><b>Prestamo</b>: Representa un préstamo de libro a un usuario</li>
 * </ul>
 * 
 * <h2>Convenciones:</h2>
 * <ul>
 *   <li>Todas las entidades tienen un ID único</li>
 *   <li>Implementan getters y setters para todos los campos</li>
 *   <li>Sobrescriben toString() para facilitar debugging</li>
 *   <li>Pueden implementar equals() y hashCode() según necesidad</li>
 * </ul>
 * 
 * @since 1.0
 */
package cl.untec.biblioteca.model;
