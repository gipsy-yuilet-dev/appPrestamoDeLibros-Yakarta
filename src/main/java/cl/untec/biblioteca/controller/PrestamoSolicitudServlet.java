package cl.untec.biblioteca.controller;

import java.io.IOException;

import cl.untec.biblioteca.dao.DAOFactory;
import cl.untec.biblioteca.dao.LibroDAO;
import cl.untec.biblioteca.dao.PrestamoDAO;
import cl.untec.biblioteca.model.Libro;
import cl.untec.biblioteca.model.Prestamo;
import cl.untec.biblioteca.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para registrar solicitudes de préstamo desde el detalle de libro.
 */
@WebServlet("/prestamos/solicitar")
public class PrestamoSolicitudServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        DAOFactory factory = DAOFactory.getInstance();
        prestamoDAO = factory.getPrestamoDAO();
        libroDAO = factory.getLibroDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Long libroId = parseId(request.getParameter("idLibro"));
        Integer plazoDias = parseInteger(request.getParameter("plazoDias"));
        String observaciones = textoOpcional(request.getParameter("observaciones"));

        if (libroId == null) {
            response.sendRedirect(request.getContextPath() + "/libros?errorPrestamo=libro-invalido");
            return;
        }

        try {
            Libro libro = libroDAO.obtenerPorId(libroId);
            if (libro == null) {
                response.sendRedirect(request.getContextPath() + "/libros?errorPrestamo=libro-no-encontrado");
                return;
            }

            if (plazoDias == null || !esPlazoPermitido(usuario, plazoDias)) {
                response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + libroId + "&prestamo=plazo-invalido");
                return;
            }

            if (libro.getCantidadDisponible() == null || libro.getCantidadDisponible() <= 0) {
                response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + libroId + "&prestamo=sin-stock");
                return;
            }

            if (prestamoDAO.tieneVencidos(usuario.getId())) {
                response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + libroId + "&prestamo=tiene-vencidos");
                return;
            }

            Prestamo prestamo = new Prestamo(usuario.getId(), libroId, plazoDias);
            prestamo.setObservaciones(observaciones);
            prestamoDAO.crear(prestamo);

            response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + libroId + "&prestamo=ok");

        } catch (RuntimeException e) {
            log("Error al registrar solicitud de préstamo", e);
            response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + libroId + "&prestamo=error");
        }
    }

    private boolean esPlazoPermitido(Usuario usuario, int plazoDias) {
        if (plazoDias <= 0) {
            return false;
        }

        Usuario.TipoUsuario tipo = usuario.getTipoUsuario();
        if (tipo == null) {
            return plazoDias == 7 || plazoDias == 14;
        }

        return switch (tipo) {
            case ESTUDIANTE -> plazoDias == 7 || plazoDias == 14;
            case PROFESOR -> plazoDias == 7 || plazoDias == 14 || plazoDias == 21 || plazoDias == 30;
            case ADMIN -> plazoDias == 7 || plazoDias == 14 || plazoDias == 21;
        };
    }

    private Long parseId(String valor) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                return null;
            }
            long id = Long.parseLong(valor.trim());
            return id > 0 ? id : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String valor) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                return null;
            }
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String textoOpcional(String valor) {
        return valor == null || valor.trim().isEmpty() ? null : valor.trim();
    }
}
