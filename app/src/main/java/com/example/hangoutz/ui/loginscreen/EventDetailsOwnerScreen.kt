package com.example.hangoutz.ui.loginscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.hangoutz.ui.navigation.NavigationItem

@Composable
fun EventOwnerScreen (navController: NavController) {

    Row(){

        Button(onClick = {

            navController.navigate(NavigationItem.MyEvents.route)
        }) {
            Text(text = "Go to MyEvents")



        Button(

            onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }}}







