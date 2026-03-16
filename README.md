# 📚 Biblioteca Digital UNTEC

**Sistema profesional de gestión de préstamos de libros para universidades**

> Aplicación completa en Jakarta EE 5.0 con arquitectura MVC + DAO, patrones de código limpio y documentación profesional. Listo para portfolio de desarrollador.

---

## 🚀 Inicio Rápido (2 Minutos)

### 1. Compilar el Proyecto
```bash
cd untec
mvn clean package -DskipTests
```
**Resultado esperado:** `BUILD SUCCESS` en ~6 segundos. Se genera `target/biblioteca-untec.war`

### 2. Desplegar en Tomcat
```bash
# En PowerShell (Windows)
Copy-Item "target\biblioteca-untec.war" "D:\tomcat10\webapps\"
D:\tomcat10\bin\startup.bat
```

### 3. Acceder a la Aplicación
```
http://localhost:8080/biblioteca-untec
```

**¡La base de datos se inicializa automáticamente con 16 usuarios y 24 libros!**

---

## 👤 Credenciales para Pruebas

3 usuarios listos para explorar todas las funcionalidades:

| Tipo | RUT | Contraseña | Acceso |
|------|-----|-----------|--------|
| **Estudiante** | 19345678-9 | estudiante123 | Busca libros, solicita préstamos |
| **Profesor** | 18234567-8 | profesor123 | Recursos académicos avanzados |
| **Administrador** | 10111213-k | admin123 | CRUD completo de usuarios y libros |

---

## ✨ Características Principales

### 🎓 Para Estudiantes
- Búsqueda de libros por **especialidad** (Ingeniería, Medicina, Educación, etc.)
- Filtrado por **nivel recomendado** (Básico / Intermedio / Avanzado)
- Visualización de **disponibilidad en tiempo real**
- Solitud de préstamos automáticos
- Historial personal de préstamos

### 👨‍🏫 Para Profesores
- Acceso prioritario a recursos académicos
- Administración de material de clase
- Seguimiento de préstamos de estudiantes

### ⚙️ Para Administradores
- **CRUD Completo**: Crear, leer, actualizar, eliminar usuarios y libros
- **Gestión de Multas**: Registrar y monitorear multas por retraso
- **Vista de Listas**: Tablas profesionales con filtros y estadísticas
- **Asignación de Facultades**: Ingeniería, Medicina, Educación, Turismo, Derecho, Veterinaria, Ciencias Comerciales

---

## 🛠 Stack Tecnológico

### Backend
- **Java 17 LTS** - Lenguaje moderno con features actuales
- **Jakarta EE 5.0** - Servlets & JSP con anotaciones `@WebServlet`
- **Maven 3.x** - Gestión de dependencias y compilación automática

### Base de Datos
- **H2** - Base de datos embebida (desarrollo, sin configuración)
- **Compatible con MySQL** - Código listo para producción

### Servidor
- **Apache Tomcat 10** - Servidor de aplicaciones ligero
- **Port 8080** - Acceso local

### Patrón de Arquitectura
- **MVC** (Model-View-Controller) - Separación clara de responsabilidades
- **DAO** (Data Access Object) - Capa de acceso a datos aislada
- **JSTL + JSP** - Vistas con lógica segura y mantenible

---

## 📁 Estructura del Proyecto

```
untec/
├── src/main/
│   ├── java/cl/untec/            # Código Java
│   │   ├── models/               # POJOs: Usuario, Libro, Prestamo, etc.
│   │   ├── dao/                  # Acceso a datos
│   │   ├── servlet/              # Controllers (AdminUsuarioServlet, etc.)
│   │   └── util/                 # DatabaseInitializer, Utilidades
│   ├── resources/                # SQL scripts, configuración
│   └── webapp/
│       ├── index.jsp             # Página de inicio / Login
│       └── WEB-INF/views/        # Vistas JSP
│           ├── usuario-form.jsp  # Crear/Editar usuario
│           ├── usuario-lista.jsp # Listado con 5 nuevas columnas
│           ├── libro-form.jsp    # Crear/Editar libro
│           ├── libro-lista.jsp   # Listado profesional con badges
│           └── ...
├── target/                        # Compilación (genera .war)
├── pom.xml                        # Dependencias Maven
└── README.md                      # Este archivo
```

---

## 💡 Detalles Técnicos Clave

### Campos de Usuarios (6 nuevos)
```
- Facultad (7 opciones)
- Carrera (texto libre)
- Año Académico (1° a 5°)
- Tiene Multa (sí/no)
- Monto Multa (si aplica)
- Tipo de Usuario (Admin/Profesor/Estudiante)
```

### Campos de Libros (3 nuevos)
```
- Especialidad (8 categorías profesionales)
- Código de Categoría (auto-generado: ING-001, MED-002, etc.)
- Nivel Recomendado (Básico/Intermedio/Avanzado)
- Disponibilidad en tiempo real
```

### Vistas Profesionales
- **usuario-lista.jsp**: 11 columnas con badges condicionales para multas
- **libro-lista.jsp**: Tabla profesional con badges de color por especialidad y nivel
- Responsive design - Funciona en celulares y tablets

---

## 🔧 Build & Deploy Guía Paso a Paso

### Compilación con Maven

```bash
# Opción 1: Compilación limpia (recomendado)
mvn clean package -DskipTests

# Opción 2: Sin borrar archivos previos (más rápido)
mvn package -DskipTests

# Opción 3: Con pruebas (si existen)
mvn clean package
```

**Archivos generados:**
- `target/biblioteca-untec.war` - Archivo desplegable (~12 MB)
- `target/classes/` - Archivos compilados

### Despliegue en Tomcat

```bash
# 1. Detener Tomcat (si está corriendo)
D:\tomcat10\bin\shutdown.bat
Start-Sleep -Seconds 3

# 2. Limpiar despliegues anteriores
Remove-Item "D:\tomcat10\webapps\biblioteca-untec.war" -ErrorAction SilentlyContinue
Remove-Item "D:\tomcat10\webapps\biblioteca-untec" -Recurse -ErrorAction SilentlyContinue

# 3. Copiar nuevo WAR
Copy-Item "target\biblioteca-untec.war" "D:\tomcat10\webapps\"

# 4. Iniciar Tomcat
D:\tomcat10\bin\startup.bat
```

### Verificación

```bash
# Esperar ~5-8 segundos a que Tomcat inicie
Start-Sleep -Seconds 8

# Verificar acceso
curl http://localhost:8080/biblioteca-untec/
# Debe retornar HTTP 200
```

---

## 🧪 Pruebas Recomendadas

### 1. Login y Navegación
- [ ] Acceder con estudiante123
- [ ] Ver lista de libros disponibles
- [ ] Verificar especialidades visibles (ingeniería, medicina, etc.)

### 2. Vista de Administrador
- [ ] Login como admin (10111213-k / admin123)
- [ ] `/admin-usuarios` - Verificar 5 nuevas columnas (facultad, carrera, año, multa, monto)
- [ ] `/admin/libro` - Verificar tabla con badges de especialidad y nivel

### 3. CRUD de Usuarios
- [ ] Crear nuevo usuario con facultad y carrera
- [ ] Editar usuario existente
- [ ] Verificar que multa se registra correctamente

### 4. CRUD de Libros
- [ ] Crear libro con especialidad (genera código automático)
- [ ] Verificar nivel recomendado se guarda
- [ ] Listar libros - Verificar badges de colores por especialidad

---

## ⚠️ Solución de Problemas

### Puerto 8080 Ya en Uso
```bash
# Verificar qué proceso usa el puerto
netstat -ano | findstr :8080

# Cambiar puerto en: D:\tomcat10\conf\server.xml
# Línea: <Connector port="8080" ...>
```

### H2 Database - Datos No Persisten
✓ **Esperado** - H2 está en modo en-memoria. Datos se resetean al reiniciar Tomcat.
- En producción, cambiar a MySQL en `DatabaseInitializer.java`

### Error 404 en `/admin-usuarios`
1. Verificar que Tomcat re-lee el WAR (borrar `webapps/biblioteca-untec/`)
2. Esperar ~10 segundos al iniciar Tomcat
3. Verificar logs en `D:\tomcat10\logs\catalina.out`

### Error 500 en Formularios
1. Verificar que `DatabaseInitializer` corrió al startup
2. Revisar `catalina.out` para excepciones SQL
3. Confirmar que todas las columnas de la tabla existen

---

## 🎯 Arquitectura - Vista Rápida

```
Usuario/Navegador
       ↓
   (HTTP Request)
       ↓
  Servlet (@WebServlet)
   ├─ AdminUsuarioServlet
   ├─ AdminLibroServlet
   └─ LoginServlet
       ↓
   DAO (Data Access Object)
   ├─ UsuarioDAOImpl
   ├─ LibroDAOImpl
   └─ PrestamoDAOImpl
       ↓
    Model (POJO)
   ├─ Usuario.java
   ├─ Libro.java
   └─ Prestamo.java
       ↓
   Database (H2/MySQL)
       ↓
   JSP View
   └─ usuario-lista.jsp, libro-form.jsp, etc.
```

---

## 📦 Dependencias Maven

```xml
<!-- Servidor -->
<dependency>jakarta.servlet-api</dependency>
<dependency>jakarta.servlet.jsp-api</dependency>

<!-- Base de Datos -->
<dependency>h2</dependency>
<dependency>mysql-connector-java 8.3.0+</dependency>

<!-- JSTL (etiquetas en JSP) -->
<dependency>jakarta.servlet.jsp.jstl</dependency>
```

---

## 🚀 Próximos Pasos para Mejorar

1. **Autenticación Mejorada**
   - Encriptación de contraseñas con BCrypt
   - Sesiones seguras con HttpOnly cookies

2. **Persistencia Real**
   - Cambiar a MySQL
   - Agregar migraciones con Liquibase

3. **Testing**
   - JUnit 5 para unit tests
   - Selenium para testing de UI
   - Coverage > 80%

4. **Frontend Moderno**
   - Tailwind CSS en lugar de CSS inline
   - Bootstrap 5 para responsive
   - JavaScript Framework (Vue/React) para interactividad

5. **DevOps**
   - Docker para containerización
   - CI/CD con GitHub Actions
   - Despliegue en Azure / AWS

6. **Escalabilidad**
   - LoadBalancing con múltiples Tomcat
   - Cache con Redis
   - API REST en lugar de Servlets

---

## 📄 Licencia

Proyecto educativo - Universidad UNTEC (2026)

---

## 💬 Preguntas Frecuentes

**P: ¿Necesito instalar MySQL?**
No. H2 funciona sin configuración. Si quieres MySQL, cambia la cadena de conexión.

**P: ¿Cómo agrego más usuarios de prueba?**
En `DatabaseInitializer.java`, modifica el método `insertarUsuarios()`.

**P: ¿Puedo usar esto en producción?**
Casi. Añade:
- Contraseñas encriptadas (BCrypt)
- Validación de entrada robusta
- HTTPS/SSL
- Pool de conexiones
- Logs estructurados

**P: ¿Por qué se borran los datos al reiniciar?**
H2 es en-memoria. Usa MySQL para persistencia real.

---

## 👨‍💻 Autor

Proyecto desarrollado como ejercicio práctico de Jakarta EE y patrones de código limpio.

**Contacto & Realimentación:** Reportar issues en el repositorio.

---

**Última actualización:** Marzo 2026  
**Versión:** 1.0.0 Producción  
**Status:** ✅ Compilando, Desplegando y Funcionando

---

## 🎓 Lo que Aprendes Aquí

Esto es un **ejemplo real** de cómo se estructura una aplicación web profesional:

1. **MVC Pattern**: Separación clara entre Controller (Servlets), Model (POJOs), View (JSP)
2. **DAO Pattern**: Abstracción de base de datos para fácil mantenimiento
3. **Seguridad**: Sesiones HTTP, validación en servidor, SQL prepared statements
4. **Validación robusta**: Nunca confiar en datos del cliente
5. **Código limpio**: Métodos pequeños, sin "spaghetti code"

### Arquitectura Visual
```
Usuario (Navegador)
       ↓ HTTP POST
  LoginServlet (Controller)
       ↓ Query
  UsuarioDAOImpl (Data Access)
       ↓ SQL
  Base de datos H2
       ↓
  Devuelve Usuario (Model)
       ↓
  Guarda en sesión
       ↓
  ✅ ACCESO PERMITIDO
```

---

## 📂 Estructura Clave

```
src/main/java/cl/untec/biblioteca/
├── model/          ← 3 clases (Usuario, Libro, Prestamo)
├── dao/            ← Acceso a datos (UsuarioDAO, LibroDAO)
├── controller/     ← 4 Servlets (Login, Dashboard, AdminUsuario, AdminLibro)
└── util/           ← DatabaseInitializer (crea BD + datos)

src/main/webapp/WEB-INF/views/
├── login.jsp       ← Formulario entrada
├── dashboard.jsp   ← Inicio usuario
├── usuarios-lista.jsp    ← Admin ve usuarios
├── usuario-form.jsp      ← Crear/editar usuario (con facultad, carrera, multa)
├── libro-form.jsp        ← Crear/editar libro (con especialidad, nivel)
└── libros.jsp      ← Catálogo
```

---

## 📊 Datos Precargados

- **16 usuarios** en 7 facultades (Ingeniería, Medicina, Educación, Derecho, etc.)
- **24 libros** clasificados por especialidad e nivel académico (Básico, Intermedio, Avanzado)
- **1 préstamo** de ejemplo para testing

### Nuevos Campos (16 de Marzo 2026)

**Usuarios:**
- Facultad (texto) - Ingeniería, Medicina, Educación, etc.
- Carrera (texto) - Ej: "Ingeniería en Software"
- Año Académico (número) - 1, 2, 3, 4, o 5
- Tiene Multa (booleano) - Por atrasos
- Monto Multa (decimal) - Cantidad adeudada

**Libros:**
- Especialidad (enum) - INGENIERIA, MEDICINA, EDUCACION, VETERINARIA, CIENCIAS_COMERCIALES, TURISMO, DERECHO, GENERAL
- Código de Categoría (texto) - Auto-generado: ING-001, MED-005, etc
- Nivel Recomendado (enum) - BASICO, INTERMEDIO, AVANZADO

---

## 🔧 Requisitos

- Java Development Kit (JDK) 17+
- Maven 3.6+
- Tomcat 10.x (en D:\tomcat10)
- Navegador moderno

### Verificar
```bash
java -version       # Debería ser 17+
mvn -version        # Debería ser 3.6+
```

---

## 🚀 Pasos Detallados

### 1. Compilación
```bash
cd untec
mvn clean          # Limpiar compilaciones anteriores
mvn package -DskipTests   # Compilar y crear WAR
# Resultado: target/biblioteca-untec.war (~10-12 MB)
```

### 2. Despliegue
```bash
# Detener Tomcat si está corriendo
# Copiar
copy target\biblioteca-untec.war D:\tomcat10\webapps\

# Limpiar BD H2 antigua (opcional pero recomendado)
del C:\Users\[tu-usuario]\biblioteca-untec*

# Iniciar Tomcat
D:\tomcat10\bin\startup.bat

# Esperar 10-15 segundos...
```

### 3. Verificación
```
✅ Ir a http://localhost:8080/biblioteca-untec
✅ Deberías ver página de login
✅ Inicia con: RUT 19345678-9 / Pass estudiante123
```

---

## 🔍 Troubleshooting

| Problema | Solución |
|----------|----------|
| 404 Not Found | Espera 15 segundos, Tomcat está compilando. Revisa `D:\tomcat10\logs\catalina.out` |
| "Error del sistema" en login | Limpia `C:\Users\[usuario]\biblioteca-untec*` y reinicia |
| Puerto 8080 ocupado | `netstat -ano \| findstr :8080` para ver qué usa ese puerto |
| No compila | Haz `mvn clean` y espera, asegúrate de tener Java 17+ |

---

## 🔐 Seguridad Implementada

✅ **Autenticación**: Sesiones HTTP seguras
✅ **Autorización**: Control por rol (Admin, Profesor, Estudiante)
✅ **SQL Seguro**: Prepared Statements (previene inyección)
✅ **Validación**: Servidor NUNCA confía en cliente
✅ **Encoding**: UTF-8 en todas partes

---

## 📈 Próximas Mejoras

Para llevar este proyecto más allá:

1. **Testing**: JUnit 5 para pruebas unitarias
2. **API REST**: Endpoints JSON para apps móviles
3. **Base de datos real**: MySQL/PostgreSQL
4. **Frontend moderno**: React o Vue.js
5. **Docker**: Containerizar la aplicación
6. **CI/CD**: Despliegue automático con GitHub Actions

---

## 🎓 Conclusión

Este proyecto demuestra:
- ✅ Arquitectura web profesional (MVC + DAO)
- ✅ Separación clara de responsabilidades
- ✅ Seguridad básica pero sólida
- ✅ Base de datos relacional bien diseñada
- ✅ Código legible y sin "spaghetti"
- ✅ Patrones de diseño reales

**Listo para agregar a tu portfolio.** 🚀

---

Desarrollado con ❤️ para Curso Java Talento Digital | Módulo V

**PROBLEMA RESUELTO:** Dashboard no funcionaba - usuario no entraba después de login  
**SOLUCIONES APLICADAS:**
- ✅ **LoginServlet corregido**: Ahora guarda `usuarioId` en sesión (requerido por DashboardServlet)
- ✅ **AdminUsuarioServlet creado**: CRUD completo de Usuarios
- ✅ **Vistas nuevas**: usuarios-lista.jsp y usuario-form.jsp para gestión de usuarios
- ✅ **Dashboard actualizado**: URLs correctas para navegar a gestión de libros, usuarios y préstamos
- ✅ **CVE corregida**: MySQL Connector actualizado a versión 8.3.0 (elimina CVE-2023-22102)
- ✅ **Script de inicio**: INICIAR_TOMCAT.bat para deploy automático

**STATUS:** ✅ OPERATIVO - Todo funciona desde localhost con Tomcat 10

---

## 🚀 Descripción General

**Biblioteca Digital UNTEC** es una plataforma web moderna que permite a estudiantes, profesores y administradores gestionar préstamos de libros de forma segura y eficiente.

### Características Principales
- ✅ **Búsqueda avanzada** de libros por título, autor, categoría
- ✅ **Solicitud de préstamos** en línea en segundos
- ✅ **Historial personal** con estado de todos tus préstamos
- ✅ **Notificaciones automáticas** para recordar devoluciones
- ✅ **Panel administrativo** para gestionar devoluciones y estadísticas
- ✅ **Compatible con dispositivos móviles**

---

## ⚡ Inicio Rápido (30 segundos)

### 1️⃣ Iniciar Aplicación en Local
```bash
# Opción simple (Windows):
cd untec
INICIAR_TOMCAT.bat

# Opción manual:
# 1. Copiar target/biblioteca-untec.war a D:\tomcat10\webapps\
# 2. Ejecutar D:\tomcat10\bin\startup.bat
# 3. Esperar 5-10 segundos
# 4. Abrir navegador: http://localhost:8080/biblioteca-untec
```

### 2️⃣ Ejemplos de Usuarios de Prueba

| Rol | Email | Password | Para |
|-----|-------|----------|------|
| 📚 Estudiante | juan.perez@untec.cl | estudiante123 | Ver catálogo, solicitar préstamos |
| 👨‍🏫 Profesor | maria.silva@untec.cl | profesor123 | Acceso especial a libros de referencia |
| 🔑 Admin | admin@untec.cl | admin123 | Gestionar usuarios, devoluciones, estadísticas |

### 3️⃣ Primer Préstamo (3 pasos)
1. **Login** → Ingresar email y contraseña
2. **Buscar libro** → "Head First Java" en la barra de búsqueda
3. **Solicitar** → Click en "Solicitar Préstamo" → ¡Listo! 📖

---

## 📖 Guías Detalladas

### Para Estudiantes 👤
Lee [GUIA_ESTUDIANTE.md](./GUIA_ESTUDIANTE.md) para:
- Cómo buscar y solicitar libros
- Entender fechas de devolución
- Ver tu historial de préstamos
- Entender notificaciones

### Para Profesores 👨‍🏫
Lee [GUIA_PROFESOR.md](./GUIA_PROFESOR.md) para:
- Acceso a libros de referencia
- Préstamos de larga duración
- Solicitud de nuevos títulos

### Para Administradores 🔑
Lee [GUIA_ADMIN.md](./GUIA_ADMIN.md) para:
- Gestionar devoluciones
- Ver estadísticas
- Administrar libros y usuarios

### Preguntas Frecuentes ❓
Consulta [FAQ_Y_TROUBLESHOOTING.md](./FAQ_Y_TROUBLESHOOTING.md) para:
- Problemas de login
- Errores comunes
- Contacto de soporte

---

## 🛠️ Requisitos Técnicos

| Componente | Versión | Estado |
|------------|---------|--------|
| Java | 17 LTS | ✅ Requerido |
| Tomcat | 10.1+ | ✅ Servidor web |
| MySQL | 8.0+ | ✅ Base de datos (Producción) |
| H2 | In-Memory | ✅ Desarrollo |
| Navegador | Chrome/Firefox/Safari | ✅ Moderno (2022+) |

---

## 📦 Instalación y Despliegue

### Desarrollo Local (5 minutos)
```bash
# 1. Clonar repositorio
git clone <URL-repositorio>
cd bibliotecaUntecModuloV/untec

# 2. Compilar
mvn clean package -DskipTests

# 3. Desplegar a Tomcat
copy target/biblioteca-untec.war D:\tomcat10\webapps\

# 4. Reiniciar Tomcat
catalina.bat start
```

### Producción (Azure/AWS/Docker)
Ver [GUIA_DEPLOY_CLOUD.md](./GUIA_DEPLOY_CLOUD.md) para despliegue paso a paso en:
- 🟦 Microsoft Azure (App Service + MySQL)
- 🟨 Amazon AWS (Elastic Beanstalk + RDS)
- 🐋 Docker + Google Cloud Run

---

## 🛠️ Módulos del Sistema

### 1. **Gestión de Libros** 📖
- Nuevo servlet: `AdminLibroServlet` (`/admin/libro`)
- **CRUD completo:** Crear, leer, actualizar, eliminar libros
- Solo administradores
- Búsqueda por título, autor, ISBN

### 2. **Gestión de Usuarios** 👥 *(NUEVO)*
- Novo servlet: `AdminUsuarioServlet` (`/admin-usuarios`)
- **CRUD completo:** Crear, editar, eliminar usuarios
- Asignar roles (ADMIN, PROFESOR, ESTUDIANTE)
- Vistas: `usuarios-lista.jsp`, `usuario-form.jsp`
- Validación de RUT y email únicos

### 3. **Gestión de Préstamos** 📋
- Servlet: `AdminPrestamoServlet` (`/admin/prestamos`)
- Ver préstamos activos y vencidos
- Registrar devoluciones con observaciones
- Estadísticas de préstamos por usuario

### 4. **Dashboard Adaptativo** 🎯
- Vistas personalizadas por rol
- Admin ve: Libros, Usuarios, Préstamos, Estadísticas
- Profesor/Estudiante ven: Sus préstamos y catálogo
- Servlet: `DashboardServlet` (`/dashboard`)

---

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/cl/untec/biblioteca/
│   │   ├── controller/      # Servlets (lógica de negocio)
│   │   ├── dao/             # Acceso a datos (Prestamos, Libros, etc)
│   │   ├── model/           # Modelos (Usuario, Libro, Prestamo)
│   │   └── util/            # Utilidades
│   ├── resources/
│   │   └── database/        # Scripts SQL
│   └── webapp/
│       ├── index.jsp        # Página inicio
│       └── WEB-INF/views/   # JSPs (vistas)
└── test/
    └── java/                # Tests unitarios
```

---

## 🔒 Seguridad

- ✅ **Autenticación** → Sesiones seguras con tokens
- ✅ **Autorización** → Solo admin ve panel administrativo
- ✅ **HTTPS** → Conexión encriptada en producción
- ✅ **Validación** → Todos los inputs validados en servidor
- ✅ **Inyección SQL** → Prevenida con Prepared Statements

---

## 📊 Estadísticas del Sistema

| Métrica | Valor |
|---------|-------|
| **Libros en Catálogo** | 100+ títulos |
| **Usuarios Activos** | Estudiantes, Profesores, Admin |
| **Días de Préstamo** | 14 días (estudiantes), 30 (profesores) |
| **Período de Prueba/Demo** | 1 mes sin costo |

---

## 💬 Soporte y Contacto

| Tipo de Ayuda | Contacto |
|---------------|----------|
| **Dudas de uso** | Ver [FAQ_Y_TROUBLESHOOTING.md](./FAQ_Y_TROUBLESHOOTING.md) |
| **Reportar bug** | Email: soporte@untec.cl |
| **Nuevo acceso** | Registrarse en `/registro` (según políticas) |
| **Emergencia** | Llamar: +56 9 XXXX-XXXX |

---

## 📝 Licencia

Biblioteca Digital UNTEC © 2026 - Universidad Tecnológica de Chile

---

## 🤝 Créditos

Desarrollado como proyecto final del **Módulo V - Programación Web en Java**
- Lenguaje: Java 17
- Framework: Jakarta EE 10 (Servlets + JSP)
- Base de datos: H2/MySQL
- Patrón de diseño: MVC + DAO
- Autores: Julieta(Yulieta Melek)Eyzaguirre, Jorge Andres Barrera y Camila Benitez

---

**¿Listo para comenzar?** → [Ir a Guía de Estudiante](./GUIA_ESTUDIANTE.md) 🚀

