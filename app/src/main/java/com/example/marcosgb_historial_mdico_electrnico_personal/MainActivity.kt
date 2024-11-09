package com.example.marcosgb_historial_mdico_electrnico_personal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.Firebase
import com.example.marcosgb_historial_mdico_electrnico_personal.modelo.SQLite
import com.example.marcosgb_historial_mdico_electrnico_personal.vista.PantallaLogin
import com.example.marcosgb_historial_mdico_electrnico_personal.vista.PantallaPrincipal
import com.example.marcosgb_historial_mdico_electrnico_personal.vista.PantallaRegistro
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var sqliteHelper: SQLite
    private lateinit var firebaseHelper: Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sqliteHelper = SQLite(this)
        firebaseHelper = Firebase()
        firebaseAuth = FirebaseAuth.getInstance()

        setContent {
            MyApp(sqliteHelper = sqliteHelper, firebaseHelper = firebaseHelper, firebaseAuth = firebaseAuth)
        }
    }
}

@Composable
fun MyApp(sqliteHelper: SQLite, firebaseHelper: Firebase, firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    var backgroundColor by remember { mutableStateOf(Color.Gray) }

    NavHost(navController = navController, startDestination = "pantalla_login") {
        composable("pantalla_login") {
            PantallaLogin(navController = navController, firebaseAuth = firebaseAuth)
        }
        composable("pantalla_registro") {
            PantallaRegistro(navController = navController, firebaseAuth = firebaseAuth)
        }
        composable("pantalla_principal") {
            val user = firebaseAuth.currentUser
            if (user != null) {
                PantallaPrincipal(
                    navController = navController,
                    backgroundColor = backgroundColor,
                    sqliteHelper = sqliteHelper,
                    firebaseHelper = firebaseHelper,
                    userId = user.uid
                )
            } else {
                navController.navigate("pantalla_login")
            }
        }
    }
}