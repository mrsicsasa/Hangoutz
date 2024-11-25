package com.example.hangoutz.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.ErrorMessage
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.components.Logo
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.screens.registerscreen.Fields
import com.example.hangoutz.ui.screens.registerscreen.RegisterViewModel
import com.example.hangoutz.utils.Constants

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    // ViewModel data
    val data = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.main_background),
                contentScale = ContentScale.FillHeight
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
                    modifier = Modifier.align(Alignment.Center),
                    animationDelay = Constants.LOGO_ANIMATION_DELAY
                )
            }
        }
        // Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(start = 30.dp, end = 30.dp, bottom = 50.dp)
        ) {
            InputField(
                label = "Name*",
                value = data.value.name,
                onValueChange = { newValue ->
                    viewModel.onTextChanged(Fields.NAME, newValue)
                },
                isError = (data.value.name.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                        || data.value.nameError.isNotEmpty()
            )
            if (data.value.nameError.isNotEmpty() && data.value.incompleteFormError.isEmpty()) {
                ErrorMessage(data.value.nameError)
            }
            InputField(
                label = "Email*",
                value = data.value.email,
                onValueChange = { newValue ->
                    viewModel.onTextChanged(Fields.EMAIL, newValue)
                },
                isError = (data.value.email.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                        || data.value.emailError.isNotEmpty()
            )
            if (data.value.emailError.isNotEmpty() && data.value.incompleteFormError.isEmpty()) {
                ErrorMessage(data.value.emailError)
            }
            InputField(
                label = "Password*",
                value = data.value.password,
                onValueChange = { newValue ->
                    viewModel.onTextChanged(Fields.PASSWORD, newValue)
                },
                isError = (data.value.password.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                        || data.value.passwordError.isNotEmpty(),
                isPassword = true
            )
            if (data.value.passwordError.isNotEmpty() && data.value.incompleteFormError.isEmpty()) {
                ErrorMessage(data.value.passwordError)
            }
            InputField(
                label = "Confirm password*",
                value = data.value.confirmPassword,
                onValueChange = { newValue ->
                    viewModel.onTextChanged(Fields.CONFIRMPASSWORD, newValue)
                },
                isError = (data.value.confirmPassword.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                        || data.value.confirmPasswordError.isNotEmpty(),
                isPassword = true
            )
            if (data.value.confirmPasswordError.isNotEmpty() && data.value.incompleteFormError.isEmpty()
            ) {
                ErrorMessage(data.value.confirmPasswordError)
            }
            // All fields must be filled
            if (data.value.incompleteFormError.isNotEmpty()) {
                ErrorMessage(data.value.incompleteFormError)
            }
        }
        // Create account button
        Box(
            modifier = Modifier
                .padding(bottom = 145.dp)
                .fillMaxWidth()
        ) {
            ActionButton(
                buttonText = "Create Account",
            ) {
                viewModel.onCreateAccountClick(context) {
                    navController.navigate(NavigationItem.Login.route)
                }
            }
        }
    }
}