<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Historial de Préstamos - Biblioteca Digital UNTEC</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        /* Navbar */
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin: -20px -20px 20px -20px;
            padding-left: 20px;
            padding-right: 20px;
        }
        
        .navbar-container {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .navbar-brand h1 {
            font-size: 24px;
            font-weight: 600;
        }
        
        .navbar-user {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        
        .user-info .name {
            font-weight: 600;
            font-size: 14px;
        }
        
        .user-info .role {
            font-size: 12px;
            opacity: 0.9;
        }
        
        .logout-btn {
            background: rgba(255, 255, 255, 0.2);
            border: 1px solid white;
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background 0.3s;
        }
        
        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.3);
        }
        
        /* Main container */
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        
        h2 {
            color: #333;
            margin: 30px 0 20px 0;
            font-size: 22px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 10px;
        }
        
        /* Alerts */
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            border-left: 4px solid;
        }
        
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border-color: #28a745;
        }
        
        .alert-info {
            background-color: #d1ecf1;
            color: #0c5460;
            border-color: #bee5eb;
        }
        
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border-color: #f5c6cb;
        }
        
        /* Tabs */
        .tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            border-bottom: 2px solid #dee2e6;
        }
        
        .tab-button {
            padding: 12px 20px;
            background: #f8f9fa;
            border: none;
            border-bottom: 3px solid transparent;
            cursor: pointer;
            font-weight: 600;
            color: #666;
            transition: all 0.3s;
            border-radius: 0;
        }
        
        .tab-button:hover {
            background: #e9ecef;
        }
        
        .tab-button.active {
            background: white;
            border-bottom-color: #667eea;
            color: #667eea;
        }
        
        .tab-content {
            display: none;
        }
        
        .tab-content.active {
            display: block;
        }
        
        /* Tables */
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        
        table thead {
            background: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
        }
        
        table th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #333;
        }
        
        table td {
            padding: 12px 15px;
            border-bottom: 1px solid #dee2e6;
        }
        
        table tbody tr:hover {
            background-color: #f8f9fa;
        }
        
        /* Badge styles */
        .badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }
        
        .badge-activo {
            background-color: #d1ecf1;
            color: #0c5460;
        }
        
        .badge-vencido {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .badge-devuelto {
            background-color: #d4edda;
            color: #155724;
        }
        
        /* Stats */
        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 15px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        
        .stat-card h3 {
            color: #667eea;
            font-size: 28px;
            margin: 10px 0;
        }
        
        .stat-card p {
            color: #666;
            font-size: 12px;
            margin: 0;
        }
        
        /* Links */
        .back-link {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            display: inline-block;
            margin-bottom: 20px;
        }
        
        .back-link:hover {
            text-decoration: underline;
        }
        
        /* Days highlight */
        .days-positive {
            color: #28a745;
            font-weight: bold;
        }
        
        .days-negative {
            color: #dc3545;
            font-weight: bold;
        }
        
        .empty-message {
            background: white;
            padding: 30px;
            border-radius: 8px;
            text-align: center;
            color: #666;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="navbar-container">
            <div class="navbar-brand">
                <h1>📚 Biblioteca Digital UNTEC</h1>
            </div>
            <div class="navbar-user">
                <div class="user-info">
                    <div class="name">${sessionScope.nombreUsuario}</div>
                    <div class="role">
                        <c:choose>
                            <c:when test="${sessionScope.tipoUsuario == 'ADMIN'}">
                                👨‍💼 Administrador
                            </c:when>
                            <c:when test="${sessionScope.tipoUsuario == 'PROFESOR'}">
                                👨‍🏫 Profesor
                            </c:when>
                            <c:otherwise>
                                👨‍🎓 Estudiante
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Cerrar sesión</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <a href="${pageContext.request.contextPath}/inicio" class="back-link">← Volver al Inicio</a>
        
        <!-- Alerts -->
        <c:if test="${not empty okHistorial}">
            <div class="alert alert-success">
                ✓ ${okHistorial}
            </div>
        </c:if>
        <c:if test="${not empty errorHistorial}">
            <div class="alert alert-danger">
                ✗ ${errorHistorial}
            </div>
        </c:if>

        <h2>📖 Mi Historial de Préstamos</h2>

        <!-- Cálculos para estadísticas -->
        <c:set var="activos" value="0"/>
        <c:set var="devueltos" value="0"/>
        <c:set var="vencidos" value="0"/>
        <c:forEach var="p" items="${todosPrestamos}">
            <c:if test="${p.estado.name() == 'ACTIVO'}">
                <c:set var="activos" value="${activos + 1}"/>
            </c:if>
            <c:if test="${p.estado.name() == 'DEVUELTO'}">
                <c:set var="devueltos" value="${devueltos + 1}"/>
            </c:if>
            <c:if test="${p.estado.name() == 'VENCIDO'}">
                <c:set var="vencidos" value="${vencidos + 1}"/>
            </c:if>
        </c:forEach>

        <!-- Stats -->
        <div class="stats">
            <div class="stat-card">
                <p>Activos</p>
                <h3>${activos}</h3>
            </div>
            <div class="stat-card">
                <p>Devueltos</p>
                <h3>${devueltos}</h3>
            </div>
            <div class="stat-card">
                <p>Vencidos</p>
                <h3>${vencidos}</h3>
            </div>
            <div class="stat-card">
                <p>Total</p>
                <h3>${todosPrestamos.size()}</h3>
            </div>
        </div>

        <!-- Tabs -->
        <div class="tabs">
            <button class="tab-button active" onclick="mostrarTab('activos')">
                📋 Activos (${activos})
            </button>
            <button class="tab-button" onclick="mostrarTab('devueltos')">
                ✓ Devueltos (${devueltos})
            </button>
            <button class="tab-button" onclick="mostrarTab('vencidos')">
                ⚠️ Vencidos (${vencidos})
            </button>
        </div>

        <!-- Tab: Activos -->
        <div id="activos" class="tab-content active">
            <c:set var="hayActivos" value="false"/>
            <c:forEach var="prestamo" items="${todosPrestamos}">
                <c:if test="${prestamo.estado.name() == 'ACTIVO'}">
                    <c:set var="hayActivos" value="true"/>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${hayActivos}">
                    <table>
                        <thead>
                            <tr>
                                <th>Libro</th>
                                <th>Fecha Préstamo</th>
                                <th>Devolución Esperada</th>
                                <th>Días Restantes</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="prestamo" items="${todosPrestamos}">
                                <c:if test="${prestamo.estado.name() == 'ACTIVO'}">
                                    <tr>
                                        <td>
                                            <strong>${prestamo.libro.titulo}</strong><br>
                                            <small>${prestamo.libro.autor}</small>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaPrestamo}" 
                                                pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaDevolucionEsperada}" 
                                                pattern="dd/MM/yyyy" />
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${prestamo.diasRestantes >= 0}">
                                                    <span class="days-positive">${prestamo.diasRestantes} días</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="days-negative">${prestamo.diasAtraso} días atraso</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <span class="badge badge-activo">Activo</span>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-message">
                        📭 No tienes préstamos activos en este momento
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Tab: Devueltos -->
        <div id="devueltos" class="tab-content">
            <c:set var="hayDevueltos" value="false"/>
            <c:forEach var="prestamo" items="${todosPrestamos}">
                <c:if test="${prestamo.estado.name() == 'DEVUELTO'}">
                    <c:set var="hayDevueltos" value="true"/>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${hayDevueltos}">
                    <table>
                        <thead>
                            <tr>
                                <th>Libro</th>
                                <th>Fecha Préstamo</th>
                                <th>Devolución Esperada</th>
                                <th>Devolución Real</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="prestamo" items="${todosPrestamos}">
                                <c:if test="${prestamo.estado.name() == 'DEVUELTO'}">
                                    <tr>
                                        <td>
                                            <strong>${prestamo.libro.titulo}</strong><br>
                                            <small>${prestamo.libro.autor}</small>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaPrestamo}" 
                                                pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaDevolucionEsperada}" 
                                                pattern="dd/MM/yyyy" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaDevolucionReal}" 
                                                pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td>
                                            <span class="badge badge-devuelto">Devuelto</span>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-message">
                        📭 No tienes préstamos devueltos
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Tab: Vencidos -->
        <div id="vencidos" class="tab-content">
            <c:set var="hayVencidos" value="false"/>
            <c:forEach var="prestamo" items="${todosPrestamos}">
                <c:if test="${prestamo.estado.name() == 'VENCIDO'}">
                    <c:set var="hayVencidos" value="true"/>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${hayVencidos}">
                    <div class="alert alert-danger">
                        ⚠️ Tienes préstamos vencidos. Por favor, devuelve los libros a la brevedad.
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Libro</th>
                                <th>Fecha Préstamo</th>
                                <th>Devolución Esperada</th>
                                <th>Días Atraso</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="prestamo" items="${todosPrestamos}">
                                <c:if test="${prestamo.estado.name() == 'VENCIDO'}">
                                    <tr>
                                        <td>
                                            <strong>${prestamo.libro.titulo}</strong><br>
                                            <small>${prestamo.libro.autor}</small>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaPrestamo}" 
                                                pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${prestamo.fechaDevolucionEsperada}" 
                                                pattern="dd/MM/yyyy" />
                                        </td>
                                        <td>
                                            <span class="days-negative">${prestamo.diasAtraso} días</span>
                                        </td>
                                        <td>
                                            <span class="badge badge-vencido">Vencido</span>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-message">
                        ✓ ¡Felicidades! No tienes préstamos vencidos
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Widget de Notificaciones -->
    <%@ include file="notificaciones-widget.jsp" %>
</body>
</html>
