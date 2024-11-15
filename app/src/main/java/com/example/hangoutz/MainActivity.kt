package com.example.hangoutz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.hangoutz.ui.navigation.AppNavHost
import com.example.hangoutz.ui.theme.HangoutzTheme
import com.example.hangoutz.ui.theme.inter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangoutzTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HangoutzTheme {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        fontFamily = inter
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangoutzTheme {
        Greeting("Android")
    }
}}