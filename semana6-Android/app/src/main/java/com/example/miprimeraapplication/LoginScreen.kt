package com.example.miprimeraapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * UI del login. Esta pantalla NO llama a Firebase directamente;
 * expone un callback (onLogin) para que MainActivity decida qué hacer
 * cuando el usuario pulsa "Ingresar".
 */
@Composable
fun LoginScreen(
    isLoading: Boolean = false,          // estado externo: muestra "Ingresando..." y deshabilita el botón
    error: String? = null,               // estado externo: mensaje de error (p.ej. de Firebase)
    onLogin: (email: String, password: String) -> Unit // callback hacia la Activity
) {
    // Estados locales del formulario (Compose re-dibuja cuando cambian)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Reglas simples para habilitar el botón
    val isValid = email.isNotBlank() && password.isNotBlank()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Campo: correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Campo: contraseña (enmascarado)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            // Botón principal
            Button(
                onClick = {
                    // Aquí NO validamos datos con Toast; eso lo hace MainActivity.
                    // Sólo delegamos el evento:
                    onLogin(email.trim(), password)
                },
                enabled = isValid && !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isLoading) "Ingresando..." else "Ingresar")
            }

            // Si llega un error externo, se muestra bajo el botón
            if (error != null) {
                Spacer(Modifier.height(12.dp))
                Text(text = error, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
