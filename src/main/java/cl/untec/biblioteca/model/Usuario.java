package cl.untec.biblioteca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un usuario del sistema de biblioteca digital.
 * 
 * <p>Un usuario puede ser un estudiante, profesor o administrador.
 * Cada usuario tiene credenciales únicas (RUT y email) que le permiten
 * autenticarse en el sistema y realizar préstamos de libros.</p>
 * 
 * <h2>Tipos de usuario:</h2>
 * <ul>
 *   <li><b>ESTUDIANTE</b>: Puede solicitar préstamos y gestionar su perfil</li>
 *   <li><b>PROFESOR</b>: Puede solicitar préstamos con mayor duración</li>
 *   <li><b>ADMIN</b>: Puede gestionar libros, usuarios y el sistema completo</li>
 * </ul>
 * 
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * Usuario usuario = new Usuario();
 * usuario.setRut("12345678-9");
 * usuario.setNombre("Juan Pérez");
 * usuario.setEmail("juan.perez@untec.cl");
 * usuario.setPassword("password123");
 * usuario.setTipoUsuario(TipoUsuario.ESTUDIANTE);
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
public class Usuario implements Serializable {
    
    /**
     * Serial version UID para serialización.
     */
    private static final long serialVersionUID = 1L;
    
    // ========== Enumeración interna ==========
    
    /**
     * Enumeración de tipos de usuario en el sistema.
     */
    public enum TipoUsuario {
        /**
         * Usuario estudiante con permisos básicos.
         */
        ESTUDIANTE,
        
        /**
         * Usuario profesor con permisos extendidos.
         */
        PROFESOR,
        
        /**
         * Usuario administrador con permisos completos.
         */
        ADMIN
    }
    
    // ========== Atributos ==========
    
    /**
     * Identificador único del usuario en la base de datos.
     */
    private Long id;
    
    /**
     * RUT del usuario sin puntos y con guión (ej: "12345678-9").
     */
    private String rut;
    
    /**
     * Nombre completo del usuario.
     */
    private String nombre;
    
    /**
     * Correo electrónico institucional del usuario.
     */
    private String email;
    
    /**
     * Contraseña del usuario (debe almacenarse encriptada).
     */
    private String password;
    
    /**
     * Tipo de usuario (ESTUDIANTE, PROFESOR, ADMIN).
     */
    private TipoUsuario tipoUsuario;
    
    /**
     * Fecha y hora de registro del usuario en el sistema.
     */
    private LocalDateTime fechaRegistro;
    
    /**
     * Indica si el usuario está activo en el sistema.
     */
    private Boolean activo;
    
    /**
     * Facultad a la que pertenece el usuario (solo estudiantes y profesores).
     * Ej: "Ingeniería", "Educación", "Medicina", "Ciencias Comerciales"
     */
    private String facultad;
    
    /**
     * Carrera académica del usuario (solo estudiantes y profesores).
     * Ej: "Ingeniería en Informática", "Derecho", "Administración de Empresas"
     */
    private String carrera;
    
    /**
     * Año de estudio actual (solo estudiantes).
     * Valores: 1, 2, 3, 4, 5, etc.
     */
    private Integer anioActual;
    
    /**
     * Indica si el usuario tiene multa activa en la biblioteca.
     */
    private Boolean tieneMulta;
    
    /**
     * Monto de la multa en dólares (si aplica).
     */
    private Double montoMulta;
    
    // ========== Constructores ==========
    
    /**
     * Constructor por defecto.
     * Inicializa un usuario vacío con valores predeterminados.
     */
    public Usuario() {
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
        this.tipoUsuario = TipoUsuario.ESTUDIANTE; // Por defecto
    }
    
    /**
     * Constructor con campos obligatorios.
     * 
     * @param rut el RUT del usuario (no puede ser null)
     * @param nombre el nombre completo (no puede ser null)
     * @param email el email institucional (no puede ser null)
     * @param password la contraseña (no puede ser null)
     * @param tipoUsuario el tipo de usuario (no puede ser null)
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public Usuario(String rut, String nombre, String email, 
                  String password, TipoUsuario tipoUsuario) {
        this();
        validarCamposObligatorios(rut, nombre, email, password, tipoUsuario);
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }
    
    // ========== Métodos de validación ==========
    
    /**
     * Valida que los campos obligatorios sean correctos.
     * 
     * @param rut el RUT del usuario
     * @param nombre el nombre del usuario
     * @param email el email del usuario
     * @param password la contraseña del usuario
     * @param tipoUsuario el tipo de usuario
     * @throws IllegalArgumentException si algún campo es inválido
     */
    private void validarCamposObligatorios(String rut, String nombre, String email,
                                          String password, TipoUsuario tipoUsuario) {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede estar vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email debe tener formato válido");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("El tipo de usuario no puede ser null");
        }
    }
    
    /**
     * Valida el formato del RUT chileno.
     * 
     * @param rut el RUT a validar (formato: 12345678-9)
     * @return true si el formato es válido, false en caso contrario
     */
    public static boolean validarFormatoRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }
        // Formato básico: números-dígito verificador
        return rut.matches("^\\d{7,8}-[0-9Kk]$");
    }
    
    // ========== Métodos de negocio ==========
    
    /**
     * Verifica si el usuario es administrador.
     * 
     * @return true si es administrador, false en caso contrario
     */
    public boolean esAdministrador() {
        return TipoUsuario.ADMIN.equals(this.tipoUsuario);
    }
    
    /**
     * Verifica si el usuario es profesor.
     * 
     * @return true si es profesor, false en caso contrario
     */
    public boolean esProfesor() {
        return TipoUsuario.PROFESOR.equals(this.tipoUsuario);
    }
    
    /**
     * Verifica si el usuario es estudiante.
     * 
     * @return true si es estudiante, false en caso contrario
     */
    public boolean esEstudiante() {
        return TipoUsuario.ESTUDIANTE.equals(this.tipoUsuario);
    }
    
    /**
     * Obtiene los días máximos de préstamo según el tipo de usuario.
     * 
     * @return número de días permitidos para préstamo
     */
    public int getDiasMaximoPrestamo() {
        switch (this.tipoUsuario) {
            case PROFESOR:
                return 30; // Profesores: 30 días
            case ADMIN:
                return 60; // Administradores: 60 días
            case ESTUDIANTE:
            default:
                return 14; // Estudiantes: 14 días
        }
    }
    
    /**
     * Obtiene el número máximo de préstamos simultáneos según el tipo de usuario.
     * 
     * @return número máximo de préstamos simultáneos
     */
    public int getMaximoPrestamosSimultaneos() {
        switch (this.tipoUsuario) {
            case PROFESOR:
                return 5; // Profesores: 5 libros
            case ADMIN:
                return 10; // Administradores: 10 libros
            case ESTUDIANTE:
            default:
                return 3; // Estudiantes: 3 libros
        }
    }
    
    // ========== Getters y Setters ==========
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRut() {
        return rut;
    }
    
    public void setRut(String rut) {
        this.rut = rut;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
    
    public String getFacultad() {
        return facultad;
    }
    
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
    
    public String getCarrera() {
        return carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    public Integer getAnioActual() {
        return anioActual;
    }
    
    public void setAnioActual(Integer anioActual) {
        this.anioActual = anioActual;
    }
    
    public Boolean getTieneMulta() {
        return tieneMulta;
    }
    
    public void setTieneMulta(Boolean tieneMulta) {
        this.tieneMulta = tieneMulta;
    }
    
    public Double getMontoMulta() {
        return montoMulta;
    }
    
    public void setMontoMulta(Double montoMulta) {
        this.montoMulta = montoMulta;
    }
    
    // ========== Métodos Object ==========
    
    /**
     * Compara este usuario con otro objeto.
     * Dos usuarios son iguales si tienen el mismo RUT.
     * 
     * @param o el objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(rut, usuario.rut);
    }
    
    /**
     * Genera el código hash basado en el RUT.
     * 
     * @return el código hash del usuario
     */
    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }
    
    /**
     * Retorna una representación en String del usuario.
     * NOTA: No incluye la contraseña por seguridad.
     * 
     * @return String con la información principal del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", rut='" + rut + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", activo=" + activo +
                '}';
    }
}
