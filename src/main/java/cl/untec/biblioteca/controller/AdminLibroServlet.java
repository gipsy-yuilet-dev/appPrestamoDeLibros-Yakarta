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
 * Servlet administrativo para gestionar el CRUD de libros.
 *
 * <p>Centraliza alta, edición y eliminación lógica de libros, restringiendo
 * el acceso exclusivamente a usuarios administradores.</p>
 */
@WebServlet("/admin/libro")
public class AdminLibroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LibroDAO libroDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        libroDAO = DAOFactory.getInstance().getLibroDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdministrador(request.getSession(false))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los administradores pueden gestionar libros");
            return;
        }

        String accion = valorSeguro(request.getParameter("accion"), "nuevo");

        if ("editar".equalsIgnoreCase(accion)) {
            Long id = parseId(request.getParameter("id"));
            if (id == null) {
                response.sendRedirect(request.getContextPath() + "/libros?errorAdmin=id-invalido");
                return;
            }

            Libro libro = libroDAO.obtenerPorId(id);
            if (libro == null) {
                response.sendRedirect(request.getContextPath() + "/libros?errorAdmin=libro-no-encontrado");
                return;
            }

            request.setAttribute("libro", libro);
            request.setAttribute("modoFormulario", "editar");
        } else {
            request.setAttribute("libro", new Libro());
            request.setAttribute("modoFormulario", "nuevo");
        }

        request.getRequestDispatcher("/WEB-INF/views/libro-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdministrador(request.getSession(false))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Solo los administradores pueden gestionar libros");
            return;
        }

        String accion = valorSeguro(request.getParameter("accion"), "crear");

        try {
            switch (accion.toLowerCase()) {
                case "crear" -> crearLibro(request, response);
                case "actualizar" -> actualizarLibro(request, response);
                case "eliminar" -> eliminarLibro(request, response);
                default -> response.sendRedirect(request.getContextPath() + "/libros?errorAdmin=accion-invalida");
            }
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("libro", construirLibroDesdeRequest(request, true));
            request.setAttribute("modoFormulario", "actualizar".equalsIgnoreCase(accion) ? "editar" : "nuevo");
            request.getRequestDispatcher("/WEB-INF/views/libro-form.jsp").forward(request, response);
        }
    }

    private void crearLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Libro libro = construirLibroDesdeRequest(request, false);
        Long id = libroDAO.crear(libro);
        response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + id + "&okAdmin=creado");
    }

    private void actualizarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Libro libro = construirLibroDesdeRequest(request, true);
        if (libro.getId() == null) {
            throw new IllegalArgumentException("No se recibió el ID del libro a actualizar");
        }

        boolean actualizado = libroDAO.actualizar(libro);
        if (!actualizado) {
            throw new IllegalArgumentException("No fue posible actualizar el libro solicitado");
        }

        response.sendRedirect(request.getContextPath() + "/detalle-libro?id=" + libro.getId() + "&okAdmin=actualizado");
    }

    private void eliminarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = parseId(request.getParameter("id"));
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/libros?errorAdmin=id-invalido");
            return;
        }

        boolean eliminado = libroDAO.eliminar(id);
        if (!eliminado) {
            response.sendRedirect(request.getContextPath() + "/libros?errorAdmin=no-eliminado");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/libros?okAdmin=eliminado");
    }

    private Libro construirLibroDesdeRequest(HttpServletRequest request, boolean incluirId) {
        Libro libro = new Libro();

        if (incluirId) {
            libro.setId(parseId(request.getParameter("id")));
        }

        libro.setIsbn(textoObligatorio(request.getParameter("isbn"), "El ISBN es obligatorio"));
        libro.setTitulo(textoObligatorio(request.getParameter("titulo"), "El título es obligatorio"));
        libro.setAutor(textoObligatorio(request.getParameter("autor"), "El autor es obligatorio"));
        libro.setEditorial(textoOpcional(request.getParameter("editorial")));
        libro.setCategoria(textoOpcional(request.getParameter("categoria")));
        libro.setDescripcion(textoOpcional(request.getParameter("descripcion")));
        libro.setUbicacion(textoOpcional(request.getParameter("ubicacion")));
        libro.setAnioPublicacion(parseIntegerOpcional(request.getParameter("anioPublicacion"), "El año de publicación no es válido"));

        Integer cantidadTotal = parseIntegerObligatorio(request.getParameter("cantidadTotal"),
                "La cantidad total es obligatoria");
        Integer cantidadDisponible = parseIntegerObligatorio(request.getParameter("cantidadDisponible"),
                "La cantidad disponible es obligatoria");

        if (cantidadTotal < 0) {
            throw new IllegalArgumentException("La cantidad total no puede ser negativa");
        }
        if (cantidadDisponible < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }
        if (cantidadDisponible > cantidadTotal) {
            throw new IllegalArgumentException("La cantidad disponible no puede superar la cantidad total");
        }

        libro.setCantidadTotal(cantidadTotal);
        libro.setCantidadDisponible(cantidadDisponible);
        libro.setActivo(true);
        return libro;
    }

    private boolean esAdministrador(HttpSession session) {
        if (session == null) {
            return false;
        }
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        return tipoUsuario != null && "ADMIN".equals(String.valueOf(tipoUsuario));
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

    private Integer parseIntegerObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private Integer parseIntegerOpcional(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private String textoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    private String textoOpcional(String valor) {
        return valor == null || valor.trim().isEmpty() ? null : valor.trim();
    }

    private String valorSeguro(String valor, String porDefecto) {
        return valor == null || valor.trim().isEmpty() ? porDefecto : valor.trim();
    }

}
