<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catálogo de Libros - Biblioteca Digital UNTEC</title>
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
        }
        
        /* Navbar */
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        .navbar-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
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
        
        .user-info {
            text-align: right;
        }
        
        .user-info .name {
            font-weight: 600;
            font-size: 14px;
        }
        
        .user-info .role {
            font-size: 12px;
            opacity: 0.9;
        }
        
        .btn-logout {
            background: rgba(255, 255, 255, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.3);
            color: white;
            padding: 8px 16px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            transition: all 0.3s ease;
        }
        
        .btn-logout:hover {
            background: rgba(255, 255, 255, 0.3);
        }
        
        /* Contenedor principal */
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }
        
        /* Estadísticas */
        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            display: flex;
            align-items: center;
            gap: 15px;
        }
        
        .stat-icon {
            font-size: 40px;
            width: 60px;
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 10px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        
        .stat-info h3 {
            font-size: 32px;
            color: #333;
            margin-bottom: 5px;
        }
        
        .stat-info p {
            font-size: 14px;
            color: #666;
        }
        
        /* Sección de libros */
        .section-header {
            background: white;
            padding: 20px 25px;
            border-radius: 12px 12px 0 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 20px;
        }
        
        .section-header h2 {
            color: #333;
            font-size: 24px;
        }

        .actions-header {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            border: none;
            border-radius: 8px;
            padding: 10px 16px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-secondary {
            background: #eef1f6;
            color: #333;
        }

        .btn-danger {
            background: #fbe9e9;
            color: #a12626;
        }

        .btn-sm {
            padding: 8px 12px;
            font-size: 12px;
        }

        .alert {
            margin-bottom: 20px;
            padding: 14px 16px;
            border-radius: 10px;
            font-size: 14px;
        }

        .alert-success {
            background: #e6f6ea;
            color: #1b6f35;
            border: 1px solid #bfe4c8;
        }

        .alert-error {
            background: #fdeaea;
            color: #a12626;
            border: 1px solid #f3c2c2;
        }

        .row-actions {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }

        .inline-form {
            margin: 0;
        }
        
        /* Tabla de libros */
        .libros-table {
            background: white;
            border-radius: 0 0 12px 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            overflow: hidden;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        thead th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            font-size: 14px;
        }
        
        tbody tr {
            border-bottom: 1px solid #f0f0f0;
            transition: background 0.2s ease;
        }
        
        tbody tr:hover {
            background: #f8f9fa;
        }
        
        tbody tr:last-child {
            border-bottom: none;
        }
        
        tbody td {
            padding: 15px;
            font-size: 14px;
            color: #333;
        }
        
        .badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .badge-success {
            background: #d4edda;
            color: #155724;
        }
        
        .badge-danger {
            background: #f8d7da;
            color: #721c24;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .empty-state .icon {
            font-size: 64px;
            margin-bottom: 20px;
            opacity: 0.5;
        }
        
        .empty-state h3 {
            font-size: 20px;
            margin-bottom: 10px;
        }
        
        .empty-state p {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <!-- Navbar con información del usuario -->
    <nav class="navbar">
        <div class="navbar-container">
            <div class="navbar-brand">
                <h1>📚 Biblioteca Digital UNTEC</h1>
            </div>
            <div class="navbar-user">
                <div style="display: flex; gap: 15px; align-items: center; margin-right: 20px;">
                    <a href="${pageContext.request.contextPath}/libros" style="text-decoration: none; color: white;">Catálogo</a>
                    <a href="${pageContext.request.contextPath}/buscar" style="text-decoration: none; color: white;">Buscar</a>
                </div>
                <div class="user-info">
                    <div class="name">
                        <!-- Usar c:out para mostrar nombre de usuario de forma segura -->
                        <c:out value="${sessionScope.nombreUsuario}" default="Usuario" />
                    </div>
                    <div class="role">
                        <!-- Usar c:out para mostrar el rol -->
                        <c:out value="${sessionScope.tipoUsuario}" default="INVITADO" />
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                    Cerrar Sesión
                </a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <c:if test="${param.okAdmin == 'eliminado'}">
            <div class="alert alert-success">El libro fue eliminado correctamente del catálogo.</div>
        </c:if>
        <c:if test="${param.errorAdmin == 'id-invalido'}">
            <div class="alert alert-error">No se recibió un identificador válido para la operación administrativa.</div>
        </c:if>
        <c:if test="${param.errorAdmin == 'libro-no-encontrado'}">
            <div class="alert alert-error">El libro solicitado no fue encontrado.</div>
        </c:if>
        <c:if test="${param.errorAdmin == 'no-eliminado'}">
            <div class="alert alert-error">No fue posible eliminar el libro solicitado.</div>
        </c:if>

        <!-- Estadísticas usando variables del request -->
        <div class="stats-container">
            <div class="stat-card">
                <div class="stat-icon">📚</div>
                <div class="stat-info">
                    <h3><c:out value="${totalLibros}" /></h3>
                    <p>Total de Libros</p>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">✅</div>
                <div class="stat-info">
                    <h3><c:out value="${librosDisponibles}" /></h3>
                    <p>Libros Disponibles</p>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">📖</div>
                <div class="stat-info">
                    <h3><c:out value="${totalCopias}" /></h3>
                    <p>Copias Totales</p>
                </div>
            </div>
        </div>
        
        <!-- Catálogo de libros -->
        <div class="section-header">
            <h2>Catálogo Completo</h2>
            <c:if test="${sessionScope.tipoUsuario == 'ADMIN'}">
                <div class="actions-header">
                    <a href="${pageContext.request.contextPath}/admin/libro?accion=nuevo" class="btn btn-primary">
                        ➕ Nuevo libro
                    </a>
                </div>
            </c:if>
        </div>
        
        <div class="libros-table">
            <!-- Usar c:if para mostrar mensaje cuando no hay libros -->
            <c:if test="${empty libros}">
                <div class="empty-state">
                    <div class="icon">📭</div>
                    <h3>No hay libros disponibles</h3>
                    <p>El catálogo está vacío. Verifica que la base de datos esté inicializada.</p>
                </div>
            </c:if>
            
            <!-- Usar c:if para mostrar tabla solo si hay libros -->
            <c:if test="${not empty libros}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>Autor</th>
                            <th>ISBN</th>
                            <th>Copias</th>
                            <th>Disponibles</th>
                            <th>Estado</th>
                            <c:if test="${sessionScope.tipoUsuario == 'ADMIN'}">
                                <th>Acciones</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Usar c:forEach para iterar sobre la lista de libros -->
                        <c:forEach var="libro" items="${libros}">
                            <tr>
                                <td><c:out value="${libro.id}" /></td>
                                <td>
                                    <strong>
                                        <a href="${pageContext.request.contextPath}/detalle-libro?id=${libro.id}" 
                                           style="color: #667eea; text-decoration: none;">
                                            <c:out value="${libro.titulo}" />
                                        </a>
                                    </strong>
                                </td>
                                <td><c:out value="${libro.autor}" /></td>
                                <td><c:out value="${libro.isbn}" /></td>
                                <td><c:out value="${libro.cantidadTotal}" /></td>
                                <td><c:out value="${libro.cantidadDisponible}" /></td>
                                <td>
                                    <!-- Usar c:if para mostrar badge según disponibilidad -->
                                    <c:if test="${libro.cantidadDisponible > 0}">
                                        <span class="badge badge-success">✓ Disponible</span>
                                    </c:if>
                                    <c:if test="${libro.cantidadDisponible == 0}">
                                        <span class="badge badge-danger">✗ No Disponible</span>
                                    </c:if>
                                </td>
                                <c:if test="${sessionScope.tipoUsuario == 'ADMIN'}">
                                    <td>
                                        <div class="row-actions">
                                            <a href="${pageContext.request.contextPath}/admin/libro?accion=editar&id=${libro.id}" class="btn btn-secondary btn-sm">
                                                Editar
                                            </a>
                                            <form method="post" action="${pageContext.request.contextPath}/admin/libro" class="inline-form"
                                                  onsubmit="return confirm('¿Seguro que deseas eliminar este libro del catálogo?');">
                                                <input type="hidden" name="accion" value="eliminar" />
                                                <input type="hidden" name="id" value="${libro.id}" />
                                                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                            </form>
                                        </div>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

    <!-- Widget de Notificaciones -->
    <%@ include file="notificaciones-widget.jsp" %>
</body>
</html>
