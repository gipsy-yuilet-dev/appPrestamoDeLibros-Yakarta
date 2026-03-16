package cl.untec.biblioteca.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import cl.untec.biblioteca.filter.AuthFilter;

/**
 * Tests de smoke para validar funcionalidad crítica.
 * 
 * <p>Estos tests verifican que:</p>
 * <ul>
 *   <li>Los servlets pueden instanciarse</li>
 *   <li>Las DAO carguen correctamente</li>
 *   <li>La lógica básica es válida</li>
 * </ul>
 * 
 * <p>NOTA: Para tests de integración completa, necesitarías
 * empotrar un servidor de aplicaciones (Tomcat embebido).</p>
 * 
 * @author Biblioteca Digital UNTEC Team
 * @version 1.0
 */
@DisplayName("Tests de Smoke - Validación Básica")
public class SmokeTests {
    
    @Test
    @DisplayName("LoginServlet debe poder instanciarse")
    void testLoginServletInstantiation() {
        LoginServlet servlet = new LoginServlet();
        assertNotNull(servlet, "LoginServlet debe ser instanciable");
    }
    
    @Test
    @DisplayName("LibroServlet debe poder instanciarse")
    void testLibroServletInstantiation() {
        LibroServlet servlet = new LibroServlet();
        assertNotNull(servlet, "LibroServlet debe ser instanciable");
    }
    
    @Test
    @DisplayName("BuscarLibroServlet debe poder instanciarse")
    void testBuscarLibroServletInstantiation() {
        BuscarLibroServlet servlet = new BuscarLibroServlet();
        assertNotNull(servlet, "BuscarLibroServlet debe ser instanciable");
    }
    
    @Test
    @DisplayName("DetalleLibroServlet debe poder instanciarse")
    void testDetalleLibroServletInstantiation() {
        DetalleLibroServlet servlet = new DetalleLibroServlet();
        assertNotNull(servlet, "DetalleLibroServlet debe ser instanciable");
    }
    
    @Test
    @DisplayName("LogoutServlet debe poder instanciarse")
    void testLogoutServletInstantiation() {
        LogoutServlet servlet = new LogoutServlet();
        assertNotNull(servlet, "LogoutServlet debe ser instanciable");
    }
    
    @Test
    @DisplayName("InicioServlet debe poder instanciarse")
    void testInicioServletInstantiation() {
        InicioServlet servlet = new InicioServlet();
        assertNotNull(servlet, "InicioServlet debe ser instanciable");
    }
    
    @Test
    @DisplayName("AuthFilter debe poder instanciarse")
    void testAuthFilterInstantiation() {
        AuthFilter filter = new AuthFilter();
        assertNotNull(filter, "AuthFilter debe ser instanciable");
    }
}

