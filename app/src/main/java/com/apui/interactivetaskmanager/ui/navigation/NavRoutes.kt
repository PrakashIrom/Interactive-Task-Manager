package com.apui.interactivetaskmanager.ui.navigation

import com.apui.interactivetaskmanager.R

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes(R.string.home_screen_title.toString())
    data object Settings : NavRoutes(R.string.settings_screen_title.toString())
    data object TaskCreation : NavRoutes(R.string.task_creation_screen_title.toString())
    data object TaskDetails : NavRoutes(R.string.task_details_screen_title.toString())
}