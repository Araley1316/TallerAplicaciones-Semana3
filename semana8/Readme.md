# 📱 Proyecto Android - Semana 8  
Este documento contiene el código fuente principal del proyecto desarrollado en Android Studio.  
Incluye la conexión con Firebase, la lectura de temperatura y la implementación de alarma con sonido y vibración.

## 🧠 MainActivity.kt

```kotlin
// --- IMPORTS ---
package com.semana8.tempmonitorfirebase

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            MaterialTheme {
                TemperatureScreen()
            }
        }
    }
}

private const val PREFS = "temp_prefs"
private const val KEY_MIN_C = "min_c"
private const val KEY_MAX_C = "max_c"
private const val KEY_ALARM_ENABLED = "alarm_enabled"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureScreen() {
    val context = LocalContext.current

    // --- Estado de Firebase ---
    var tempF by remember { mutableStateOf<Double?>(null) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    // --- Preferencias locales ---
    val prefs = remember { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }
    var minCText by remember { mutableStateOf(prefs.getFloat(KEY_MIN_C, 0f).toString()) }
    var maxCText by remember { mutableStateOf(prefs.getFloat(KEY_MAX_C, 50f).toString()) }
    var alarmEnabled by remember { mutableStateOf(prefs.getBoolean(KEY_ALARM_ENABLED, true)) }

    // --- Suscripción a Firebase ---
    DisposableEffect(Unit) {
        val ref = FirebaseDatabase.getInstance().reference.child("temperatura")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                errorMsg = null
                tempF = snapshot.getValue(Double::class.java)
                    ?: snapshot.getValue(Number::class.java)?.toDouble()
            }
            override fun onCancelled(error: DatabaseError) {
                errorMsg = error.message
            }
        }
        ref.addValueEventListener(listener)
        onDispose { ref.removeEventListener(listener) }
    }

    // Conversión y validación de rango
    val tempC = tempF?.let { (it - 32.0) * 5.0 / 9.0 }
    val minC = minCText.toDoubleOrNull()
    val maxC = maxCText.toDoubleOrNull()
    val outOfRange = tempC != null && minC != null && maxC != null &&
            (tempC < minC || tempC > maxC)

    // --- Alarma activa SOLO si está habilitada y fuera de rango
    val alarmActive = alarmEnabled && outOfRange

    // --- Sonido + vibración con anti-repetición ---
    var lastAlarmTs by remember { mutableStateOf(0L) }
    LaunchedEffect(alarmActive) {
        if (alarmActive) {
            val now = System.currentTimeMillis()
            val minIntervalMs = 8_000L
            if (now - lastAlarmTs > minIntervalMs) {
                lastAlarmTs = now
                // Sonido breve
                ToneGenerator(AudioManager.STREAM_ALARM, 100).startTone(
                    ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500
                )
                // Vibración
                val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= 26) {
                    vib.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION") vib.vibrate(600)
                }
            }
        }
    }

    // --- UI ---
    Box(Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Lectura desde Firebase", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))

            when {
                errorMsg != null -> Text("Error: $errorMsg")
                tempF == null -> Text("Cargando temperatura…")
                else -> {
                    Text("Temperatura (°F): ${"%.2f".format(tempF)}")
                    Text("Temperatura (°C): ${"%.2f".format(tempC)}")
                }
            }

            Spacer(Modifier.height(20.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            Text("Rango permitido (°C)", style = MaterialTheme.typography.titleMedium)

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = minCText,
                    onValueChange = { minCText = it },
                    label = { Text("Mín °C") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = maxCText,
                    onValueChange = { maxCText = it },
                    label = { Text("Máx °C") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = {
                    val min = minCText.toDoubleOrNull()
                    val max = maxCText.toDoubleOrNull()
                    if (min != null && max != null) {
                        prefs.edit()
                            .putFloat(KEY_MIN_C, min.toFloat())
                            .putFloat(KEY_MAX_C, max.toFloat())
                            .apply()
                    }
                }) { Text("Guardar rango") }

                FilterChip(
                    selected = alarmEnabled,
                    onClick = {
                        alarmEnabled = !alarmEnabled
                        prefs.edit().putBoolean(KEY_ALARM_ENABLED, alarmEnabled).apply()
                    },
                    label = { Text(if (alarmEnabled) "Alarma Habilitada: ON" else "Alarma Habilitada: OFF") }
                )
            }

            Spacer(Modifier.height(14.dp))

            val statusText =
                if (tempC != null && minC != null && maxC != null) {
                    when {
                        alarmActive -> "🔔 Alarma ACTIVA (fuera de rango)"
                        outOfRange -> "⚠️ Fuera de rango"
                        else -> "✅ Dentro de rango"
                    }
                } else "Definí el rango y/o espera la lectura…"

            val statusColor = when {
                alarmActive -> MaterialTheme.colorScheme.tertiary   // morado (o el que tenga tu tema)
                outOfRange -> MaterialTheme.colorScheme.error        // rojo
                else -> MaterialTheme.colorScheme.primary            // verde/primario
            }

            Text(statusText, color = statusColor, style = MaterialTheme.typography.titleMedium)
        }
    }
}

```



