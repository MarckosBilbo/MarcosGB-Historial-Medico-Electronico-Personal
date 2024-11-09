package com.example.marcosgb_historial_mdico_electrnico_personal.vista

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaRegistro(navController: NavHostController, firebaseAuth: FirebaseAuth) {
    var correo by remember { mutableStateOf(TextFieldValue("")) }
    var contrasena by remember { mutableStateOf(TextFieldValue("")) }
    var confirmarContrasena by remember { mutableStateOf(TextFieldValue("")) }
    var mensajeError by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        TextField(
            value = confirmarContrasena,
            onValueChange = { confirmarContrasena = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                if (correo.text.isEmpty() || contrasena.text.isEmpty() || confirmarContrasena.text.isEmpty()) {
                    mensajeError = "Por favor, completa todos los campos."
                } else if (contrasena.text != confirmarContrasena.text) {
                    mensajeError = "Las contraseñas no coinciden"
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(correo.text, contrasena.text)
                        .addOnCompleteListener { tarea ->
                            if (tarea.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        mensajeError = "Registro exitoso. Por favor, verifica tu correo electrónico."
                                    } else {
                                        mensajeError = "Error al enviar el correo de verificación."
                                    }
                                }
                            } else {
                                mensajeError = tarea.exception?.message ?: "Error al registrarse"
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
        if (mensajeError.isNotEmpty()) {
            Text(mensajeError, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        }
        Button(
            onClick = { navController.navigate("pantalla_login") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Ir a Iniciar sesión")
        }
    }
}