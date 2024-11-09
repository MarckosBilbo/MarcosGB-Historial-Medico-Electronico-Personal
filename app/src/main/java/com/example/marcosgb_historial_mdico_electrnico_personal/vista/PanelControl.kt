package com.example.marcosgb_historial_mdico_electrnico_personal.vista

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.Firebase
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.SQLite
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanelControl(
    navController: NavHostController,
    sqliteHelper: SQLite,
    firebaseHelper: Firebase,
    firebaseAuth: FirebaseAuth
) {
    var numeroUsuarios by remember { mutableStateOf(0) }
    var satisfaccionServicio by remember { mutableStateOf("Indiferente") }
    var satisfaccionSeguridad by remember { mutableStateOf("Indiferente") }
    var satisfaccionUtilidad by remember { mutableStateOf("Indiferente") }
    var tipoProblema by remember { mutableStateOf("Otro") }
    var gravedadProblema by remember { mutableStateOf("Media") }
    var longevidadProblema by remember { mutableStateOf("Semanas") }
    var perfilMedico by remember { mutableStateOf("General") }

    LaunchedEffect(Unit) {
        // Obtener el número de usuarios registrados
        firebaseHelper.obtenerNumeroUsuarios(object : Firebase.NumeroUsuariosCallback {
            override fun onSuccess(numero: Int) {
                numeroUsuarios = numero
            }

            override fun onFailure(e: Exception) {
                // Manejar error
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Control", fontSize = 24.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text("Número de usuarios registrados: $numeroUsuarios", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            EncuestaSatisfaccion(
                satisfaccionServicio = satisfaccionServicio,
                onSatisfaccionServicioChange = { satisfaccionServicio = it },
                satisfaccionSeguridad = satisfaccionSeguridad,
                onSatisfaccionSeguridadChange = { satisfaccionSeguridad = it },
                satisfaccionUtilidad = satisfaccionUtilidad,
                onSatisfaccionUtilidadChange = { satisfaccionUtilidad = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            EncuestaMedica(
                tipoProblema = tipoProblema,
                onTipoProblemaChange = { tipoProblema = it },
                gravedadProblema = gravedadProblema,
                onGravedadProblemaChange = { gravedadProblema = it },
                longevidadProblema = longevidadProblema,
                onLongevidadProblemaChange = { longevidadProblema = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                perfilMedico = asignarPerfilMedico(tipoProblema, gravedadProblema, longevidadProblema)
                // Guardar resultados en Firebase
                firebaseAuth.currentUser?.let { user ->
                    firebaseHelper.guardarResultadosEncuesta(
                        user.uid,
                        satisfaccionServicio,
                        satisfaccionSeguridad,
                        satisfaccionUtilidad,
                        tipoProblema,
                        gravedadProblema,
                        longevidadProblema,
                        perfilMedico
                    )
                }
            }) {
                Text("Enviar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("pantalla_principal") },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Volver a la Pantalla Principal")
            }
        }
    }
}

@Composable
fun EncuestaSatisfaccion(
    satisfaccionServicio: String,
    onSatisfaccionServicioChange: (String) -> Unit,
    satisfaccionSeguridad: String,
    onSatisfaccionSeguridadChange: (String) -> Unit,
    satisfaccionUtilidad: String,
    onSatisfaccionUtilidadChange: (String) -> Unit
) {
    Column {
        Text("Satisfacción del Servicio")
        Row {
            RadioButton(selected = satisfaccionServicio == "Satisfecho", onClick = { onSatisfaccionServicioChange("Satisfecho") })
            Text("Satisfecho")
            RadioButton(selected = satisfaccionServicio == "Indiferente", onClick = { onSatisfaccionServicioChange("Indiferente") })
            Text("Indiferente")
            RadioButton(selected = satisfaccionServicio == "Insatisfecho", onClick = { onSatisfaccionServicioChange("Insatisfecho") })
            Text("Insatisfecho")
        }
        Text("Satisfacción con la Seguridad")
        Row {
            RadioButton(selected = satisfaccionSeguridad == "Satisfecho", onClick = { onSatisfaccionSeguridadChange("Satisfecho") })
            Text("Satisfecho")
            RadioButton(selected = satisfaccionSeguridad == "Indiferente", onClick = { onSatisfaccionSeguridadChange("Indiferente") })
            Text("Indiferente")
            RadioButton(selected = satisfaccionSeguridad == "Insatisfecho", onClick = { onSatisfaccionSeguridadChange("Insatisfecho") })
            Text("Insatisfecho")
        }
        Text("Satisfacción con la Utilidad")
        Row {
            RadioButton(selected = satisfaccionUtilidad == "Satisfecho", onClick = { onSatisfaccionUtilidadChange("Satisfecho") })
            Text("Satisfecho")
            RadioButton(selected = satisfaccionUtilidad == "Indiferente", onClick = { onSatisfaccionUtilidadChange("Indiferente") })
            Text("Indiferente")
            RadioButton(selected = satisfaccionUtilidad == "Insatisfecho", onClick = { onSatisfaccionUtilidadChange("Insatisfecho") })
            Text("Insatisfecho")
        }
    }
}

@Composable
fun EncuestaMedica(
    tipoProblema: String,
    onTipoProblemaChange: (String) -> Unit,
    gravedadProblema: String,
    onGravedadProblemaChange: (String) -> Unit,
    longevidadProblema: String,
    onLongevidadProblemaChange: (String) -> Unit
) {
    Column {
        Text("Tipo de Problema Médico")
        Row {
            RadioButton(selected = tipoProblema == "Virus", onClick = { onTipoProblemaChange("Virus") })
            Text("Virus")
            RadioButton(selected = tipoProblema == "Físico", onClick = { onTipoProblemaChange("Físico") })
            Text("Físico")
            RadioButton(selected = tipoProblema == "Otro", onClick = { onTipoProblemaChange("Otro") })
            Text("Otro")
        }
        Text("Gravedad del Problema")
        Row {
            RadioButton(selected = gravedadProblema == "Alta", onClick = { onGravedadProblemaChange("Alta") })
            Text("Alta")
            RadioButton(selected = gravedadProblema == "Media", onClick = { onGravedadProblemaChange("Media") })
            Text("Media")
            RadioButton(selected = gravedadProblema == "Baja", onClick = { onGravedadProblemaChange("Baja") })
            Text("Baja")
        }
        Text("Longevidad del Problema")
        Row {
            RadioButton(selected = longevidadProblema == "Días", onClick = { onLongevidadProblemaChange("Días") })
            Text("Días")
            RadioButton(selected = longevidadProblema == "Semanas", onClick = { onLongevidadProblemaChange("Semanas") })
            Text("Semanas")
            RadioButton(selected = longevidadProblema == "Más", onClick = { onLongevidadProblemaChange("Más") })
            Text("Más")
        }
    }
}

fun asignarPerfilMedico(tipoProblema: String, gravedadProblema: String, longevidadProblema: String): String {
    return when {
        tipoProblema == "Virus" && gravedadProblema == "Alta" -> "Especialista en Enfermedades Infecciosas"
        tipoProblema == "Físico" && gravedadProblema == "Alta" -> "Traumatólogo"
        tipoProblema == "Otro" && gravedadProblema == "Alta" -> "Médico General"
        else -> "Médico General"
    }
}