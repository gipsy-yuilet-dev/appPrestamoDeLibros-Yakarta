# 🔑 Guía del Administrador - Biblioteca Digital UNTEC

Bienvenido al panel administrativo. Esta guía te instruirá en la gestión completa de Biblioteca Digital UNTEC.

**Tiempo de lectura**: 6 minutos | **Nivel**: Avanzado

---

## 📋 Índice Rápido

1. [Acceso administrativo](#-acceso-administrativo)
2. [Panel de control](#-panel-de-control)
3. [Gestionar devoluciones](#-gestionar-devoluciones)
4. [Administrar libros](#-administrar-libros)
5. [Administrar usuarios](#-administrar-usuarios)
6. [Reportes y estadísticas](#-reportes-y-estadísticas)

---

## 🔑 Acceso Administrativo

### Credenciales de Admin

```
Email:    admin@untec.cl
Password: 123456 (CAMBIAR EN PRODUCCIÓN ⚠️)
```

### Primer Acceso

```
1. Login con cuenta admin
2. Verás menú especial en la barra de navegación:

┌───────────────────────────────────┐
│ INICIO | LIBROS | ADMIN ▼         │
│        │        │ ├─ Mi Historial │
│        │        │ ├─ Gestión      │
│        │        │ ├─ Panel Admin  │
│        │        │ └─ Configuración│
└───────────────────────────────────┘

3. Click en [ADMIN] → [Panel Admin]
```

---

## 📊 Panel de Control

### Dashboard Administrativo

```
┌──────────────────────────────────────┐
│ PANEL ADMINISTRATIVO                │
├──────────────────────────────────────┤
│                                      │
│ ESTADÍSTICAS GENERALES:              │
│ ┌──────────┬──────────┬────────────┐ │
│ │ Total    │ Libros   │ Usuarios   │ │
│ │ Préstamos│ Activos  │ Registrados│ │
│ │    47    │    28    │    125     │ │
│ └──────────┴──────────┴────────────┘ │
│                                      │
│ ALERTAS PRIORITARIAS:                │
│ ⚠️  3 Libros vencidos (sin devolver)│
│ ⚠️  2 Usuarios con bloqueo           │
│ ⚠️  5 Solicitudes pendientes         │
│                                      │
│ [Ir a Gestión de Devoluciones] →    │
│ [Ver Usuarios Bloqueados]       →    │
│ [Revisar Solicitudes]           →    │
├──────────────────────────────────────┤
│ ACCESO RÁPIDO:                       │
│ [Agregar Libro] [Editar Libro]      │
│ [Usuarios] [Reportes] [Config]      │
└──────────────────────────────────────┘
```

---

## 🔄 Gestionar Devoluciones

### Pantura: Préstamos Pendientes

```
URL: /admin/prestamos
```

### Pantalla de Gestión

```
┌─────────────────────────────────────────┐
│ GESTIÓN DE PRÉSTAMOS                   │
├─────────────────────────────────────────┤
│                                         │
│ PRÉSTAMOS ACTIVOS (14)                 │
├───────────┬────────────┬──────────────┐│
│ Estudiante│ Libro      │ Vencimiento  ││
├───────────┼────────────┼──────────────┤│
│ Juan Pérez│Head First..│ 30/03 (+14d) ││
│ María García│Design...  │ 25/03 (+9d) ││
│ Carlos T. │Clean Code  │ 22/03 (+6d) ││
│ ...       │...         │...           ││
└───────────┴────────────┴──────────────┘│
│ [Registrar Devolución] ↓               │
│                                         │
│ PRÉSTAMOS VENCIDOS (3)                 │
├───────────┬────────────┬──────────────┐│
│ Estudiante│ Libro      │ Atraso       ││
├───────────┼────────────┼──────────────┤│
│ Pedro López│Web Dev...  │ -3 días ⚠️   ││
│ Ana Silva │Java OOP    │ -7 días 🔴   ││
│ Luis Mora │Data Base   │ -1 día ⚠️    ││
└───────────┴────────────┴──────────────┘│
│ [ACCIÓN: Contactar Estudiante]         │
└─────────────────────────────────────────┘
```

### Paso 1: Registrar Devolución Normal

```
1. Click en estudiante "Juan Pérez"
2. Se abre el formulario:

┌──────────────────────────────────┐
│ DEVOLVER LIBRO                   │
├──────────────────────────────────┤
│ Estudiante: Juan Pérez           │
│ Libro: Head First Java           │
│ Fecha Préstamo: 16/03/2026       │
│ Esperaba devolver: 30/03/2026    │
│                                  │
│ Estado del Libro:                │
│ ⦿ Excelente (sin daños)         │
│ ○ Buen estado (daños leves)      │
│ ○ Aceptable (daños moderados)    │
│ ○ Deteriorado (no reusable) 🔴   │
│                                  │
│ Observaciones (opcional):        │
│ [_____________________]          │
│                                  │
│ [Confirmar Devolución]           │
│ [Cancelar]                       │
└──────────────────────────────────┘

3. Selecciona estado del libro
4. Click [Confirmar Devolución]
```

✅ **Resultado**: "Devolución registrada exitosamente"

### Paso 2: Seguimiento a Vencidos

```
Para "Pedro López" con atraso de 3 días:

1. Click en el registro
2. Se abre menú de acciones:

┌──────────────────────────────────┐
│ ACCIONES:                        │
│                                  │
│ [📧 Enviar Recordatorio Email]   │
│ [📞 Marcar Contactado]           │
│ [🔒 Bloquear Usuario]            │
│ [📋 Ver Historial del Estudiante]
│                                  │
│ Comentario (para historial):     │
│ [_____________________]          │
│                                  │
│ [Guardar]                        │
└──────────────────────────────────┘

3. Selecciona acción apropiada
```

**Opciones de Acción:**

| Acción | Cuándo Usar | Efecto |
|--------|------------|--------|
| **Recordatorio Email** | Atraso < 5 días | Envía aviso amable |
| **Bloquear Usuario** | Atraso > 14 días | No puede solicitar más libros |
| **Contactado** | Ya llamaste al estudiante | Registra el intento |

---

## 📚 Administrar Libros

### Sección: Catálogo de Libros

```
URL: /admin/libro (o Menu ADMIN → Libros)
```

### Agregar un Nuevo Libro

```
Click [+ AGREGAR LIBRO]

┌──────────────────────────────────┐
│ NUEVO LIBRO                      │
├──────────────────────────────────┤
│ ISBN: [9780596007126_____]       │
│ Título: [Head First Java_____]   │
│ Autor: [Kathy Sierra, Bert B_]   │
│ Editorial: [O'Reilly Media___]   │
│ Año Publicación: [2005__________]│
│ Categoría: [Programación ▼]      │
│ Cantidad Copias: [3____]         │
│ Ubicación: [Estante A1_______]   │
│                                  │
│ Descripción:                     │
│ [_____________________           │
│  _____________________           │
│  _____________________]          │
│                                  │
│ [Agregar] [Cancelar]             │
└──────────────────────────────────┘
```

**Campos Obligatorios:**
- ✅ ISBN (único)
- ✅ Título
- ✅ Autor
- ✅ Categoría
- ✅ Cantidad de copias

### Editar un Libro Existente

```
1. Click en el libro de la lista
2. Se abre interfaz de edición

OPCIONES:
[✏️ Editar Información]
[➕ Aumentar Stock] (de 3 a 5 copias)
[➖ Disminuir Stock] (de 5 a 3 copias)
[🔒 Desactivar] (No aparece en búsqueda)
[🗑️ Eliminar] (Cuidado: irreversible)
```

**Ejemplo: Libro dañado sin reparación**
```
1. Click [🔒 Desactivar]
2. Se pregunta: "¿Desactivar 'Head First Java'?"
3. Verás: "Este libro no aparecerá en búsquedas de estudiantes"
4. Click [Confirmar]

✅ El libro sigue en historial pero NO aparece en catálogo
```

---

## 👥 Administrar Usuarios

### Gestión de Cuentas

```
URL: /admin/usuarios
```

### Listar Usuarios

```
┌──────────────────────────────────────┐
│ USUARIOS DEL SISTEMA              │
├──────────────────────────────────────┤
│ Total: 125 usuarios               │
│ Filtro por tipo:                  │
│ [Todos] [Estudiantes] [Profesores] [Admin]
│                                     │
│ LISTA:                              │
├─────────────┬──────────┬──────────┤│
│ Nombre      │ Tipo     │ Estado   ││
├─────────────┼──────────┼──────────┤│
│ Juan Pérez  │ESTUDIANTE│ Activo   ││
│ Prof. López │PROFESOR  │ Activo   ││
│ Ana Silva   │ESTUDIANTE│Bloqueado ││
│ Admin UNTEC │ADMIN     │ Activo   ││
└─────────────┴──────────┴──────────┘│
│ [Nuevo Usuario] [Editar] [Bloquear]│
└──────────────────────────────────────┘
```

### Bloquear un Usuario (Caso: Deuda de libros)

```
Escenario: Ana Silva debe 3 libros hace 2 meses

1. Busca a "Ana Silva" en la lista
2. Click en su nombre
3. Se abre perfil:

┌──────────────────────────────────┐
│ ANA SILVA MORALES              │
├──────────────────────────────────┤
│ Email: ana.silva@untec.cl        │
│ Tipo: ESTUDIANTE                 │
│ Estado: Activo ▼ [Cambiar]      │
│                                  │
│ DEUDA ACTUAL:                    │
│ ⚠️  3 libros sin devolver       │
│ ⏰ Atraso: 54 días               │
│                                  │
│ ACCIONES:                        │
│ [📧 Enviar Notificación Email]   │
│ [⛔ Bloquear Usuario]             │
│ [🔓 Desbloquear]                 │
│ [📊 Ver Historial Completo]      │
│                                  │
│ Razón del bloqueo (opcional):    │
│ [Deuda de libros sin devolver]   │
│                                  │
│ [CONFIRMAR]                      │
└──────────────────────────────────┘

4. Click [⛔ Bloquear Usuario]
5. Click [CONFIRMAR]

✅ Resultado:
Ana no puede solicitar más libros
(Hasta que devuelva los 3)
```

---

## 📈 Reportes y Estadísticas

### Ver Reportes Generales

```
URL: /admin/reportes
```

### Reporte 1: Actividad Mensual

```
┌──────────────────────────────────┐
│ ACTIVIDAD - MARZO 2026          │
├──────────────────────────────────┤
│                                 │
│ Préstamos nuevos:       47      │
│ Devoluciones:          45      │
│ Libros vencidos:        3      │
│ Usuarios nuevos:        8      │
│ Búsquedas totales:    312      │
│                                 │
│ LIBRO MÁS SOLICITADO:           │
│ "Head First Java" (12 préstamos)│
│                                 │
│ CATEGORÍA MÁS ACTIVA:           │
│ "Programación" (34 préstamos)   │
│                                 │
│ [Descargar como PDF]            │
│ [Descargar como Excel]          │
└──────────────────────────────────┘
```

### Reporte 2: Usuarios

```
┌──────────────────────────────────┐
│ REPORTE DE USUARIOS             │
├──────────────────────────────────┤
│ Total usuarios: 125             │
│                                 │
│ Por tipo:                       │
│ Estudiantes: 95 (76%)          │
│ Profesores: 20 (16%)           │
│ Administradores: 10 (8%)       │
│                                 │
│ Sin usar en 90 días: 12        │
│ Bloqueados: 2                  │
│ Nuevos este mes: 8             │
│                                 │
│ USUARIOS CON MÁS PRÉSTAMOS:    │
│ 1. Juan Pérez (12)             │
│ 2. María García (10)           │
│ 3. Carlos Tomás (9)            │
│                                 │
│ [Contactar Inactivos]          │
│ [Exportar Listado Excel]       │
└──────────────────────────────────┘
```

---

## 🎯 Ejemplo Real: Un Día de Administrador

### Mañana: 08:00

```
1. Login como admin
2. Reviso Dashboard
   → 2 libros vencidos sin devolver ⚠️
   → 1 nueva solicitud de profesor
   
3. Gestión de Devoluciones
   → Pedro López devolvió "Web Dev" (estado excelente)
   → Ana Silva aún debe "Java OOP" (contacto pendiente)
   → Envío recordatorio email a Ana
```

### Medio día: 12:00

```
1. Recibo llamada de librería proveedora
2. Agregar 3 copies nuevas de "Clean Code"
   → Click [+ AGREGAR LIBRO]
   → ISBN: 9780132350884
   → Cantidad: 3
   → Guardan en estante B2

3. Revisar solicitud de Prof. López
   → Quiere acceso a "Design Patterns" para clase
   → Aprobar solicitud de largo plazo
```

### Final del día: 17:00

```
1. Revisar estadísticas diarias
   → 12 préstamos nuevos
   → 8 devoluciones
   → 0 incidentes
   
2. Generar reporte semanal en PDF
3. Enviar a dirección académica
4. Cerrar sesión
```

---

## 🛡️ Seguridad y Mejores Prácticas

### ✅ Obligatorio

| Práctica | Razón |
|----------|-------|
| **Cambiar password inicial** | Seguridad de cuenta |
| **Backup semanal de DB** | Recuperación ante errores |
| **Auditar accesos admin** | Prevenir cambios no autorizados |
| **Revisar reportes mensales** | Detectar anomalías |
| **Validar ISBN antes de agregar** | Evitar duplicados |

### ❌ NUNCA

```
✗ Compartir credenciales de admin
✗ Prestar tu sesión a otro admin
✗ Eliminar historial de préstamos
✗ Modificar fechas de devolución manualmente
✗ Acceder fuera de horario sin documentar la razón
```

---

## 📞 Soporte Administrativo

### Contacto de Soporte Técnico
```
Email: soporte-admin@untec.cl
Teléfono: +56 9 XXXX-XXXX (línea directa)
WhatsApp: Disponible para emergencias
```

### Escalación de Problemas
```
Problema: Estudiante reporta que no puede devolver

1. Intenta in sistema
2. Si persiste → Email a soporte
3. Si es urgente → Llamada directa
4. Extremo: Resetear sesión en servidor
```

---

## ✅ Checklist del Administrador

- [ ] Sé registrar una devolución
- [ ] Entiendo los bloques de usuario
- [ ] Puedo agregar nuevos libros
- [ ] He generado un reporte
- [ ] Sé contactar a estudiantes con atraso
- [ ] Sé respaldar la base de datos

---

**Documento versión 1.0** | Última actualización: 16/03/2026

⚠️ **IMPORTANTE**: Esta guía es para administradores. No compartir accesos con usuarios regulares. 

**Soporte:** soporte-admin@untec.cl 📞
