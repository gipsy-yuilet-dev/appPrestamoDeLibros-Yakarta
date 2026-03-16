<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
  Componente de Notificaciones para incluir en páginas principales.
  Se auto-carga al incluir este archivo.
-->

<div id="notificacionesContainer" style="position: fixed; top: 70px; right: 20px; z-index: 999; max-width: 450px;">
    <div id="notificacionesList"></div>
</div>

<style>
    .notificacion {
        background: white;
        border-left: 4px solid;
        padding: 15px;
        margin-bottom: 10px;
        border-radius: 6px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        animation: slideInRight 0.3s ease-out;
        display: flex;
        align-items: flex-start;
        gap: 12px;
    }
    
    @keyframes slideInRight {
        from {
            opacity: 0;
            transform: translateX(400px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
    
    .notificacion-icono {
        font-size: 24px;
        flex-shrink: 0;
        margin-top: 2px;
    }
    
    .notificacion-contenido {
        flex: 1;
    }
    
    .notificacion-titulo {
        font-weight: 600;
        margin-bottom: 4px;
        font-size: 14px;
    }
    
    .notificacion-mensaje {
        font-size: 13px;
        margin-bottom: 8px;
        line-height: 1.4;
    }
    
    .notificacion-cerrar {
        align-self: center;
        background: none;
        border: none;
        color: #999;
        cursor: pointer;
        font-size: 18px;
        padding: 0;
        width: 24px;
        height: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    
    .notificacion-cerrar:hover {
        color: #666;
    }
    
    .notificacion.danger {
        border-left-color: #dc3545;
        background-color: #fff5f5;
    }
    
    .notificacion.warning {
        border-left-color: #ffc107;
        background-color: #fffaf0;
    }
    
    .notificacion.success {
        border-left-color: #28a745;
        background-color: #f0fdf4;
    }
    
    .notificacion.info {
        border-left-color: #17a2b8;
        background-color: #f0f9ff;
    }
    
    .notificacion.danger .notificacion-titulo {
        color: #dc3545;
    }
    
    .notificacion.danger .notificacion-mensaje {
        color: #721c24;
    }
    
    .notificacion.warning .notificacion-titulo {
        color: #f59e0b;
    }
    
    .notificacion.warning .notificacion-mensaje {
        color: #92400e;
    }
    
    .notificacion.success .notificacion-titulo {
        color: #28a745;
    }
    
    .notificacion.success .notificacion-mensaje {
        color: #155724;
    }
    
    .notificacion.info .notificacion-titulo {
        color: #17a2b8;
    }
    
    .notificacion.info .notificacion-mensaje {
        color: #0c5460;
    }
    
    /* Notificaciones badge (contador en navbar) */
    .notificaciones-badge {
        display: inline-block;
        background: #dc3545;
        color: white;
        border-radius: 50%;
        width: 24px;
        height: 24px;
        line-height: 24px;
        text-align: center;
        font-size: 12px;
        font-weight: bold;
        margin-left: 5px;
    }
</style>

<script>
    /**
     * Carga y muestra notificaciones del usuario.
     * Se ejecuta cada 5 minutos y al cargar la página.
     */
    (function() {
        // Verificar si hay sesión activa
        const usuarioEnSesion = '<%= session.getAttribute("usuarioId") %>';
        if (!usuarioEnSesion || usuarioEnSesion === 'null') {
            return; // No mostrar notificaciones si no hay sesión
        }

        // Auto-ejecutar al cargar
        cargarNotificaciones();

        // Recargar cada 5 minutos
        setInterval(cargarNotificaciones, 300000);

        function cargarNotificaciones() {
            fetch('<%= request.getContextPath() %>/api/notificaciones')
                .then(response => response.json())
                .then(data => {
                    if (data.success && data.notificaciones) {
                        mostrarNotificaciones(data.notificaciones);
                        actualizarBadge(data.totalAlertas);
                    }
                })
                .catch(error => {
                    console.warn('Error al cargar notificaciones:', error);
                });
        }

        function mostrarNotificaciones(notificaciones) {
            const container = document.getElementById('notificacionesList');
            
            // Limpiar notificaciones anteriores
            container.innerHTML = '';

            if (!notificaciones || notificaciones.length === 0) {
                return;
            }

            notificaciones.forEach((notif, index) => {
                const notifElement = crearElementoNotificacion(notif, index);
                container.appendChild(notifElement);
            });
        }

        function crearElementoNotificacion(notif, index) {
            const div = document.createElement('div');
            div.className = 'notificacion ' + (notif.nivel || 'info');
            div.setAttribute('role', 'alert');
            
            let titulo = '';
            if (notif.tipo === 'VENCIDO') {
                titulo = '⚠️ Préstamo Vencido';
            } else if (notif.tipo === 'PROXIMO_VENCER') {
                titulo = '⏰ Próximo a Vencer';
            }

            const contenido = `
                <div class="notificacion-icono">${notif.icono || '📘'}</div>
                <div class="notificacion-contenido">
                    <div class="notificacion-titulo">${titulo}</div>
                    <div class="notificacion-mensaje">${notif.mensaje}</div>
                </div>
                <button class="notificacion-cerrar" onclick="this.parentElement.remove()" aria-label="Cerrar">
                    ×
                </button>
            `;
            
            div.innerHTML = contenido;

            // Auto-cerrar en 8 segundos (solo para avisos moderados)
            if (notif.tipo === 'PROXIMO_VENCER') {
                setTimeout(() => {
                    if (div.parentElement) {
                        div.remove();
                    }
                }, 8000);
            }

            return div;
        }

        function actualizarBadge(totalAlertas) {
            // Buscar y actualizar badge en navbar si existe
            const badge = document.querySelector('.notificaciones-badge');
            if (badge && totalAlertas > 0) {
                badge.textContent = totalAlertas;
                badge.style.display = 'inline-block';
            } else if (badge) {
                badge.style.display = 'none';
            }
        }

        // Exponemos función global para recargar manualmente
        window.recargarNotificaciones = cargarNotificaciones;
    })();
</script>
