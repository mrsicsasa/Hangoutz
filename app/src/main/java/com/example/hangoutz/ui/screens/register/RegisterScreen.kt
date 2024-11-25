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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.hangoutz.utils.Dimensions

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    Box {
        // ViewModel data
        val data = viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.main_background),
                    contentScale = ContentScale.FillHeight
                )
                .testTag(Constants.REGISTER_BACKGROUND_COLUMN)
        ) {
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .testTag(Constants.REGISTER_LOGO_COLUMN)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .testTag(Constants.REGISTER_LOGO_BOX),
                    contentAlignment = Alignment.Center
                ) {
                    Logo(
                        painterResource(id = R.drawable.logo),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(Constants.REGISTER_LOGO),
                        animationDelay = Constants.LOGO_ANIMATION_DELAY
                    )
                }
            }
            // Form
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        start = Dimensions.REGISTER_FORM_SIDE_PADDING,
                        end = Dimensions.REGISTER_FORM_SIDE_PADDING,
                        bottom = Dimensions.REGISTER_FORM_BOTTOM_PADDING
                    )
                    .fillMaxWidth()
                    .testTag(Constants.REGISTER_FORM_COLUMN)
            ) {
                InputField(
                    label = stringResource(R.string.input_name),
                    value = data.value.name,
                    onValueChange = { newValue ->
                        viewModel.onTextChanged(Fields.NAME, newValue)
                    },
                    isError = (data.value.name.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                            || data.value.nameError.isNotEmpty(),
                    modifier = Modifier
                        .testTag(Constants.REGISTER_NAME_INPUT)
                )
                if (data.value.nameError.isNotEmpty() && data.value.incompleteFormError.isEmpty()) {
                    ErrorMessage(
                        data.value.nameError,
                        Modifier.testTag(Constants.REGISTER_NAME_ERROR)
                    )
                }
                InputField(
                    label = stringResource(R.string.input_email),
                    value = data.value.email,
                    onValueChange = { newValue ->
                        viewModel.onTextChanged(Fields.EMAIL, newValue)
                    },
                    isError = (data.value.email.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                            || data.value.emailError.isNotEmpty(),
                    modifier = Modifier
                        .testTag(Constants.REGISTER_EMAIL_INPUT)
                )
                if (data.value.emailError.isNotEmpty() && data.value.incompleteFormError.isEmpty()) {
                    ErrorMessage(
                        data.value.emailError,
                        Modifier.testTag(Constants.REGISTER_EMAIL_ERROR)
                    )
                }
                InputField(
                    label = stringResource(R.string.input_password),
                    value = data.value.password,
                    onValueChange = { newValue ->
                        viewModel.onTextChanged(Fields.PASSWORD, newValue)
                    },
                    isError = (data.value.password.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                            || data.value.passwordError.isNotEmpty(),
                    isPassword = true,
                    modifier = Modifier
                        .testTag(Constants.REGISTER_PASSWORD_INPUT)
                )
                if (data.value.passwordError.isNotEmpty() && data.value.incompleteFormError.isEmpty()) {
                    ErrorMessage(
                        data.value.passwordError,
                        Modifier.testTag(Constants.REGISTER_PASSWORD_ERROR)
                    )
                }
                InputField(
                    label = stringResource(R.string.input_confirm_password),
                    value = data.value.confirmPassword,
                    onValueChange = { newValue ->
                        viewModel.onTextChanged(Fields.CONFIRMPASSWORD, newValue)
                    },
                    isError = (data.value.confirmPassword.isEmpty() && data.value.incompleteFormError.isNotEmpty())
                            || data.value.confirmPasswordError.isNotEmpty(),
                    isPassword = true,
                    modifier = Modifier
                        .testTag(Constants.REGISTER_CONFIRM_PASSWORD_INPUT)
                )
                if (data.value.confirmPasswordError.isNotEmpty() && data.value.incompleteFormError.isEmpty()
                ) {
                    ErrorMessage(
                        data.value.confirmPasswordError,
                        Modifier.testTag(Constants.REGISTER_CONFIRM_PASSWORD_ERROR)
                    )
                }
                // All fields must be filled
                if (data.value.incompleteFormError.isNotEmpty()) {
                    ErrorMessage(
                        data.value.incompleteFormError,
                        Modifier.testTag(Constants.REGISTER_INCOMPLETE_FORM_ERROR)
                    )
                }
            }
            // Create account button
            Box(
                modifier = Modifier
                    .padding(bottom = Dimensions.REGISTER_BOTTOM_PADDING)
                    .fillMaxWidth()
                    .testTag(Constants.REGISTER_CREATE_ACCOUNT_BOX)
            ) {
                ActionButton(
                    buttonText = stringResource(R.string.create_account_text),
                    modifier = Modifier.testTag(Constants.REGISTER_CREATE_ACCOUNT_BUTTON)
                ) {
                    viewModel.onCreateAccountClick {
                        navController.navigate(NavigationItem.Login.route) {
                            popUpTo(NavigationItem.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}