package com.example.login001v

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login001v.ui.postscreen.PostScreen
import com.example.login001v.ui.theme.ApiRestTheme
import com.example.login001v.ui.theme.Tema
import com.example.login001v.view.AppNavigation

class MainActivity : ComponentActivity() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ApiRestTheme{
                val postViewModel: com.example.login001v.viewmodel.PostViewModel = viewModel()
                PostScreen(viewModel = postViewModel)
            }

            Tema {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavigation()
                }
            }


        }
    }
}