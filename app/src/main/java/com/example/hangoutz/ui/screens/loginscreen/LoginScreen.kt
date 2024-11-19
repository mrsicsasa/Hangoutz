package com.example.hangoutz.ui.screens.loginscreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.ErrorMessage
import com.example.hangoutz.ui.components.InputField

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.main_background), contentScale = ContentScale.FillHeight
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1.5f)
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "app logo",
                    modifier = Modifier
                        .height(50.dp)
                        .align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {

            InputField("Email")
            InputField("Password")

            ErrorMessage("Invalid email or password")

            ActionButton(R.drawable.enter, "Login") {

            }

        }
    }
}