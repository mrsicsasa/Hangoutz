package com.example.hangoutz.ui.screens.loginscreen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.ErrorMessage
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.theme.Ivory

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
            InputField("Email", data.value.email, { viewmodel.onTextChanged(it) })
            InputField(
                "Password",
                data.value.password,
                { viewmodel.onPassChanged(it) },
                isPassword = true
            )
            ErrorMessage(data.value.errorMessage)
            ActionButton(
                R.drawable.enter,
                "Login",
                onClick = { viewmodel.userAuth({ navController.navigate(NavigationItem.MainScreen.route) }) })
        }

        Column(
            modifier = Modifier
                .padding(bottom = 40.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "OR",
                color = Ivory,
                modifier = Modifier
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Create Account",
                color = Ivory,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clickable {
                        navController.navigate(NavigationItem.Register.route)
                    },
                textAlign = TextAlign.Center
            )
        }
    }
}