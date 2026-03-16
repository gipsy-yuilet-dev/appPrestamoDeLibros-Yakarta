<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Préstamos - Biblioteca Digital UNTEC</title>
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
            max-width: 1200px;
            margin: 0 auto;
        }
        
        h2 {
            color: #333;
            margin-top: 30px;
            margin-bottom: 20px;
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
        
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border-color: #f5c6cb;
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
        
        /* Buttons */
        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background-color: #667eea;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #5568d3;
        }
        
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #218838;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
        }
        
        .btn-sm {
            padding: 6px 10px;
            font-size: 12px;
        }
        
        /* Modal */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }
        
        .modal.show {
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .modal-content {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
        }
        
        .modal-header {
            margin-bottom: 20px;
            border-bottom: 2px solid #f0f0f0;
            padding-bottom: 10px;
        }
        
        .modal-header h3 {
            color: #333;
            margin: 0;
        }
        
        .modal-body {
            margin-bottom: 20px;
        }
        
        .modal-body .form-group {
            margin-bottom: 15px;
        }
        
        .modal-body label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: 600;
            font-size: 14px;
        }
        
        .modal-body textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-family: inherit;
            font-size: 14px;
            resize: vertical;
            min-height: 100px;
        }
        
        .modal-footer {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }
        
        .close-modal {
            background-color: #6c757d;
            color: white;
        }
        
        .close-modal:hover {
            background-color: #5a6268;
        }
        
        /* Stats section */
        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        
        .stat-card h3 {
            color: #667eea;
            font-size: 32px;
            margin: 10px 0;
        }
        
        .stat-card p {
            color: #666;
            font-size: 14px;
        }
        
        /* Days overdue highlight */
        .days-overdue {
            color: #dc3545;
            font-weight: bold;
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
        <!-- Alerts -->
        <c:if test="${not empty okPrestamo}">
            <div class="alert alert-success">
                ✓ Préstamo devuelto correctamente
            </div>
        </c:if>
        <c:if test="${not empty errorPrestamo}">
            <div class="alert alert-danger">
                ✗ Error: ${errorPrestamo}
            </div>
        </c:if>

        <!-- Stats -->
        <div class="stats">
            <div class="stat-card">
                <p>Préstamos Activos</p>
                <h3>${prestamosActivos.size()}</h3>
            </div>
            <div class="stat-card">
                <p>Préstamos Vencidos</p>
                <h3>${prestamosVencidos.size()}</h3>
            </div>
            <div class="stat-card">
                <p>Total</p>
                <h3>${prestamosActivos.size() + prestamosVencidos.size()}</h3>
            </div>
        </div>

        <!-- Préstamos Activos -->
        <h2>📋 Préstamos Activos</h2>
        <c:choose>
            <c:when test="${not empty prestamosActivos}">
                <table>
                    <thead>
                        <tr>
                            <th>Usuario</th>
                            <th>Libro</th>
                            <th>Fecha Préstamo</th>
                            <th>Devolución Esperada</th>
                            <th>Días Restantes</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="prestamo" items="${prestamosActivos}">
                            <tr>
                                <td>
                                    <strong>${prestamo.usuario.nombre}</strong><br>
                                    <small>${prestamo.usuario.email}</small>
                                </td>
                                <td>
                                    <strong>${prestamo.libro.titulo}</strong><br>
                                    <small>ISBN: ${prestamo.libro.isbn}</small>
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
                                            ${prestamo.diasRestantes} días
                                        </c:when>
                                        <c:otherwise>
                                            <span class="days-overdue">${prestamo.diasAtraso} días atraso</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="badge badge-activo">Activo</span>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-success" 
                                        onclick="abrirModalDevolucion(${prestamo.id}, '${prestamo.libro.titulo}', '${prestamo.usuario.nombre}')">
                                        Devolver
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-success">
                    ✓ No hay préstamos activos en este momento
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Préstamos Vencidos -->
        <h2>⚠️ Préstamos Vencidos</h2>
        <c:choose>
            <c:when test="${not empty prestamosVencidos}">
                <table>
                    <thead>
                        <tr>
                            <th>Usuario</th>
                            <th>Libro</th>
                            <th>Fecha Préstamo</th>
                            <th>Devolución Esperada</th>
                            <th>Días Atraso</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="prestamo" items="${prestamosVencidos}">
                            <tr>
                                <td>
                                    <strong>${prestamo.usuario.nombre}</strong><br>
                                    <small>${prestamo.usuario.email}</small>
                                </td>
                                <td>
                                    <strong>${prestamo.libro.titulo}</strong><br>
                                    <small>ISBN: ${prestamo.libro.isbn}</small>
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
                                    <span class="days-overdue">${prestamo.diasAtraso} días</span>
                                </td>
                                <td>
                                    <span class="badge badge-vencido">Vencido</span>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-danger" 
                                        onclick="abrirModalDevolucion(${prestamo.id}, '${prestamo.libro.titulo}', '${prestamo.usuario.nombre}')">
                                        Devolver
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-success">
                    ✓ No hay préstamos vencidos en este momento
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Modal de Devolución -->
    <div id="modalDevolucion" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Registrar Devolución</h3>
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/admin/prestamos">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Libro:</label>
                        <input type="text" id="libroNombre" readonly style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div class="form-group">
                        <label>Usuario:</label>
                        <input type="text" id="usuarioNombre" readonly style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div class="form-group">
                        <label for="observaciones">Observaciones (estado del libro, daños, etc.):</label>
                        <textarea id="observaciones" name="observaciones" placeholder="Ej: Portada con marcas, páginas bien conservadas..."></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="accion" value="devolver">
                    <input type="hidden" name="idPrestamo" id="idPrestamo">
                    <button type="button" class="btn close-modal" onclick="cerrarModalDevolucion()">Cancelar</button>
                    <button type="submit" class="btn btn-success">Confirmar Devolución</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function abrirModalDevolucion(idPrestamo, libroTitulo, usuarioNombre) {
            document.getElementById('idPrestamo').value = idPrestamo;
            document.getElementById('libroNombre').value = libroTitulo;
            document.getElementById('usuarioNombre').value = usuarioNombre;
            document.getElementById('observaciones').value = '';
            document.getElementById('modalDevolucion').classList.add('show');
        }

        function cerrarModalDevolucion() {
            document.getElementById('modalDevolucion').classList.remove('show');
        }

        // Cerrar modal al hacer clic fuera
        document.getElementById('modalDevolucion').addEventListener('click', function(event) {
            if (event.target === this) {
                this.classList.remove('show');
            }
        });
    </script>
</body>
</html>
