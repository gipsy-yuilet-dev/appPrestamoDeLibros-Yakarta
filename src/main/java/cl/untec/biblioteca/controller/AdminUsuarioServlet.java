package cl.untec.biblioteca.controller;

import java.io.IOException;
import java.util.List;

import cl.untec.biblioteca.dao.DAOFactory;
import cl.untec.biblioteca.dao.UsuarioDAO;
import cl.untec.biblioteca.model.Usuario;
import cl.untec.biblioteca.model.Usuario.TipoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * AdminUsuarioServlet - CRUD de Usuarios
 * 
 * Gestiona la administración de usuarios del sistema:
 * - LISTAR todos los usuarios
 * - CREAR nuevo usuario
 * - EDITAR usuario existente
 * - ELIMINAR usuario
 * 
 * Solo administradores pueden acceder a este servlet.
 * 
 * @author Biblioteca UNTEC Team
 * @version 1.0.0
 * @since Marzo 2026
 */
@WebServlet(urlPatterns = {"/admin-usuarios", "/admin/usuarios"})
public class AdminUsuarioServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar autenticación y rol ADMIN
        if (!verificarAdmin(request, response)) {
            return;
        }
        
        String accion = request.getParameter("accion");
        
        try {
            if ("editar".equals(accion)) {
                editarUsuario(request, response);
            } else if ("eliminar".equals(accion)) {
                eliminarUsuario(request, response);
            } else {
                listarUsuarios(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar autenticación y rol ADMIN
        if (!verificarAdmin(request, response)) {
            return;
        }
        
        String accion = request.getParameter("accion");
        
        try {
            if ("guardar".equals(accion)) {
                guardarUsuario(request, response);
            } else if ("crear".equals(accion)) {
                crearUsuario(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin-usuarios");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error al procesar formulario: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Listar todos los usuarios del sistema
     */
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/WEB-INF/views/usuarios-lista.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al listar usuarios", e);
        }
    }
    
    /**
     * Mostrar formulario para editar usuario
     */
    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin-usuarios");
                return;
            }
            
            Long id = Long.parseLong(idStr);
            Usuario usuario = usuarioDAO.obtenerPorId(id);
            
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/admin-usuarios?error=Usuario+no+encontrado");
                return;
            }
            
            request.setAttribute("usuario", usuario);
            request.setAttribute("tiposUsuario", TipoUsuario.values());
            request.getRequestDispatcher("/WEB-INF/views/usuario-form.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al editar usuario", e);
        }
    }
    
    /**
     * Guardar cambios de usuario existente
     */
    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            String rut = request.getParameter("rut");
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String tipoStr = request.getParameter("tipoUsuario");
            String facultad = request.getParameter("facultad");
            String carrera = request.getParameter("carrera");
            String anioStr = request.getParameter("anioActual");
            String tieneMultaStr = request.getParameter("tieneMulta");
            String montoMultaStr = request.getParameter("montoMulta");
            
            if (idStr == null || rut == null || nombre == null || email == null || tipoStr == null) {
                request.setAttribute("error", "Todos los campos obligatorios deben estar completos");
                request.getRequestDispatcher("/WEB-INF/views/usuario-form.jsp").forward(request, response);
                return;
            }
            
            Long id = Long.parseLong(idStr);
            Usuario usuario = usuarioDAO.obtenerPorId(id);
            
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/admin-usuarios?error=Usuario+no+encontrado");
                return;
            }
            
            usuario.setRut(rut.trim());
            usuario.setNombre(nombre.trim());
            usuario.setEmail(email.trim());
            usuario.setTipoUsuario(TipoUsuario.valueOf(tipoStr));
            usuario.setFacultad(facultad != null && !facultad.isEmpty() ? facultad : null);
            usuario.setCarrera(carrera != null && !carrera.trim().isEmpty() ? carrera.trim() : null);
            
            // Procesar año académico (solo para estudiantes y profesores)
            if (anioStr != null && !anioStr.isEmpty()) {
                usuario.setAnioActual(Integer.parseInt(anioStr));
            }
            
            // Procesar multa (solo para estudiantes)
            boolean tieneMulta = "on".equals(tieneMultaStr) || "true".equals(tieneMultaStr);
            usuario.setTieneMulta(tieneMulta);
            
            if (tieneMulta && montoMultaStr != null && !montoMultaStr.isEmpty()) {
                usuario.setMontoMulta(Double.parseDouble(montoMultaStr));
            } else {
                usuario.setMontoMulta(0.0);
            }
            
            usuarioDAO.actualizar(usuario);
            response.sendRedirect(request.getContextPath() + "/admin-usuarios?mensaje=Usuario+actualizado");
        } catch (NumberFormatException e) {
            throw new ServletException("Error en formato de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ServletException("Error al guardar usuario", e);
        }
    }
    
    /**
     * Crear nuevo usuario
     */
    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String rut = request.getParameter("rut");
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String tipoStr = request.getParameter("tipoUsuario");
            String facultad = request.getParameter("facultad");
            String carrera = request.getParameter("carrera");
            String anioStr = request.getParameter("anioActual");
            String tieneMultaStr = request.getParameter("tieneMulta");
            String montoMultaStr = request.getParameter("montoMulta");
            
            if (rut == null || nombre == null || email == null || password == null || tipoStr == null) {
                request.setAttribute("error", "Todos los campos obligatorios deben estar completos");
                request.setAttribute("tiposUsuario", TipoUsuario.values());
                request.getRequestDispatcher("/WEB-INF/views/usuario-form.jsp").forward(request, response);
                return;
            }
            
            Usuario usuario = new Usuario();
            usuario.setRut(rut.trim());
            usuario.setNombre(nombre.trim());
            usuario.setEmail(email.trim().toLowerCase());
            usuario.setPassword(password);
            usuario.setTipoUsuario(TipoUsuario.valueOf(tipoStr));
            usuario.setFacultad(facultad != null && !facultad.isEmpty() ? facultad : null);
            usuario.setCarrera(carrera != null && !carrera.trim().isEmpty() ? carrera.trim() : null);
            
            // Procesar año académico
            if (anioStr != null && !anioStr.isEmpty()) {
                usuario.setAnioActual(Integer.parseInt(anioStr));
            }
            
            // Procesar multa
            boolean tieneMulta = "on".equals(tieneMultaStr) || "true".equals(tieneMultaStr);
            usuario.setTieneMulta(tieneMulta);
            
            if (tieneMulta && montoMultaStr != null && !montoMultaStr.isEmpty()) {
                usuario.setMontoMulta(Double.parseDouble(montoMultaStr));
            } else {
                usuario.setMontoMulta(0.0);
            }
            
            usuarioDAO.crear(usuario);
            response.sendRedirect(request.getContextPath() + "/admin-usuarios?mensaje=Usuario+creado");
        } catch (NumberFormatException e) {
            throw new ServletException("Error en formato de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ServletException("Error al crear usuario", e);
        }
    }
    
    /**
     * Eliminar usuario
     */
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin-usuarios");
                return;
            }
            
            Long id = Long.parseLong(idStr);
            usuarioDAO.eliminar(id);
            response.sendRedirect(request.getContextPath() + "/admin-usuarios?mensaje=Usuario+eliminado");
        } catch (Exception e) {
            throw new ServletException("Error al eliminar usuario", e);
        }
    }
    
    /**
     * Verificar que el usuario es administrador
     */
    private boolean verificarAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (!"ADMIN".equals(tipoUsuario)) {
            response.sendRedirect(request.getContextPath() + "/dashboard?error=Acceso+denegado");
            return false;
        }
        
        return true;
    }
}
