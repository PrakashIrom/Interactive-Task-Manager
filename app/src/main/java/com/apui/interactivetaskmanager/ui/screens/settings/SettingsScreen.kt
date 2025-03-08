package com.apui.interactivetaskmanager.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import com.apui.interactivetaskmanager.utils.Screens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(topBarViewModel: TopBarViewModel = koinViewModel()) {
    LaunchedEffect(Unit) { topBarViewModel.currentTop(Screens.SETTINGS) }
    Row(
        modifier = Modifier
            .padding(top = 120.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Change Theme",
            fontWeight = FontWeight.W600,
            fontSize = 20.sp
        )
        ToggleTheme()
    }
}

@Composable
fun ToggleTheme(themeSettingsViewModel: ThemeSettingsViewModel = koinViewModel()) {
    var isDarkMode = themeSettingsViewModel.isDarkMode.collectAsStateWithLifecycle().value
    Switch(
        checked = isDarkMode,
        onCheckedChange = {
            themeSettingsViewModel.toggleTheme()
            isDarkMode = it
        },
        thumbContent = if (isDarkMode) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )
}