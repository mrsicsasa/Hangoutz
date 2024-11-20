package com.example.hangoutz.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hangoutz.ui.navigation.BottomNavItem
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.bottomNavigationColor

@Composable
fun TabView(
    tabBarItems: List<BottomNavItem>,
    navController: NavController,
) {

    NavigationBar(
        modifier = Modifier.height(67.dp),
        containerColor = bottomNavigationColor,
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        tabBarItems.forEach { tabBarItem ->
            NavigationBarItem(
                modifier = Modifier.padding(top = 18.dp),
                selected = tabBarItem.route.name == currentRoute,
                onClick = {
                    if (tabBarItem.route.name != currentRoute) {
                        navController.navigate(route = tabBarItem.route.name)
                    }
                },
                icon = {
                    Image(
                        painterResource(isBottomItemSelectedIcon(tabBarItem, currentRoute)),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        contentScale = ContentScale.FillBounds
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Ivory,
                    selectedTextColor = Ivory,
                    selectedIndicatorColor = bottomNavigationColor,
                    unselectedIconColor = bottomNavigationColor,
                    unselectedTextColor = Ivory,
                    disabledIconColor = Color.Blue,
                    disabledTextColor = Color.Blue
                )
            )
        }
    }
}

private fun isBottomItemSelectedIcon(item: BottomNavItem, currentRoute: String?): Int {
    return if (item.route.name == currentRoute) item.selectedIcon else item.icon
}