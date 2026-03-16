package cl.untec.biblioteca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un libro en el catálogo de la biblioteca digital.
 * 
 * <p>Esta clase es un POJO (Plain Old Java Object) que modela la entidad
 * libro con todos sus atributos y comportamientos básicos. Implementa
 * {@link Serializable} para permitir su almacenamiento en sesión HTTP.</p>
 * 
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * Libro libro = new Libro();
 * libro.setIsbn("9780134685991");
 * libro.setTitulo("Effective Java");
 * libro.setAutor("Joshua Bloch");
 * libro.setCantidadTotal(3);
 * libro.setCantidadDisponible(3);
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class Libro implements Serializable {
    
    /**
     * Serial version UID para serialización.
     */
    private static final long serialVersionUID = 1L;
    
    // ========== Atributos ==========
    
    /**
     * Identificador único del libro en la base de datos.
     */
    private Long id;
    
    /**
     * Código ISBN-13 del libro (13 dígitos).
     */
    private String isbn;
    
    /**
     * Título del libro.
     */
    private String titulo;
    
    /**
     * Autor principal del libro.
     */
    private String autor;
    
    /**
     * Editorial que publica el libro.
     */
    private String editorial;
    
    /**
     * Año de publicación del libro.
     */
    private Integer anioPublicacion;
    
    /**
     * Categoría o género del libro (ej: "Programación", "Matemáticas").
     */
    private String categoria;
    
    /**
     * Descripción o sinopsis del libro.
     */
    private String descripcion;
    
    /**
     * Cantidad total de ejemplares del libro en la biblioteca.
     */
    private Integer cantidadTotal;
    
    /**
     * Cantidad de ejemplares disponibles para préstamo.
     */
    private Integer cantidadDisponible;
    
    /**
     * Ubicación física del libro en la biblioteca (ej: "Estante A1").
     */
    private String ubicacion;
    
    /**
     * Fecha y hora de registro del libro en el sistema.
     */
    private LocalDateTime fechaRegistro;
    
    /**
     * Indica si el libro está activo en el catálogo.
     */
    private Boolean activo;
    
    // ========== Constructores ==========
    
    /**
     * Constructor por defecto.
     * Inicializa un libro vacío con valores predeterminados.
     */
    public Libro() {
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    /**
     * Constructor con campos obligatorios.
     * 
     * @param isbn el ISBN-13 del libro (no puede ser null)
     * @param titulo el título del libro (no puede ser null)
     * @param autor el autor del libro (no puede ser null)
     * @param cantidadTotal cantidad total de ejemplares (debe ser mayor a 0)
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public Libro(String isbn, String titulo, String autor, Integer cantidadTotal) {
        this();
        validarCamposObligatorios(isbn, titulo, autor, cantidadTotal);
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadTotal = cantidadTotal;
        this.cantidadDisponible = cantidadTotal;
    }
    
    // ========== Métodos de validación ==========
    
    /**
     * Valida que los campos obligatorios sean correctos.
     * 
     * @param isbn el ISBN del libro
     * @param titulo el título del libro
     * @param autor el autor del libro
     * @param cantidadTotal la cantidad total de ejemplares
     * @throws IllegalArgumentException si algún campo es inválido
     */
    private void validarCamposObligatorios(String isbn, String titulo, 
                                          String autor, Integer cantidadTotal) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (cantidadTotal == null || cantidadTotal < 1) {
            throw new IllegalArgumentException("La cantidad total debe ser mayor a 0");
        }
    }
    
    // ========== Métodos de negocio ==========
    
    /**
     * Verifica si el libro está disponible para préstamo.
     * 
     * @return true si hay al menos un ejemplar disponible, false en caso contrario
     */
    public boolean estaDisponible() {
        return this.cantidadDisponible != null && this.cantidadDisponible > 0;
    }
    
    /**
     * Reduce la cantidad disponible en 1 (cuando se presta).
     * 
     * @return true si se pudo reducir, false si no hay ejemplares disponibles
     */
    public boolean reducirDisponibilidad() {
        if (estaDisponible()) {
            this.cantidadDisponible--;
            return true;
        }
        return false;
    }
    
    /**
     * Aumenta la cantidad disponible en 1 (cuando se devuelve).
     * 
     * @return true si se pudo aumentar, false si ya está al máximo
     */
    public boolean aumentarDisponibilidad() {
        if (this.cantidadDisponible < this.cantidadTotal) {
            this.cantidadDisponible++;
            return true;
        }
        return false;
    }
    
    // ========== Getters y Setters ==========
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getCantidadTotal() {
        return cantidadTotal;
    }
    
    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
    
    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }
    
    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    // ========== Métodos Object ==========
    
    /**
     * Compara este libro con otro objeto.
     * Dos libros son iguales si tienen el mismo ISBN.
     * 
     * @param o el objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }
    
    /**
     * Genera el código hash basado en el ISBN.
     * 
     * @return el código hash del libro
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
    
    /**
     * Retorna una representación en String del libro.
     * 
     * @return String con la información principal del libro
     */
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", disponible=" + cantidadDisponible + "/" + cantidadTotal +
                '}';
    }
}
