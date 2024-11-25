package com.example.hangoutz.ui.screens.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.theme.Dimensions.Large1
import com.example.hangoutz.ui.theme.Dimensions.Large2
import com.example.hangoutz.ui.theme.Dimensions.Medium1
import com.example.hangoutz.ui.theme.Dimensions.Medium2
import com.example.hangoutz.ui.theme.Dimensions.Small1
import com.example.hangoutz.ui.theme.Dimensions.Small4
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants.LOGOUT

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = Medium2)
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
                    .size(Large1)
                    .clip(CircleShape)
                    .border(2.5f.dp, Ivory, CircleShape)
            ) {}
            GlideImage(
                model = "${BuildConfig.BASE_URL_AVATAR}${data.value.avatar}",
                contentDescription = "Profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(Large2)
                    .height(Large2)
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .testTag("userPhoto")
            )
            Image(
                painter = painterResource(R.drawable.profilelines),
                contentDescription = "lines",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("backgroundLines"),
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
                    .height(Medium1),
                horizontalArrangement = Arrangement.spacedBy(Small1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NameInput(
                    data.value.name,
                    data.value.isReadOnly,
                    { viewmodel.onNameChanged(it) },
                    { viewmodel.onPencilClick() },
                )
            }
            Text(
                text = data.value.email,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(end = Small4)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(bottom = Small4)
        ) {
            ActionButton(R.drawable.iconlogout, LOGOUT, onClick = {
                viewmodel.logoutUser(context, {
                    navController.navigate(NavigationItem.Login.route)
                })
            })
        }
    }
}