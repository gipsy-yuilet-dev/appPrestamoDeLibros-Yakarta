package cl.untec.biblioteca.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

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
 * Servlet REST que proporciona notificaciones de préstamos del usuario.
 * Devuelve JSON con alertas sobre préstamos próximos a vencer o vencidos.
 */
@WebServlet("/api/notificaciones")
public class NotificacionesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PrestamoDAO prestamoDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
        prestamoDAO = DAOFactory.getInstance().getPrestamoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            responderJSON(response, crearRespuesta(false, "No autenticado", new ArrayList<>(), 0));
            return;
        }

        Long usuarioId = (Long) session.getAttribute("usuarioId");

        try {
            List<Prestamo> prestamos = prestamoDAO.obtenerPorUsuario(usuarioId);
            List<Map<String, Object>> notificaciones = new ArrayList<>();
            int totalAlertas = 0;

            for (Prestamo p : prestamos) {
                p.actualizarEstado();
                Map<String, Object> alerta = evaluarNotificacion(p);
                if (alerta != null) {
                    notificaciones.add(alerta);
                    totalAlertas++;
                }
            }

            responderJSON(response, crearRespuesta(true, "OK", notificaciones, totalAlertas));

        } catch (IOException | RuntimeException e) {
            responderJSON(response, crearRespuesta(false, "Error: " + e.getMessage(), 
                    new ArrayList<>(), 0));
        }
    }

    /**
     * Evalúa si un préstamo debe generar una notificación.
     * Retorna null si no hay alerta, o un Map con los datos de la alerta.
     */
    private Map<String, Object> evaluarNotificacion(Prestamo prestamo) {
        Map<String, Object> alerta = null;

        // Solo alertar sobre préstamos ACTIVOS o VENCIDOS
        if (!prestamo.estaActivo() && 
            !prestamo.getEstado().equals(Prestamo.EstadoPrestamo.VENCIDO)) {
            return null;
        }

        if (prestamo.estaVencido() || 
            prestamo.getEstado().equals(Prestamo.EstadoPrestamo.VENCIDO)) {
            // ALERTA CRÍTICA: Vencido
            alerta = new HashMap<>();
            alerta.put("tipo", "VENCIDO");
            alerta.put("nivel", "danger");
            alerta.put("libro", prestamo.getLibro().getTitulo());
            alerta.put("diasAtraso", prestamo.getDiasAtraso());
            alerta.put("mensaje", "⚠️ Libro vencido: " + prestamo.getLibro().getTitulo() +
                    " (" + prestamo.getDiasAtraso() + " días atraso)");
            alerta.put("icono", "⚠️");

        } else if (prestamo.getDiasRestantes() <= 2 && prestamo.getDiasRestantes() > 0) {
            // ALERTA MODERADA: Próximo a vencer (2 días o menos)
            alerta = new HashMap<>();
            alerta.put("tipo", "PROXIMO_VENCER");
            alerta.put("nivel", "warning");
            alerta.put("libro", prestamo.getLibro().getTitulo());
            alerta.put("diasRestantes", prestamo.getDiasRestantes());
            alerta.put("mensaje", "⏰ Próximo a vencer: " + prestamo.getLibro().getTitulo() +
                    " (devuelve en " + prestamo.getDiasRestantes() + " días)");
            alerta.put("icono", "⏰");
        }

        return alerta;
    }

    /**
     * Estructura estándar de respuesta JSON.
     */
    private Map<String, Object> crearRespuesta(boolean exito, String mensaje, 
            List<Map<String, Object>> notificaciones, int totalAlertas) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", exito);
        resp.put("message", mensaje);
        resp.put("notificaciones", notificaciones);
        resp.put("totalAlertas", totalAlertas);
        resp.put("timestamp", System.currentTimeMillis());
        return resp;
    }

    /**
     * Envía JSON serializado como respuesta.
     */
    private void responderJSON(HttpServletResponse response, Map<String, Object> datos)
            throws IOException {
        response.getWriter().write(gson.toJson(datos));
    }
}
