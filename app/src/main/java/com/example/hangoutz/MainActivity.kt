package com.example.hangoutz

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hangoutz.ui.UserViewModel
import com.example.hangoutz.ui.theme.HangoutzTheme
import com.example.hangoutz.ui.theme.inter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangoutzTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val userViewModel: UserViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        Log.d("DiTest", "test ${userViewModel.getUserByName("Mikica").isSuccessful}")
    }
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