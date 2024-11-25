package com.example.hangoutz.ui.screens.login


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.ErrorMessage
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.components.Logo
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.theme.Dimensions.actionButtonMedium1
import com.example.hangoutz.ui.theme.Dimensions.actionButtonMedium2
import com.example.hangoutz.ui.theme.Dimensions.actionButtonSmall1
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Constants.EMAIL
import com.example.hangoutz.utils.Constants.LOGIN
import com.example.hangoutz.utils.Constants.PASSWORD

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(navController: NavController, viewmodel: LoginViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()

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
                contentAlignment = Alignment.Center
            ) {
                Logo(
                    painterResource(id = R.drawable.logo),
                    initialValue = 0f,
                    targetValue = 1f,
                    modifier = Modifier.align(Alignment.Center),
                    animationDelay = Constants.LOGO_ANIMATION_DELAY
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(start = actionButtonMedium2, end = actionButtonMedium2)
        ) {
            InputField(
                EMAIL,
                data.value.email,
                { viewmodel.onTextChanged(it) },
                isError = data.value.isEmailError
            )
            InputField(
                PASSWORD,
                data.value.password,
                { viewmodel.onPassChanged(it) },
                isPassword = true,
                isError = data.value.isPasswordError
            )
            ErrorMessage(data.value.errorMessage)
            ActionButton(
                R.drawable.enter,
                LOGIN,
                onClick = {
                    viewmodel.userAuth()
                    {
                        navController.navigate(NavigationItem.MainScreen.route) {
                            popUpTo(0)
                            launchSingleTop
                        }
                    }
                }, Modifier.testTag(Constants.SETTINGS_LOGOUT_BUTTON)
            )
        }
        Column(
            modifier = Modifier
                .padding(bottom = actionButtonMedium1)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.or_text),
                color = Ivory,
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.create_account_text),
                color = Ivory,
                modifier = Modifier
                    .padding(top = actionButtonSmall1)
                    .clickable {
                        navController.navigate(NavigationItem.Register.route)
                    },
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}