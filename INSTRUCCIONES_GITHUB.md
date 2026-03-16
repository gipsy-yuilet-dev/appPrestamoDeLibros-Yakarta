# 📤 Instrucciones para subir a GitHub

## ✅ Lo que ya completé

Tu proyecto está listo para GitHub. Los pasos ejecutados fueron:

```bash
✅ git init                                    # Inicializar repositorio
✅ git config user.name "gipsy-yuilet-dev"   # Configurar usuario
✅ git add --all                              # Agregar todos los archivos
✅ git commit -m "..."                        # Commit inicial (56 archivos)
✅ git remote add origin <URL>                # Agregar repositorio remoto
```

---

## 📋 Estado Actual

```
Repository: https://github.com/gipsy-yuilet-dev/appPrestamoDeLibros-Yakarta
Branch: main
Commits: 1 (commit inicial)
Files: 56 archivos (12,635 líneas de código)
```

---

## 🚀 Paso Final: Hacer Push a GitHub

### Opción 1: Usar GitHub CLI (RECOMENDADO - Más fácil)

```bash
# 1. Instalar GitHub CLI si no lo tienes
# Descarga desde: https://cli.github.com/

# 2. Autenticarse con GitHub (se abre navegador)
gh auth login

# 3. Hacer push
git push -u origin main
```

---

### Opción 2: Usar Token Personal de GitHub (Token Based)

#### Paso 1: Crear un Personal Access Token en GitHub

```
1. Ve a: https://github.com/settings/tokens
2. Click en "Generate new token" → "Generate new token (classic)"
3. Configura:
   - Name: "Git CLI Token"
   - Expiration: 90 días (o más)
   - Select scopes:
     ✅ repo (Full control of private repositories)
     ✅ workflow (Update GitHub Action workflows)
4. Click "Generate token"
5. COPIA el token (no lo compartas)
```

#### Paso 2: Hacer Push con Token

```bash
git push https://gipsy-yuilet-dev:<TU_TOKEN>@github.com/gipsy-yuilet-dev/appPrestamoDeLibros-Yakarta.git
```

**Reemplaza `<TU_TOKEN>` con el token que copiaste**

---

### Opción 3: Almacenar Token en Git (Sin escribir cada vez)

#### Para Windows (Credential Manager):

```bash
# Git lo pedirá las credenciales la primera vez
git push -u origin main

# Te aparecerá un popup pidiendo credenciales:
# Username: gipsy-yuilet-dev
# Password: <pega tu token aquí>

# Git recordará la credencial automáticamente
```

#### Para Linux/Mac:

```bash
# Configurar caché de credenciales por 1 hora
git config --global credential.helper cache

# Hacer push (pedirá token una vez)
git push -u origin main

# Git recordará por 1 hora
```

---

## ⚠️ IMPORTANTE: Cambiar Email del Commit

Primero, cambia el email de Git a uno válido:

```bash
git config user.email "tuCorreo@gmail.com"
git commit --amend --no-edit
git push -u origin main
```

---

## 🎯 Comando Rápido Completo

**Copia, pega y ejecuta esto en PowerShell:**

```powershell
cd f:\RespaldoMelek\CursoJavaTalentoDigital\javaProyectosTalentoDigital\tareas\bibliotecaUntecModuloV\untec

# Actualizar email
git config user.email "tu-email@gmail.com"

# Hacer push
git push -u origin main
```

---

## ✅ Verificar que Funcionó

```bash
# Ver estado
git status

# Debería mostrar:
# On branch main
# Your branch is up to date with 'origin/main'.
```

---

## 📊 Después de hacer Push

Podrás ver tu código en:
```
https://github.com/gipsy-yuilet-dev/appPrestamoDeLibros-Yakarta
```

---

## 🔧 Solucionar Errores Comunes

### Error: "fatal: repository not found"

**Causas:**
- La URL es incorrecta
- No tienes permisos en el repositorio
- El repositorio no existe en GitHub

**Solución:**
```bash
# Verificar la URL
git remote -v

# Si es incorrecta, cambiarla
git remote set-url origin https://github.com/gipsy-yuilet-dev/appPrestamoDeLibros-Yakarta.git
```

### Error: "Authentication failed"

**Causas:**
- Token inválido o expirado
- Contraseña incorrecta

**Solución:**
```bash
# Crear nuevo token (ver Paso 1 arriba)
# O usar GitHub CLI (opción 1)
gh auth login
git push -u origin main
```

---

## 📝 Próximos Pasos (Opcionales)

Después de hacer push, puedes:

1. **Crear un README en GitHub**
   - El `README_GITHUB.md` que creaste se mostrará automáticamente

2. **Agregar descripción del repo**
   - Ve a Settings → Edit description
   - Escribe: "Sistema web de gestión digital de préstamos de libros"

3. **Agregar Topics** (etiquetas)
   - Topics: `java` `jakarta-ee` `maven` `biblioteca` `tomcat`

4. **Activar GitHub Pages** (Opcional)
   - Settings → Pages → Source: main
   - Tus documentos Markdown se mostrarán como sitio web

---

## 💡 Tips Importantes

```
✅ Haz commits pequenos y frecuentes
✅ Escribe mensajes de commit descriptivos
✅ Usa .gitignore para excluir archivos innecesarios
✅ No hagas commit de archivos sensibles (contraseñas, tokens)
✅ Antes de push, verifica: git status
```

---

**¿Necesitas ayuda? Contáctame 📧**
