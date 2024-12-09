package com.example.hangoutz

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import com.example.hangoutz.ui.navigation.AppNavHost
import com.example.hangoutz.ui.theme.HangoutzTheme
import com.example.hangoutz.utils.AppKeyboardFocusManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppKeyboardFocusManager(LocalFocusManager, context = LocalContext)
            HangoutzTheme {
                //  SplashScreen()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Initialize NavController
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}