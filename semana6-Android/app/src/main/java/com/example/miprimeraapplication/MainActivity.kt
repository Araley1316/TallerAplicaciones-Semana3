package com.example.miprimeraapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Compose
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier


import com.example.miprimeraapplication.ui.theme.MiPrimeraApplicationTheme

// Firebase Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia de autenticación
        auth = Firebase.auth

        setContent {
            // aplicamos el tema del proyecto para estilos y colores
            MiPrimeraApplicationTheme {
                //contenedor base con el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Estados de UI para la pantalla de login
                    var isLoading by remember { mutableStateOf(false) }
                    var error by remember { mutableStateOf<String?>(null) }

                    // pantalla de login (ya creada en LoginScreen.kt)
                    LoginScreen(
                        isLoading = isLoading,
                        error = error
                    ) { email, password ->
                        // Callback (no es composable): aquí llamamos a Firebase
                        isLoading = true
                        error = null

                        auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener {
                                isLoading = false
                                Toast
                                    .makeText(this, "Login OK", Toast.LENGTH_SHORT)
                                    .show()
                                // Navegar a MenuActivity
                                startActivity(Intent(this, MenuActivity::class.java))
                            }
                            .addOnFailureListener { e ->
                                isLoading = false
                                error = e.localizedMessage ?: "Error al iniciar sesión"
                            }
                    }
                }
            }
        }
    }
}
