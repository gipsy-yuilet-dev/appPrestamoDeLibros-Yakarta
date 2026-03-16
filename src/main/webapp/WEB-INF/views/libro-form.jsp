<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:choose><c:when test="${modoFormulario == 'editar'}">Editar libro</c:when><c:otherwise>Nuevo libro</c:otherwise></c:choose> - Biblioteca Digital UNTEC</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            padding: 30px 20px;
        }
        .container {
            max-width: 980px;
            margin: 0 auto;
        }
        .card {
            background: white;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.10);
            overflow: hidden;
        }
        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 28px 32px;
        }
        .card-header h1 {
            font-size: 28px;
            margin-bottom: 8px;
        }
        .card-header p {
            opacity: 0.92;
        }
        .card-body {
            padding: 32px;
        }
        .alert {
            border-radius: 10px;
            padding: 14px 16px;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .alert-error {
            background: #fdeaea;
            color: #a12626;
            border: 1px solid #f3c2c2;
        }
        .grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 20px;
        }
        .field {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }
        .field.full {
            grid-column: 1 / -1;
        }
        label {
            font-weight: 600;
            color: #333;
        }
        input, textarea {
            border: 1px solid #d7dce5;
            border-radius: 10px;
            padding: 12px 14px;
            font-size: 14px;
            width: 100%;
        }
        textarea {
            min-height: 120px;
            resize: vertical;
        }
        .actions {
            display: flex;
            gap: 14px;
            margin-top: 28px;
            flex-wrap: wrap;
        }
        .btn {
            border: none;
            border-radius: 10px;
            text-decoration: none;
            padding: 12px 20px;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
        }
        .btn-primary {
            color: white;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .btn-secondary {
            color: #333;
            background: #ebedf2;
        }
        select {
            border: 1px solid #d7dce5;
            border-radius: 10px;
            padding: 12px 14px;
            font-size: 14px;
            width: 100%;
            background: white;
        }
        .especialidad-badge {
            display: inline-block;
            background: #e8f1f5;
            color: #0066cc;
            padding: 4px 8px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 600;
            margin-top: 4px;
        }
        @media (max-width: 768px) {
            .grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h1>
                <c:choose>
                    <c:when test="${modoFormulario == 'editar'}">✏️ Editar libro</c:when>
                    <c:otherwise>➕ Registrar nuevo libro</c:otherwise>
                </c:choose>
            </h1>
            <p>Gestión administrativa del catálogo de libros.</p>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>Error:</strong> <c:out value="${error}" />
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/admin/libro">
                <input type="hidden" name="accion" value="${modoFormulario == 'editar' ? 'actualizar' : 'crear'}" />
                <c:if test="${modoFormulario == 'editar'}">
                    <input type="hidden" name="id" value="${libro.id}" />
                </c:if>

                <div class="grid">
                    <div class="field">
                        <label for="isbn">ISBN</label>
                        <input type="text" id="isbn" name="isbn" value="<c:out value='${libro.isbn}' />" maxlength="13" required />
                    </div>

                    <div class="field">
                        <label for="titulo">Título</label>
                        <input type="text" id="titulo" name="titulo" value="<c:out value='${libro.titulo}' />" required />
                    </div>

                    <div class="field">
                        <label for="autor">Autor</label>
                        <input type="text" id="autor" name="autor" value="<c:out value='${libro.autor}' />" required />
                    </div>

                    <div class="field">
                        <label for="editorial">Editorial</label>
                        <input type="text" id="editorial" name="editorial" value="<c:out value='${libro.editorial}' />" />
                    </div>

                    <div class="field">
                        <label for="anioPublicacion">Año de publicación</label>
                        <input type="number" id="anioPublicacion" name="anioPublicacion" value="<c:out value='${libro.anioPublicacion}' />" min="0" />
                    </div>

                    <div class="field">
                        <label for="categoria">Categoría</label>
                        <input type="text" id="categoria" name="categoria" value="<c:out value='${libro.categoria}' />" />
                    </div>

                    <div class="field">
                        <label for="especialidad">Especialidad 📚</label>
                        <select id="especialidad" name="especialidad" onchange="actualizarCodigoCategoriaAutomatico()" required>
                            <option value="">-- Seleccionar especialidad --</option>
                            <option value="INGENIERIA" <c:if test="${libro.especialidad == 'INGENIERIA'}">selected</c:if>>Ingeniería (ING)</option>
                            <option value="EDUCACION" <c:if test="${libro.especialidad == 'EDUCACION'}">selected</c:if>>Educación (EDU)</option>
                            <option value="MEDICINA" <c:if test="${libro.especialidad == 'MEDICINA'}">selected</c:if>>Medicina (MED)</option>
                            <option value="VETERINARIA" <c:if test="${libro.especialidad == 'VETERINARIA'}">selected</c:if>>Veterinaria (VET)</option>
                            <option value="CIENCIAS_COMERCIALES" <c:if test="${libro.especialidad == 'CIENCIAS_COMERCIALES'}">selected</c:if>>Ciencias Comerciales (COM)</option>
                            <option value="TURISMO" <c:if test="${libro.especialidad == 'TURISMO'}">selected</c:if>>Turismo (TUR)</option>
                            <option value="DERECHO" <c:if test="${libro.especialidad == 'DERECHO'}">selected</c:if>>Derecho (DER)</option>
                            <option value="GENERAL" <c:if test="${libro.especialidad == 'GENERAL'}">selected</c:if>>General (GEN)</option>
                        </select>
                    </div>

                    <div class="field">
                        <label for="codigoCategoria">Código de categoría</label>
                        <input type="text" id="codigoCategoria" name="codigoCategoria" value="<c:out value='${libro.codigoCategoria}' />" placeholder="Ej: ING-001" maxlength="10" />
                        <small style="color: #666; margin-top: 4px;">Se auto-genera con especialidad. Ej: ING-001, MED-005</small>
                    </div>

                    <div class="field">
                        <label for="nivelRecomendado">Nivel recomendado ⭐</label>
                        <select id="nivelRecomendado" name="nivelRecomendado" required>
                            <option value="">-- Seleccionar nivel --</option>
                            <option value="BASICO" <c:if test="${libro.nivelRecomendado == 'BASICO'}">selected</c:if>>Básico (Introducción)</option>
                            <option value="INTERMEDIO" <c:if test="${libro.nivelRecomendado == 'INTERMEDIO'}">selected</c:if>>Intermedio (Desarrollo)</option>
                            <option value="AVANZADO" <c:if test="${libro.nivelRecomendado == 'AVANZADO'}">selected</c:if>>Avanzado (Especialización)</option>
                        </select>
                    </div>

                    <div class="field">
                        <label for="cantidadTotal">Cantidad total</label>
                        <input type="number" id="cantidadTotal" name="cantidadTotal" value="<c:out value='${libro.cantidadTotal}' />" min="0" required />
                    </div>

                    <div class="field">
                        <label for="cantidadDisponible">Cantidad disponible</label>
                        <input type="number" id="cantidadDisponible" name="cantidadDisponible" value="<c:out value='${libro.cantidadDisponible}' />" min="0" required />
                    </div>

                    <div class="field full">
                        <label for="ubicacion">Ubicación</label>
                        <input type="text" id="ubicacion" name="ubicacion" value="<c:out value='${libro.ubicacion}' />" />
                    </div>

                    <div class="field full">
                        <label for="descripcion">Descripción</label>
                        <textarea id="descripcion" name="descripcion"><c:out value='${libro.descripcion}' /></textarea>
                    </div>
                </div>

                <div class="actions">
                    <button type="submit" class="btn btn-primary">
                        <c:choose>
                            <c:when test="${modoFormulario == 'editar'}">💾 Guardar cambios</c:when>
                            <c:otherwise>✅ Crear libro</c:otherwise>
                        </c:choose>
                    </button>
                    <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">← Volver al catálogo</a>
                    <c:if test="${modoFormulario == 'editar'}">
                        <a href="${pageContext.request.contextPath}/detalle-libro?id=${libro.id}" class="btn btn-secondary">Ver detalle</a>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Mapa de especialidades a sus prefijos de código
    const prefijosEspecialidad = {
        'INGENIERIA': 'ING',
        'EDUCACION': 'EDU',
        'MEDICINA': 'MED',
        'VETERINARIA': 'VET',
        'CIENCIAS_COMERCIALES': 'COM',
        'TURISMO': 'TUR',
        'DERECHO': 'DER',
        'GENERAL': 'GEN'
    };

    // Auto-generar código de categoría cuando cambia especialidad
    function actualizarCodigoCategoriaAutomatico() {
        const especialidad = document.getElementById('especialidad').value;
        const codigoField = document.getElementById('codigoCategoria');
        
        if (especialidad && prefijosEspecialidad[especialidad]) {
            const prefijo = prefijosEspecialidad[especialidad];
            // Generar número secuencial simple (001, 002, etc.)
            // En modo edición, esto se puede sobrescribir
            const numeroActual = codigoField.value;
            if (!numeroActual || !numeroActual.startsWith(prefijo)) {
                // Si no hay código o es de otra especialidad, proponer uno nuevo
                codigoField.value = prefijo + '-001';
                codigoField.placeholder = prefijo + '-XXX';
            }
        }
    }

    // Inicializar campos al cargar la página
    document.addEventListener('DOMContentLoaded', function() {
        const especialidadField = document.getElementById('especialidad');
        if (especialidadField.value) {
            actualizarCodigoCategoriaAutomatico();
        }
    });
</script>
</body>
</html>
