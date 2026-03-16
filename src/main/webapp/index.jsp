<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Sistema de gestión de biblioteca digital UNTEC">
    <meta name="author" content="Biblioteca Digital UNTEC Team">
    <title>Biblioteca Digital UNTEC - Inicio</title>
    
    <style>
        /* ========== Reset y configuración base ========== */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        /* ========== Contenedor principal ========== */
        .container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 1000px;
            width: 100%;
            overflow: hidden;
        }
        
        /* ========== Header ========== */
        header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            text-align: center;
        }
        
        header h1 {
            font-size: 2.5em;
            margin-bottom: 10px;
        }
        
        header p {
            font-size: 1.1em;
            opacity: 0.9;
        }
        
        /* ========== Contenido principal ========== */
        .content {
            padding: 40px;
        }
        
        .welcome-text {
            text-align: center;
            margin-bottom: 40px;
        }
        
        .welcome-text h2 {
            color: #667eea;
            margin-bottom: 15px;
            font-size: 2em;
        }
        
        .welcome-text p {
            color: #666;
            font-size: 1.1em;
            line-height: 1.6;
        }
        
        /* ========== Tarjetas de funcionalidades ========== */
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        
        .feature-card {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 30px;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
            border: 2px solid transparent;
        }
        
        .feature-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
            border-color: #667eea;
        }
        
        .feature-card .icon {
            font-size: 3em;
            margin-bottom: 15px;
        }
        
        .feature-card h3 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 1.3em;
        }
        
        .feature-card p {
            color: #666;
            font-size: 0.95em;
            line-height: 1.5;
        }
        
        /* ========== Botones de acción ========== */
        .actions {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 40px;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 15px 30px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            font-size: 1em;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
            display: inline-block;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        
        .btn-secondary {
            background: white;
            color: #667eea;
            border: 2px solid #667eea;
        }
        
        .btn-secondary:hover {
            background: #667eea;
            color: white;
        }
        
        /* ========== Footer ========== */
        footer {
            background: #f8f9fa;
            padding: 20px;
            text-align: center;
            color: #666;
            border-top: 1px solid #dee2e6;
        }
        
        footer a {
            color: #667eea;
            text-decoration: none;
        }
        
        footer a:hover {
            text-decoration: underline;
        }
        
        /* ========== Responsive ========== */
        @media (max-width: 768px) {
            header h1 {
                font-size: 2em;
            }
            
            .welcome-text h2 {
                font-size: 1.5em;
            }
            
            .features {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <header>
            <h1>📚 Biblioteca Digital UNTEC</h1>
            <p>Sistema de Gestión de Biblioteca Universitaria</p>
        </header>
        
        <!-- Contenido Principal -->
        <div class="content">
            <!-- Texto de bienvenida -->
            <div class="welcome-text">
                <h2>Bienvenido al Sistema de Biblioteca</h2>
                <p>
                    Gestiona el catálogo de libros, usuarios y préstamos de manera 
                    eficiente y moderna. Sistema desarrollado con tecnologías Jakarta EE 
                    siguiendo el patrón MVC.
                </p>
            </div>
            
            <!-- Funcionalidades principales -->
            <div class="features">
                <div class="feature-card">
                    <div class="icon">📖</div>
                    <h3>Gestión de Libros</h3>
                    <p>
                        Administra el catálogo completo de libros con información 
                        detallada, búsqueda y filtros avanzados.
                    </p>
                </div>
                
                <div class="feature-card">
                    <div class="icon">👥</div>
                    <h3>Gestión de Usuarios</h3>
                    <p>
                        Registra estudiantes, profesores y administradores con 
                        diferentes niveles de acceso.
                    </p>
                </div>
                
                <div class="feature-card">
                    <div class="icon">🔄</div>
                    <h3>Préstamos y Devoluciones</h3>
                    <p>
                        Control completo de préstamos activos, historial y 
                        gestión de devoluciones.
                    </p>
                </div>
            </div>
            
            <!-- Botones de acción -->
            <div class="actions">
                <a href="${pageContext.request.contextPath}/inicio" class="btn btn-primary">
                    Probar Sistema
                </a>
                <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">
                    Ver Catálogo
                </a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">
                    Iniciar Sesión
                </a>
            </div>
            
            <!-- Información del proyecto -->
            <div style="margin-top: 40px; padding: 20px; background: #e3f2fd; border-radius: 10px; border-left: 4px solid #667eea;">
                <h3 style="color: #667eea; margin-bottom: 10px;">ℹ️ Información del Proyecto</h3>
                <ul style="margin-left: 20px; line-height: 1.8; color: #666;">
                    <li><strong>Curso:</strong> Talento Digital - Módulo 5</li>
                    <li><strong>Proyecto:</strong> Desarrollo de Aplicaciones Web Dinámicas Java</li>
                    <li><strong>Tecnologías:</strong> Jakarta EE, JSP, Servlets, JSTL, JDBC</li>
                    <li><strong>Patrón:</strong> MVC (Modelo-Vista-Controlador)</li>
                    <li><strong>Base de Datos:</strong> H2/MySQL</li>
                </ul>
            </div>
        </div>
        
        <!-- Footer -->
        <footer>
            <p>
                &copy; 2026 Biblioteca Digital UNTEC - 
                Desarrollado para el Módulo 5 de Talento Digital
            </p>
            <p style="margin-top: 10px;">
                <a href="${pageContext.request.contextPath}/inicio">Información del Sistema</a> | 
                <a href="https://github.com" target="_blank">GitHub</a> |
                <strong>Versión:</strong> 1.0.0
            </p>
        </footer>
    </div>
</body>
</html>
