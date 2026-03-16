<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios - Biblioteca UNTEC</title>
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
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        .btn-danger:hover {
            background: #c82333;
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
        .badge-admin { background: #dc3545; color: white; }
        .badge-profesor { background: #fd7e14; color: white; }
        .badge-estudiante { background: #28a745; color: white; }
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
        .goto-dashboard {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }
        .goto-dashboard:hover {
            text-decoration: underline;
        }
        .multa-si {
            color: #dc3545;
            font-weight: 600;
        }
        .multa-no {
            color: #28a745;
            font-weight: 600;
        }
        .text-muted {
            color: #6c757d;
            font-size: 0.9em;
        }
        @media (max-width: 1024px) {
            table { font-size: 0.9em; }
            th, td { padding: 10px; }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>📋 Gestión de Usuarios</h1>
            <div class="header-controls">
                <div>
                    <p>Total de usuarios: <strong>${usuarios.size()}</strong></p>
                </div>
                <div>
                    <a href="<%= request.getContextPath() %>/admin-usuarios?accion=nueva" class="btn btn-primary">+ Nuevo Usuario</a>
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
        
        <!-- Tabla de usuarios -->
        <c:if test="${not empty usuarios}">
            <table>
                <thead>
                    <tr>
                        <th>RUT</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Tipo</th>
                        <th>Facultad 🏫</th>
                        <th>Carrera 📚</th>
                        <th>Año</th>
                        <th>Multa ⚠️</th>
                        <th>Monto 💰</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usuario" items="${usuarios}">
                        <tr>
                            <td>${usuario.rut}</td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${usuario.tipoUsuario == 'ADMIN'}">
                                        <span class="badge badge-admin">ADMIN</span>
                                    </c:when>
                                    <c:when test="${usuario.tipoUsuario == 'PROFESOR'}">
                                        <span class="badge badge-profesor">PROFESOR</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-estudiante">ESTUDIANTE</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty usuario.facultad}">
                                        ${usuario.facultad}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty usuario.carrera}">
                                        ${usuario.carrera}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="text-align: center;">
                                <c:choose>
                                    <c:when test="${not empty usuario.anioActual}">
                                        ${usuario.anioActual}º
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="text-align: center;">
                                <c:choose>
                                    <c:when test="${usuario.tieneMulta}">
                                        <span class="multa-si">✓ SÍ</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="multa-no">✗ NO</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="text-align: right;">
                                <c:choose>
                                    <c:when test="${usuario.tieneMulta && usuario.montoMulta > 0}">
                                        $<fmt:formatNumber value="${usuario.montoMulta}" minFractionDigits="2" maxFractionDigits="2" />
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">$0.00</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="actions">
                                    <a href="<%= request.getContextPath() %>/admin-usuarios?accion=editar&id=${usuario.id}" class="btn btn-small btn-edit">Editar</a>
                                    <a href="<%= request.getContextPath() %>/admin-usuarios?accion=eliminar&id=${usuario.id}" class="btn btn-small btn-delete" onclick="return confirm('¿Confirma eliminación?')">Eliminar</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <c:if test="${empty usuarios}">
            <div class="alert alert-error">
                ⚠️ No hay usuarios registrados en el sistema.
            </div>
        </c:if>
    </div>
</body>
</html>
