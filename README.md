# Historial Médico Electrónico Personal (Marcos_Garcia_Benito)

Repositorio --> https://github.com/MarckosBilbo/MarcosGB-Historial-Medico-Electronico-Personal

## Resumen
Este proyecto es una aplicación de historial médico electrónico personal desarrollada en **Kotlin** y **Java**. Utiliza **Firebase** para la autenticación y almacenamiento de datos, **SQLite** para almacenamiento local, y **Jetpack Compose** para la interfaz de usuario.

---

## Características
- **Autenticación de usuario**: inicio de sesión, registro y cierre de sesión usando Firebase.
- **Almacenamiento de historial médico**: almacenar y recuperar registros de historial médico.
- **Sincronización de datos**: entre la base de datos local de SQLite y Firebase.
- **Encuestas de usuario**: satisfacción del usuario y encuestas de salud.
- **Panel de control**: para tareas administrativas.

---

## Estructura del Proyecto
- **modelo**: Contiene los modelos de datos y los ayudantes de base de datos.
  - `HistorialMedico.java`: Modelo de datos para los registros de historial médico.
  - `SQLite.java`: Clase de ayuda para operaciones con la base de datos SQLite.
  - `Firebase.java`: Clase de ayuda para operaciones con Firebase.
  
- **util**: Contiene clases de utilidad.
  - `AlmacenamientoInterno.java`: Utilidad para operaciones de almacenamiento interno.
  - `Encriptacion.java`: Utilidad para encriptación y desencriptación.
  - `SharedPreference.java`: Utilidad para operaciones con preferencias compartidas.

- **vista**: Contiene los componentes de la interfaz de usuario.
  - `PantallaLogin.kt`: Composable para la pantalla de inicio de sesión.
  - `PantallaRegistro.kt`: Composable para la pantalla de registro.
  - `PantallaPrincipal.kt`: Composable para la pantalla principal.
  - `PanelControl.kt`: Composable para la pantalla del panel de control.
  - `MainActivity.java`: Actividad principal que configura la aplicación.
  - `MyApplication.java`: Clase de aplicación para la configuración de Firebase.

---

## Configuración

1. **Clona el repositorio**:  
   ```bash
   git clone https://github.com/MarckosBilbo/Personal-Electronic-Medical-History.git
   cd Personal-Electronic-Medical-History

# Configuración del Proyecto en Android Studio

## Paso 1: Abrir el Proyecto
Abre el proyecto en **Android Studio**.

## Paso 2: Configura Firebase
1. **Agrega tu archivo `google-services.json`**  
   Coloca el archivo `google-services.json` en el directorio `app` del proyecto.
   
2. **Configura Firebase**  
   Asegúrate de que la autenticación y la base de datos de Firebase estén configuradas en la [consola de Firebase](https://console.firebase.google.com/).

## Paso 3: Ejecuta el Proyecto
Compila y ejecuta el proyecto en un dispositivo o emulador Android para verificar la configuración.

---

## Uso de la Aplicación

### Inicio de Sesión
Los usuarios pueden iniciar sesión con su correo electrónico y contraseña registrados.

### Registro
Nuevos usuarios pueden registrarse, y una vez completado el registro, verificar su correo electrónico.

### Pantalla Principal
Los usuarios pueden ver y editar su historial médico desde la pantalla principal.

### Panel de Control
Los administradores tienen acceso a un panel de control donde pueden ver estadísticas de usuarios y resultados de encuestas.

---

## Dependencias del Proyecto

- **Firebase Authentication**  
- **Firebase Realtime Database**  
- **Jetpack Compose**  
- **SQLite**  

---

Continúa con la [configuración del proyecto](#configuración) y asegúrate de tener todas las dependencias correctamente instaladas para una experiencia completa.

# Agradecimientos

Quiero expresar mi agradecimiento por su tiempo y dedicación al explorar este proyecto. Su interés y apoyo son fundamentales para el desarrollo de aplicaciones que puedan mejorar la experiencia de gestión de la salud personal. Valoramos cualquier comentario o sugerencia que pueda ayudarnos a mejorar este proyecto. ¡Gracias!

---

