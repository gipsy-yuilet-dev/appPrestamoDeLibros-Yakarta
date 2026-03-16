# ❓ FAQs y Troubleshooting - Biblioteca Digital UNTEC

Soluciones rápidas para los problemas más comunes. Si tu pregunta no está aquí, contacta soporte.

**Tiempo de lectura**: 5 minutos | **Nivel**: Todos

---

## 📋 Índice Rápido

1. [Problemas de Login](#-problemas-de-login)
2. [Búsqueda y Catálogo](#-búsqueda-y-catálogo)
3. [Solicitud de Préstamos](#-solicitud-de-préstamos)
4. [Historial y Notificaciones](#-historial-y-notificaciones)
5. [Devoluciones](#-devoluciones)
6. [Panel Administrativo](#-panel-administrativo)
7. [Problemas Técnicos](#-problemas-técnicos)

---

## 🔑 Problemas de Login

### ❌ P1: "Correo o contraseña incorrectos"

**Problema**: No puedo ingresar al sistema

**Soluciones** (en orden):

```
1️⃣ VERIFICA QUE ESCRIBISTE BIEN:
   Email: juan.perez@untec.cl  (busca errores de tipeo)
   
2️⃣ REINICIA EL NAVEGADOR:
   • Firefox: Ctrl + Shift + Del → Borrar cookies
   • Chrome: Ctrl + Shift + Del → Borrar datos
   • Vuelve a: http://localhost:8080/biblioteca
   
3️⃣ ¿ERES NUEVO USUARIO?
   Contacta a: soporte@untec.cl
   Asunto: "Crear mi cuenta en Biblioteca"
   
4️⃣ ¿OLVIDASTE CONTRASEÑA?
   ⚠️ Actualmente no hay auto-reset
   Email a: soporte@untec.cl
   Respuesta: 24 horas (se reseteará manualmente)
```

**Resultado esperado**: ✅ Login exitoso

---

### ❌ P2: "Tu sesión ha expirado"

**Problema**: Estaba usando el sistema y de repente me sacó

**Causas y Soluciones**:

```
CAUSA 1: Mucho tiempo inactivo (> 2 horas)
SOLUCIÓN: Login nuevamente

CAUSA 2: Multiples navegadores con tu cuenta
SOLUCIÓN: Cierra todas las pestañas de Biblioteca y vuelve a login

CAUSA 3: Cambio de contraseña en otro dispositivo
SOLUCIÓN: Cierra navegador y login nuevamente
```

---

### ❌ P3: "Acceso Denegado"

**Problema**: Entro al login pero luego me dice que no tengo acceso

**Explicación**:
```
Significa que tu cuenta está BLOQUEADA por:
✗ Deuda de libros sin devolver (>14 días)
✗ Demasiados intentos fallidos de login
✗ Sanción manual del administrador
```

**Soluciones**:

```
SI ES POR DEUDA:
1. Ve a devolver los libros a biblioteca física
2. Avisa al admin: biblioteca@untec.cl
3. Admin desbloqueará tu cuenta (24-48 horas)

SI ES POR SANCIÓN:
→ Contacta a: soporte@untec.cl
   Asunto: "Solicitud de desbloqueo de cuenta"
```

**Contacto urgente**: +56 9 XXXX-XXXX

---

## 🔍 Búsqueda y Catálogo

### ❌ P4: "No encuentro el libro que busco"

**Problema**: Escribo el título pero no aparece

**Soluciones** (paso a paso):

```
1️⃣ REVISA LA ORTOGRAFÍA:
   ✗ "Head Fisrt Java" (incorrecto)
   ✓ "Head First Java" (correcto)
   
2️⃣ BUSCA POR AUTOR:
   En lugar de título, prueba:
   Ej: "Kathy Sierra" (si buscas Head First Java)
   
3️⃣ USA BÚSQUEDA PARCIAL:
   ✗ "Head First Java Comprehensive Guide"
   ✓ "Head First" (busca solo primera palabra)
   
4️⃣ FILTRA POR CATEGORÍA:
   1. Click "Categoría" en menú lateral
   2. Selecciona "Programación"
   3. Hojea la lista
   
5️⃣ EL LIBRO NO ESTÁ EN SISTEMA:
   Si realmente no lo encuentras:
   → Email a: sugerencias@untec.cl
   → Asunto: "Sugerir compra: [Nombre del Libro]"
   → El departamento lo considerará
```

**Nota**: Solo verás libros "activos". Si fue desactivado, no aparecerá.

---

### ❌ P5: "Dice 'Libro no disponible' pero necesito usarlo"

**Problema**: Todas las copias están prestadas

**Opciones**:

```
✅ OPCIÓN 1: ESPERAR (Recomendado)
   → Vuelve mañana, alguien puede devolverlo
   → Promedio: 2-3 días
   
✅ OPCIÓN 2: SOLICITAR EXTENDER ESPERA
   Email a: biblioteca@untec.cl
   Asunto: "Reservar libro [nombre] cuando esté disponible"
   Respuesta: Te avisamos cuando llegue
   
✅ OPCIÓN 3: BUSCAR SIMILAR
   → "Design Patterns" también tiene info sobre patrones
   → "Effective Java" como complemento
   → Pregunta a tu profesor
```

---

## 📌 Solicitud de Préstamos

### ❌ P6: "No puedo solicitar un préstamo"

**Problema**: Click en [Solicitar Préstamo] no funciona

**Causas Comunes**:

```
CAUSA 1: No estás logueado
SOLUCIÓN: Ve a login y vuelve al libro

CAUSA 2: Tu usuario está bloqueado
SOLUCIÓN: Ver P3 (Acceso Denegado)

CAUSA 3: JavaScript deshabilitado en navegador
SOLUCIÓN: 
   Firefox: about:config → javascript.enabled = true
   Chrome: Configuración → Privacidad → Javascript (Habilitado)
   Safari: Menú → Desarrollo → Habilitar JavaScript

CAUSA 4: Navegador muy antiguo (< 2019)
SOLUCIÓN: Actualiza Firefox o Chrome
```

**Si nada funciona**:
```
Email a: soporte@untec.cl
Adjunta:
• Nombre del libro
• Tu email
• Navegador (Chrome v120, Firefox v122, etc)
```

---

### ❌ P7: "¿Cuántos días puedo tener un libro?"

**Respuesta según tu tipo de usuario**:

| Tipo | Período | Renovaciones |
|------|---------|--------------|
| **Estudiante** | 14 días | 1 vez |
| **Profesor** | 30 días | 3 veces |
| **Admin** | Indefinido | S/A |

**Ejemplo**:
```
Juan (Estudiante) solicita libro el 16/03
Fecha devolución: 30/03 (14 días después)

Si vence el 30/03 pero aún lo necesita:
→ Click [Renovar] en su historial
→ Nueva fecha: 13/04 (14 días más)
→ Solo puede renovar 1 vez total
```

---

## 📖 Historial y Notificaciones

### ❌ P8: "No veo mis notificaciones"

**Problema**: El sistema dice que tengo alertas pero no aparecen

**Soluciones**:

```
1️⃣ REVISA LA ESQUINA SUPERIOR DERECHA:
   Las notificaciones aparecen en pequeñas cajas
   (arriba y a la derecha de la pantalla)
   
2️⃣ REFRESCA LA PÁGINA:
   F5 o presiona Ctrl + R
   Las notificaciones se actualizan cada 5 min
   
3️⃣ ABRE TU HISTORIAL:
   Click en tu nombre → "Mi Historial"
   Verás el estado de TODOS tus préstamos
   (Mi historial SÍ muestra estado real)
   
4️⃣ JAVASCRIPT ESTÁ DESHABILITADO:
   Las notificaciones requieren JavaScript
   → Ver P6, Causa 3 (Habilitar JavaScript)
   
5️⃣ NAVEGADOR NO SOPORTA:
   Si tienes navegador SUPER antiguo:
   → Actualiza a Chrome o Firefox reciente
```

---

### ❌ P9: "Mi historial no se actualiza"

**Problema**: Devolví un libro pero sigue mostrando como "Activo"

**Motivos y Soluciones**:

```
MOTIVO 1: Acabas de devolver (< 5 minutos)
SOLUCIÓN: Espera 5 minutos y refresca (F5)

MOTIVO 2: Admin aún no ha registrado la devolución
SOLUCIÓN: 
   → Busca al admin en biblioteca
   → Dale el comprobante de devolución
   → En 24 horas se actualiza el sistema

MOTIVO 3: Problema técnico
SOLUCIÓN:
   Email a: soporte@untec.cl
   Adjunta: 
   • Tu nombre
   • Libro que devolviste
   • Fecha de devolución
   • Comprobante (si tienes)
```

---

## 🔄 Devoluciones

### ❌ P10: "¿Cómo devuelvo un libro?"

**Respuesta**: 

```
PASO 1: VE A LA BIBLIOTECA FÍSICA
Ubicación: Campus UNTEC, Piso 2, Sector A
Horarios: L-V 08:00-18:00, Sáb 09:00-13:00

PASO 2: BUSCA AL PERSONAL
Di: "Quiero devolver estos libros"

PASO 3: ELLOS REGISTRAN EN SISTEMA
El personal admin lo registra en Biblioteca Digital
(autom instantáneamente en pantalla de admin)

PASO 4: VERIFICAS EN TU HISTORIAL
Dentro de 24 horas tu historial se actualiza
(Cambia de ACTIVO a DEVUELTO)
```

**¿Puedo devolver por correo?**
```
Sí, pero:
✉️ Contacta a: devolicines@untec.cl
   Asunto: "Solicitud de devolución por correo"
   
⚠️ Tú pagas el envío y el riesgo
   (Se recomienda seguimiento)

⏱️ Tiempo de llegada: 3-5 días laborales

📋 Procedure:
   1. Empacar bien el/los libro(s)
   2. Enviar a: Biblioteca UNTEC, Campus Central
   3. Guardar comprobante de envío
   4. Email con comprobante a soporte@untec.cl
```

---

### ❌ P11: "¿Qué pasa si devuelvo tarde?"

**Respuesta**: Depende de cuántos días de atraso:

| Atraso | Consecuencia |
|--------|--------------|
| **0-3 días** | Sin penalización |
| **4-7 días** | Aviso de soporte |
| **8-14 días** | Bloqueo temporal (no puedes solicitar más) |
| **> 14 días** | Bloqueo permanente + comunicación a dirección |

**¿Puede perdonarme?**
```
Sí, contacta a: biblioteca@untec.cl

Casos que pueden perdonarse:
✅ Emergencia médica con comprobante
✅ Fuerza mayor (accidente, desastre natural)
✅ Error administrativo

Casos que NO se perdonan:
✗ "Se me olvidó"
✗ "Estaba ocupado"
✗ Atrasos repidos
```

---

### ❌ P12: "El libro tiene daños, ¿tengo que pagarlo?"

**Respuesta según el daño**:

```
DAÑO LEVE (rayones, poco desgaste):
→ Sin costo adicional
→ Se registra en sistema
→ Admin decide si lo repara o descarta

DAÑO MODERADO (páginas rotas, mojado):
→ Costo: 30% del valor del libro
→ Factura expedida

DAÑO GRAVE (no legible, destruido):
→ Costo: 100% del valor del libro + multa
→ Debes comprar reemplazo

PÉRDIDA COMPLETA:
→ Costo: 150% del valor del libro
→ Debes comprar reemplazo + multa
```

**Procedimiento**:
```
1. Devuelves el libro dañado
2. Admin lo evalúa en el momento
3. Te dice: "Sin cargo" o "Debes $X"
4. Si hay cargo: factura + payment link
5. Pagas en 7 días o se bloquea tu cuenta
```

---

## 🖥️ Panel Administrativo

### ❌ P13: "No puedo acceder al panel admin"

**Problema**: No veo opción de "Panel Admin"

**Soluciones**:

```
VERIFICAR 1: ¿Realmente soy admin?
→ Tu email debe estar registrado como ADMIN
→ Contacta a: soporte-admin@untec.cl
→ Pedirá comprobante de autorización

VERIFICAR 2: ¿Logout y login nuevamente?
→ Cierra sesión
→ Borra cookies (Ctrl + Shift + Del)
→ Login nuevamente

VERIFICAR 3: ¿Navegador moderno?
→ Admin panel requiere navegador reciente
→ Actualiza Chrome, Firefox o Safari

VERIFICAR 4: ¿Tienes JavaScript?
→ Ver P6, Causa 3
```

---

### ❌ P14: "Olvidé de cambiar mi password de admin"

**Este es CRÍTICO en producción**:

```
⚠️ ¡CAMBIOS INMEDIATAMENTE!

PASOS:
1. Click en tu nombre (arriba derecha)
2. Selecciona "Mi Perfil"
3. Click [Cambiar Contraseña]
4. Ingresa:
   - Password actual: 123456
   - Password nuevo: [MÁS SEGURO]
   - Confirmar: [REPETIR]
5. Click [Guardar]

✅ IMPORTANTE:
   • Usa 8+ caracteres
   • Mezcla mayúsculas, números, símbolos
   • No uses "admin", "password", tu nombre
   • Guárdala en un lugar seguro

EJEMPLOS MALOS: admin123, password1, untec2026
EJEMPLOS BUENOS: Untec@2026#Lib7, B1bliot3c@2k26
```

---

## 🔧 Problemas Técnicos

### ❌ P15: "Página se carga lentamente o no carga"

**Problema**: Biblioteca tarda mucho o se queda en blanco

**Soluciones**:

```
PASO 1: ¿ESTÁS CONECTADO A INTERNET?
   → Abre Google.com
   → Si funciona, continúa
   → Si no, reinicia tu router

PASO 2: LIMPIA CACHÉ DEL NAVEGADOR
   → Firefox: Ctrl + Shift + Del
   → Chrome: Ctrl + Shift + Del
   → Selecciona "Toda hora"
   → Marca: Cookies, Imágenes, Archivos
   → Click [Borrar datos]

PASO 3: REINICIA NAVEGADOR COMPLETAMENTE
   → Cierra todas las pestañas
   → Abre nueva pestaña
   → Escribe: http://localhost:8080/biblioteca
   → Presiona ENTER

PASO 4: INTENTA CON OTRO NAVEGADOR
   Si Firefox es lento → prueba Chrome
   Si Chrome falla → prueba Safari

PASO 5: CONTACTA SOPORTE
   Si sigue lento:
   Email a: soporte@untec.cl
   Adjunta: Navegador (Chrome v120), OS (Windows 10)
```

---

### ❌ P16: "Error 500 - Something went wrong"

**Problema**: Pantalla de error roja del servidor

**Significado**: El sistema tiene un problema interno

**Qué hacer**:

```
1️⃣ SI ACABAS DE SOLICITAR UN PRÉSTAMO:
   → No hagas click de nuevo
   → Espera 30 segundos
   → Refresca (F5)
   → Revisa tu historial para confirmar
   
2️⃣ SI PERSISTE:
   → Intenta logout
   → Cierra navegador completamente
   → Abre una nueva sesión de incógnito
   → Login nuevamente
   
3️⃣ SI AÚN FALLA:
   Email a: soporte@untec.cl
   Asunto: "Error 500 en Biblioteca"
   Información a proporcionar:
   • Qué estabas haciendo
   • URL exacto donde ocurrió
   • Hora del error
   • Tu navegador y versión
   
   Tiempo respuesta: 2-4 horas
```

---

### ❌ P17: "No funciona la búsqueda"

**Problema**: Escribo en la barra de búsqueda pero no filtra

**Causas**:

```
CAUSA 1: No presionaste ENTER
SOLUCIÓN: Escribe + ENTER (no solo escribir)

CAUSA 2: JavaScript deshabilitado
SOLUCIÓN: Ver P6, Causa 3

CAUSA 3: Caché del navegador
SOLUCIÓN: Ctrl + Shift + Del → Borrar todo

CAUSA 4: Problema de servidor
SOLUCIÓN:
   • Intenta con Firefox si usas Chrome
   • Contacta a: soporte@untec.cl
   • Espera 30 min (servidor se reinicia solo)
```

---

## 📞 Contacto de Soporte General

### Email: soporte@untec.cl

**Información a incluir siempre**:
```
Asunto: [DEBE SER DESCRIPTIVO]
Ej: "Error 500 al solicitar préstamo"
   "No puedo hacer login"
   "Libro no aparece en búsqueda"

Cuerpo:
1. ¿Cuál es tu problema exacto?
2. ¿Qué pasos hiciste?
3. ¿Qué error viste?
4. ¿Navegador y versión?
5. ¿A qué hora ocurrió?

Archivos adjuntos (si es posible):
• Screenshot del error
• Comprobante de transacción
```

### Tiempo de Respuesta
```
Normal: 24-48 horas
Urgente: Llamar a +56 9 XXXX-XXXX
```

---

## 🎓 Ejemplo de Ticket Bien Hecho

```
ASUNTO: Error 500 al solicitar "Head First Java"

DESCRIPCIÓN:
Hola, soy Juan Pérez (juan.perez@untec.cl).

Hoy a las 14:30,  intenté solicitar el libro
"Head First Java" y obtuve error 500.

Pasos que hice:
1. Login ✓
2. Busqué "Head First Java" ✓
3. Presioné "Ver Detalles" ✓
4. Presioné "Solicitar Préstamo" ✗ [ERROR 500]

Mensaje de error:
"Sorry, something went wrong. Please try again later."

Mi navegador: Google Chrome versión 122.0.6261.128
Mi SO: Windows 10
Hora del error: 2026-03-16 14:32 EST

¿Pueden ayudarme?
```

---

## ✅ Checklist de Troubleshooting

Antes de contactar soporte, asegúrate de:
- [ ] Estoy logueado
- [ ] Mi navegador está actualizado (2022+)
- [ ] JavaScript está habilitado
- [ ] Borré caché del navegador
- [ ] Intenté logout/login
- [ ] Probé con otro navegador
- [ ] Esperé 30 minutos (reinicio de servidor)

---

**Documento versión 1.0** | Última actualización: 16/03/2026

**¿Tu problema no está aquí?** Email: soporte@untec.cl 📧
