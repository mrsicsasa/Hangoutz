package com.example.hangoutz.ui.screens.loginscreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.HangoutzTheme

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.main_background),
            contentDescription = "image background",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )

        // Logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "app logo",
                modifier = Modifier
                    .height(50.dp)
                    .align(Alignment.Center)
            )


        }
       Column (

            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center)
        ){


            Develop()
        }

        // Padding L+R= 30+30

    }


}

@Composable
fun Develop() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        label = { Text("Name") },
        onValueChange = { text = it },
        singleLine = true,
        shape = RoundedCornerShape(20.dp)
    )
}

@Preview
@Composable
private fun Show() {
    HangoutzTheme {
        Develop()
    }
}