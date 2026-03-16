package cl.untec.biblioteca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa un préstamo de libro a un usuario.
 * 
 * <p>Esta clase modela la relación entre un usuario y un libro prestado,
 * incluyendo las fechas de préstamo, devolución esperada y real, así como
 * el estado actual del préstamo.</p>
 * 
 * <h2>Estados posibles de un préstamo:</h2>
 * <ul>
 *   <li><b>ACTIVO</b>: El préstamo está vigente y el libro no ha sido devuelto</li>
 *   <li><b>DEVUELTO</b>: El libro fue devuelto dentro del plazo</li>
 *   <li><b>VENCIDO</b>: El préstamo superó la fecha de devolución esperada</li>
 * </ul>
 * 
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * Prestamo prestamo = new Prestamo();
 * prestamo.setIdUsuario(1L);
 * prestamo.setIdLibro(5L);
 * prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(14));
 * prestamo.setEstado(EstadoPrestamo.ACTIVO);
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class Prestamo implements Serializable {
    
    /**
     * Serial version UID para serialización.
     */
    private static final long serialVersionUID = 1L;
    
    // ========== Enumeración interna ==========
    
    /**
     * Enumeración de estados posibles de un préstamo.
     */
    public enum EstadoPrestamo {
        /**
         * Préstamo activo, libro aún no devuelto.
         */
        ACTIVO,
        
        /**
         * Libro devuelto a tiempo.
         */
        DEVUELTO,
        
        /**
         * Préstamo vencido, no devuelto en plazo.
         */
        VENCIDO
    }
    
    // ========== Constantes ==========
    
    /**
     * Duración estándar de un préstamo en días (para estudiantes).
     */
    public static final int DURACION_PRESTAMO_DIAS = 14;
    
    // ========== Atributos ==========
    
    /**
     * Identificador único del préstamo en la base de datos.
     */
    private Long id;
    
    /**
     * ID del usuario que solicita el préstamo.
     */
    private Long idUsuario;
    
    /**
     * ID del libro prestado.
     */
    private Long idLibro;
    
    /**
     * Fecha y hora en que se realizó el préstamo.
     */
    private LocalDateTime fechaPrestamo;
    
    /**
     * Fecha en que se espera la devolución del libro.
     */
    private LocalDate fechaDevolucionEsperada;
    
    /**
     * Fecha y hora real en que se devolvió el libro (null si no devuelto).
     */
    private LocalDateTime fechaDevolucionReal;
    
    /**
     * Estado actual del préstamo (ACTIVO, DEVUELTO, VENCIDO).
     */
    private EstadoPrestamo estado;
    
    /**
     * Observaciones o notas sobre el préstamo.
     */
    private String observaciones;
    
    // Atributos adicionales para uso en vistas (no se mapean a BD)
    
    /**
     * Objeto Usuario completo (para uso en vistas, no se mapea a BD).
     * Se carga mediante JOIN en las consultas.
     */
    private transient Usuario usuario;
    
    /**
     * Objeto Libro completo (para uso en vistas, no se mapea a BD).
     * Se carga mediante JOIN en las consultas.
     */
    private transient Libro libro;
    
    // ========== Constructores ==========
    
    /**
     * Constructor por defecto.
     * Inicializa un préstamo con valores predeterminados.
     */
    public Prestamo() {
        this.fechaPrestamo = LocalDateTime.now();
        this.estado = EstadoPrestamo.ACTIVO;
    }
    
    /**
     * Constructor con campos principales.
     * 
     * @param idUsuario el ID del usuario que solicita el préstamo
     * @param idLibro el ID del libro a prestar
     * @param diasPrestamo número de días de duración del préstamo
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public Prestamo(Long idUsuario, Long idLibro, int diasPrestamo) {
        this();
        validarCamposObligatorios(idUsuario, idLibro, diasPrestamo);
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaDevolucionEsperada = LocalDate.now().plusDays(diasPrestamo);
    }
    
    // ========== Métodos de validación ==========
    
    /**
     * Valida que los campos obligatorios sean correctos.
     * 
     * @param idUsuario el ID del usuario
     * @param idLibro el ID del libro
     * @param diasPrestamo la duración del préstamo en días
     * @throws IllegalArgumentException si algún campo es inválido
     */
    private void validarCamposObligatorios(Long idUsuario, Long idLibro, int diasPrestamo) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser válido");
        }
        if (idLibro == null || idLibro <= 0) {
            throw new IllegalArgumentException("El ID de libro debe ser válido");
        }
        if (diasPrestamo <= 0) {
            throw new IllegalArgumentException("Los días de préstamo deben ser mayor a 0");
        }
    }
    
    // ========== Métodos de negocio ==========
    
    /**
     * Verifica si el préstamo está activo.
     * 
     * @return true si el estado es ACTIVO, false en caso contrario
     */
    public boolean estaActivo() {
        return EstadoPrestamo.ACTIVO.equals(this.estado);
    }
    
    /**
     * Verifica si el préstamo está vencido comparando con la fecha actual.
     * 
     * @return true si la fecha actual es posterior a la fecha de devolución esperada
     */
    public boolean estaVencido() {
        if (this.fechaDevolucionEsperada == null) {
            return false;
        }
        return LocalDate.now().isAfter(this.fechaDevolucionEsperada) 
               && this.fechaDevolucionReal == null;
    }
    
    /**
     * Calcula los días restantes hasta la fecha de devolución esperada.
     * Un valor negativo indica días de atraso.
     * 
     * @return número de días restantes (negativo si está atrasado)
     */
    public long getDiasRestantes() {
        if (this.fechaDevolucionEsperada == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), this.fechaDevolucionEsperada);
    }
    
    /**
     * Calcula los días de atraso si el préstamo está vencido.
     * 
     * @return número de días de atraso (0 si no está atrasado)
     */
    public long getDiasAtraso() {
        long diasRestantes = getDiasRestantes();
        return diasRestantes < 0 ? Math.abs(diasRestantes) : 0;
    }
    
    /**
     * Marca el préstamo como devuelto con la fecha actual.
     * Actualiza el estado según si fue devuelto a tiempo o con atraso.
     */
    public void marcarComoDevuelto() {
        this.fechaDevolucionReal = LocalDateTime.now();
        
        // Determinar si fue devuelto a tiempo
        if (LocalDate.now().isAfter(this.fechaDevolucionEsperada)) {
            this.estado = EstadoPrestamo.VENCIDO;
        } else {
            this.estado = EstadoPrestamo.DEVUELTO;
        }
    }
    
    /**
     * Actualiza el estado del préstamo basándose en las fechas.
     * Este método debería ejecutarse periódicamente o al consultar préstamos.
     */
    public void actualizarEstado() {
        // Si ya fue devuelto, no cambiar el estado
        if (this.fechaDevolucionReal != null) {
            return;
        }
        
        // Si está vencido y aún activo, cambiar a VENCIDO
        if (estaVencido() && estaActivo()) {
            this.estado = EstadoPrestamo.VENCIDO;
        }
    }
    
    /**
     * Calcula la duración total del préstamo en días.
     * 
     * @return número de días desde el préstamo hasta la devolución (o hasta hoy si no devuelto)
     */
    public long getDuracionDias() {
        LocalDateTime fechaFin = this.fechaDevolucionReal != null 
                                ? this.fechaDevolucionReal 
                                : LocalDateTime.now();
        return ChronoUnit.DAYS.between(this.fechaPrestamo, fechaFin);
    }
    
    // ========== Getters y Setters ==========
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Long getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }
    
    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    
    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
    
    public LocalDateTime getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }
    
    public void setFechaDevolucionReal(LocalDateTime fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
    
    public EstadoPrestamo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    // ========== Métodos Object ==========
    
    /**
     * Compara este préstamo con otro objeto.
     * 
     * @param o el objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(id, prestamo.id);
    }
    
    /**
     * Genera el código hash basado en el ID.
     * 
     * @return el código hash del préstamo
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    /**
     * Retorna una representación en String del préstamo.
     * 
     * @return String con la información principal del préstamo
     */
    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idLibro=" + idLibro +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucionEsperada=" + fechaDevolucionEsperada +
                ", estado=" + estado +
                ", diasRestantes=" + getDiasRestantes() +
                '}';
    }
}
