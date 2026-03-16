<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${libro.titulo}" /> - Biblioteca Digital UNTEC</title>
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
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
        }
        
        /* Breadcrumb */
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
        
        /* Detalle del libro */
        .libro-detalle {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            overflow: hidden;
        }
        
        .libro-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            text-align: center;
        }
        
        .libro-header h1 {
            font-size: 32px;
            margin-bottom: 10px;
        }
        
        .libro-header .autor {
            font-size: 18px;
            opacity: 0.9;
        }
        
        .libro-body {
            padding: 40px;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
            margin-bottom: 30px;
        }
        
        .info-item {
            display: flex;
            flex-direction: column;
        }
        
        .info-item label {
            font-weight: 600;
            color: #666;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
        }
        
        .info-item .value {
            font-size: 18px;
            color: #333;
        }
        
        .descripcion-section {
            margin-top: 30px;
            padding-top: 30px;
            border-top: 2px solid #f0f0f0;
        }
        
        .descripcion-section h2 {
            color: #333;
            margin-bottom: 15px;
            font-size: 22px;
        }
        
        .descripcion-section p {
            color: #666;
            line-height: 1.8;
            font-size: 16px;
        }
        
        /* Status badge */
        .status-badge {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            border-radius: 50px;
            font-weight: 600;
            font-size: 16px;
        }
        
        .status-badge.disponible {
            background: #d4edda;
            color: #155724;
        }
        
        .status-badge.no-disponible {
            background: #f8d7da;
            color: #721c24;
        }
        
        /* Estadísticas */
        .stats {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin: 30px 0;
        }
        
        .stat-card {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        
        .stat-card .number {
            font-size: 32px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 5px;
        }
        
        .stat-card .label {
            font-size: 14px;
            color: #666;
        }
        
        /* Acciones */
        .acciones {
            margin-top: 30px;
            padding-top: 30px;
            border-top: 2px solid #f0f0f0;
            display: flex;
            gap: 15px;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
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
        
        .btn-disabled {
            background: #ccc;
            color: #999;
            cursor: not-allowed;
        }

        .btn-danger {
            background: #fbe9e9;
            color: #a12626;
        }

        .btn-outline {
            background: #eef1f6;
            color: #333;
        }

        .alert {
            padding: 14px 16px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-success {
            background: #e6f6ea;
            border: 1px solid #bfe4c8;
            color: #1b6f35;
        }

        .alert-error {
            background: #fdeaea;
            border: 1px solid #f3c2c2;
            color: #a12626;
        }

        .prestamo-panel {
            margin-top: 24px;
            background: #f8f9fc;
            border: 1px solid #e3e8f2;
            border-radius: 12px;
            padding: 24px;
            display: none;
        }

        .prestamo-panel.visible {
            display: block;
        }

        .prestamo-grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 18px;
        }

        .prestamo-field {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .prestamo-field.full {
            grid-column: 1 / -1;
        }

        .prestamo-field label {
            font-weight: 600;
            color: #555;
            font-size: 13px;
        }

        .prestamo-field input,
        .prestamo-field select,
        .prestamo-field textarea {
            border: 1px solid #d7dce5;
            border-radius: 10px;
            padding: 12px 14px;
            font-size: 14px;
            width: 100%;
        }

        .prestamo-field input[readonly] {
            background: #f0f3f8;
            color: #555;
        }

        .prestamo-field textarea {
            min-height: 100px;
            resize: vertical;
        }
    </style>
    <script>
        function togglePrestamoForm() {
            const panel = document.getElementById('prestamo-panel');
            if (panel) {
                panel.classList.toggle('visible');
            }
        }
    </script>
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
        <c:if test="${param.okAdmin == 'creado'}">
            <div class="alert alert-success">El libro fue registrado correctamente.</div>
        </c:if>
        <c:if test="${param.okAdmin == 'actualizado'}">
            <div class="alert alert-success">El libro fue actualizado correctamente.</div>
        </c:if>
        <c:if test="${param.prestamo == 'ok'}">
            <div class="alert alert-success">La solicitud de préstamo fue registrada correctamente.</div>
        </c:if>
        <c:if test="${param.prestamo == 'sin-stock'}">
            <div class="alert alert-error">No hay stock disponible para registrar el préstamo.</div>
        </c:if>
        <c:if test="${param.prestamo == 'plazo-invalido'}">
            <div class="alert alert-error">El plazo seleccionado no es válido para tu tipo de usuario.</div>
        </c:if>
        <c:if test="${param.prestamo == 'tiene-vencidos'}">
            <div class="alert alert-error">No puedes solicitar un nuevo préstamo mientras tengas préstamos vencidos.</div>
        </c:if>
        <c:if test="${param.prestamo == 'error'}">
            <div class="alert alert-error">Ocurrió un problema al registrar el préstamo. Intenta nuevamente.</div>
        </c:if>

        <!-- Breadcrumb dinámico -->
        <div class="breadcrumb">
            <a href="${pageContext.request.contextPath}/libros">Inicio</a> / 
            <c:if test="${origen == 'busqueda'}">
                <a href="${pageContext.request.contextPath}/buscar">Búsqueda</a> /
            </c:if>
            <c:out value="${libro.titulo}" />
        </div>
        
        <!-- Detalle del libro -->
        <div class="libro-detalle">
            <!-- Header con título -->
            <div class="libro-header">
                <h1><c:out value="${libro.titulo}" /></h1>
                <p class="autor">por <c:out value="${libro.autor}" /></p>
            </div>
            
            <!-- Cuerpo con detalles -->
            <div class="libro-body">
                <!-- Status principal -->
                <div style="text-align: center; margin-bottom: 30px;">
                    <c:if test="${disponible}">
                        <span class="status-badge disponible">
                            ✓ Disponible para préstamo
                        </span>
                    </c:if>
                    <c:if test="${!disponible}">
                        <span class="status-badge no-disponible">
                            ✗ No disponible actualmente
                        </span>
                    </c:if>
                </div>
                
                <!-- Estadísticas -->
                <div class="stats">
                    <div class="stat-card">
                        <div class="number"><c:out value="${libro.cantidadTotal}" /></div>
                        <div class="label">Copias Totales</div>
                    </div>
                    <div class="stat-card">
                        <div class="number"><c:out value="${libro.cantidadDisponible}" /></div>
                        <div class="label">Disponibles</div>
                    </div>
                    <div class="stat-card">
                        <div class="number"><c:out value="${prestados}" /></div>
                        <div class="label">En Préstamo</div>
                    </div>
                </div>
                
                <!-- Información del libro -->
                <div class="info-grid">
                    <div class="info-item">
                        <label>ISBN</label>
                        <div class="value"><c:out value="${libro.isbn}" /></div>
                    </div>
                    
                    <div class="info-item">
                        <label>Ubicación</label>
                        <div class="value">
                            <c:out value="${libro.ubicacion}" default="No especificada" />
                        </div>
                    </div>
                    
                    <c:if test="${not empty libro.editorial}">
                        <div class="info-item">
                            <label>Editorial</label>
                            <div class="value"><c:out value="${libro.editorial}" /></div>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty libro.anioPublicacion}">
                        <div class="info-item">
                            <label>Año de Publicación</label>
                            <div class="value"><c:out value="${libro.anioPublicacion}" /></div>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty libro.categoria}">
                        <div class="info-item">
                            <label>Categoría</label>
                            <div class="value"><c:out value="${libro.categoria}" /></div>
                        </div>
                    </c:if>
                </div>
                
                <!-- Descripción -->
                <c:if test="${not empty libro.descripcion}">
                    <div class="descripcion-section">
                        <h2>📖 Descripción</h2>
                        <p><c:out value="${libro.descripcion}" /></p>
                    </div>
                </c:if>
                
                <!-- Acciones -->
                <div class="acciones">
                    <c:if test="${disponible}">
                        <button type="button" class="btn btn-primary" onclick="togglePrestamoForm()">
                            📚 Solicitar Préstamo
                        </button>
                    </c:if>
                    <c:if test="${!disponible}">
                        <button class="btn btn-disabled" disabled>
                            Préstamo no disponible
                        </button>
                    </c:if>

                    <c:if test="${sessionScope.tipoUsuario == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/libro?accion=editar&id=${libro.id}" class="btn btn-outline">
                            ✏️ Editar libro
                        </a>
                        <form method="post" action="${pageContext.request.contextPath}/admin/libro"
                              onsubmit="return confirm('¿Deseas eliminar este libro del catálogo?');">
                            <input type="hidden" name="accion" value="eliminar" />
                            <input type="hidden" name="id" value="${libro.id}" />
                            <button type="submit" class="btn btn-danger">🗑 Eliminar</button>
                        </form>
                    </c:if>
                    
                    <c:if test="${origen == 'busqueda'}">
                        <a href="${pageContext.request.contextPath}/buscar" class="btn btn-secondary">
                            ← Volver a búsqueda
                        </a>
                    </c:if>
                    <c:if test="${origen != 'busqueda'}">
                        <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">
                            ← Volver al catálogo
                        </a>
                    </c:if>
                </div>

                <c:if test="${disponible}">
                    <div id="prestamo-panel" class="prestamo-panel ${param.prestamo == 'plazo-invalido' || param.prestamo == 'sin-stock' || param.prestamo == 'tiene-vencidos' || param.prestamo == 'error' ? 'visible' : ''}">
                        <h2 style="margin-bottom: 18px; color: #333;">📝 Solicitud de préstamo</h2>
                        <form method="post" action="${pageContext.request.contextPath}/prestamos/solicitar">
                            <input type="hidden" name="idLibro" value="${libro.id}" />

                            <div class="prestamo-grid">
                                <div class="prestamo-field">
                                    <label>Solicitante</label>
                                    <input type="text" value="${sessionScope.nombreUsuario}" readonly />
                                </div>

                                <div class="prestamo-field">
                                    <label>Rol</label>
                                    <input type="text" value="${sessionScope.tipoUsuario}" readonly />
                                </div>

                                <div class="prestamo-field">
                                    <label>Libro seleccionado</label>
                                    <input type="text" value="${libro.titulo}" readonly />
                                </div>

                                <div class="prestamo-field">
                                    <label>Stock disponible</label>
                                    <input type="text" value="${libro.cantidadDisponible}" readonly />
                                </div>

                                <div class="prestamo-field">
                                    <label for="plazoDias">Plazo del préstamo</label>
                                    <select id="plazoDias" name="plazoDias" required>
                                        <option value="">Selecciona un plazo</option>
                                        <option value="7">7 días</option>
                                        <option value="14">14 días</option>
                                        <c:if test="${sessionScope.tipoUsuario == 'PROFESOR' || sessionScope.tipoUsuario == 'ADMIN'}">
                                            <option value="21">21 días</option>
                                        </c:if>
                                        <c:if test="${sessionScope.tipoUsuario == 'PROFESOR'}">
                                            <option value="30">30 días</option>
                                        </c:if>
                                    </select>
                                </div>

                                <div class="prestamo-field full">
                                    <label for="observaciones">Observaciones</label>
                                    <textarea id="observaciones" name="observaciones" placeholder="Ejemplo: préstamo para estudio de programación orientada a objetos"></textarea>
                                </div>
                            </div>

                            <div class="acciones" style="margin-top: 20px; padding-top: 0; border-top: none;">
                                <button type="submit" class="btn btn-primary">Confirmar préstamo</button>
                                <button type="button" class="btn btn-secondary" onclick="togglePrestamoForm()">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Widget de Notificaciones -->
    <%@ include file="notificaciones-widget.jsp" %>
</body>
</html>
