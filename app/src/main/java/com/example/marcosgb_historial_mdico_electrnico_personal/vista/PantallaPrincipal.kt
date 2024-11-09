package com.example.marcosgb_historial_mdico_electrnico_personal.vista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.Firebase
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.HistorialMedico
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.SQLite
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    colorFondo: Color,
    sqliteHelper: SQLite,
    firebaseHelper: Firebase,
    idUsuario: String
) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var fechaNacimiento by remember { mutableStateOf(Calendar.getInstance()) }
    var edad by remember { mutableStateOf(TextFieldValue("")) }
    var estadoSalud by remember { mutableStateOf("Optimo") }
    var especificaciones by remember { mutableStateOf(TextFieldValue("")) }
    var historiales by remember { mutableStateOf(listOf<HistorialMedico>()) }
    var estaEditando by remember { mutableStateOf(false) }
    var idHistorialActual by remember { mutableStateOf(0) }
    var contrasenaActual by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        // Obtener historiales desde SQLite
        val historialesSQLite = sqliteHelper.obtenerHistoriales().filter { it.userId == idUsuario }
        if (historialesSQLite.isNotEmpty()) {
            historiales = historialesSQLite
            val historialUsuario = historiales.first()
            nombre = TextFieldValue(historialUsuario.nombre)
            fechaNacimiento.time = Date(historialUsuario.fechaNacimiento.toLong())
            edad = TextFieldValue(historialUsuario.edad)
            estadoSalud = historialUsuario.estadoSalud
            especificaciones = TextFieldValue(historialUsuario.especificaciones)
            idHistorialActual = historialUsuario.id
            estaEditando = true
        } else {
            // Si no hay datos en SQLite, obtener desde Firebase
            firebaseHelper.obtenerHistoriales(object : Firebase.DataCallback {
                override fun onSuccess(data: List<HistorialMedico>) {
                    historiales = data.filter { it.userId == idUsuario }
                    if (historiales.isNotEmpty()) {
                        val historialUsuario = historiales.first()
                        nombre = TextFieldValue(historialUsuario.nombre)
                        fechaNacimiento.time = Date(historialUsuario.fechaNacimiento.toLong())
                        edad = TextFieldValue(historialUsuario.edad)
                        estadoSalud = historialUsuario.estadoSalud
                        especificaciones = TextFieldValue(historialUsuario.especificaciones)
                        idHistorialActual = historialUsuario.id
                        estaEditando = true
                    }
                }

                override fun onFailure(e: Exception) {
                    // Manejar error
                }
            })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial Médico", fontSize = 42.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan),
                actions = {
                    Button(
                        onClick = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("pantalla_login")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Cerrar sesión")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(colorFondo)
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            DatePicker(
                selectedDate = fechaNacimiento,
                onDateChange = { fechaNacimiento = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            TextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Optimo", "Mejorable", "Malo").forEach { estado ->
                    Button(
                        onClick = { estadoSalud = estado },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (estadoSalud == estado) Color.Green else Color.Gray
                        )
                    ) {
                        Text(estado)
                    }
                }
            }
            TextField(
                value = especificaciones,
                onValueChange = { especificaciones = it },
                label = { Text("Especificaciones del Paciente") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            TextField(
                value = contrasenaActual,
                onValueChange = { contrasenaActual = it },
                label = { Text("Contraseña Actual") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    val usuario = FirebaseAuth.getInstance().currentUser
                    val credencial = EmailAuthProvider.getCredential(usuario?.email!!, contrasenaActual.text)
                    usuario.reauthenticate(credencial).addOnCompleteListener { tarea ->
                        if (tarea.isSuccessful) {
                            val nuevoHistorial = HistorialMedico(
                                idHistorialActual,
                                nombre.text,
                                fechaNacimiento.timeInMillis.toString(),
                                edad.text,
                                estadoSalud,
                                especificaciones.text,
                                idUsuario
                            )
                            if (estaEditando) {
                                sqliteHelper.actualizarHistorial(nuevoHistorial)
                                firebaseHelper.updateHistorial(nuevoHistorial, idUsuario)
                            } else {
                                sqliteHelper.insertarHistorial(nuevoHistorial)
                                firebaseHelper.saveHistorial(nuevoHistorial, idUsuario)
                            }
                            // Actualizar la UI después de guardar
                            val historialesSQLite = sqliteHelper.obtenerHistoriales().filter { it.userId == idUsuario }
                            historiales = historialesSQLite
                            if (historiales.isNotEmpty()) {
                                val historialUsuario = historiales.first()
                                nombre = TextFieldValue(historialUsuario.nombre)
                                fechaNacimiento.time = Date(historialUsuario.fechaNacimiento.toLong())
                                edad = TextFieldValue(historialUsuario.edad)
                                estadoSalud = historialUsuario.estadoSalud
                                especificaciones = TextFieldValue(historialUsuario.especificaciones)
                                idHistorialActual = historialUsuario.id
                                estaEditando = true
                            } else {
                                nombre = TextFieldValue("")
                                fechaNacimiento = Calendar.getInstance()
                                edad = TextFieldValue("")
                                estadoSalud = "Optimo"
                                especificaciones = TextFieldValue("")
                                idHistorialActual = 0
                                estaEditando = false
                            }
                        } else {
                            // Manejar error de reautenticación
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (estaEditando) "Editar Historial" else "Guardar Historial", fontSize = 30.sp)
            }
            historiales.forEach { historial ->
                Text(
                    text = "${historial.nombre} - ${Date(historial.fechaNacimiento.toLong())} - ${historial.edad} - ${historial.estadoSalud} - ${historial.especificaciones}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Button(
                onClick = { navController.navigate("panel_control") },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Ir al Panel de Control")
            }
        }
    }
}

@Composable
fun DatePicker(
    selectedDate: Calendar,
    onDateChange: (Calendar) -> Unit,
    modifier: Modifier = Modifier
) {
    val year = selectedDate.get(Calendar.YEAR).toString()
    val month = (selectedDate.get(Calendar.MONTH) + 1).toString()
    val day = selectedDate.get(Calendar.DAY_OF_MONTH).toString()

    var dayText by remember { mutableStateOf(TextFieldValue(day)) }
    var monthText by remember { mutableStateOf(TextFieldValue(month)) }
    var yearText by remember { mutableStateOf(TextFieldValue(year)) }

    Row(modifier = modifier) {
        // Selector de día
        TextField(
            value = dayText,
            onValueChange = { nuevoDia ->
                dayText = nuevoDia
                val diaInt = nuevoDia.text.toIntOrNull()
                if (diaInt != null && diaInt in 1..31) {
                    selectedDate.set(Calendar.DAY_OF_MONTH, diaInt)
                    onDateChange(selectedDate)
                }
            },
            label = { Text("Día") },
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        // Selector de mes
        TextField(
            value = monthText,
            onValueChange = { nuevoMes ->
                monthText = nuevoMes
                val mesInt = nuevoMes.text.toIntOrNull()
                if (mesInt != null && mesInt in 1..12) {
                    selectedDate.set(Calendar.MONTH, mesInt - 1)
                    onDateChange(selectedDate)
                }
            },
            label = { Text("Mes") },
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        // Selector de año
        TextField(
            value = yearText,
            onValueChange = { nuevoAno ->
                yearText = nuevoAno
                val anoInt = nuevoAno.text.toIntOrNull()
                if (anoInt != null && anoInt > 0) {
                    selectedDate.set(Calendar.YEAR, anoInt)
                    onDateChange(selectedDate)
                }
            },
            label = { Text("Año") },
            modifier = Modifier.weight(1f)
        )
    }
}