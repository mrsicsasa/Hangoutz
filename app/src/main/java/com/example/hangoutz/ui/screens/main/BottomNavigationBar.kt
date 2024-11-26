package com.example.hangoutz.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hangoutz.R
import com.example.hangoutz.ui.navigation.BottomNavItem
import com.example.hangoutz.ui.theme.BottomNavigationColor
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun TabView(
    tabBarItems: List<BottomNavItem>,
    navController: NavController,
) {
    NavigationBar(
        modifier = Modifier
            .height(Dimensions.BOTTOM_NAVIGATION_BAR_HEIGHT)
            .testTag(Constants.BOTTOM_NAVIGATION_BAR),
        containerColor = BottomNavigationColor,
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        tabBarItems.forEach { tabBarItem ->
            NavigationBarItem(
                modifier = Modifier.testTag(Constants.BOTTOM_NAVIGATION_BAR_ITEM),
                selected = tabBarItem.route.name == currentRoute,
                onClick = {
                    if (tabBarItem.route.name != currentRoute) {
                        navController.navigate(route = tabBarItem.route.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Image(
                        painterResource(isBottomItemSelectedIcon(tabBarItem, currentRoute)),
                        contentDescription = stringResource(R.string.bottom_navigation_item_icon),
                        modifier = Modifier.size(Dimensions.BOTTOM_NAVIGATION_ICON_SIZE),
                        contentScale = ContentScale.FillBounds
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Ivory,
                    selectedTextColor = Ivory,
                    selectedIndicatorColor = BottomNavigationColor,
                    unselectedIconColor = BottomNavigationColor,
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