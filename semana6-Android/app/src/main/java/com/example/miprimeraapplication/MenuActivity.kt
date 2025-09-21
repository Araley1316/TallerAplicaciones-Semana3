ackage com.example.miprimeraapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.PermissionChecker
import com.example.miprimeraapplication.ui.theme.MiPrimeraApplicationTheme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class MenuActivity : ComponentActivity() {

    // Firebase
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val dbRef = FirebaseDatabase.getInstance().reference

    // Ubicación
    private lateinit var fused: FusedLocationProviderClient

    // Lanzador para permisos múltiples (fine + coarse)
    private val locationPermLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val granted = result.any { it.value }  // si cualquiera fue aceptado
            if (granted) {
                getAndSaveLocation()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fused = LocationServices.getFusedLocationProviderClient(this)

        // Si no hay sesión, volver al login
        if (auth.currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            MiPrimeraApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MenuScreen(
                        email = auth.currentUser?.email ?: "(sin correo)",
                        onSignOut = {
                            auth.signOut()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        },
                        onSaveLocation = { requestLocationOrSave() }
                    )
                }
            }
        }
    }

    //Comprueba permisos; si los tiene, obtiene/guarda ubicación; si no, los solicita.
    private fun requestLocationOrSave() {
        val fineOk = PermissionChecker.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED

        val coarseOk = PermissionChecker.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED

        if (fineOk || coarseOk) {
            getAndSaveLocation()
        } else {
            locationPermLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    // Lee lastLocation; si es nulo, pide 1 actualización; luego guarda en Realtime DB.
    @SuppressLint("MissingPermission") // ya comprobamos permisos antes
    private fun getAndSaveLocation() {
        val uid = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "No hay usuario autenticado", Toast.LENGTH_LONG).show()
            return
        }

        // 1) Intento rápido: última ubicación conocida
        fused.lastLocation
            .addOnSuccessListener { loc ->
                if (loc != null) {
                    saveLocation(uid, loc.latitude, loc.longitude)
                } else {
                    // 2) Fallback: pedir una única actualización
                    val request = LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY, 0L
                    ).setMaxUpdates(1).build()

                    val callback = object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            fused.removeLocationUpdates(this)
                            val l = result.lastLocation ?: return
                            saveLocation(uid, l.latitude, l.longitude)
                        }
                    }

                    fused.requestLocationUpdates(request, callback, Looper.getMainLooper())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "No fue posible obtener la ubicación", Toast.LENGTH_LONG)
                    .show()
            }
    }

    //Guarda la ubicación bajo users/{uid}/lastLocation en Realtime Database.
    private fun saveLocation(uid: String, lat: Double, lon: Double) {
        val data = mapOf(
            "lat" to lat,
            "lon" to lon,
            "timestamp" to ServerValue.TIMESTAMP
        )

        dbRef.child("users").child(uid).child("lastLocation")
            .setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Ubicación guardada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al guardar ubicación: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}

//UI Simple

@Composable
private fun MenuScreen(
    email: String,
    onSignOut: () -> Unit,
    onSaveLocation: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Sesión iniciada",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Usuario: $email",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)      // opcional, para altura consistente
            ) {
                Text("Cerrar sesión")
            }

            Button(
                onClick = onSaveLocation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)      // opcional
            ) {
                Text("Guardar ubicación")
            }
        }
    }
}
