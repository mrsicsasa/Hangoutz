package com.example.hangoutz.ui.screens.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.theme.Error
import com.example.hangoutz.ui.theme.Ivory

@Composable
fun SettingsScreen(navController: NavController) {

    Column(modifier = Modifier.padding(top = 70.dp)) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),

                    contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(215.dp)
                    .clip(CircleShape)
                    .border(2.5f.dp, Ivory, CircleShape)
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

            Image(
                painter = painterResource(R.drawable.profilelines),
                contentDescription = "lines",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center),
                        colorFilter =  ColorFilter.tint(Ivory)
            )

        }
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center



        ) {

          Row(
            //  modifier = Modifier.align(Alignment.CenterHorizontally),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically

          ) {
              Text(
                  text = "Sava Savic",
                  style = MaterialTheme.typography.bodyLarge,
                  color = Ivory,
                  fontSize = 32.sp,
                  modifier = Modifier.padding(end = 10.dp)
              )
              Image(
                  painter = painterResource(R.drawable.pencil),
                  contentDescription = "lines",
                  colorFilter =  ColorFilter.tint(Ivory),
                  modifier = Modifier.size(26.dp)
              )


          }
            Text(
                text = "savasavic@gmail.com",
                style = MaterialTheme.typography.bodyLarge,
                color = Ivory,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )

        }


        Column(modifier = Modifier
            .background(Color.Green)
            .weight(1f)
            .fillMaxWidth()) {

           ActionButton(R.drawable.logout, )
        }


    }
}