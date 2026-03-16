# 📚 Guía del Estudiante - Biblioteca Digital UNTEC

Bienvenido a Biblioteca Digital UNTEC. Esta guía te mostrará cómo usar el sistema paso a paso con ejemplos reales.

**Tiempo de lectura**: 5 minutos | **Nivel**: Principiante

---

## 📋 Índice Rápido

1. [Primer acceso (Login)](#-primer-acceso)
2. [Buscar un libro](#-buscar-un-libro)
3. [Solicitar un préstamo](#-solicitar-un-préstamo)
4. [Ver tu historial](#-ver-tu-historial)
5. [Entender notificaciones](#-entender-notificaciones)
6. [Tips útiles](#-tips-útiles)

---

## 🔑 Primer Acceso

### Paso 1: Abrir la página
```
URL: http://localhost:8080/biblioteca
O en producción: https://biblioteca.untec.cl
```

### Paso 2: Hacer login
```
Usuario de prueba:
Email:    juan.perez@untec.cl
Password: 123456
```

**Pantalla esperada:**
```
┌─────────────────────────┐
│   BIBLIOTECA DIGITAL    │
│     UNTEC 2026          │
├─────────────────────────┤
│ Email: [juan.perez...] │
│ Contraseña: [••••••] │
│                         │
│ [Iniciar Sesión] [X]    │
└─────────────────────────┘
```

✅ **Resultado**: Verás el catálogo de libros

---

## 🔍 Buscar un Libro

### Ejemplo: Buscar "Head First Java"

**Método 1: Barra de búsqueda (RECOMENDADO)**
```
1. Click en el campo de búsqueda (arriba de la página)
2. Escribe: "Head First Java"
3. Presiona ENTER o click en lupa 🔍
```

**Pantalla esperada:**
```
┌─────────────────────────────┐
│ 🔍 [Buscar libros...____] │
├─────────────────────────────┤
│ RESULTADOS (1)              │
├─────────────────────────────┤
│ Head First Java             │
│ Autor: Kathy Sierra         │
│ Disponibles: 2 de 3         │
│ [Ver Detalles]              │
└─────────────────────────────┘
```

**Método 2: Por categoría**
```
1. Selecciona "Programación" en el menú lateral
2. Los libros se filtran automáticamente
```

### Filtros disponibles:
- 📖 Por título
- ✍️ Por autor
- 🏷️ Por categoría (Ficción, Técnico, etc)

---

## 📌 Solicitar un Préstamo

### Paso a Paso: Pedir "Head First Java"

**PASO 1: Abrir detalles del libro**
```
Click en el libro "Head First Java"
```

**PASO 2: Ver información**
```
┌──────────────────────────────┐
│ HEAD FIRST JAVA              │
│                              │
│ Autor: Kathy Sierra          │
│ Editorial: O'Reilly Media    │
│ Año: 2005                    │
│ Categoría: Programación      │
│ Disponibles: 2 de 3 copias  │
│ Ubicación: Estante A1        │
│                              │
│ Descripción: Guía            │
│ introductoria a Java...      │
│                              │
│ [SOLICITAR PRÉSTAMO] [Atrás] │
└──────────────────────────────┘
```

**PASO 3: Hacer la solicitud**
```
Click en botón [SOLICITAR PRÉSTAMO]
```

**PASO 4: Completar formulario**
```
┌──────────────────────────────┐
│ CONFIRMAR PRÉSTAMO           │
├──────────────────────────────┤
│ Libro: Head First Java       │
│                              │
│ Fecha de préstamo:           │
│   16/03/2026 ✓               │
│                              │
│ Fecha de devolución:         │
│   30/03/2026 (14 días) ✓    │
│                              │
│ Notas (opcional):            │
│ [_____________________]      │
│                              │
│ [Confirmar] [Cancelar]       │
└──────────────────────────────┘
```

✅ **¡Listo!** Verás un mensaje: "Préstamo aprobado exitosamente"

---

## 📖 Ver tu Historial

### Acceder a "Mi Historial"

```
1. Click en tu nombre (arriba derecha)
2. Selecciona "Mi Historial de Préstamos"
```

O directamente:
```
URL: http://localhost:8080/biblioteca/mi-historial
```

### Pantalla de Historial

```
┌────────────────────────────────────┐
│ MI HISTORIAL DE PRÉSTAMOS          │
├────────────────────────────────────┤
│ Estadísticas:                      │
│ 📕 Activos: 3    📗 Devueltos: 5  │
│ ⏰ Vencidos: 1    📚 Total: 9     │
├────────────────────────────────────┤
│ [ACTIVOS] [DEVUELTOS] [VENCIDOS]  │
├────────────────────────────────────┤
│ LIBROS ACTIVOS                     │
├─────────────┬──────────┬───────────┤
│Libro        │Devuelvo  │ Restante  │
├─────────────┼──────────┼───────────┤
│Head First.. │30/03/26  │ 14 días ✓ │
│Java Design  │02/04/26  │ 17 días ✓ │
│Clean Java   │25/03/26  │ 9 días ⚠  │
└────────────────────────────────────┘
```

### Pesañas disponibles:

| Pestaña | Significa | Color |
|---------|-----------|-------|
| **ACTIVOS** | Aún no devuelves | 🟢 Verde |
| **DEVUELTOS** | Ya devolviste | 🔵 Azul |
| **VENCIDOS** | Pasó la fecha ¿Dónde está el libro? | 🔴 Rojo |

---

## 🔔 Entender Notificaciones

### ¿Qué son las notificaciones?

El sistema te avisa automáticamente en la esquina superior derecha cuando:

**Notificación NARANJA (⏰ Proximo a vencer)**
```
┌────────────────────┐
│ ⏰ ATENCIÓN        │
│ "Clean Java" vence │
│ en 2 días          │
│ Devuelve pronto    │
└────────────────────┘
```
⚠️ **Acción**: Planifica la devolución

**Notificación ROJA (⚠️ VENCIDO)**
```
┌────────────────────┐
│ ⚠️  URGENTE       │
│ "Design Pattern"   │
│ venció hace 3 días │
│ Devuelve YA        │
└────────────────────┘
```
🚨 **Acción**: Devuelve el libro hoy mismo

### Cómo funcionan:
- ✅ Se actualizan cada 5 minutos automáticamente
- ✅ Solo ves TUS notificaciones
- ✅ Desaparecen solos después de 8 segundos
- ✅ Puedes cerrar manualmente con la X

---

## 💡 Tips Útiles

### ✅ Buenas Prácticas

| Tip | Beneficio |
|-----|-----------|
| **Devuelve a tiempo** | Evita penalizaciones futuras |
| **Usa búsqueda rápida** | Encuentra libros en segundos |
| **Revisa tu historial** | Mantente al día con devóltuciones |
| **Atiende notificaciones** | No olvides fechas de devolución |
| **Documenta observaciones** | Si hay daños, avisa al admin |

### ❌ Errores Comunes

| Problema | Solución |
|----------|----------|
| "No puedo buscar" | Asegúrate de estar logueado |
| "Libro no disponible" | Todas las copias están prestadas, intenta otro |
| "Error de sesión" | Cierra navegador y vuelve a login |
| "¿Perdiste contraseña?" | Contacta a soporte@untec.cl |

### 🎯 Ejemplo Real: Mi Día Típico

```
MAÑANA (08:00)
→ Login a Biblioteca Digital
→ Busco "Effective Java"
→ Presiono "Solicitar Préstamo"
✅ ¡Aprobado! Voy a retirar el libro

DESPUÉS DE 14 DÍAS (22/03/2026)
→ Notification: "Vence en 2 días ⏰"
→ Preparo el libro para devolver

DÍA DE DEVOLUCIÓN (29/03/2026)
→ Voy a biblioteca a devolver
→ Admin registra la devolución
→ Historial actualizado ✓
```

---

## 🆘 Problemas Frecuentes

### "Me dice 'Libro no disponible'"

**Significado**: Todas las copias están prestadas

**Soluciones**:
```
1. Intenta otro libro similar
2. Vuelve a intentar mañana
3. Contacta al admin para tu lista de espera
```

### "No veo mis notificaciones"

**Causas**:
```
1. Navegador bloqueando notificaciones
   → Revisa permisos del navegador
   
2. No estás logueado
   → Haz login nuevamente
   
3. JavaScript deshabilitado
   → Actívalo en configuración del navegador
```

### "¿Puedo devolver en otro lugar?"

**Respuesta**: 
- Dependencia en biblioteca física: Localización en campus UNTEC
- Por correo: Contacta a soporte@untec.cl para envío

---

## 📞 Necesitas Ayuda?

### Contacto Rápido

| Tipo de Ayuda | Contacto |
|---------------|----------|
| **Técnico** | soporte@untec.cl |
| **Devoluciones** | biblioteca@untec.cl |
| **Acceso nuevos libros** | sugerencias@untec.cl |
| **Emergencia** | +56 9 XXXX-XXXX (biblioteca) |

### Horarios de Atención
```
Lunes a Viernes: 08:00 - 18:00
Sábados: 09:00 - 13:00
Domingos y festivos: Cerrado
```

---

## ✅ Checklist: Ya Sé Cómo...

- [ ] Login y ver catálogo
- [ ] Buscar libros por título
- [ ] Solicitar un préstamo
- [ ] Ver mi historial
- [ ] Entender las notificaciones
- [ ] Devolver un libro a tiempo

**¿Completaste todo?** ¡Eres un usuario experto! 🎉

---

**Documento versión 1.0** | Última actualización: 16/03/2026
