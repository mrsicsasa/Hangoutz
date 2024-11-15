package com.example.hangoutz

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.hangoutz.data.remote.RetrofitInstance
import com.example.hangoutz.ui.theme.HangoutzTheme
import com.example.hangoutz.ui.theme.inter
import kotlinx.coroutines.launch

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

suspend fun getData() {
    try {
        val response = RetrofitInstance.api.getUserByName("eq.Mikica")
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                println("Response: $body")
            } else {
                println("Response body is  null")
            }
        } else {
            println("Error: ${response.code()} - ${response.message()}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        getData()
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