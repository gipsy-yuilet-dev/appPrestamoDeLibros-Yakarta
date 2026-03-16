<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Biblioteca Digital UNTEC</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }
        
        .login-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            width: 100%;
            max-width: 400px;
            padding: 40px;
            animation: slideIn 0.4s ease-out;
        }
        
        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .logo {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .logo h1 {
            color: #667eea;
            font-size: 28px;
            margin-bottom: 5px;
        }
        
        .logo p {
            color: #666;
            font-size: 14px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }
        
        .form-group input[type="email"],
        .form-group input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 15px;
            transition: all 0.3s ease;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        .checkbox-group {
            display: flex;
            align-items: center;
            margin-bottom: 25px;
        }
        
        .checkbox-group input[type="checkbox"] {
            margin-right: 8px;
            width: 18px;
            height: 18px;
            cursor: pointer;
        }
        
        .checkbox-group label {
            font-size: 14px;
            color: #666;
            cursor: pointer;
            user-select: none;
        }
        
        .btn-login {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 8px;
            color: white;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5);
        }
        
        .btn-login:active {
            transform: translateY(0);
        }
        
        .error-message {
            background: #fee;
            border: 1px solid #fcc;
            color: #c33;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            display: flex;
            align-items: center;
            animation: shake 0.4s ease-in-out;
        }
        
        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-10px); }
            75% { transform: translateX(10px); }
        }
        
        .error-message::before {
            content: "⚠ ";
            font-size: 18px;
            margin-right: 8px;
        }
        
        .info-box {
            background: #e8f4f8;
            border: 1px solid #b8dce8;
            color: #31708f;
            padding: 15px;
            border-radius: 8px;
            margin-top: 25px;
            font-size: 13px;
            line-height: 1.6;
        }
        
        .info-box strong {
            display: block;
            margin-bottom: 8px;
            font-size: 14px;
        }
        
        .info-box ul {
            margin-left: 20px;
            margin-top: 8px;
        }
        
        .info-box li {
            margin-bottom: 4px;
        }
        
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        
        .back-link a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s ease;
        }
        
        .back-link a:hover {
            color: #764ba2;
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="logo">
            <h1>📚 Biblioteca UNTEC</h1>
            <p>Sistema de Gestión Digital</p>
        </div>
        
        <!-- Mostrar mensaje de error usando JSTL c:if -->
        <c:if test="${not empty error}">
            <div class="error-message">
                <c:out value="${error}" />
            </div>
        </c:if>
        
        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label for="email">Correo Electrónico</label>
                <!-- Usar c:out para mantener el email ingresado si hubo error -->
                <input 
                    type="email" 
                    id="email" 
                    name="email" 
                    value="<c:out value='${email}' default='' />"
                    placeholder="usuario@untec.cl"
                    required
                    autocomplete="email">
            </div>
            
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input 
                    type="password" 
                    id="password" 
                    name="password" 
                    placeholder="••••••••"
                    required
                    autocomplete="current-password">
            </div>
            
            <div class="checkbox-group">
                <input type="checkbox" id="recordar" name="recordar">
                <label for="recordar">Recordarme por 7 días</label>
            </div>
            
            <button type="submit" class="btn-login">Iniciar Sesión</button>
        </form>
        
        <div class="info-box">
            <strong>🔑 Usuarios de Prueba (credenciales por defecto):</strong>
            <ul>
                <li><b>Admin:</b> admin@untec.cl / admin123</li>
                <li><b>Estudiante:</b> juan.perez@untec.cl / estudiante123</li>
                <li><b>Profesor:</b> maria.silva@untec.cl / profesor123</li>
            </ul>
            <p style="margin-top: 10px; font-size: 0.9em; color: #555;">
                ℹ️ Estas credenciales se crean automáticamente al ejecutar schema.sql
            </p>
        </div>
        
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/">← Volver a la página principal</a>
        </div>
    </div>
</body>
</html>
