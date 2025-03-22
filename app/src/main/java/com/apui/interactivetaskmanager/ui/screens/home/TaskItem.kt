package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.apui.interactivetaskmanager.data.model.Priority
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import com.apui.interactivetaskmanager.ui.navigation.NavRoutes
import com.apui.interactivetaskmanager.ui.screens.settings.ThemeSettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskItem(
    task: TaskEntity,
    modifier: Modifier = Modifier,
    onDeleteClick: (TaskEntity) -> Unit,
    navController: NavHostController,
    onStatusChange: (Int, TaskStatus) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.StartToEnd) {
                coroutineScope.launch {
                    delay(300)
                    onDeleteClick(task)
                }
            }
            true
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState = dismissState) },
        enableDismissFromEndToStart = false
    ) {
        TaskCard(
            task = task,
            modifier = modifier,
            onDeleteClick = onDeleteClick,
            navController = navController,
            onStatusChange = onStatusChange
        )
    }
}

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(0xFFFF1744)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete",
            tint = Color.White
        )
    }
}

@Composable
fun TaskCard(
    task: TaskEntity,
    modifier: Modifier = Modifier,
    onDeleteClick: (TaskEntity) -> Unit,
    navController: NavHostController,
    onStatusChange: (Int, TaskStatus) -> Unit,
    themeSettingsViewModel: ThemeSettingsViewModel = koinViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(task.taskStatus) }
    val isDark = themeSettingsViewModel.isDarkMode.collectAsStateWithLifecycle().value
    val cardBackgroundColor = if (isDark) Color(0xFF2C2C2C) else Color(0xFFEEEEEE)
    val textColor = if (isDark) Color.White else Color.Black
    val secondaryTextColor = if (isDark) Color.LightGray else Color.Gray

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(NavRoutes.TaskDetails.route)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                color = textColor // Dynamic text color
            )

            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = secondaryTextColor,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Priority: ${Priority.fromValue(task.priority)}",
                    fontWeight = FontWeight.Bold,
                    color = when (task.priority) {
                        Priority.HIGH.priorityValue -> if (isDark) Color(0xFFFF6B6B) else Color.Red
                        Priority.MEDIUM.priorityValue -> if (isDark) Color(0xFFFFD166) else Color.Yellow
                        else -> if (isDark) Color(0xFF06D6A0) else Color.Green
                    },
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Due: ${task.dueDate}",
                    color = if (isDark) Color.Cyan else Color.Blue,
                    modifier = Modifier.padding(end = 8.dp)
                )

                IconButton(
                    onClick = { onDeleteClick(task) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = if (isDark) Color(0xFFFF6B6B) else Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDark) Color(0xFF1E88E5) else Color(0xFF1976D2)
                    )
                ) {
                    Text(text = "Status: ${task.taskStatus.name}", color = Color.White)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    TaskStatus.entries.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.name, color = textColor) },
                            onClick = {
                                selectedStatus = status
                                expanded = false
                                onStatusChange(task.id, status)
                            }
                        )
                    }
                }
            }
        }
    }
}