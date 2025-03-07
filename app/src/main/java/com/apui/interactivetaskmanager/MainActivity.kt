package com.apui.interactivetaskmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apui.interactivetaskmanager.ui.navigation.AppNavHost
import com.apui.interactivetaskmanager.ui.navigation.NavRoutes
import com.apui.interactivetaskmanager.ui.theme.InteractiveTaskManagerTheme
import com.apui.interactivetaskmanager.utils.FABButton
import com.apui.interactivetaskmanager.utils.TopBar

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InteractiveTaskManagerTheme() {

                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                    },
                    floatingActionButton = {
                        if (currentRoute == NavRoutes.Home.route)
                            FABButton { navController.navigate(NavRoutes.TaskCreation.route) }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
