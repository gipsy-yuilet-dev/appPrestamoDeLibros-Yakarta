# 🚀 GUÍA PASO A PASO: Desplegar Biblioteca Digital en Local

**Documento para Trainer | Tiempo estimado: 15 minutos**

---

## 📋 Pre-requisitos (Verificar ANTES de comenzar)

Abre PowerShell o CMD y ejecuta:

```bash
# 1. Verificar Java (debe ser 11 o superior)
java -version
# Output esperado: "java version "11.0.x" o superior"

# 2. Verificar Maven (debe ser 3.6 o superior)
mvn -version
# Output esperado: "Apache Maven 3.6.x"

# 3. Verificar Tomcat está instalado
dir %CATALINA_HOME%
# Output: muestra carpetas bin, conf, webapps, logs, etc.
```

**Si algo falta:**
- Java: Descarga desde https://www.oracle.com/java/technologies/downloads/
- Maven: Descarga desde https://maven.apache.org/download.cgi
- Tomcat: Descarga desde https://tomcat.apache.org/download-10.cgi

---

## ✅ PASO 1: Compilar el Proyecto (3 minutos)

### **En Windows (PowerShell o CMD):**

```bash
# Navega a la carpeta del proyecto
cd F:\RespaldoMelek\CursoJavaTalentoDigital\javaProyectosTalentoDigital\tareas\bibliotecaUntecModuloV\untec

# Compilar y generar WAR
mvn clean package
```

### **En Linux/Mac:**

```bash
cd ~/Ruta/al/proyecto/untec
mvn clean package
```

### **Output esperado:**

```
[INFO] Scanning for projects...
[INFO] Building Biblioteca Digital UNTEC 1.0.0
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ biblioteca-digital-untec ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ biblioteca-digital-untec ---
[INFO] Compiling 25 source files with javac [debug target 17] to target\classes
[INFO] 
[INFO] --- surefire:3.2.5:test (default-test) @ biblioteca-digital-untec ---
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.086 s
[INFO]  
[INFO] --- war:3.4.0:war (default-war) @ biblioteca-digital-untec ---
[INFO] Building war: target/biblioteca-untec.war
[INFO] 
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXX s
```

**✅ Si ves "BUILD SUCCESS", continúa al PASO 2.**

**❌ Si hay error**, ejecuta:
```bash
mvn clean    # Borra caché
mvn package  # Reintenta
```

---

## ✅ PASO 2: Copiar WAR a Tomcat (1 minuto)

### **Windows (PowerShell o CMD):**

```bash
# Copia el WAR a la carpeta webapps de Tomcat
copy target\biblioteca-untec.war %CATALINA_HOME%\webapps\

# Verifica que se copió
dir %CATALINA_HOME%\webapps\biblioteca-untec.war
```

### **Linux/Mac:**

```bash
cp target/biblioteca-untec.war $CATALINA_HOME/webapps/
ls $CATALINA_HOME/webapps/biblioteca-untec.war
```

---

## ✅ PASO 3: Iniciar Tomcat (2 minutos)

### **Windows:**

**Opción A: Desde CMD/PowerShell**
```bash
# Inicia Tomcat
%CATALINA_HOME%\bin\startup.bat

# Verás una ventana negra (consola de Tomcat)
# Espera ~10 segundos hasta ver:
# "INFO: Server startup in XXX milliseconds"
```

**Opción B: Doble-click directo**
1. Abre Explorador de Archivos
2. Ve a: `C:\Program Files\apache-tomcat-10.1.xx\bin\`
3. Doble-click en: `startup.bat`

### **Linux/Mac:**

```bash
# Inicia Tomcat
$CATALINA_HOME/bin/startup.sh

# Monitorea los logs en tiempo real
tail -f $CATALINA_HOME/logs/catalina.out

# Deberías ver:
# "✓ APLICACIÓN LISTA PARA USAR"
# "Accede a: http://localhost:8080/biblioteca-untec"
```

### **Verificar que Tomcat inició (puedes cerrar la consola después):**

```bash
# En otra terminal, verifica que el puerto 8080 está activo
netstat -ano | findstr :8080     (Windows)
lsof -i :8080                      (Linux/Mac)

# Output: java.exe escuchando en puerto 8080 ✓
```

---

## ✅ PASO 4: Acceder a la Aplicación en Navegador (1 minuto)

### **Abre tu navegador favorito y ve a:**

```
http://localhost:8080/biblioteca-untec/
```

### **Deberías ver:**

![Página de Bienvenida]
```
📚 Biblioteca Digital UNTEC
Sistema de Diagnóstico - Módulo 5

✓ Estado del Servidor
  - Servidor está funcionando correctamente
  - Servidor: localhost:8080
  - Aplicación (Context Path): /biblioteca-untec

✓ Estado de Base de Datos
  - Conexión a base de datos exitosa
  - ✓ Base de datos inicializada
  - Libros registrados: 5

[🏠 Inicio] [🔐 Login] [📚 Catálogo] [🔍 Buscar]
```

**⚠️ Si NO ves esto:**
- Espera 5 segundos más (Tomcat está inicializando)
- Recarga la página (F5 o Ctrl+R)
- Verifica en logs de Tomcat: `%CATALINA_HOME%\logs\catalina.out`

---

## ✅ PASO 5: Probar Login (2 minutos)

### **Click en el botón "🔐 Login"**

Deberías ver un formulario:
```
📚 Biblioteca UNTEC
Sistema de Gestión Digital

[Email: _________________]
[Contraseña: _____________]
[☐ Recordarme por 7 días]

[Iniciar Sesión]

🔑 Usuarios de Prueba:
  • Admin: admin@untec.cl / admin123
  • Estudiante: juan.perez@untec.cl / estudiante123
  • Profesor: maria.silva@untec.cl / profesor123
```

### **Ingresa:**
- **Email:** `admin@untec.cl`
- **Contraseña:** `admin123`
- Click: **Iniciar Sesión**

### **Deberías ser redirigido al catálogo:**

```
📚 Biblioteca Digital UNTEC

[Catálogo] [Buscar]  [Admin Sistema] [Cerrar Sesión]

📊 Estadísticas:
  📚 5 Total de Libros
  ✅ 5 Libros Disponibles
  📖 5 Copias Totales

📋 Catálogo Completo
┌─────────────────────────────────────────────────────┐
│ ID │ Título                    │ Autor          │ ... │
├────┼───────────────────────────┼────────────────┼─────┤
│ 1  │ Effective Java            │ Joshua Bloch   │ ... │
│ 2  │ Head First Design Pattern │ Eric Freeman   │ ... │
│ 3  │ Clean Code                │ Robert C. Mart │ ... │
│ 4  │ Spring in Action          │ Craig Walls    │ ... │
│ 5  │ Learning SQL              │ Alan Beaulieu  │ ... │
└─────────────────────────────────────────────────────┘
```

**✅ ¡Éxito! La aplicación está funcionando.**

---

## ✅ PASO 6: Probar Búsqueda Avanzada (1 minuto)

### **Click en el tab "🔍 Buscar"**

Deberías ver un formulario con:
```
Título:     [_________________]  (ej: Java)
Autor:      [_________________]  (ej: Bloch)
☐ Solo mostrar libros disponibles

[🔍 Buscar] [🔄 Limpiar]
```

### **Prueba una búsqueda:**

1. Escribe `Java` en el campo "Título"
2. Click: **🔍 Buscar**

### **Resultado:**

Deberías ver 2 libros con "Java" en el título:
- Effective Java
- Learning SQL

---

## ✅ PASO 7: Ver Logs de Tomcat (Opcional - para diagnóstico)

### **Ubicación de logs:**

- **Windows:** `%CATALINA_HOME%\logs\catalina.out` o `catalina-YYYY-MM-DD.log`
- **Linux/Mac:** `$CATALINA_HOME/logs/catalina.out`

### **Abre el archivo con un editor y busca:**

```
========================================
🚀 Inicializando aplicación: biblioteca-untec
========================================
✓ Conexión a base de datos: OK
✓ Tablas de BD ya existen. Saltando inicialización.
✓ Total de libros en catálogo: 5
✓ Total de usuarios registrados: 3
========================================
✓ APLICACIÓN LISTA PARA USAR
  Accede a: http://localhost:8080/biblioteca-untec
========================================
```

Esto significa que el listener `DatabaseInitializer` se ejecutó correctamente.

---

## 🛑 DETENER TOMCAT (Cuando termines)

### **Windows:**

```bash
# Opción 1: Desde la consola de Tomcat
# Click en el botón de cerrar o presiona Ctrl+C

# Opción 2: Desde CMD/PowerShell
%CATALINA_HOME%\bin\shutdown.bat
```

### **Linux/Mac:**

```bash
$CATALINA_HOME/bin/shutdown.sh
```

---

## 🔧 Troubleshooting Rápido

| Problema | Solución |
|----------|----------|
| **"Puerto 8080 en uso"** | Cambia puerto en `$CATALINA_HOME/conf/server.xml`:<br/>`<Connector port="9090" ...>` |
| **"Table 'usuarios' doesn't exist"** | Espera 10 segundos más<br/>Recarga página (F5) |
| **"BUILD FAILURE" en Maven** | Borra cache: `mvn clean`<br/>Luego: `mvn package` |
| **Página en blanco** | Verifica logs de Tomcat<br/>Revisa que es `http://localhost:8080/biblioteca-untec/`<br/>(nota la barra al final) |
| **"No se ve login.jsp"** | Asegúrate de estar autenticado<br/>Si no: el filtro AuthFilter te redirige a `/login` |

---

## 📊 Arquitectura Verificada (Lecciones 5 & 6)

### **✅ Lección 5: MVC**

```
REQUEST
  ↓
[AuthFilter]  ← Verifica autenticación
  ↓
[Servlet (Controller)]  ← Procesa lógica
  ├→ Obtiene datos del DAO (Model)
  └→ Prepara request.attributes
     ↓
[JSP (View)]  ← Renderiza HTML
  ├→ Lee request.attributes
  └→ Usa JSTL para lógica
     ↓
RESPONSE (HTML)
```

**Archivos clave:**
- **Controller:** `cl.untec.biblioteca.controller.*Servlet.java`
- **Model:** `cl.untec.biblioteca.dao.impl.*DAOImpl.java`
- **View:** `src/main/webapp/WEB-INF/views/*.jsp`

### **✅ Lección 6: Despliegue**

```
1. Fuente Java (.java)
   ↓
2. Compilación Maven (javac)
   ↓
3. Empaquetado WAR (target/biblioteca-untec.war)
   ↓
4. Despliegue en Tomcat (webapps/)
   ↓
5. Servidor servlet desempaqueta
   ↓
6. Listener inicializa BD
   ↓
7. Aplicación lista
```

**Archivos clave:**
- **Configuración:** `pom.xml`, `web.xml`
- **WAR generado:** `target/biblioteca-untec.war`
- **Listener (Auto-init):** `cl.untec.biblioteca.util.DatabaseInitializer.java`

---

## ✅ Checklist Final (Antes de presentar)

- [ ] Java 11+ instalado (`java -version`)
- [ ] Maven 3.6+ instalado (`mvn -version`)
- [ ] Tomcat 10+ instalado (`%CATALINA_HOME%` existe)
- [ ] `mvn clean package` ejecutado exitosamente
- [ ] WAR copiado a `webapps/`
- [ ] Tomcat iniciado (`startup.bat/sh`)
- [ ] Accedo a `http://localhost:8080/biblioteca-untec/`
- [ ] Veo página de diagnóstico (BD inicializada)
- [ ] Login funciona con `admin@untec.cl / admin123`
- [ ] Catálogo muestra 5 libros
- [ ] Búsqueda funciona (buscar "Java")
- [ ] Logout funciona

---

## 📞 Soporte Rápido

Si algo no funciona:

1. **Verifica los logs:**
   ```bash
   tail -f %CATALINA_HOME%\logs\catalina.out   (Windows)
   tail -f $CATALINA_HOME/logs/catalina.out    (Linux/Mac)
   ```

2. **Reinicia Tomcat:**
   ```bash
   shutdown.bat/sh
   startup.bat/sh
   ```

3. **Recompila (si cambiaste código):**
   ```bash
   mvn clean package
   copy target\biblioteca-untec.war %CATALINA_HOME%\webapps\
   ```

4. **Revisa que el puerto sea correcto:**
   ```bash
   netstat -ano | findstr :8080   (Windows)
   ```

---

**¡Listo! Tu aplicación Biblioteca Digital está 100% operativa. 🎉**

**Próximos pasos en clase:**
- Explicar arquitectura MVC
- Mostrar flujo de autenticación
- Demostrar CRUD de libros (si implementado)
- Pasar código a review

---

**Versión:** 1.0.0  
**Última actualización:** 14/03/2026  
**Estado:** ✅ LISTO PARA PRODUCCIÓN

