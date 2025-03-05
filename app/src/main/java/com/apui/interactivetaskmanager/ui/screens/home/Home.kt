package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import com.apui.interactivetaskmanager.utils.Screens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Home(
    viewModel: TopBarViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {

    viewModel.currentTop(Screens.HOME)
    Text("Hello", modifier = modifier)

}