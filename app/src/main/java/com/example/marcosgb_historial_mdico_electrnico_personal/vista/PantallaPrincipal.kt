package com.example.marcosgb_historial_mdico_electrnico_personal.vista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.Firebase
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.HistorialMedico
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.SQLite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    backgroundColor: Color,
    sqliteHelper: SQLite,
    firebaseHelper: Firebase
) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var fechaNacimiento by remember { mutableStateOf(TextFieldValue("")) }
    var historial by remember { mutableStateOf(TextFieldValue("")) }
    var historiales by remember { mutableStateOf(listOf<HistorialMedico>()) }

    LaunchedEffect(Unit) {
        firebaseHelper.obtenerHistoriales(object : Firebase.DataCallback {
            override fun onSuccess(data: List<HistorialMedico>) {
                historiales = data
            }

            override fun onFailure(e: Exception) {
                // Handle error
            }
        })
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Historial Médico", fontSize = 42.sp) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan)) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(backgroundColor)
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            TextField(
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
                label = { Text("Fecha de Nacimiento") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            TextField(
                value = historial,
                onValueChange = { historial = it },
                label = { Text("Historial Médico") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    val nuevoHistorial = HistorialMedico(0, nombre.text, fechaNacimiento.text, historial.text)
                    sqliteHelper.insertarHistorial(nuevoHistorial)
                    firebaseHelper.saveHistorial(nuevoHistorial)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Historial", fontSize = 30.sp)
            }
            historiales.forEach { historial ->
                Text(
                    text = "${historial.nombre} - ${historial.fechaNacimiento} - ${historial.historial}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}