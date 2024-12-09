package com.example.hangoutz.ui.screens.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.Logo
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen(navController: NavController) {
    val viewmodel: SplashScreenViewModel = hiltViewModel()
    val alpha = remember { Animatable(Dimensions.SPLASH_SCREEN_STARTING_ALPHA) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.main_background),
                contentScale = ContentScale.FillBounds,
                alpha = alpha.value
            )
            .semantics {
                contentDescription = Constants.SPLASH_SCREEN_BACKGROUND
            },
        contentAlignment = Alignment.Center
    ) {
        Logo(
            painterResource(id = R.drawable.logo),
            initialValue = Dimensions.SPLASH_SCREEN_LOGO_INITIAL_ALPHA,
            targetValue = Dimensions.SPLASH_SCREEN_LOGO_TARGETED_ALPHA,
            modifier = Modifier
                .align(Alignment.Center)
                .semantics {
                    contentDescription = Constants.SPLASH_SCREEN_LOGO
                },
            animationDelay = Constants.LOGO_ANIMATION_DELAY_SPLASH
        )
    }
    LaunchedEffect(key1 = true) {
        alpha.animateTo(Dimensions.SPLASH_SCREEN_TARGETED_ALPHA, animationSpec = tween(Constants.BACKGROUND_ANIMATION_DURATION))
        viewmodel.deleteEventsFromPast()
        if (viewmodel.isUserLoggedIn(context)) {
            navController.navigate(route = NavigationItem.MainScreen.route) {
                popUpTo(NavigationItem.Splash.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(route = NavigationItem.Login.route) {
                popUpTo(NavigationItem.Splash.route) {
                    inclusive = true
                }
            }
        }
    }
}
