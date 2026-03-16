# 👨‍🏫 Guía del Profesor - Biblioteca Digital UNTEC

Bienvenido, estimado profesor. Esta guía te mostrará cómo usar Biblioteca Digital UNTEC con privilegios especiales.

**Tiempo de lectura**: 4 minutos | **Nivel**: Intermedio

---

## 📋 Índice Rápido

1. [Diferencias con estudiantes](#-diferencias-con-estudiantes)
2. [Primer acceso](#-primer-acceso)
3. [Buscar y solicitar libros](#-buscar-y-solicitar-libros)
4. [Préstamos extendidos](#-préstamos-extendidos)
5. [Sección de referencias](#-sección-de-referencias)
6. [Tu historial académico](#-tu-historial-académico)

---

## 🎓 Diferencias con Estudiantes

### Privilegios Especiales de Profesores

| Característica | Estudiantes | Profesores |
|---|---|---|
| **Período de préstamo** | 14 días | **30 días** ⭐ |
| **Libros de referencia** | ❌ Limitado | ✅ Acceso completo |
| **Renovaciones** | 1 vez | **Hasta 3 veces** |
| **Límite de préstamos** | 5 libros | **10 libros** |
| **Acceso a sección académica** | ❌ No | ✅ **Sí** |
| **Solicitud de nuevos libros** | ❌ No | ✅ **Sí** |

---

## 🔑 Primer Acceso

### Paso 1: Login con tu cuenta docente
```
Email:    profesor.lopez@untec.cl
Password: 123456
```

### Paso 2: Verificar tu rol
```
Arriba a la derecha deberías ver:
┌────────────────────────────┐
│ 👨‍🏫 Prof. Roberto López   │
│ [Mi Historial]             │
│ [Panel Académico]          │
│ [Cerrar Sesión]            │
└────────────────────────────┘
```

✅ Si ves **"Panel Académico"**, tu cuenta está correcta

---

## 🔍 Buscar y Solicitar Libros

### Ejemplo: Buscar libro técnico para clase

```
PASO 1: Búsqueda normal
Escribe: "Clean Code"
Click en 🔍

PASO 2: Ver detalles
Click en el libro encontrado

PASO 3: Información especial para profesor
┌──────────────────────────────┐
│ CLEAN CODE                   │
│                              │
│ Autor: Robert C. Martin      │
│ Disponibles: 1 de 2          │
│                              │
│ DURACIÓN ESPECIAL:           │
│ • Profesor: 30 días          │
│ • Renovable: Sí (hasta 3x)   │
│                              │
│ SECCIÓN: Referencia académica│
│                              │
│ [SOLICITAR PARA CLASE]       │
│ [Ver Comentarios de Colegas] │
└──────────────────────────────┘

PASO 4: Completar solicitud
```

### Campo especial: "Propósito de la solicitud"
```
Aquí puedes indicar por qué necesitas el libro:

Ejemplo 1: "Referencia para mi clase de Patrones de Diseño"
Ejemplo 2: "Investigación para tesis de estudiante X"
Ejemplo 3: "Material de apoyo para laboratorio"
```

---

## 📅 Préstamos Extendidos

### Renovar un Préstamo

**Cuando tu libro está por vencer:**
```
┌───────────────────────────┐
│ ⏰ Vence en 3 días        │
│                           │
│ "Design Patterns"         │
│ Devuelves: 22/04/2026     │
│                           │
│ [Renovar Préstamo]        │
│ [Devolver Ahora]          │
│ [Enviar a Base de Datos]  │
└───────────────────────────┘
```

**Presiona [Renovar Préstamo]**
```
✅ Resultado:
"Préstamo renovado exitosamente"
Nueva fecha devolución: 06/05/2026 (30 días más)

⚠️ Nota: Solo puedes renovar 3 veces por recurso
```

### Solicitud Manual de Extensión
```
Si NO puedes renovar por el sistema:
1. Contacta a: biblioteca@untec.cl
2. Asunto: "Solicitud extensión de préstamo"
3. Adjunta: Motivo académico
→ El admin revisará en 24 horas
```

---

## 📚 Sección de Referencias

### ¿Qué es la "Sección Académica"?

```
Libros que:
✓ El departamento considera obligatorios para tu carrera
✓ NO pueden sacarse del campus (consulta en biblioteca)
✓ Tienen períodos extendidos si se necesita
✓ Están respaldados por el área académica
```

### Acceder a Sección Académica

```
1. Haz login como profesor
2. Click en [Panel Académico] (arriba derecha)
3. Verás:
```

```
┌───────────────────────────────────┐
│ PANEL ACADÉMICO - REFERENCIAS     │
├───────────────────────────────────┤
│ BÚSQUEDA POR ÁREA:                │
│                                   │
│ [Ingeniería en Informática]       │
│ [Administración]                  │
│ [Contabilidad]                    │
│ [Tecnología]                      │
│                                   │
│ LIBROS RECOMENDADOS:              │
│                                   │
│ 📕 Java Programming 6th Ed.       │
│ 📕 Database Design & SQL          │
│ 📕 Web Development with Java      │
│                                   │
│ [Ver disponibilidad]              │
└───────────────────────────────────┘
```

### Solicitar un Libro de Referencia

```
1. Busca el libro en la sección académica
2. Click en [SOLICITAR PARA CLASE]
3. Completa:
   - Propósito: "Referencias para estudiantes"
   - Grupo: "Programación Web - Sección A"
   - Período: "Sesiones 3-5 (Marzo)"

4. El admin lo reserva en préstamo extendido
```

---

## 📖 Tu Historial Académico

### Acceder a Historial

```
1. Click en tu nombre (arriba derecha)
2. Selecciona [Mi Historial de Préstamos]

O directamente:
URL: http://localhost:8080/biblioteca/mi-historial
```

### Información Especial para Profesores

```
┌──────────────────────────────────┐
│ MI HISTORIAL ACADÉMICO           │
├──────────────────────────────────┤
│ Estadísticas (Año Académico):    │
│ 📕 Activos: 5     📗 Devueltos: 12
│ ⏰ Vencidos: 0    📚 Total: 17   │
├──────────────────────────────────┤
│ [ACTIVOS] [DEVUELTOS] [VENCIDOS] │
├──────────────────────────────────┤
│ LIBROS ACTIVOS (Período: 30 días)│
├─────────────┬──────────┬─────────┤
│Libro        │Devuelvo  │Renovables
├─────────────┼──────────┼─────────┤
│Design Pat.. │02/05/26  │ 2 más ✓ │
│Clean Java   │15/04/26  │ 1 más   │
│Web Dev...   │22/04/26  │ 3 más ✓ │
└──────────────────────────────────┘
```

---

## 🎯 Ejemplo Real: Preparar Clase

### Escenario: Clase de "Patrones de Diseño"

```
SEMANA 1 (01/03/2026):
1. Busco "Design Patterns" en Biblioteca Digital
2. Presiono "Solicitar para Clase"
3. Completo:
   Propósito: "Referencias para clase Patrones de Diseño"
   Grupo: "Ingeniería en Informática - Sección A"
   Período: "Marzo - Abril (4 sesiones)"

RESULTADO:
✅ Préstamo aprobado
Fecha devolución: 30/04/2026 (período especial de profesor)

SEMANA 4 (22/03/2026):
Notificación: "Vence en 8 días"
→ Presiono [Renovar]
→ Nueva fecha: 30/05/2026

SEMANA 8 (28/04/2026):
✓ Devuelvo el libro en biblioteca física
✓ Mi historial se actualiza automáticamente
```

---

## 💡 Tips para Profesores

### ✅ Mejores Prácticas

| Tip | Razón |
|-----|-------|
| Planifica libros con anticipación | La renovación es limitada (3x máx) |
| Notifica al admin de necesidades | Puede comprar libros para la carrera |
| Renueva ANTES de que venza | No acumules retrasos |
| Usa "comentarios" para colegas | Comparte referencias valiosas |
| Solicita "sección académica" para estudiantes | Libros de consulta en campus |

### ❌ Restricciones Importantes

```
⚠️ No puedes:
   • Solicitar más de 10 libros simultáneamente
   • Renovar más de 3 veces el mismo libro
   • Prestar tu tarjeta a otros usuarios
   • Fotocopiar libros completos (respeta copyright)

✅ Sí puedes:
   • Usar fotocopiadora para capítulos específicos (10% máx)
   • Recomendar libros nuevos al área académica
   • Acceder a sección de referencias sin límite de consulta
```

---

## 📞 Contacto Especial para Profesores

### Email Directo de Soporte Académico
```
academia@untec.cl
Asunto: "Soporte Profesor - Biblioteca"

Respuesta esperada: 24 horas máximo
```

### Solicitudes Especiales
```
Si necesitas:
• Compra de libros nuevos
• Acceso a base de datos académica
• Sección cerrada (laboratorio, etc)
• Donaciones de libros

Contacta directamente a:
direccion.biblioteca@untec.cl
```

---

## 🎓 Otros Recursos Docentes

### Integración con LMS (Aula Virtual)
```
Si tu institución usa plataforma de aula virtual:
Puedes compartir referencias de Biblioteca Digital

Ejemplo para estudiantes:
"Para la lectura obligatoria de esta semana,
consulta 'Effective Java' en la Biblioteca Digital
(Puedes solicitarlo por 14 días)"
```

### Crear Bibliografía Sincronizada
```
Próximamente: Feature para exportar tu historial
a formato para bibliografía en trabajos

Formatos soportados:
• APA
• Chicago
• MLA
• Harvard
```

---

## ✅ Checklist del Profesor

- [ ] Tengo acceso a 30 días de préstamo
- [ ] Sé cómo renovar un libro (hasta 3 veces)
- [ ] Conozco la sección académica
- [ ] Puedo solicitar libros para clase
- [ ] Entiendo los límites (10 libros activos)
- [ ] Tengo el contacto de soporte

---

**Documento versión 1.0** | Última actualización: 16/03/2026

**¿Necesitas ayuda?** Contacta a: academia@untec.cl 📚
