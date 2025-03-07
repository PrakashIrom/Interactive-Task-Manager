package com.apui.interactivetaskmanager.utils

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun <T> TaskDropDownMenu(
    expanded: MutableState<Boolean>,
    onSelect: (T) -> Unit,
    items: List<T>
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.toString()) },
                onClick = {
                    expanded.value = false
                    onSelect(item)
                }
            )
        }
    }
}