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
 * Servlet administrativo para gestionar devoluciones de préstamos.
 *
 * <p>Permite al administrador ver todos los préstamos activos y vencidos,
 * y procesar devoluciones registrando observaciones (daños, estado, etc.).</p>
 */
@WebServlet("/admin/prestamos")
public class AdminPrestamoServlet extends HttpServlet {

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

        if (!esAdministrador(request.getSession(false))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los administradores pueden gestionar préstamos");
            return;
        }

        try {
            // Obtener préstamos activos y vencidos
            List<Prestamo> prestamosActivos = prestamoDAO.obtenerActivosConDetalles();
            List<Prestamo> prestamosVencidos = prestamoDAO.obtenerVencidos();

            // Actualizar estado de préstamos vencidos
            for (Prestamo p : prestamosVencidos) {
                p.actualizarEstado();
            }

            request.setAttribute("prestamosActivos", prestamosActivos);
            request.setAttribute("prestamosVencidos", prestamosVencidos);

            // Parámetros de retroalimentación
            String okMsg = request.getParameter("ok");
            String errorMsg = request.getParameter("error");

            if (okMsg != null) {
                request.setAttribute("okPrestamo", okMsg);
            }
            if (errorMsg != null) {
                request.setAttribute("errorPrestamo", errorMsg);
            }

            request.getRequestDispatcher("/WEB-INF/views/prestamos-admin.jsp")
                    .forward(request, response);

        } catch (RuntimeException e) {
            request.setAttribute("errorPrestamo", "Error al cargar préstamos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/prestamos-admin.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdministrador(request.getSession(false))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String accion = valorSeguro(request.getParameter("accion"), "");

        switch (accion) {
            case "devolver" -> procesarDevolucion(request, response);
            default -> response.sendRedirect(request.getContextPath() + "/admin/prestamos?error=accion-invalida");
        }
    }

    /**
     * Procesa la devolución de un préstamo registrando observaciones.
     */
    private void procesarDevolucion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long idPrestamo = parseId(request.getParameter("idPrestamo"));
        if (idPrestamo == null) {
            response.sendRedirect(request.getContextPath() + "/admin/prestamos?error=id-invalido");
            return;
        }

        String observaciones = valorSeguro(request.getParameter("observaciones"), null);

        try {
            boolean devuelto;
            if (observaciones != null && !observaciones.isEmpty()) {
                devuelto = prestamoDAO.devolverLibro(idPrestamo, observaciones);
            } else {
                devuelto = prestamoDAO.devolverLibro(idPrestamo);
            }

            if (devuelto) {
                response.sendRedirect(request.getContextPath() 
                        + "/admin/prestamos?ok=devuelto");
            } else {
                response.sendRedirect(request.getContextPath() 
                        + "/admin/prestamos?error=no-existe");
            }

        } catch (RuntimeException e) {
            response.sendRedirect(request.getContextPath() 
                    + "/admin/prestamos?error=" + urlEncode(e.getMessage()));
        }
    }

    /**
     * Verifica si el usuario en sesión es administrador.
     */
    private boolean esAdministrador(HttpSession session) {
        if (session == null) {
            return false;
        }
        Object tipoObj = session.getAttribute("tipoUsuario");
        return "ADMIN".equals(tipoObj != null ? tipoObj.toString() : null);
    }

    /**
     * Convierte String a Long de forma segura.
     */
    private Long parseId(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(param.trim());
            return id > 0 ? id : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retorna un valor seguro si el parámetro es null o vacío.
     */
    private String valorSeguro(String param, String seguro) {
        return (param == null || param.isEmpty()) ? seguro : param;
    }

    /**
     * Codifica URL para parámetros seguros.
     */
    private String urlEncode(String valor) {
        return valor.replace(" ", "+").replace(":", "-");
    }
}
