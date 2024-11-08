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
import com.example.marcosgb_historial_mdico_electrnico_personal.vista.PantallaPrincipal


class MainActivity : ComponentActivity() {
    private lateinit var sqliteHelper: SQLite
    private lateinit var firebaseHelper: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sqliteHelper = SQLite(this)
        firebaseHelper = Firebase()

        setContent {
            MyApp(sqliteHelper = sqliteHelper, firebaseHelper = firebaseHelper)
        }
    }
}

@Composable
fun MyApp(sqliteHelper: SQLite, firebaseHelper: Firebase) {
    val navController = rememberNavController()
    var backgroundColor by remember { mutableStateOf(Color.Gray) }

    NavHost(navController = navController, startDestination = "pantalla_principal") {
        composable("pantalla_principal") {
            PantallaPrincipal(
                navController = navController,
                backgroundColor = backgroundColor,
                sqliteHelper = sqliteHelper,
                firebaseHelper = firebaseHelper
            )
        }
    }
}