package com.example.login001v

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login001v.ui.postscreen.PostScreen
import com.example.login001v.ui.theme.ApiRestTheme
import com.example.login001v.ui.theme.Tema
import com.example.login001v.view.AppNavigation
import com.example.login001v.ui.qr.QrScannerScreen
import com.example.login001v.ui.qr.QrViewModel

class MainActivity : ComponentActivity() {

    private val qrViewModel: QrViewModel by viewModels()
    private var hasCameraPermission by mutableStateOf(false)
    companion object {
        var showCameraScreen by mutableStateOf(false)
    }

    // Permiso de cámara
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Se requiere permiso para usar la cámara", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkCameraPermission() {
        hasCameraPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkCameraPermission()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {

                // Tema para PostScreen
                ApiRestTheme{
                    val postViewModel: com.example.login001v.viewmodel.PostViewModel = viewModel()
                    PostScreen(viewModel = postViewModel)
                }

                // Tema principal y Navegación
                Tema {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        AppNavigation()
                    }
                }

                // PANTALLA DE CÁMARA

                if (showCameraScreen) {

                    BackHandler {
                        showCameraScreen = false
                    }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        QrScannerScreen(
                            viewModel = qrViewModel,
                            hasCameraPermission = hasCameraPermission,
                            onRequestPermission = {
                                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }
}