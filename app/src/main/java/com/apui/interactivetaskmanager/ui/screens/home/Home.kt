package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apui.interactivetaskmanager.data.model.Priority
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import com.apui.interactivetaskmanager.utils.CustomBottomSheet
import com.apui.interactivetaskmanager.utils.Screens
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    topBarViewModel: TopBarViewModel = koinViewModel(),
    taskListViewModel: TaskListViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    showBottomSheet: MutableState<Boolean>
) {
    LaunchedEffect(Unit) { topBarViewModel.currentTop(Screens.HOME) }

    val tasks = taskListViewModel.tasks.collectAsStateWithLifecycle().value
    val onDeleteClick: (TaskEntity) -> Unit = { task ->
        taskListViewModel.deleteTask(task)
    }
    var selectedOption by taskListViewModel.sortOption
    val isLoading = taskListViewModel.isLoading.value

    when {
        isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        tasks.isEmpty() -> Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    "No task is added!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }

        else -> LazyColumn(modifier = modifier) {
            items(tasks) { task ->
                TaskItem(task = task, onDeleteClick = onDeleteClick) { taskId, taskStatus ->
                    taskListViewModel.updateTaskStatus(taskId, taskStatus)
                }
            }
        }
    }

    if (showBottomSheet.value) {
        CustomBottomSheet(
            onDismiss = { showBottomSheet.value = false },
            sheetState = rememberModalBottomSheetState(true)
        ) {
            BottomSheetContent(selectedOption = selectedOption,
                onOptionSelected = { option ->
                    selectedOption = option
                    when (option) {
                        "Sort by Priority" -> taskListViewModel.saveSortOption("Sort by Priority")
                        "Sort by Due Date" -> taskListViewModel.saveSortOption("Sort by Due Date")
                        else -> taskListViewModel.saveSortOption("Sort by Alphabetical Order")
                    }
                })
        }
    }

}

@Composable
fun TaskItem(
    task: TaskEntity,
    modifier: Modifier = Modifier,
    onDeleteClick: (TaskEntity) -> Unit,
    onStatusChange: (Int, TaskStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(task.taskStatus) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = task.title, style = MaterialTheme.typography.titleMedium)

            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
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
                        Priority.HIGH.priorityValue -> Color.Red
                        Priority.MEDIUM.priorityValue -> Color.Yellow
                        else -> Color.Green
                    },
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Due: ${task.dueDate}",
                    color = Color.Blue,
                    modifier = Modifier.padding(end = 8.dp)
                )

                IconButton(
                    onClick = { onDeleteClick(task) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color.Red
                    )
                }
            }

            // Task Status Dropdown
            Spacer(modifier = Modifier.height(8.dp))
            Box {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Status: ${task.taskStatus.name}")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    TaskStatus.entries.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.name) },
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