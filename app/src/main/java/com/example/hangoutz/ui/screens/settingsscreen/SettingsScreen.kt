package com.example.hangoutz.ui.screens.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.Ivory

@Composable
fun SettingsScreen(navController: NavController) {

    Column(modifier = Modifier.padding(top = 90.dp)) {

        Box(
            modifier = Modifier
                .background(Color.Blue)
                .weight(1f)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .size(215.dp) // Image size (200.dp) + padding (2.dp on each side)
                    .clip(CircleShape)
                    .border(3.dp, Ivory, CircleShape)
            ) {
                Image(

                    painter = painterResource(R.drawable.default_avatar),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
            }

        }
        Column(modifier = Modifier
            .background(Color.Green)
            .weight(1f)
            .fillMaxWidth()) {

            Text("lala")
        }


    }
}