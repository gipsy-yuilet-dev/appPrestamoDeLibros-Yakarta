package cl.untec.biblioteca.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro para verificar autenticación en rutas protegidas.
 * 
 * <p>Este filtro intercepta todas las peticiones y verifica que el usuario
 * esté autenticado antes de permitir el acceso a recursos protegidos.</p>
 * 
 * <h2>¿Qué es un Filter?</h2>
 * <p>Un filtro es un componente que intercepta requests/responses ANTES
 * de que lleguen al servlet. Se usa para:</p>
 * <ul>
 *   <li>Autenticación y autorización</li>
 *   <li>Logging de requests</li>
 *   <li>Compresión de respuestas</li>
 *   <li>Modificar headers</li>
 *   <li>Validaciones comunes</li>
 * </ul>
 * 
 * <h2>Cadena de Filtros:</h2>
 * <pre>
 * Request → Filter1 → Filter2 → Servlet → Response
 *           ↓         ↓          ↓
 *         chain     chain      process
 * </pre>
 * 
 * <h2>Ventajas sobre verificar en cada servlet:</h2>
 * <ul>
 *   <li>✅ Evita código duplicado</li>
 *   <li>✅ Centraliza la lógica de seguridad</li>
 *   <li>✅ Más fácil de mantener</li>
 *   <li>✅ Aplica automáticamente a todas las rutas</li>
 * </ul>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 * @since 1.0
 */
@WebFilter("/*") // Intercepta TODAS las URLs
public class AuthFilter implements Filter {
    
    /**
     * URLs públicas que NO requieren autenticación.
     * Estas rutas se pueden acceder sin login.
     */
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/login",
            "/logout",
            "/",
            "/index.jsp",
            "/inicio",
            "/bienvenida",
            "/404.jsp",
            "/500.jsp"
    );
    
    /**
     * Prefijos de recursos estáticos que NO requieren autenticación.
     */
    private static final List<String> PUBLIC_PREFIXES = Arrays.asList(
            "/css/",
            "/js/",
            "/images/",
            "/resources/"
    );
    
    /**
     * Inicializa el filtro.
     * Se ejecuta UNA sola vez al iniciar la aplicación.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        // Aquí podrías cargar configuraciones desde web.xml
        filterConfig.getServletContext().log("AuthFilter inicializado");
    }
    
    /**
     * Método principal del filtro.
     * Se ejecuta en CADA request antes de llegar al servlet.
     * 
     * <h2>Flujo:</h2>
     * <pre>
     * 1. Request llega
     * 2. doFilter() se ejecuta
     * 3. Verifica si la ruta es pública
     * 4. Si es pública → chain.doFilter() (continuar)
     * 5. Si es privada → verificar sesión
     * 6. Si hay sesión → chain.doFilter() (continuar)
     * 7. Si NO hay sesión → redirect a /login
     * </pre>
     * 
     * @param request el request HTTP
     * @param response el response HTTP
     * @param chain la cadena de filtros
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Cast a tipos HTTP específicos
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 1. Obtener la URI solicitada (sin context path)
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        // 2. Verificar si es una URL pública
        if (isPublicURL(path)) {
            // URL pública: permitir acceso sin verificar sesión
            chain.doFilter(request, response);
            return;
        }
        
        // 3. Verificar si es un recurso estático
        if (isStaticResource(path)) {
            // Recurso estático: permitir acceso
            chain.doFilter(request, response);
            return;
        }
        
        // 4. URL protegida: verificar autenticación
        HttpSession session = httpRequest.getSession(false);
        boolean isAuthenticated = (session != null && session.getAttribute("usuario") != null);
        
        if (isAuthenticated) {
            // Usuario autenticado: permitir acceso
            
            // Opcional: Logging de acceso
                Object usernameAttr = session.getAttribute("nombreUsuario");
                String username = usernameAttr != null ? String.valueOf(usernameAttr) : "usuario-autenticado";
            httpRequest.getServletContext().log(
                    String.format("Acceso autorizado: %s → %s", username, path)
            );
            
            // Continuar con la cadena de filtros/servlets
            chain.doFilter(request, response);
            
        } else {
            // Usuario NO autenticado: redirigir a login
            
            // Opcional: Guardar URL original para redirigir después del login
            session = httpRequest.getSession(true);
            session.setAttribute("urlOriginal", requestURI);
            
            // Logging de intento de acceso no autorizado
            httpRequest.getServletContext().log(
                    String.format("Acceso denegado (no autenticado): %s desde IP: %s", 
                                 path, httpRequest.getRemoteAddr())
            );
            
            // Redirigir al login
            httpResponse.sendRedirect(contextPath + "/login");
        }
    }
    
    /**
     * Verifica si una URL es pública (no requiere autenticación).
     * 
     * @param path la ruta a verificar
     * @return true si es pública, false si requiere autenticación
     */
    private boolean isPublicURL(String path) {
        // Verificar coincidencia exacta
        return PUBLIC_URLS.contains(path);
    }
    
    /**
     * Verifica si es un recurso estático (CSS, JS, imágenes).
     * 
     * @param path la ruta a verificar
     * @return true si es un recurso estático
     */
    private boolean isStaticResource(String path) {
        // Verificar si comienza con algún prefijo público
        return PUBLIC_PREFIXES.stream().anyMatch(path::startsWith);
    }
    
    /**
     * Se ejecuta al destruir el filtro (apagar la aplicación).
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
        // Limpiar recursos si es necesario
    }
}
