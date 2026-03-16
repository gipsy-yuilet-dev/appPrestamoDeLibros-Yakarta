package cl.untec.biblioteca.controller;

import java.io.IOException;
import java.util.List;

import cl.untec.biblioteca.dao.DAOFactory;
import cl.untec.biblioteca.dao.PrestamoDAO;
import cl.untec.biblioteca.model.Prestamo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para visualizar el historial personal de préstamos del usuario actual.
 * 
 * <p>Permite a estudiantes, profesores y admin ver sus propios préstamos,
 * incluyendo activos, devueltos y vencidos.</p>
 */
@WebServlet("/mi-historial")
public class HistorialPrestamoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PrestamoDAO prestamoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        prestamoDAO = DAOFactory.getInstance().getPrestamoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");

        try {
            // Obtener todos los préstamos del usuario
            List<Prestamo> todosPrestamos = prestamoDAO.obtenerPorUsuario(usuarioId);

            // Actualizar estados de préstamos vencidos
            for (Prestamo p : todosPrestamos) {
                p.actualizarEstado();
            }

            request.setAttribute("todosPrestamos", todosPrestamos);
            request.setAttribute("nombreUsuario", nombreUsuario);

            // Parámetros de retroalimentación
            String okMsg = request.getParameter("ok");
            if (okMsg != null) {
                request.setAttribute("okHistorial", okMsg);
            }

            request.getRequestDispatcher("/WEB-INF/views/historial-prestamos.jsp")
                    .forward(request, response);

        } catch (RuntimeException e) {
            request.setAttribute("errorHistorial", 
                    "Error al cargar historial: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/historial-prestamos.jsp")
                    .forward(request, response);
        }
    }
}
