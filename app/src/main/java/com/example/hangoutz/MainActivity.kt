package com.example.hangoutz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hangoutz.ui.screens.splash.SplashScreen
import com.example.hangoutz.ui.theme.HangoutzTheme
import com.example.hangoutz.ui.theme.inter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangoutzTheme {
                SplashScreen()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
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
}