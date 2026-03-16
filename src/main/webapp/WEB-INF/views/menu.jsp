<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú Principal - Biblioteca UNTEC</title>
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
        }
        .header {
            background: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            text-align: center;
        }
        .header h1 {
            color: #667eea;
            font-size: 2.5em;
            margin-bottom: 10px;
        }
        .user-info {
            color: #666;
            font-size: 1.1em;
            margin-bottom: 15px;
        }
        .badge {
            display: inline-block;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 600;
            color: white;
            margin-left: 10px;
        }
        .badge-admin { background: #dc3545; }
        .badge-profesor { background: #fd7e14; }
        .badge-estudiante { background: #28a745; }
        
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .menu-btn {
            background: white;
            border: none;
            border-radius: 12px;
            padding: 30px;
            text-align: center;
            cursor: pointer;
            box-shadow: 0 8px 30px rgba(0,0,0,0.15);
            transition: all 0.3s;
            text-decoration: none;
            color: #333;
        }
        .menu-btn:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 50px rgba(0,0,0,0.25);
        }
        .menu-btn.disabled {
            opacity: 0.5;
            cursor: not-allowed;
        }
        .menu-btn.disabled:hover {
            transform: none;
            box-shadow: 0 8px 30px rgba(0,0,0,0.15);
        }
        .menu-icon {
            font-size: 3em;
            margin-bottom: 15px;
        }
        .menu-title {
            font-size: 1.3em;
            font-weight: 600;
            margin-bottom: 8px;
        }
        .menu-desc {
            font-size: 0.95em;
            color: #666;
        }
        .footer {
            background: white;
            padding: 20px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 8px 30px rgba(0,0,0,0.15);
        }
        .logout-btn {
            background: #dc3545;
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1em;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        .logout-btn:hover {
            background: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>📚 Biblioteca Digital UNTEC</h1>
            <div class="user-info">
                Bienvenido, <strong><c:out value="${nombreUsuario}"/></strong>
                <c:choose>
                    <c:when test="${tipoUsuario == 'ADMIN'}">
                        <span class="badge badge-admin">👨‍💼 ADMIN</span>
                    </c:when>
                    <c:when test="${tipoUsuario == 'PROFESOR'}">
                        <span class="badge badge-profesor">👨‍🏫 PROFESOR</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge-estudiante">👨‍🎓 ESTUDIANTE</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Menú Principal -->
        <div class="menu-grid">
            <!-- Búsqueda de Libros (Todos) -->
            <a href="${pageContext.request.contextPath}/libros" class="menu-btn">
                <div class="menu-icon">📖</div>
                <div class="menu-title">Catálogo de Libros</div>
                <div class="menu-desc">Buscar y explorar libros</div>
            </a>

            <!-- Mis Préstamos (Todos) -->
            <a href="${pageContext.request.contextPath}/mis-prestamos" class="menu-btn">
                <div class="menu-icon">📋</div>
                <div class="menu-title">Mis Préstamos</div>
                <div class="menu-desc">Ver mi historial</div>
            </a>

            <!-- Gestión de Usuarios (Solo ADMIN) -->
            <c:if test="${tipoUsuario == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin-usuarios" class="menu-btn">
                    <div class="menu-icon">👥</div>
                    <div class="menu-title">Gestionar Usuarios</div>
                    <div class="menu-desc">CRUD de usuarios</div>
                </a>
            </c:if>

            <!-- Gestión de Libros (Solo ADMIN) -->
            <c:if test="${tipoUsuario == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin-libros" class="menu-btn">
                    <div class="menu-icon">📚</div>
                    <div class="menu-title">Gestionar Libros</div>
                    <div class="menu-desc">Agregar, editar, eliminar</div>
                </a>
            </c:if>

            <!-- Gestión de Préstamos (Solo ADMIN) -->
            <c:if test="${tipoUsuario == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin-prestamos" class="menu-btn">
                    <div class="menu-icon">📋</div>
                    <div class="menu-title">Gestionar Préstamos</div>
                    <div class="menu-desc">Aprobar, rechazar, devoluciones</div>
                </a>
            </c:if>
        </div>

        <!-- Footer -->
        <div class="footer">
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">🚪 Cerrar Sesión</a>
        </div>
    </div>
</body>
</html>
