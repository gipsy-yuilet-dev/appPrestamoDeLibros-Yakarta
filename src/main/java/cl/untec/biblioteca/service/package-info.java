/**
 * Paquete de servicios (Lógica de negocio).
 * 
 * <p>Contiene la lógica de negocio de la aplicación. Los servicios 
 * coordinan las operaciones entre controladores y DAOs, implementando 
 * las reglas de negocio específicas de la biblioteca.</p>
 * 
 * <h2>Responsabilidades:</h2>
 * <ul>
 *   <li>Implementar reglas de negocio complejas</li>
 *   <li>Coordinar múltiples operaciones DAO</li>
 *   <li>Validar datos de negocio</li>
 *   <li>Gestionar transacciones (en versiones futuras)</li>
 * </ul>
 * 
 * <h2>Ejemplo de reglas de negocio:</h2>
 * <ul>
 *   <li>Un usuario no puede tener más de 3 préstamos simultáneos</li>
 *   <li>Un libro no puede prestarse si ya está prestado</li>
 *   <li>Los préstamos tienen una duración máxima de 14 días</li>
 * </ul>
 * 
 * @since 1.0
 */
package cl.untec.biblioteca.service;
