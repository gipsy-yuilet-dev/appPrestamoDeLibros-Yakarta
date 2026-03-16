<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error 500 - Error del servidor</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .error-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            padding: 60px;
            text-align: center;
            max-width: 700px;
        }
        .error-code {
            font-size: 8em;
            font-weight: bold;
            background: linear-gradient(135deg, #dc3545 0%, #c82333 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 20px;
        }
        h1 {
            color: #333;
            margin-bottom: 15px;
            font-size: 2em;
        }
        p {
            color: #666;
            font-size: 1.1em;
            margin-bottom: 20px;
            line-height: 1.6;
        }
        .error-details {
            background: #f8d7da;
            border-left: 4px solid #dc3545;
            padding: 15px;
            margin: 20px 0;
            text-align: left;
            border-radius: 5px;
        }
        .error-details strong {
            color: #721c24;
        }
        .btn {
            display: inline-block;
            padding: 15px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: transform 0.3s;
            margin-top: 20px;
        }
        .btn:hover {
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">500</div>
        <h1>Error del Servidor</h1>
        <p>
            Lo sentimos, ha ocurrido un error interno en el servidor. 
            Nuestro equipo ha sido notificado y estamos trabajando para solucionarlo.
        </p>
        
        <%-- Log detallado en servidor (solo en logs, no en HTML) --%>
        <% if (exception != null) {
               request.getServletContext().log("Error 500 capturado:", exception);
           }
        %>

        <p style="font-size: 0.9em; color: #666; margin-top: 20px;">
            Si el problema persiste, contacte al administrador.
        </p>

        <a href="${pageContext.request.contextPath}/" class="btn">Volver al Inicio</a>
    </div>
</body>
</html>
