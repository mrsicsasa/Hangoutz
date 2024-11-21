package com.example.hangoutz.ui.screens.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants.LOGOUT

@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()
    val context = LocalContext.current


    Column(modifier = Modifier.padding(top = 70.dp).fillMaxSize()) {

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
                colorFilter = ColorFilter.tint(Ivory)
            )

        }
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
             .fillMaxWidth(),
            verticalArrangement = Arrangement.Center


        ) {

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .fillMaxWidth().height(45.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {


                    Ime()
//                        Text(
//                            text = "Sava Savic",
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = Ivory,
//                            fontSize = 32.sp,
//                            modifier = Modifier.padding(end = 10.dp)
//                                .align(Alignment.Center)
//                                .background(Color.Blue)
//
//
//                        )
//                        Image(
//                            painter = painterResource(R.drawable.pencil),
//                            contentDescription = "lines",
//                            colorFilter = ColorFilter.tint(Ivory),
//                            modifier = Modifier.size(26.dp)
//                                .align(Alignment.CenterEnd)
//                                .padding(end = 20.dp)
//
//                        )

//                    Icon(
//                        Icons.Default.Edit, "",
//                        modifier = Modifier.size(25.dp).align(Alignment.Center)
//
//                    )



            }
            Text(
                text = "savasavic@gmail.com",
                style = MaterialTheme.typography.bodyLarge,
                color = Ivory,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally)
            )

        }


        Column(
            modifier = Modifier
                // .background(Color.Green)
                .weight(1f)
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(bottom = 10.dp)

        ) {

            ActionButton(
                R.drawable.iconlogout, LOGOUT,
                onClick = {
                    viewmodel.logoutUser(
                        context,
                        {
                            navController.navigate(NavigationItem.Login.route)
                        }
                    )
                }
            )

        }


    }

}

@Composable
fun Ime(){
    ConstraintLayout(modifier = Modifier.fillMaxWidth(),
        ) {
        val(x, y) = createRefs()

        Box(modifier = Modifier.constrainAs(x){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }){


            Text(
                text = "Sava Savic",
                style = MaterialTheme.typography.bodyLarge,
                color = Ivory,
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 10.dp)
                    .align(Alignment.Center)



            )
        }

        Box(modifier = Modifier.constrainAs(y){
            start.linkTo(x.end)

        }.fillMaxHeight()){
            Image(
                painterResource(R.drawable.pencil), "",
                modifier = Modifier.size(25.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(Ivory)


            )

        }


    }
}