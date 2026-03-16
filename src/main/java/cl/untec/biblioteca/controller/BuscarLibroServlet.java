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
 * Servlet para buscar libros con parámetros opcionales.
 * 
 * <p>Demuestra el manejo de parámetros GET opcionales y construcción
 * de consultas de búsqueda flexibles.</p>
 * 
 * <h2>Parámetros GET aceptados:</h2>
 * <ul>
 *   <li><b>titulo</b>: Búsqueda parcial por título (opcional)</li>
 *   <li><b>autor</b>: Búsqueda parcial por autor (opcional)</li>
 *   <li><b>disponible</b>: Filtrar solo disponibles (opcional: true/false)</li>
 * </ul>
 * 
 * <h2>Ejemplos de uso:</h2>
 * <pre>
 * /buscar                          → Todos los libros
 * /buscar?titulo=java              → Libros con "java" en el título
 * /buscar?autor=bloch              → Libros de autor "bloch"
 * /buscar?titulo=java&autor=bloch  → Combinación de filtros
 * /buscar?disponible=true          → Solo libros disponibles
 * </pre>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
@WebServlet("/buscar")
public class BuscarLibroServlet extends HttpServlet {
    
    private LibroDAO libroDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        libroDAO = DAOFactory.getInstance().getLibroDAO();
    }
    
    /**
     * Procesa búsquedas GET con parámetros opcionales.
     * 
     * <p>Demuestra conceptos clave de Servlets:</p>
     * <ul>
     *   <li>Manejo de parámetros GET opcionales</li>
     *   <li>Validación de entrada del usuario</li>
     *   <li>Búsquedas flexibles en el DAO</li>
     *   <li>Mensajes informativos al usuario</li>
     * </ul>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verificar autenticación (requerido)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // 2. Obtener parámetros de búsqueda (todos opcionales)
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");
            String disponibleStr = request.getParameter("disponible");
            
            // 3. Normalizar parámetros (trim, null-safe)
            titulo = normalizeSearchParam(titulo);
            autor = normalizeSearchParam(autor);
            
            // 4. Ejecutar búsqueda según criterios
            List<Libro> resultados;
            String mensajeBusqueda;
            
            if (titulo != null && autor != null) {
                // Búsqueda combinada: título Y autor
                resultados = buscarPorTituloYAutor(titulo, autor);
                mensajeBusqueda = String.format("Resultados para título: \"%s\" y autor: \"%s\"", 
                                                titulo, autor);
                
            } else if (titulo != null) {
                // Solo por título
                resultados = libroDAO.buscarPorTitulo(titulo);
                mensajeBusqueda = String.format("Resultados para título: \"%s\"", titulo);
                
            } else if (autor != null) {
                // Solo por autor
                resultados = libroDAO.buscarPorAutor(autor);
                mensajeBusqueda = String.format("Resultados para autor: \"%s\"", autor);
                
            } else if ("true".equalsIgnoreCase(disponibleStr)) {
                // Solo disponibles
                resultados = libroDAO.obtenerDisponibles();
                mensajeBusqueda = "Libros disponibles para préstamo";
                
            } else {
                // Sin filtros: todos los libros
                resultados = libroDAO.obtenerTodos();
                mensajeBusqueda = "Todos los libros del catálogo";
            }
            
            // 5. Aplicar filtro de disponibilidad si se especificó (solo si hay búsqueda)
            if ("true".equalsIgnoreCase(disponibleStr) && (titulo != null || autor != null)) {
                resultados = resultados.stream()
                        .filter(libro -> libro.getCantidadDisponible() != null && 
                                       libro.getCantidadDisponible() > 0)
                        .toList();
                mensajeBusqueda += " (solo disponibles)";
            }
            
            // 6. Calcular estadísticas de resultados
            long totalResultados = resultados.size();
            long disponibles = resultados.stream()
                    .filter(libro -> libro.getCantidadDisponible() != null && 
                                   libro.getCantidadDisponible() > 0)
                    .count();
            
            // 7. Logging de la búsqueda
            log(String.format("Búsqueda: titulo=%s, autor=%s, disponible=%s → %d resultados",
                    titulo, autor, disponibleStr, totalResultados));
            
            // 8. Pasar datos a la vista
            request.setAttribute("libros", resultados);
            request.setAttribute("mensajeBusqueda", mensajeBusqueda);
            request.setAttribute("totalResultados", totalResultados);
            request.setAttribute("disponibles", disponibles);
            
            // Mantener parámetros de búsqueda para el formulario
            request.setAttribute("tituloBusqueda", titulo);
            request.setAttribute("autorBusqueda", autor);
            request.setAttribute("disponibleBusqueda", disponibleStr);
            
            // 9. Renderizar vista de búsqueda
            request.getRequestDispatcher("/WEB-INF/views/buscar.jsp").forward(request, response);
            
        } catch (RuntimeException e) {
            log("Error en búsqueda de libros", e);
            request.setAttribute("error", "Error al realizar la búsqueda");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Normaliza un parámetro de búsqueda.
     * 
     * @param param el parámetro a normalizar
     * @return el parámetro limpio o null si está vacío
     */
    private String normalizeSearchParam(String param) {
        if (param == null || param.trim().isEmpty()) {
            return null;
        }
        return param.trim();
    }
    
    /**
     * Busca libros que coincidan con título Y autor.
     * Demuestra cómo combinar múltiples criterios.
     * 
     * @param titulo el título a buscar
     * @param autor el autor a buscar
     * @return lista de libros que cumplen ambos criterios
     */
    private List<Libro> buscarPorTituloYAutor(String titulo, String autor) {
        // Obtener resultados por título
        List<Libro> porTitulo = libroDAO.buscarPorTitulo(titulo);
        
        // Filtrar por autor usando Streams
        return porTitulo.stream()
                .filter(libro -> libro.getAutor() != null && 
                               libro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }
}
