# 📚 Biblioteca Digital UNTEC

> Sistema web para gestión digital de préstamos de libros - Universidad Tecnológica de Chile

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

### 1️⃣ Acceder al Sistema
```
URL: https://biblioteca.untec.cl
(O tu servidor local: http://localhost:8080/biblioteca)
```

### 2️⃣ Ejemplos de Usuarios de Prueba

| Rol | Email | Password | Para |
|-----|-------|----------|------|
| 📚 Estudiante | juan.perez@untec.cl | 123456 | Ver catálogo, solicitar préstamos |
| 👨‍🏫 Profesor | profesor.lopez@untec.cl | 123456 | Acceso especial a libros de referencia |
| 🔑 Admin | admin@untec.cl | 123456 | Gestionar devoluciones y usuarios |

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

---

**¿Listo para comenzar?** → [Ir a Guía de Estudiante](./GUIA_ESTUDIANTE.md) 🚀

