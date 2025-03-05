package com.apui.interactivetaskmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apui.interactivetaskmanager.ui.screens.home.Home

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            Home(modifier = modifier)
        }
        composable(NavRoutes.TaskDetails.route) {

        }
        composable(NavRoutes.TaskCreation.route) {

        }
        composable(NavRoutes.Settings.route) {

        }
    }
}