<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Libros - Biblioteca UNTEC</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1400px;
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
            font-size: 2em;
            margin-bottom: 10px;
        }
        .header-controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 0.95em;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .alert {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        table {
            width: 100%;
            background: white;
            border-collapse: collapse;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 8px 30px rgba(0,0,0,0.15);
        }
        thead {
            background: #667eea;
            color: white;
        }
        th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
        }
        td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        tbody tr:hover {
            background: #f8f9fa;
        }
        .badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
        }
        .badge-ing { background: #e3f2fd; color: #1976d2; }
        .badge-med { background: #f3e5f5; color: #7b1fa2; }
        .badge-edu { background: #e8f5e9; color: #388e3c; }
        .badge-com { background: #fff3e0; color: #f57c00; }
        .badge-der { background: #fce4ec; color: #c2185b; }
        .badge-tur { background: #e0f2f1; color: #00796b; }
        .badge-vet { background: #f1f8e9; color: #689f38; }
        .badge-gen { background: #ede7f6; color: #512da8; }
        
        .nivel-basico { background: #c8e6c9; color: #2e7d32; padding: 4px 8px; border-radius: 4px; font-size: 0.85em; }
        .nivel-intermedio { background: #fff9c4; color: #f57f17; padding: 4px 8px; border-radius: 4px; font-size: 0.85em; }
        .nivel-avanzado { background: #ffccbc; color: #d84315; padding: 4px 8px; border-radius: 4px; font-size: 0.85em; }
        
        .disponible { color: #28a745; font-weight: 600; }
        .no-disponible { color: #dc3545; font-weight: 600; }
        .text-muted { color: #6c757d; font-size: 0.9em; }
        
        .actions {
            display: flex;
            gap: 8px;
        }
        .btn-small {
            padding: 6px 12px;
            font-size: 0.85em;
        }
        .btn-edit {
            background: #007bff;
            color: white;
        }
        .btn-edit:hover {
            background: #0056b3;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
        }
        .btn-delete:hover {
            background: #c82333;
        }
        @media (max-width: 1024px) {
            table { font-size: 0.9em; }
            th, td { padding: 10px; }
            .actions { flex-direction: column; }
            .btn-small { width: 100%; }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>📚 Gestión de Libros</h1>
            <div class="header-controls">
                <div>
                    <p>Total de libros: <strong>${libros.size()}</strong></p>
                </div>
                <div>
                    <a href="<%= request.getContextPath() %>/admin/libro?accion=nuevo" class="btn btn-primary">+ Nuevo Libro</a>
                    <a href="<%= request.getContextPath() %>/dashboard" class="btn btn-secondary">Volver al Dashboard</a>
                </div>
            </div>
        </div>
        
        <!-- Mensajes -->
        <c:if test="${not empty param.mensaje}">
            <div class="alert alert-success">
                ✓ ${param.mensaje}
            </div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">
                ✗ ${param.error}
            </div>
        </c:if>
        
        <!-- Tabla de libros -->
        <c:if test="${not empty libros}">
            <table>
                <thead>
                    <tr>
                        <th>ISBN</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Especialidad 🎓</th>
                        <th>Código</th>
                        <th>Nivel ⭐</th>
                        <th>Disponible</th>
                        <th>Total</th>
                        <th>Año</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="libro" items="${libros}">
                        <tr>
                            <td><code>${libro.isbn}</code></td>
                            <td style="font-weight: 500;">${libro.titulo}</td>
                            <td>${libro.autor}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${libro.especialidad == 'INGENIERIA'}">
                                        <span class="badge badge-ing">Ingeniería</span>
                                    </c:when>
                                    <c:when test="${libro.especialidad == 'MEDICINA'}">
                                        <span class="badge badge-med">Medicina</span>
                                    </c:when>
                                    <c:when test="${libro.especialidad == 'EDUCACION'}">
                                        <span class="badge badge-edu">Educación</span>
                                    </c:when>
                                    <c:when test="${libro.especialidad == 'CIENCIAS_COMERCIALES'}">
                                        <span class="badge badge-com">Ciencias Com.</span>
                                    </c:when>
                                    <c:when test="${libro.especialidad == 'DERECHO'}">
                                        <span class="badge badge-der">Derecho</span>
                                    </c:when>
                                    <c:when test="${libro.especialidad == 'TURISMO'}">
                                        <span class="badge badge-tur">Turismo</span>
                                    </c:when>
                                    <c:when test="${libro.especialidad == 'VETERINARIA'}">
                                        <span class="badge badge-vet">Veterinaria</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-gen">General</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty libro.codigoCategoria}">
                                        <code>${libro.codigoCategoria}</code>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${libro.nivelRecomendado == 'BASICO'}">
                                        <span class="nivel-basico">Básico</span>
                                    </c:when>
                                    <c:when test="${libro.nivelRecomendado == 'INTERMEDIO'}">
                                        <span class="nivel-intermedio">Intermedio</span>
                                    </c:when>
                                    <c:when test="${libro.nivelRecomendado == 'AVANZADO'}">
                                        <span class="nivel-avanzado">Avanzado</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="text-align: center;">
                                <c:choose>
                                    <c:when test="${libro.cantidadDisponible > 0}">
                                        <span class="disponible">${libro.cantidadDisponible}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="no-disponible">0</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="text-align: center;">${libro.cantidadTotal}</td>
                            <td style="text-align: center;">
                                <c:choose>
                                    <c:when test="${not empty libro.anioPublicacion}">
                                        ${libro.anioPublicacion}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="actions">
                                    <a href="<%= request.getContextPath() %>/admin/libro?accion=editar&id=${libro.id}" class="btn btn-small btn-edit">Editar</a>
                                    <a href="<%= request.getContextPath() %>/admin/libro?accion=eliminar&id=${libro.id}" class="btn btn-small btn-delete" onclick="return confirm('¿Confirma eliminación?')">Eliminar</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <c:if test="${empty libros}">
            <div class="alert alert-error">
                ⚠️ No hay libros registrados en el catálogo.
            </div>
        </c:if>
    </div>
</body>
</html>
