package com.apui.interactivetaskmanager.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FABButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = Color(0xFF9C4DB9)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Button")
    }
}