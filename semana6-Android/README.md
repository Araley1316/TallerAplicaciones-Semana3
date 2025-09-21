# Aplicación Android – Autenticación con Firebase + Almacenamiento de GPS  
**Taller de Aplicaciones – Semana 6**

Este proyecto corresponde al desarrollo de una aplicación Android (Kotlin + Jetpack Compose) que:  
- Permite iniciar sesión con Firebase Authentication (correo y contraseña).  
- Almacena en Firebase Realtime Database la posición GPS del dispositivo del usuario autenticado.  

El flujo base es móvil con interfaz Compose; se prueba en emulador configurando la ubicación desde los Extended Controls del AVD.  

---

## Objetivo general
Construir un prototipo funcional que demuestre:  
- Autenticación segura con email/password en Firebase.  
- Navegación post-login hacia un menú que muestra el correo activo.  
- Solicitud de permisos de ubicación en tiempo de ejecución.  
- Obtención y persistencia de la última ubicación (lat, lon, timestamp) en Realtime Database bajo la ruta `/users/{uid}/lastLocation`.  

---

## Caso de estudio
Como parte de un sistema interno, se requiere:  
- Validar la identidad del usuario (correo/contraseña).  
- Una vez autenticado, guardar en backend la ubicación del dispositivo (para auditoría / trazabilidad).  
- Permitir al usuario cerrar sesión cuando lo desee.  

**Nota**: en emulador, la ubicación depende de lo que se configure en *Location* (no es la ubicación real del PC).  

---

## Requerimientos funcionales

### Login (Firebase Auth = email/contraseña)
- Campo de correo y contraseña.  
- Botón Ingresar que valida y autentica.  
- Manejo de mensajes de error (credenciales inválidas, red, etc.).  

### Menú post-login
- Muestra el correo del usuario autenticado.  
- **Botón Guardar ubicación**:  
  - Solicita permisos de ubicación si no están concedidos.  
  - Obtiene la ubicación del `FusedLocationProviderClient`.  
  - Guarda `{ lat, lon, timestamp }` en `/users/{uid}/lastLocation`.  
- **Botón Cerrar sesión**:  
  - Llama a `FirebaseAuth.signOut()` y vuelve al login.  

### Navegación
- Si no hay sesión válida al abrir la app, va a Login.  
- Si el login es exitoso, va a `MenuActivity`.  

---

## Requerimientos no funcionales
- **Plataforma**: Android (probado en emulador API 30+).  
- **UI**: Jetpack Compose; componentes Material 3.  
- **Seguridad**: manejo de permisos de ubicación en tiempo de ejecución; autenticación con Firebase.  
- **Mantenibilidad**: código separando UI (Compose) de lógica de autenticación/ubicación.  
- **Control de versiones**: Git/GitHub. Carpeta `semana6-Android/` dentro del repo del taller.  
- **Documentación**: este README + capturas en `/evidencias`.  

---

## Historias de usuario

**HU1 – Autenticación**  
Como usuario, quiero iniciar sesión con mi correo y contraseña, para acceder al menú de la aplicación.  

**HU2 – Visualización de sesión**  
Como usuario autenticado, quiero ver mi correo en la pantalla principal, para confirmar que entré con mi cuenta.  

**HU3 – Guardar ubicación**  
Como usuario autenticado, quiero guardar mi ubicación actual en la base de datos, para que el sistema registre mi última posición.  

**HU4 – Cerrar sesión**  
Como usuario, quiero cerrar sesión, para que nadie más use mi cuenta en este dispositivo.  

**HU5 – Evidencias y revisión**  
Como docente/evaluador, quiero revisar el código y las capturas en el repositorio, para validar el cumplimiento de los requisitos.  

---

## Configuración de Firebase
1. Crear proyecto Firebase y añadir app Android (usar `applicationId` del módulo).  
2. Descargar `google-services.json` y colocarlo en `app/src/main/`.  
3. Habilitar Authentication = Email/Password.  
4. Crear Realtime Database (modo de prueba para desarrollo).  

**Reglas básicas de lectura/escritura para pruebas:**

```json
**{
  "rules": {
    "users": {
      "$uid": {
        ".read": "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    }
  }
}**

---
## Flujo de la aplicación

1. **MainActivity** + `setContent { LoginScreen(...) }`
   - Renderiza la UI de login (Compose).
   - Al pulsar **Ingresar**, llama a `FirebaseAuth.signInWithEmailAndPassword`.
   - Si el login es exitoso → `startActivity(MenuActivity)`.

2. **MenuActivity**
   - Muestra el correo del usuario actual: `FirebaseAuth.getInstance().currentUser?.email`.
   - **Botón Guardar ubicación**:
     - Verifica permisos `ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION`.
     - Si faltan → los solicita con `registerForActivityResult`.
     - Usa `FusedLocationProviderClient`:
       - Intenta `lastLocation`.
       - Si es `null`, solicita una actualización con `requestLocationUpdates(PRIORITY_HIGH_ACCURACY)` y toma la primera lectura.
     - Persiste en `/users/{uid}/lastLocation` los campos:
       ```json
       {
         "lat": "<Double>",
         "lon": "<Double>",
         "timestamp": "<ServerValue.TIMESTAMP>"
       }
       ```
     - Muestra un *Toast* de confirmación o error.
   - **Botón Cerrar sesión**:
     - Ejecuta `FirebaseAuth.signOut()` y navega de vuelta a `MainActivity`.

---

## Ejecución y pruebas

1. Compilar y ejecutar el módulo/app.
2. En el emulador, ir a **Extended Controls → Location**:
   - Fijar un punto (p. ej., Santiago o mina Quebrada Blanca).
   - Pulsar **Set location**.
3. Iniciar sesión con un usuario válido (precreado en Firebase Auth).
4. En **MenuActivity**, pulsar **Guardar ubicación**:
   - Aceptar el permiso *“while using the app”*.
   - Ver un *Toast* indicando **“ubicación guardada”**.
5. Verificar en Firebase Realtime Database la ruta:
/users/{uid}/lastLocation

├─ lat
├─ lon
└─ timestamp

**Tip de pruebas**: si `lastLocation` llega `null`, usar la ruta de actualización (el código contempla *fallback* con `requestLocationUpdates`).
---


