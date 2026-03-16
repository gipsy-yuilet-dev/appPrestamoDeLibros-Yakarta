package cl.untec.biblioteca.controller;

import java.io.IOException;

import cl.untec.biblioteca.dao.DAOFactory;
import cl.untec.biblioteca.dao.PrestamoDAO;
import cl.untec.biblioteca.dao.UsuarioDAO;
import cl.untec.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * DashboardServlet - Dashboard adaptativo por rol de usuario
 * 
 * Gestiona el flujo principal después del login:
 * - ESTUDIANTE: Ver mi catálogo y mis préstamos
 * - PROFESOR: Ver referencias y préstamos extendidos
 * - ADMIN: Panel de administración completo
 * 
 * Patrón: MVC (Servlet → JSP)
 * Autor: Desarrollado con estándares de Clean Code
 * @author Biblioteca UNTEC
 * @version 1.0.0
 * @since Marzo 2026
 */
@WebServlet(urlPatterns = {"/dashboard"}, loadOnStartup = 1)
public class DashboardServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final PrestamoDAO prestamoDAO = DAOFactory.getInstance().getPrestamoDAO();
    private final UsuarioDAO usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
    
    /**
     * GET /dashboard - Mostrar dashboard según rol del usuario
     * 
     * Lógica:
     * 1. Verificar que el usuario esté logueado (sesión)
     * 2. Obtener tipo de usuario (rol)
     * 3. Cargar estadísticas según rol
     * 4. Renderizar página con datos
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar autenticación
        Long usuarioId = (Long) request.getSession().getAttribute("usuarioId");
        String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
        
        if (usuarioId == null || tipoUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Obtener datos del usuario
            Usuario usuario = usuarioDAO.obtenerPorId(usuarioId);
            
            // Cargar estadísticas según rol
            if ("ADMIN".equals(tipoUsuario)) {
                cargarDatosAdmin(request, usuarioId);
            } else if ("PROFESOR".equals(tipoUsuario)) {
                cargarDatosProfesor(request, usuarioId);
            } else { // ESTUDIANTE
                cargarDatosEstudiante(request, usuarioId);
            }
            
            // Parámetros generales
            request.setAttribute("usuario", usuario);
            request.setAttribute("tipoUsuario", tipoUsuario);
            
            // Renderizar dashboard
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                   .forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error al cargar el dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                   .forward(request, response);
        }
    }
    
    /**
     * Cargar datos para dashboard de ADMINISTRATOR
     * - Cuenta total de préstamos activos
     * - Préstamos vencidos
     * - Usuarios bloqueados
     * - Total de libros
     */
    private void cargarDatosAdmin(HttpServletRequest request, Long usuarioId) {
        try {
            int totalActivos = prestamoDAO.obtenerActivosConDetalles().size();
            int totalVencidos = prestamoDAO.obtenerVencidos().size();
            int totalLibros = usuarioDAO.obtenerTodos().size();
            
            request.setAttribute("totalActivos", totalActivos);
            request.setAttribute("totalVencidos", totalVencidos);
            request.setAttribute("totalLibros", totalLibros);
            
        } catch (Exception e) {
            request.setAttribute("adminError", "Error cargando datos de admin: " + e.getMessage());
        }
    }
    
    /**
     * Cargar datos para dashboard de PROFESOR
     * - Mis préstamos activos (período de 30 días)
     * - Acceso a sección académica
     * - Información de referencias
     */
    private void cargarDatosProfesor(HttpServletRequest request, Long usuarioId) {
        try {
            int prestamosMios = prestamoDAO.obtenerPorUsuario(usuarioId).size();
            
            request.setAttribute("prestamosMios", prestamosMios);
            request.setAttribute("periodoPrestamo", 30);
            request.setAttribute("renovacionesDisponibles", 3);
            
        } catch (Exception e) {
            request.setAttribute("profesorError", "Error cargando datos de profesor: " + e.getMessage());
        }
    }
    
    /**
     * Cargar datos para dashboard de ESTUDIANTE
     * - Mis préstamos activos
     * - Notificaciones de vencimiento
     * - Acceso a catálogo
     */
    private void cargarDatosEstudiante(HttpServletRequest request, Long usuarioId) {
        try {
            int prestamosMios = prestamoDAO.obtenerPorUsuario(usuarioId).size();
            
            request.setAttribute("prestamosMios", prestamosMios);
            request.setAttribute("periodoPrestamo", 14);
            request.setAttribute("renovacionesDisponibles", 1);
            
        } catch (Exception e) {
            request.setAttribute("estudianteError", "Error cargando datos de estudiante: " + e.getMessage());
        }
    }
}
