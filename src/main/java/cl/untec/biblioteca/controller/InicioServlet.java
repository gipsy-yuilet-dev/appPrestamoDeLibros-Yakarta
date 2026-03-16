package cl.untec.biblioteca.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cl.untec.biblioteca.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet de bienvenida y prueba del sistema.
 * 
 * <p>Este servlet sirve como punto de entrada para verificar que la aplicación
 * está funcionando correctamente. Prueba la conexión a la base de datos y
 * muestra información del sistema.</p>
 * 
 * <h2>Funcionalidades:</h2>
 * <ul>
 *   <li>Verifica la conexión a base de datos</li>
 *   <li>Muestra información del servidor</li>
 *   <li>Proporciona enlaces a las funcionalidades principales</li>
 * </ul>
 * 
 * <p><b>URL de mapeo:</b> /inicio o /bienvenida</p>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
@WebServlet(name = "InicioServlet", urlPatterns = {"/inicio", "/bienvenida"})
public class InicioServlet extends HttpServlet {
    
    /**
     * Serial version UID para serialización.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Maneja peticiones GET.
     * Muestra la página de bienvenida con información del sistema.
     * 
     * @param request el objeto HttpServletRequest con la petición del cliente
     * @param response el objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Probar conexión a base de datos
        boolean dbConnected;
        String dbInfo = "";
        int cantidadLibros = 0;
        String dbError = null;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            dbConnected = true;
            dbInfo = DatabaseConnection.getConnectionInfo();
            
            // Intentar contar libros si la tabla existe
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM libros");
                if (rs.next()) {
                    cantidadLibros = rs.getInt("total");
                }
            } catch (SQLException e) {
                // La tabla probablemente no existe aún - esto es OK en desarrollo
                dbError = "Tablas no inicializadas. Ejecute: schema.sql";
            }
            
        } catch (SQLException e) {
            dbConnected = false;
            dbError = e.getMessage();
        }
        
        // Pasar datos a la vista
        request.setAttribute("dbConnected", dbConnected);
        request.setAttribute("dbInfo", dbInfo);
        request.setAttribute("cantidadLibros", cantidadLibros);
        request.setAttribute("dbError", dbError);
        request.setAttribute("serverName", request.getServerName());
        request.setAttribute("serverPort", request.getServerPort());
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("sessionId", request.getSession().getId());
        
        // Despachar a JSP
        request.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(request, response);
    }
    
    /**
     * Maneja peticiones POST (simplemente redirige a GET).
     * 
     * @param request el objeto HttpServletRequest con la petición del cliente
     * @param response el objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Retorna una descripción corta del servlet.
     * 
     * @return descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet de bienvenida de la Biblioteca Digital UNTEC";
    }
}
