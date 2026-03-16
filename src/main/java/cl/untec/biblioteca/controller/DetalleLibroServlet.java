package cl.untec.biblioteca.controller;

import java.io.IOException;

import cl.untec.biblioteca.dao.DAOFactory;
import cl.untec.biblioteca.dao.LibroDAO;
import cl.untec.biblioteca.model.Libro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para mostrar el detalle de un libro específico.
 * 
 * <p>Demuestra validación de parámetros, manejo de recursos no encontrados,
 * y navegación entre vistas.</p>
 * 
 * <h2>Parámetros GET:</h2>
 * <ul>
 *   <li><b>id</b>: ID del libro a mostrar (requerido, numérico)</li>
 * </ul>
 * 
 * <h2>Ejemplo:</h2>
 * <pre>
 * /detalle-libro?id=5
 * </pre>
 * 
 * <h2>Códigos de respuesta:</h2>
 * <ul>
 *   <li>200 OK: Libro encontrado</li>
 *   <li>400 Bad Request: ID inválido</li>
 *   <li>404 Not Found: Libro no existe</li>
 *   <li>302 Redirect: No autenticado</li>
 * </ul>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
@WebServlet("/detalle-libro")
public class DetalleLibroServlet extends HttpServlet {
    
    private LibroDAO libroDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        libroDAO = DAOFactory.getInstance().getLibroDAO();
    }
    
    /**
     * Muestra el detalle de un libro específico.
     * 
     * <p>Demuestra conceptos clave:</p>
     * <ul>
     *   <li>Validación de parámetros numéricos</li>
     *   <li>Manejo de errores 400 y 404</li>
     *   <li>Navegación con breadcrumbs</li>
     *   <li>Mensajes de error descriptivos</li>
     * </ul>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verificar autenticación
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // 2. Obtener y validar parámetro ID
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.trim().isEmpty()) {
            // Error 400: Parámetro faltante
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                             "Se requiere el parámetro 'id'");
            return;
        }
        
        Long libroId;
        try {
            libroId = Long.valueOf(idParam.trim());
            
            if (libroId < 1) {
                throw new NumberFormatException("ID debe ser positivo");
            }
            
        } catch (NumberFormatException e) {
            // Error 400: ID inválido
            log(String.format("ID inválido recibido: %s", idParam));
            request.setAttribute("error", "El ID del libro debe ser un número válido");
            request.setAttribute("errorDetalle", String.format("ID recibido: '%s'", idParam));
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        
        try {
            // 3. Buscar libro en la base de datos
            Libro libro = libroDAO.obtenerPorId(libroId);
            
            if (libro == null) {
                // Error 404: Libro no encontrado
                log(String.format("Libro no encontrado: ID=%d", libroId));
                response.sendError(HttpServletResponse.SC_NOT_FOUND, 
                                 String.format("No existe libro con ID: %d", libroId));
                return;
            }
            
            // 4. Calcular datos adicionales para la vista
            boolean disponible = libro.getCantidadDisponible() != null && 
                                libro.getCantidadDisponible() > 0;
            int prestados = 0;
            if (libro.getCantidadTotal() != null && libro.getCantidadDisponible() != null) {
                prestados = libro.getCantidadTotal() - libro.getCantidadDisponible();
            }
            
            // 5. Logging de acceso
            log(String.format("Detalle libro ID=%d accedido por usuario: %s", 
                    libroId, session.getAttribute("nombreUsuario")));
            
            // 6. Pasar datos a la vista
            request.setAttribute("libro", libro);
            request.setAttribute("disponible", disponible);
            request.setAttribute("prestados", prestados);
            
            // Para breadcrumbs
            request.setAttribute("origen", request.getParameter("origen")); // catálogo, búsqueda, etc.
            
            // 7. Renderizar vista de detalle
            request.getRequestDispatcher("/WEB-INF/views/detalle-libro.jsp").forward(request, response);
            
        } catch (RuntimeException e) {
            log(String.format("Error al obtener detalle del libro ID=%d", libroId), e);
            request.setAttribute("error", "Error al cargar el detalle del libro");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
