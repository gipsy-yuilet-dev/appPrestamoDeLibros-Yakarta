<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Biblioteca Digital UNTEC</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .header {
            background: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }
        .header h1 {
            color: #667eea;
            font-size: 2.5em;
            margin-bottom: 10px;
        }
        .user-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 15px;
            padding-top: 15px;
            border-top: 2px solid #eee;
        }
        .user-data {
            font-size: 0.95em;
            color: #666;
        }
        .user-badge {
            display: inline-block;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 600;
            margin-left: 15px;
        }
        .badge-admin {
            background: #dc3545;
            color: white;
        }
        .badge-profesor {
            background: #fd7e14;
            color: white;
        }
        .badge-estudiante {
            background: #28a745;
            color: white;
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 25px;
            margin-bottom: 30px;
        }
        .card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.15);
            transition: transform 0.3s, box-shadow 0.3s;
            border-left: 5px solid;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 50px rgba(0,0,0,0.25);
        }
        .card-admin { border-left-color: #dc3545; }
        .card-profesor { border-left-color: #fd7e14; }
        .card-estudiante { border-left-color: #28a745; }
        .card-info { border-left-color: #17a2b8; }
        
        .card-header {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
        }
        .card-icon {
            font-size: 2.5em;
            margin-right: 15px;
        }
        .card-title {
            font-size: 1.2em;
            color: #333;
            font-weight: 600;
        }
        .card-subtitle {
            font-size: 0.85em;
            color: #999;
            margin-top: 3px;
        }
        .card-content {
            margin: 20px 0;
        }
        .stat {
            display: flex;
            justify-content: space-between;
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }
        .stat:last-child {
            border-bottom: none;
        }
        .stat-label {
            color: #666;
            font-size: 0.95em;
        }
        .stat-value {
            font-weight: bold;
            font-size: 1.1em;
            color: #667eea;
        }
        .card-footer {
            margin-top: 20px;
            padding-top: 15px;
            border-top: 1px solid #eee;
        }
        .btn-primary, .btn-secondary {
            display: inline-block;
            padding: 10px 18px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: 600;
            font-size: 0.9em;
            margin-right: 10px;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-secondary {
            background: #f0f0f0;
            color: #333;
            border: 1px solid #ddd;
        }
        .btn-secondary:hover {
            background: #e0e0e0;
        }
        .alert {
            background: #fff3cd;
            border-left: 4px solid #ffc107;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            color: #856404;
        }
        .footer-nav {
            text-align: center;
            margin-top: 40px;
            padding: 20px;
        }
        .footer-nav a {
            display: inline-block;
            margin: 10px 15px;
            color: white;
            text-decoration: none;
            font-weight: 600;
            transition: transform 0.2s;
        }
        .footer-nav a:hover {
            transform: scale(1.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- HEADER -->
        <div class="header">
            <h1>📚 Biblioteca Digital UNTEC</h1>
            <div class="user-info">
                <div class="user-data">
                    Bienvenido, <strong><c:out value="${usuario.nombre}"/></strong> 
                    <c:choose>
                        <c:when test="${tipoUsuario == 'ADMIN'}">
                            <span class="user-badge badge-admin">👨‍💼 ADMINISTRADOR</span>
                        </c:when>
                        <c:when test="${tipoUsuario == 'PROFESOR'}">
                            <span class="user-badge badge-profesor">👨‍🏫 PROFESOR</span>
                        </c:when>
                        <c:otherwise>
                            <span class="user-badge badge-estudiante">👤 ESTUDIANTE</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <a href="${contextPath}/logout" style="color: #667eea; text-decoration: none; font-weight: bold;">🚪 Cerrar sesión</a>
                </div>
            </div>
        </div>

        <!-- DASHBOARD PARA ADMINISTRADOR -->
        <c:if test="${tipoUsuario == 'ADMIN'}">
            <h2 style="color: white; margin-bottom: 20px; font-size: 1.8em;">🔧 Panel de Administración</h2>
            
            <div class="dashboard-grid">
                <!-- Gestión de Libros -->
                <div class="card card-admin">
                    <div class="card-header">
                        <div class="card-icon">📚</div>
                        <div>
                            <div class="card-title">Gestión de Libros</div>
                            <div class="card-subtitle">Administrar catálogo</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Total de libros:</span>
                            <span class="stat-value"><c:out value="${totalLibros}"/></span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Categorías activas:</span>
                            <span class="stat-value">5</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Stock total:</span>
                            <span class="stat-value">+100</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/admin/libro" class="btn-primary">Ir a Gestión</a>
                        <a href="${contextPath}/libros" class="btn-secondary">Ver Catálogo</a>
                    </div>
                </div>

                <!-- Gestión de Usuarios -->
                <div class="card card-admin">
                    <div class="card-header">
                        <div class="card-icon">👥</div>
                        <div>
                            <div class="card-title">Gestión de Usuarios</div>
                            <div class="card-subtitle">Administrar cuentas</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Estudiantes:</span>
                            <span class="stat-value">95</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Profesores:</span>
                            <span class="stat-value">20</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Bloqueados:</span>
                            <span class="stat-value">2</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/admin/usuarios" class="btn-primary">Ir a Gestión</a>
                        <a href="${contextPath}/admin/prestamos" class="btn-secondary">Ver Deudas</a>
                    </div>
                </div>

                <!-- Gestión de Préstamos & Devoluciones -->
                <div class="card card-admin">
                    <div class="card-header">
                        <div class="card-icon">📋</div>
                        <div>
                            <div class="card-title">Préstamos & Devoluciones</div>
                            <div class="card-subtitle">Control completo</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Activos:</span>
                            <span class="stat-value"><c:out value="${totalActivos}"/></span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Vencidos:</span>
                            <span class="stat-value" style="color: #dc3545;"><c:out value="${totalVencidos}"/></span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Este mes:</span>
                            <span class="stat-value">47</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/admin/prestamos" class="btn-primary">Registrar Devolución</a>
                        <a href="${contextPath}/mi-historial" class="btn-secondary">Ver Historial</a>
                    </div>
                </div>

                <!-- Estadísticas -->
                <div class="card card-info">
                    <div class="card-header">
                        <div class="card-icon">📊</div>
                        <div>
                            <div class="card-title">Estadísticas</div>
                            <div class="card-subtitle">Reportes del sistema</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Actividad (hoy):</span>
                            <span class="stat-value">12</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Préstamos (mes):</span>
                            <span class="stat-value">47</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Tasa devolución:</span>
                            <span class="stat-value">98%</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/admin/reportes" class="btn-primary">Ver Reportes</a>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- DASHBOARD PARA PROFESOR -->
        <c:if test="${tipoUsuario == 'PROFESOR'}">
            <h2 style="color: white; margin-bottom: 20px; font-size: 1.8em;">👨‍🏫 Panel de Profesor</h2>
            
            <div class="dashboard-grid">
                <!-- Mi Catálogo de Libros -->
                <div class="card card-profesor">
                    <div class="card-header">
                        <div class="card-icon">📖</div>
                        <div>
                            <div class="card-title">Catálogo Académico</div>
                            <div class="card-subtitle">Acceso a sección de referencias</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Período de préstamo:</span>
                            <span class="stat-value">30 días</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Renovaciones:</span>
                            <span class="stat-value">hasta 3 veces</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Libros de ref:</span>
                            <span class="stat-value">45 títulos</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/libros?seccion=referencias" class="btn-primary">Explorar Referencias</a>
                        <a href="${contextPath}/buscar" class="btn-secondary">Buscar</a>
                    </div>
                </div>

                <!-- Mis Préstamos Activos -->
                <div class="card card-profesor">
                    <div class="card-header">
                        <div class="card-icon">📚</div>
                        <div>
                            <div class="card-title">Mis Préstamos</div>
                            <div class="card-subtitle">Libros en uso</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Activos:</span>
                            <span class="stat-value"><c:out value="${prestamosMios}"/></span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Límite disponible:</span>
                            <span class="stat-value"><c:out value="${10 - prestamosMios}"/> libros</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Próximos a vencer:</span>
                            <span class="stat-value">2</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/mi-historial" class="btn-primary">Ver Historial</a>
                        <a href="${contextPath}/libros" class="btn-secondary">Solicitar Más</a>
                    </div>
                </div>

                <!-- Solicitar Nuevos Recursos -->
                <div class="card card-info">
                    <div class="card-header">
                        <div class="card-icon">✏️</div>
                        <div>
                            <div class="card-title">Solicitar Recursos</div>
                            <div class="card-subtitle">Para tus clases</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Solicitudes pendientes:</span>
                            <span class="stat-value">0</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Aprobadas este mes:</span>
                            <span class="stat-value">3</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Tiempo respuesta:</span>
                            <span class="stat-value">24-48h</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/solicitar-libro" class="btn-primary">Nueva Solicitud</a>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- DASHBOARD PARA ESTUDIANTE -->
        <c:if test="${tipoUsuario == 'ESTUDIANTE'}">
            <h2 style="color: white; margin-bottom: 20px; font-size: 1.8em;">👤 Mi Panel</h2>
            
            <div class="dashboard-grid">
                <!-- Explorar Catálogo -->
                <div class="card card-estudiante">
                    <div class="card-header">
                        <div class="card-icon">🔍</div>
                        <div>
                            <div class="card-title">Explorar Catálogo</div>
                            <div class="card-subtitle">Descubre nuestros libros</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Total de libros:</span>
                            <span class="stat-value">100+</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Disponibles ahora:</span>
                            <span class="stat-value">87</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Categorías:</span>
                            <span class="stat-value">12</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/libros" class="btn-primary">Ver Catálogo Completo</a>
                        <a href="${contextPath}/buscar" class="btn-secondary">Buscar Libro</a>
                    </div>
                </div>

                <!-- Mis Préstamos Activos -->
                <div class="card card-estudiante">
                    <div class="card-header">
                        <div class="card-icon">📚</div>
                        <div>
                            <div class="card-title">Mis Préstamos</div>
                            <div class="card-subtitle">Estado de tus libros</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Activos:</span>
                            <span class="stat-value"><c:out value="${prestamosMios}"/></span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Período:</span>
                            <span class="stat-value"><c:out value="${periodoPrestamo}"/> días</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Devueltos este mes:</span>
                            <span class="stat-value">7</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/mi-historial" class="btn-primary">Ver Historial Completo</a>
                    </div>
                </div>

                <!-- Notificaciones -->
                <div class="card card-info">
                    <div class="card-header">
                        <div class="card-icon">🔔</div>
                        <div>
                            <div class="card-title">Notificaciones Activas</div>
                            <div class="card-subtitle">Próximas a vencer</div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="stat">
                            <span class="stat-label">Alertas activas:</span>
                            <span class="stat-value">2</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Vence en 2 días:</span>
                            <span class="stat-value">1 libro</span>
                        </div>
                        <div class="stat">
                            <span class="stat-label">Renovaciones:</span>
                            <span class="stat-value"><c:out value="${renovacionesDisponibles}"/> disponible</span>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="${contextPath}/mi-historial" class="btn-primary">Ver Detalles</a>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- NAVEGACIÓN RÁPIDA -->
        <div class="footer-nav">
            <a href="${contextPath}/">🏠 Inicio</a>
            <a href="${contextPath}/libros">📚 Catálogo</a>
            <a href="${contextPath}/buscar">🔍 Buscar</a>
            <a href="${contextPath}/mi-historial">📖 Mi Historial</a>
            <a href="${contextPath}/logout">🚪 Salir</a>
        </div>
    </div>
</body>
</html>
