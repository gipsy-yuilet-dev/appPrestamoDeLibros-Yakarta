package cl.untec.biblioteca.controller;

import java.io.IOException;

import cl.untec.biblioteca.dao.DAOFactory;
import cl.untec.biblioteca.dao.UsuarioDAO;
import cl.untec.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para manejar el login de usuarios.
 * 
 * <p>Este servlet procesa el formulario de login y gestiona las sesiones
 * de usuario. Implementa buenas prácticas de seguridad básicas.</p>
 * 
 * <h2>Funcionalidad:</h2>
 * <ul>
 *   <li>GET: Muestra el formulario de login</li>
 *   <li>POST: Procesa credenciales y crea sesión</li>
 * </ul>
 * 
 * <h2>Notas de seguridad:</h2>
 * <p><b>IMPORTANTE:</b> Este código es educativo. En producción debes:</p>
 * <ul>
 *   <li>Usar HTTPS siempre</li>
 *   <li>Implementar CSRF tokens</li>
 *   <li>Hashear contraseñas con BCrypt/PBKDF2</li>
 *   <li>Limitar intentos de login</li>
 *   <li>Implementar logging de seguridad</li>
 * </ul>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Obtener el DAO desde la factory
        usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
    }
    
    /**
     * Muestra el formulario de login.
     * Si el usuario ya está logueado, redirige a la página principal.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Si ya hay sesión activa, redirigir a la página principal
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            response.sendRedirect(request.getContextPath() + "/libros");
            return;
        }
        
        // Mostrar formulario de login
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    /**
     * Procesa el formulario de login.
     * Valida credenciales y crea sesión si son correctas.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener parámetros del formulario
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String recordar = request.getParameter("recordar"); // checkbox "recordarme"
        
        // Validar que los campos no estén vacíos
        if (email == null || email.trim().isEmpty() || 
            password == null || password.isEmpty()) {
            
            request.setAttribute("error", "Por favor ingrese email y contraseña");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Intentar autenticar al usuario
            Usuario usuario = usuarioDAO.autenticar(email.trim(), password);
            
            if (usuario != null) {
                // Credenciales correctas: crear sesión
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario", usuario);
                session.setAttribute("usuarioId", usuario.getId());
                session.setAttribute("nombreUsuario", usuario.getNombre());
                session.setAttribute("tipoUsuario", usuario.getTipoUsuario().name());
                
                // Si marcó "recordarme", extender tiempo de sesión
                if ("on".equals(recordar)) {
                    session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 días
                } else {
                    session.setMaxInactiveInterval(30 * 60); // 30 minutos (default)
                }
                
                // Registrar login exitoso
                log(String.format("Login exitoso: %s (%s)", usuario.getEmail(), usuario.getTipoUsuario()));
                
                // Redirigir al menú principal
                response.sendRedirect(request.getContextPath() + "/menu");
                
            } else {
                // Credenciales incorrectas
                request.setAttribute("error", "Email o contraseña incorrectos");
                request.setAttribute("email", email); // Mantener el email ingresado
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
            
        } catch (RuntimeException e) {
            log("Error durante el login", e);
            request.setAttribute("error", "Error del sistema. Por favor intente más tarde");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
