package com.example.hangoutz.ui.screens.settingsscreen


import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.nameField
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants.LOGOUT

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()
    val context = LocalContext.current

    viewmodel.getUser(context)
    Column(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxSize()
    ) {
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
            }
            GlideImage(
                model = "${BuildConfig.BASE_URL_AVATAR}${data.value.avatar}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(45.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                NameInput({ viewmodel.onNameChanged(it) }, data.value.name)
            }
            Text(
                text = data.value.email,
                style = MaterialTheme.typography.bodyLarge,
                color = Ivory,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
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
fun NameInput(onNameChanged: (String) -> Unit, name: String) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 50.dp, start = 50.dp),
    ) {
        val (x, y) = createRefs()
        Box(modifier = Modifier.constrainAs(x) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }) {
            nameField(
                name,
                { onNameChanged(it) },
                modifier = Modifier.align(Alignment.Center)

            )
        }
        Box(
            modifier = Modifier
                .constrainAs(y) {
                    start.linkTo(x.end)
                }
                .padding(start = 5.dp)
                .fillMaxHeight()
        ) {
            Image(
                painterResource(R.drawable.pencil), "",
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(Ivory)
            )
        }
    }
}