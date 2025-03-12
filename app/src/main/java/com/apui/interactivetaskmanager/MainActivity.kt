package com.apui.interactivetaskmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apui.interactivetaskmanager.ui.navigation.AppNavHost
import com.apui.interactivetaskmanager.ui.navigation.NavRoutes
import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import com.apui.interactivetaskmanager.ui.screens.settings.ThemeSettingsViewModel
import com.apui.interactivetaskmanager.ui.theme.InteractiveTaskManagerTheme
import com.apui.interactivetaskmanager.utils.FABButton
import com.apui.interactivetaskmanager.utils.TopBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeSettingsViewModel by viewModel<ThemeSettingsViewModel>()
            val isDarkMode = themeSettingsViewModel.isDarkMode.collectAsStateWithLifecycle().value
            InteractiveTaskManagerTheme(darkTheme = isDarkMode) {

                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route ?: NavRoutes.Home.route
                val topBarViewModel by viewModel<TopBarViewModel>()
                val showBottomSheet = remember { mutableStateOf(false) }
                val onFilterClick: () -> Unit = {
                    showBottomSheet.value = true
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(viewModel = topBarViewModel, navController, onFilterClick)
                    },
                    floatingActionButton = {
                        if (currentRoute == NavRoutes.Home.route)
                            FABButton { navController.navigate(NavRoutes.TaskCreation.route) }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        showBottomSheet = showBottomSheet
                    )
                }
            }
        }
    }
}
