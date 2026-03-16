@echo off
REM Script de inicio de Tomcat 10 y deployment de Biblioteca UNTEC
REM Asegúrate de que CATALINA_HOME esté configurado o edita la ruta siguiente:

setlocal enabledelayedexpansion

REM Configuración
set TOMCAT_HOME=D:\tomcat10
set APP_HOME=%~dp0
set WAR_FILE=%APP_HOME%target\biblioteca-untec.war
set WEBAPPS=%TOMCAT_HOME%\webapps

echo.
echo ======================================
echo  BIBLIOTECA DIGITAL UNTEC - Deploy
echo ======================================
echo.

REM Verificar que Tomcat existe
if not exist "%TOMCAT_HOME%\bin\startup.bat" (
    echo ❌ ERROR: Tomcat no encontrado en %TOMCAT_HOME%
    echo Edita TOMCAT_HOME en este script
    pause
    exit /b 1
)

REM Verificar que el WAR fue creado
if not exist "%WAR_FILE%" (
    echo ❌ ERROR: archivo biblioteca-untec.war no encontrado
    echo Ejecuta primero: mvn package
    pause
    exit /b 1
)

REM Copiar WAR a webapps de Tomcat
echo 📦 Copiando aplicacion a Tomcat...
copy "%WAR_FILE%" "%WEBAPPS%\biblioteca-untec.war"

if %errorlevel% equ 0 (
    echo ✓ Archivo WAR copiado correctamente
) else (
    echo ❌ Error al copiar el WAR
    pause
    exit /b 1
)

echo.
echo 🚀 Iniciando Tomcat 10...
echo.

REM Iniciar Tomcat
call "%TOMCAT_HOME%\bin\startup.bat"

REM Esperar a que se inicie
timeout /t 5 /nobreak

echo.
echo ======================================
echo ✓ Tomcat está iniciando...
echo.
echo 🌐 Abre tu navegador y ve a:
echo    http://localhost:8080/biblioteca-untec
echo.
echo 📝 Credenciales de prueba:
echo    Admin:      admin@untec.cl / admin123
echo    Estudiante: juan.perez@untec.cl / estudiante123
echo    Profesor:   maria.silva@untec.cl / profesor123
echo.
echo 📋 Logs en: %TOMCAT_HOME%\logs\catalina.out
echo ======================================
echo.

pause
