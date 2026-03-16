<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Diagnóstico del Sistema - Biblioteca Digital UNTEC</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 40px;
        }
        h1 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 2.5em;
        }
        .subtitle {
            color: #666;
            margin-bottom: 30px;
            font-size: 1.1em;
        }
        h2 {
            color: #764ba2;
            margin: 30px 0 15px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        .status-box {
            padding: 20px;
            border-radius: 8px;
            margin: 15px 0;
            border-left: 4px solid;
        }
        .status-success {
            background: #d4edda;
            border-left-color: #28a745;
            color: #155724;
        }
        .status-error {
            background: #f8d7da;
            border-left-color: #dc3545;
            color: #721c24;
        }
        .status-info {
            background: #d1ecf1;
            border-left-color: #17a2b8;
            color: #0c5460;
        }
        .status-box strong {
            font-size: 1.1em;
            display: block;
            margin-bottom: 8px;
        }
        pre {
            background: #f4f4f4;
            padding: 15px;
            border-radius: 5px;
            overflow-x: auto;
            font-size: 0.85em;
            margin: 10px 0;
        }
        ul, ol {
            margin-left: 20px;
            line-height: 1.8;
        }
        a {
            color: #667eea;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
        .btn {
            display: inline-block;
            padding: 12px 24px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 5px;
            margin: 8px 8px 8px 0;
            text-decoration: none;
            font-weight: 600;
            transition: transform 0.2s;
        }
        .btn:hover {
            transform: scale(1.05);
        }
        .info-box {
            background: #e8f4f8;
            border-left: 4px solid #667eea;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .alert {
            background: #fff3cd;
            border-left: 4px solid #ffc107;
            color: #856404;
            padding: 15px;
            border-radius: 8px;
            margin: 15px 0;
        }
        footer {
            text-align: center;
            margin-top: 40px;
            padding-top: 20px;
            border-top: 2px solid #667eea;
            color: #666;
            font-size: 0.95em;
        }
        footer a {
            color: #667eea;
            font-weight: bold;
            transition: color 0.3s;
        }
        footer a:hover {
            color: #764ba2;
            text-decoration: underline;
        }
        footer .footer-links {
            margin: 15px 0;
        }
        footer .footer-links a {
            margin: 0 15px;
            display: inline-block;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📚 Biblioteca Digital UNTEC</h1>
        <p class="subtitle">Sistema de Diagnóstico - Módulo 5</p>

        <!-- Información del servidor -->
        <h2>✓ Estado del Servidor</h2>
        <div class="status-box status-info">
            <strong>✓ El servidor está funcionando correctamente</strong>
            Servidor: <c:out value="${serverName}"/>:<c:out value="${serverPort}"/><br>
            Aplicación (Context Path): <c:out value="${contextPath}"/><br>
            Sesión ID: <c:out value="${sessionId}"/>
        </div>

        <!-- Estado de la base de datos -->
        <h2>Estado de Base de Datos</h2>
        <c:choose>
            <c:when test="${dbConnected}">
                <div class="status-box status-success">
                    <strong>✓ Conexión a base de datos exitosa</strong>
                    <pre><c:out value="${dbInfo}"/></pre>
                    <c:if test="${cantidadLibros > 0}">
                        <p><strong>✓ Base de datos inicializada</strong><br>
                        Libros registrados: <strong><c:out value="${cantidadLibros}"/></strong></p>
                    </c:if>
                    <c:if test="${cantidadLibros == 0}">
                        <div class="alert">
                            <strong>⚠ Base de datos vacía</strong><br>
                            <p>Las tablas no han sido inicializadas. Necesitas ejecutar el script <code>schema.sql</code> para crear las tablas e insertar datos de prueba.</p>
                            <p><strong>Instrucciones:</strong> Ver la sección "Configuración de BD" en el README.md</p>
                        </div>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="status-box status-error">
                    <strong>✗ Error en conexión a base de datos</strong>
                    <p><c:out value="${dbError}"/></p>
                    <p>Verifica que:</p>
                    <ul>
                        <li>La base de datos H2/MySQL está disponible</li>
                        <li>Las credenciales en <code>web.xml</code> son correctas</li>
                        <li>El puerto de la BD es accesible</li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Funcionalidades principales -->
        <h2>📋 Funcionalidades del Sistema</h2>
        <p>Este proyecto incluye:</p>
        <ul>
            <li>✅ Gestión de libros (CRUD completo)</li>
            <li>✅ Gestión de usuarios (estudiantes, profesores, admin)</li>
            <li>✅ Sistema de préstamos y devoluciones</li>
            <li>✅ Control de sesiones de usuario</li>
            <li>✅ Búsqueda avanzada de libros</li>
            <li>✅ Filtros por disponibilidad</li>
        </ul>

        <!-- Próximos pasos -->
        <h2>🚀 Próximos Pasos</h2>
        <div class="info-box">
            <c:if test="${cantidadLibros == 0}">
                <ol>
                    <li><strong>Inicializar la base de datos</strong> ejecutando <code>schema.sql</code></li>
                    <li><a href="${contextPath}/login">Ir a Login</a> con las credenciales de prueba</li>
                    <li><a href="${contextPath}/libros">Ver catálogo de libros</a></li>
                    <li><a href="${contextPath}/buscar">Buscar libros</a></li>
                </ol>
            </c:if>
            <c:if test="${cantidadLibros > 0}">
                <ol>
                    <li><a href="${contextPath}/login">✅ Ir a Login</a></li>
                    <li><a href="${contextPath}/libros">✅ Ver catálogo de libros</a></li>
                    <li><a href="${contextPath}/buscar">✅ Buscar libros</a></li>
                </ol>
                <p style="margin-top: 15px; color: #28a745;"><strong>✓ Sistema listo para usar. Usa las credenciales de prueba en login.jsp</strong></p>
            </c:if>
        </div>

        <!-- Botones de acción -->
        <h2>🔗 Enlaces Rápidos</h2>
        <div style="margin: 20px 0;">
            <a href="${contextPath}/" class="btn">🏠 Inicio</a>
            <a href="${contextPath}/login" class="btn">🔐 Login</a>
            <a href="${contextPath}/libros" class="btn">📚 Catálogo</a>
            <a href="${contextPath}/buscar" class="btn">🔍 Buscar</a>
        </div>

        <!-- Información del proyecto -->
        <h2>ℹ️ Información del Proyecto</h2>
        <div class="info-box">
            <ul style="list-style-type: none; margin-left: 0;">
                <li><strong>Curso:</strong> Talento Digital - Módulo 5</li>
                <li><strong>Proyecto:</strong> Desarrollo de Aplicaciones Web Dinámicas Java</li>
                <li><strong>Patrón:</strong> MVC (Modelo-Vista-Controlador)</li>
                <li><strong>Framework:</strong> Jakarta EE 9+</li>
                <li><strong>Autor:</strong> Julieta (Yulieta) Eyzaguirre</li>
                <li><strong>Servidor Web:</strong> Apache Tomcat 10+</li>
                <li><strong>Base de Datos:</strong> H2 (desarrollo) / MySQL 8 (producción)</li>
                <li><strong>Versión:</strong> 1.0.0</li>
                <li><strong>Fecha:</strong> Marzo 2026</li>
            </ul>
        </div>

        <!-- Pie de página -->
        <footer>
            <div class="footer-links">
                <a href="https://github.com/gipsy-yuilet-dev/appPrestamoDeLibros-Yakarta" target="_blank" rel="noopener noreferrer">🔗 GitHub</a>
                <a href="https://github.com" target="_blank" rel="noopener noreferrer">💻 GitHub Perfil</a>
                <a href="mailto:soporte@untec.cl">📧 Contacto</a>
            </div>
            <p>&copy; 2026 Biblioteca Digital UNTEC. Todos los derechos reservados.</p>
            <p style="font-size: 0.9em; margin-top: 10px; color: #999;">
                Desarrollado como proyecto final del Módulo V - Programación Web en Java<br>
                <a href="https://github.com/gipsy-yuilet-dev/appPrestamoDeLibros-Yakarta" target="_blank" rel="noopener noreferrer">Ver repositorio en GitHub →</a>
            </p>
        </footer>
    </div>
</body>
</html>

