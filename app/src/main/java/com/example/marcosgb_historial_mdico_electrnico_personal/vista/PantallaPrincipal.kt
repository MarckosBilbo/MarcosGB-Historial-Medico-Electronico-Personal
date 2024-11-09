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
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    backgroundColor: Color,
    sqliteHelper: SQLite,
    firebaseHelper: Firebase,
    userId: String
) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var fechaNacimiento by remember { mutableStateOf(Calendar.getInstance()) }
    var edad by remember { mutableStateOf(TextFieldValue("")) }
    var estadoSalud by remember { mutableStateOf("Optimo") }
    var especificaciones by remember { mutableStateOf(TextFieldValue("")) }
    var historiales by remember { mutableStateOf(listOf<HistorialMedico>()) }
    var isEditing by remember { mutableStateOf(false) }
    var currentHistorialId by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        // Obtener historiales desde SQLite
        val sqliteHistoriales = sqliteHelper.obtenerHistoriales().filter { it.userId == userId }
        if (sqliteHistoriales.isNotEmpty()) {
            historiales = sqliteHistoriales
            val userHistorial = historiales.first()
            nombre = TextFieldValue(userHistorial.nombre)
            fechaNacimiento.time = Date(userHistorial.fechaNacimiento.toLong())
            edad = TextFieldValue(userHistorial.edad)
            estadoSalud = userHistorial.estadoSalud
            especificaciones = TextFieldValue(userHistorial.especificaciones)
            currentHistorialId = userHistorial.id
            isEditing = true
        } else {
            // Si no hay datos en SQLite, obtener desde Firebase
            firebaseHelper.obtenerHistoriales(object : Firebase.DataCallback {
                override fun onSuccess(data: List<HistorialMedico>) {
                    historiales = data.filter { it.userId == userId }
                    if (historiales.isNotEmpty()) {
                        val userHistorial = historiales.first()
                        nombre = TextFieldValue(userHistorial.nombre)
                        fechaNacimiento.time = Date(userHistorial.fechaNacimiento.toLong())
                        edad = TextFieldValue(userHistorial.edad)
                        estadoSalud = userHistorial.estadoSalud
                        especificaciones = TextFieldValue(userHistorial.especificaciones)
                        currentHistorialId = userHistorial.id
                        isEditing = true
                    }
                }

                override fun onFailure(e: Exception) {
                    // Handle error
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
                        Text("Logout")
                    }
                }
            )
        }
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
            Button(
                onClick = {
                    val nuevoHistorial = HistorialMedico(
                        currentHistorialId,
                        nombre.text,
                        fechaNacimiento.timeInMillis.toString(),
                        edad.text,
                        estadoSalud,
                        especificaciones.text,
                        userId
                    )
                    if (isEditing) {
                        sqliteHelper.actualizarHistorial(nuevoHistorial)
                        firebaseHelper.updateHistorial(nuevoHistorial, userId)
                    } else {
                        sqliteHelper.insertarHistorial(nuevoHistorial)
                        firebaseHelper.saveHistorial(nuevoHistorial, userId)
                    }
                    // Update the UI after saving
                    val sqliteHistoriales = sqliteHelper.obtenerHistoriales().filter { it.userId == userId }
                    historiales = sqliteHistoriales
                    if (historiales.isNotEmpty()) {
                        val userHistorial = historiales.first()
                        nombre = TextFieldValue(userHistorial.nombre)
                        fechaNacimiento.time = Date(userHistorial.fechaNacimiento.toLong())
                        edad = TextFieldValue(userHistorial.edad)
                        estadoSalud = userHistorial.estadoSalud
                        especificaciones = TextFieldValue(userHistorial.especificaciones)
                        currentHistorialId = userHistorial.id
                        isEditing = true
                    } else {
                        nombre = TextFieldValue("")
                        fechaNacimiento = Calendar.getInstance()
                        edad = TextFieldValue("")
                        estadoSalud = "Optimo"
                        especificaciones = TextFieldValue("")
                        currentHistorialId = 0
                        isEditing = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditing) "Editar Historial" else "Guardar Historial", fontSize = 30.sp)
            }
            historiales.forEach { historial ->
                Text(
                    text = "${historial.nombre} - ${Date(historial.fechaNacimiento.toLong())} - ${historial.edad} - ${historial.estadoSalud} - ${historial.especificaciones}",
                    modifier = Modifier.padding(top = 8.dp)
                )
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
        // Day picker
        TextField(
            value = dayText,
            onValueChange = { newDay ->
                dayText = newDay
                val dayInt = newDay.text.toIntOrNull()
                if (dayInt != null && dayInt in 1..31) {
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayInt)
                    onDateChange(selectedDate)
                }
            },
            label = { Text("Día") },
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        // Month picker
        TextField(
            value = monthText,
            onValueChange = { newMonth ->
                monthText = newMonth
                val monthInt = newMonth.text.toIntOrNull()
                if (monthInt != null && monthInt in 1..12) {
                    selectedDate.set(Calendar.MONTH, monthInt - 1)
                    onDateChange(selectedDate)
                }
            },
            label = { Text("Mes") },
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        // Year picker
        TextField(
            value = yearText,
            onValueChange = { newYear ->
                yearText = newYear
                val yearInt = newYear.text.toIntOrNull()
                if (yearInt != null && yearInt > 0) {
                    selectedDate.set(Calendar.YEAR, yearInt)
                    onDateChange(selectedDate)
                }
            },
            label = { Text("Año") },
            modifier = Modifier.weight(1f)
        )
    }
}