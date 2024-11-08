// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Archivo de nivel superior donde puedes agregar opciones de configuración comunes a todos los subproyectos/módulos.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false // Asegúrate de que esta línea esté incluidaz
    id("com.google.gms.google-services") version "4.4.2" apply false // Aplica el plugin de Google Services
}