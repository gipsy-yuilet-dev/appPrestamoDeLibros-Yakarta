<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Libros - Biblioteca Digital UNTEC</title>
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
        
        .navbar-menu {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        
        .navbar-menu a {
            color: white;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 5px;
            transition: background 0.3s;
        }
        
        .navbar-menu a:hover {
            background: rgba(255, 255, 255, 0.2);
        }
        
        .user-info {
            font-size: 14px;
            opacity: 0.9;
        }
        
        /* Container */
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }
        
        /* Breadcrumbs */
        .breadcrumb {
            background: white;
            padding: 12px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
        }
        
        .breadcrumb a {
            color: #667eea;
            text-decoration: none;
        }
        
        .breadcrumb a:hover {
            text-decoration: underline;
        }
        
        /* Formulario de búsqueda */
        .search-box {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
        }
        
        .search-box h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 24px;
        }
        
        .search-form {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
        }
        
        .form-group label {
            font-weight: 500;
            margin-bottom: 8px;
            color: #555;
        }
        
        .form-group input[type="text"] {
            padding: 12px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 15px;
            transition: border-color 0.3s;
        }
        
        .form-group input[type="text"]:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .checkbox-group {
            display: flex;
            align-items: center;
            padding-top: 30px;
        }
        
        .checkbox-group input {
            margin-right: 8px;
            width: 18px;
            height: 18px;
        }
        
        .search-buttons {
            grid-column: 1 / -1;
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }
        
        .btn-secondary {
            background: #e0e0e0;
            color: #333;
        }
        
        .btn-secondary:hover {
            background: #d0d0d0;
        }
        
        /* Resultados */
        .results-header {
            background: white;
            padding: 20px 25px;
            border-radius: 12px 12px 0 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
        }
        
        .results-header h3 {
            color: #333;
            font-size: 20px;
            margin-bottom: 5px;
        }
        
        .results-info {
            color: #666;
            font-size: 14px;
        }
        
        .results-table {
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
            transition: background 0.2s;
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
        
        .btn-link {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
        }
        
        .btn-link:hover {
            text-decoration: underline;
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
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar">
        <div class="navbar-container">
            <div class="navbar-brand">
                <h1>📚 Biblioteca Digital UNTEC</h1>
            </div>
            <div class="navbar-menu">
                <a href="${pageContext.request.contextPath}/libros">Catálogo</a>
                <a href="${pageContext.request.contextPath}/buscar">Buscar</a>
                <span class="user-info">
                    <c:out value="${sessionScope.nombreUsuario}" default="Usuario" />
                </span>
                <a href="${pageContext.request.contextPath}/logout">Salir</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <!-- Breadcrumb -->
        <div class="breadcrumb">
            <a href="${pageContext.request.contextPath}/libros">Inicio</a> / Buscar Libros
        </div>
        
        <!-- Formulario de búsqueda -->
        <div class="search-box">
            <h2>🔍 Buscar Libros</h2>
            <form method="get" action="${pageContext.request.contextPath}/buscar" class="search-form">
                <div class="form-group">
                    <label for="titulo">Título</label>
                    <input type="text" 
                           id="titulo" 
                           name="titulo" 
                           value="<c:out value='${tituloBusqueda}' default='' />"
                           placeholder="Ej: Java, Python, Algoritmos...">
                </div>
                
                <div class="form-group">
                    <label for="autor">Autor</label>
                    <input type="text" 
                           id="autor" 
                           name="autor" 
                           value="<c:out value='${autorBusqueda}' default='' />"
                           placeholder="Ej: Bloch, Gamma, Knuth...">
                </div>
                
                <div class="checkbox-group">
                    <input type="checkbox" 
                           id="disponible" 
                           name="disponible" 
                           value="true"
                           <c:if test="${disponibleBusqueda == 'true'}">checked</c:if>>
                    <label for="disponible">Solo mostrar libros disponibles</label>
                </div>
                
                <div class="search-buttons">
                    <button type="submit" class="btn btn-primary">🔍 Buscar</button>
                    <button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/buscar'">
                        🔄 Limpiar
                    </button>
                </div>
            </form>
        </div>
        
        <!-- Resultados -->
        <c:if test="${not empty libros or not empty mensajeBusqueda}">
            <div class="results-header">
                <h3><c:out value="${mensajeBusqueda}" default="Resultados de búsqueda" /></h3>
                <div class="results-info">
                    <strong><c:out value="${totalResultados}" /></strong> libro(s) encontrado(s), 
                    <strong><c:out value="${disponibles}" /></strong> disponible(s)
                </div>
            </div>
            
            <div class="results-table">
                <c:if test="${empty libros}">
                    <div class="empty-state">
                        <div class="icon">📭</div>
                        <h3>No se encontraron resultados</h3>
                        <p>Intenta con otros criterios de búsqueda</p>
                    </div>
                </c:if>
                
                <c:if test="${not empty libros}">
                    <table>
                        <thead>
                            <tr>
                                <th>Título</th>
                                <th>Autor</th>
                                <th>ISBN</th>
                                <th>Disponibles</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="libro" items="${libros}">
                                <tr>
                                    <td><strong><c:out value="${libro.titulo}" /></strong></td>
                                    <td><c:out value="${libro.autor}" /></td>
                                    <td><c:out value="${libro.isbn}" /></td>
                                    <td>
                                        <c:out value="${libro.cantidadDisponible}" /> / 
                                        <c:out value="${libro.cantidadTotal}" />
                                    </td>
                                    <td>
                                        <c:if test="${libro.cantidadDisponible > 0}">
                                            <span class="badge badge-success">✓ Disponible</span>
                                        </c:if>
                                        <c:if test="${libro.cantidadDisponible == 0}">
                                            <span class="badge badge-danger">✗ No Disponible</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/detalle-libro?id=${libro.id}&origen=busqueda" 
                                           class="btn-link">
                                            Ver detalle →
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </c:if>
    </div>
</body>
</html>
