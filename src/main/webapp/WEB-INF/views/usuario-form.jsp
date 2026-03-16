<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:if test="${not empty usuario}">Editar</c:if><c:if test="${empty usuario}">Crear</c:if> Usuario - Biblioteca UNTEC</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
        }
        .card {
            background: white;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
        }
        h1 {
            color: #667eea;
            margin-bottom: 30px;
            font-size: 2em;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
        }
        input, select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1em;
            transition: all 0.3s;
        }
        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 30px;
        }
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1em;
            font-weight: 600;
            transition: all 0.3s;
        }
        .btn-save {
            background: #667eea;
            color: white;
        }
        .btn-save:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }
        .btn-cancel {
            background: #6c757d;
            color: white;
        }
        .btn-cancel:hover {
            background: #5a6268;
        }
        .alert {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .field-hint {
            font-size: 0.85em;
            color: #6c757d;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <h1>
                <c:if test="${not empty usuario}">✏️ Editar Usuario</c:if>
                <c:if test="${empty usuario}">➕ Crear Nuevo Usuario</c:if>
            </h1>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            
            <form method="post" action="<%= request.getContextPath() %>/admin-usuarios">
                <!-- Campo oculto para ID (si es edición) -->
                <c:if test="${not empty usuario}">
                    <input type="hidden" name="id" value="${usuario.id}">
                    <input type="hidden" name="accion" value="guardar">
                </c:if>
                <c:if test="${empty usuario}">
                    <input type="hidden" name="accion" value="crear">
                </c:if>
                
                <!-- RUT -->
                <div class="form-group">
                    <label for="rut">RUT *</label>
                    <input type="text" id="rut" name="rut" value="<c:if test="${not empty usuario}">${usuario.rut}</c:if>" required placeholder="12345678-9">
                    <div class="field-hint">Formato: 12345678-9</div>
                </div>
                
                <!-- Nombre -->
                <div class="form-group">
                    <label for="nombre">Nombre Completo *</label>
                    <input type="text" id="nombre" name="nombre" value="<c:if test="${not empty usuario}">${usuario.nombre}</c:if>" required placeholder="Juan Pérez García">
                </div>
                
                <!-- Email -->
                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" value="<c:if test="${not empty usuario}">${usuario.email}</c:if>" required placeholder="usuario@untec.cl">
                </div>
                
                <!-- Contraseña -->
                <c:if test="${empty usuario}">
                    <div class="form-group">
                        <label for="password">Contraseña *</label>
                        <input type="password" id="password" name="password" required placeholder="Contraseña segura">
                        <div class="field-hint">Mínimo 6 caracteres (en producción usar hash)</div>
                    </div>
                </c:if>
                
                <!-- Tipo de Usuario -->
                <div class="form-group">
                    <label for="tipoUsuario">Tipo de Usuario *</label>
                    <select id="tipoUsuario" name="tipoUsuario" required onchange="mostrarCamposEstudiante()">
                        <option value="">-- Seleccionar --</option>
                        <c:forEach var="tipo" items="${tiposUsuario}">
                            <option value="${tipo}" <c:if test="${not empty usuario && usuario.tipoUsuario == tipo}">selected</c:if>>
                                <c:choose>
                                    <c:when test="${tipo == 'ADMIN'}">👨‍💼 Administrador</c:when>
                                    <c:when test="${tipo == 'PROFESOR'}">👨‍🏫 Profesor</c:when>
                                    <c:otherwise>👨‍🎓 Estudiante</c:otherwise>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <!-- Facultad (Solo para estudiantes y profesores) -->
                <div class="form-group" id="groupFacultad" style="display: none;">
                    <label for="facultad">Facultad</label>
                    <select id="facultad" name="facultad" onchange="actualizarCarreras()">
                        <option value="">-- Seleccionar --</option>
                        <option value="Ingeniería" <c:if test="${usuario.facultad == 'Ingeniería'}">selected</c:if>>🏗️ Ingeniería</option>
                        <option value="Educación" <c:if test="${usuario.facultad == 'Educación'}">selected</c:if>>📚 Educación</option>
                        <option value="Medicina" <c:if test="${usuario.facultad == 'Medicina'}">selected</c:if>>⚕️ Medicina</option>
                        <option value="Veterinaria" <c:if test="${usuario.facultad == 'Veterinaria'}">selected</c:if>>🐾 Veterinaria</option>
                        <option value="Ciencias Comerciales" <c:if test="${usuario.facultad == 'Ciencias Comerciales'}">selected</c:if>>💼 Ciencias Comerciales</option>
                        <option value="Turismo" <c:if test="${usuario.facultad == 'Turismo'}">selected</c:if>>✈️ Turismo</option>
                        <option value="Derecho" <c:if test="${usuario.facultad == 'Derecho'}">selected</c:if>>⚖️ Derecho</option>
                    </select>
                </div>
                
                <!-- Carrera (Solo para estudiantes y profesores) -->
                <div class="form-group" id="groupCarrera" style="display: none;">
                    <label for="carrera">Carrera</label>
                    <input type="text" id="carrera" name="carrera" value="<c:if test="${not empty usuario}">${usuario.carrera}</c:if>" placeholder="Ej: Ingeniería en Informática">
                    <div class="field-hint">Nombre de la carrera académica</div>
                </div>
                
                <!-- Año Académico (Solo para estudiantes) -->
                <div class="form-group" id="groupAnioActual" style="display: none;">
                    <label for="anioActual">Año de Estudio</label>
                    <select id="anioActual" name="anioActual">
                        <option value="">-- Seleccionar --</option>
                        <option value="1" <c:if test="${usuario.anioActual == 1}">selected</c:if>>1º Año</option>
                        <option value="2" <c:if test="${usuario.anioActual == 2}">selected</c:if>>2º Año</option>
                        <option value="3" <c:if test="${usuario.anioActual == 3}">selected</c:if>>3º Año</option>
                        <option value="4" <c:if test="${usuario.anioActual == 4}">selected</c:if>>4º Año</option>
                        <option value="5" <c:if test="${usuario.anioActual == 5}">selected</c:if>>5º Año</option>
                    </select>
                </div>
                
                <!-- Multa (Solo para estudiantes) -->
                <div class="form-group" id="groupMulta" style="display: none;">
                    <label>
                        <input type="checkbox" name="tieneMulta" id="tieneMulta" 
                               <c:if test="${usuario.tieneMulta}">checked</c:if> onchange="mostrarMontoMulta()">
                        💸 ¿Tiene multa activa en la biblioteca?
                    </label>
                </div>
                
                <!-- Monto Multa (Solo si tiene multa) -->
                <div class="form-group" id="groupMontoMulta" style="display: none;">
                    <label for="montoMulta">Monto de Multa (USD)</label>
                    <input type="number" id="montoMulta" name="montoMulta" step="0.01" 
                           value="<c:if test="${not empty usuario && usuario.montoMulta}"> ${usuario.montoMulta}</c:if>" 
                           placeholder="0.00">
                </div>
                
                <!-- Botones -->
                <div class="form-actions">
                    <button type="submit" class="btn btn-save">
                        <c:if test="${not empty usuario}">💾 Actualizar</c:if>
                        <c:if test="${empty usuario}">➕ Crear Usuario</c:if>
                    </button>
                    <a href="<%= request.getContextPath() %>/admin-usuarios" class="btn btn-cancel">❌ Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        // Mostrar/ocultar campos según tipo de usuario
        function mostrarCamposEstudiante() {
            const tipoUsuario = document.getElementById('tipoUsuario').value;
            const groupFacultad = document.getElementById('groupFacultad');
            const groupCarrera = document.getElementById('groupCarrera');
            const groupAnioActual = document.getElementById('groupAnioActual');
            const groupMulta = document.getElementById('groupMulta');
            
            // Mostrar facultad y carrera para estudiantes y profesores
            if (tipoUsuario === 'ESTUDIANTE' || tipoUsuario === 'PROFESOR') {
                groupFacultad.style.display = 'block';
                groupCarrera.style.display = 'block';
            } else {
                groupFacultad.style.display = 'none';
                groupCarrera.style.display = 'none';
            }
            
            // Mostrar año académico solo para estudiantes
            if (tipoUsuario === 'ESTUDIANTE') {
                groupAnioActual.style.display = 'block';
                groupMulta.style.display = 'block';
            } else {
                groupAnioActual.style.display = 'none';
                groupMulta.style.display = 'none';
                document.getElementById('tieneMulta').checked = false;
            }
        }
        
        // Mostrar/ocultar monto de multa
        function mostrarMontoMulta() {
            const tieneMulta = document.getElementById('tieneMulta').checked;
            const groupMontoMulta = document.getElementById('groupMontoMulta');
            groupMontoMulta.style.display = tieneMulta ? 'block' : 'none';
        }
        
        // Inicializar visiblidad al cargar
        document.addEventListener('DOMContentLoaded', function() {
            mostrarCamposEstudiante();
            mostrarMontoMulta();
        });
    </script>
</body>
</html>
