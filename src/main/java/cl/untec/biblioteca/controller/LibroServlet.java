package cl.untec.biblioteca.controller;

import java.io.IOException;
import java.util.List;

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
 * Servlet para mostrar el catálogo de libros.
 * 
 * <p>Requiere autenticación. Muestra todos los libros disponibles
 * usando JSTL para renderizar la lista.</p>
 * 
 * <h2>Características:</h2>
 * <ul>
 *   <li>Verifica sesión activa</li>
 *   <li>Obtiene libros desde el DAO</li>
 *   <li>Pasa datos a la vista JSP</li>
 *   <li>Usa JSTL para mostrar la lista</li>
 * </ul>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
@WebServlet("/libros")
public class LibroServlet extends HttpServlet {
    
    private LibroDAO libroDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        libroDAO = DAOFactory.getInstance().getLibroDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que el usuario esté autenticado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Obtener todos los libros
            List<Libro> libros = libroDAO.obtenerTodos();
            
            // Calcular estadísticas
            long totalLibros = libros.size();
            long librosDisponibles = libros.stream()
                    .filter(libro -> libro.getCantidadDisponible() != null && libro.getCantidadDisponible() > 0)
                    .count();
            long totalCopias = libros.stream()
                    .filter(libro -> libro.getCantidadTotal() != null)
                    .mapToInt(Libro::getCantidadTotal)
                    .sum();
            
            // Pasar datos a la vista
            request.setAttribute("libros", libros);
            request.setAttribute("totalLibros", totalLibros);
            request.setAttribute("librosDisponibles", librosDisponibles);
            request.setAttribute("totalCopias", totalCopias);
            
            // Renderizar la vista
            request.getRequestDispatcher("/WEB-INF/views/libros.jsp").forward(request, response);
            
        } catch (RuntimeException e) {
            log("Error al obtener libros", e);
            request.setAttribute("error", "Error al cargar el catálogo de libros");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
