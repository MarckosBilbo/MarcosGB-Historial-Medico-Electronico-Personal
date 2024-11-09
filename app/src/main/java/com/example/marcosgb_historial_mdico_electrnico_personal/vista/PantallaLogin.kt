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
fun PantallaLogin(navController: NavHostController, firebaseAuth: FirebaseAuth) {
    var correo by remember { mutableStateOf(TextFieldValue("")) }
    var contrasena by remember { mutableStateOf(TextFieldValue("")) }
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
        Button(
            onClick = {
                if (correo.text.isEmpty() || contrasena.text.isEmpty()) {
                    mensajeError = "Por favor, completa todos los campos."
                } else {
                    firebaseAuth.signInWithEmailAndPassword(correo.text, contrasena.text)
                        .addOnCompleteListener { tarea ->
                            if (tarea.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                if (user != null && user.isEmailVerified) {
                                    navController.navigate("pantalla_principal")
                                } else {
                                    mensajeError = "Por favor, verifica tu correo electrónico."
                                }
                            } else {
                                mensajeError = tarea.exception?.message ?: "Error al iniciar sesión"
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
        if (mensajeError.isNotEmpty()) {
            Text(mensajeError, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        }
        Button(
            onClick = { navController.navigate("pantalla_registro") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Ir a Registro")
        }
    }
}