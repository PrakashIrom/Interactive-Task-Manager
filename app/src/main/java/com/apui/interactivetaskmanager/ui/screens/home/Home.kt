package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apui.interactivetaskmanager.data.model.Priority
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import com.apui.interactivetaskmanager.utils.Screens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Home(
    topBarViewModel: TopBarViewModel = koinViewModel(),
    taskListViewModel: TaskListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) { topBarViewModel.currentTop(Screens.HOME) }

    val tasks = taskListViewModel.tasks.collectAsStateWithLifecycle().value
    if (tasks.isEmpty()) {
        Box(
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
    }

    LazyColumn(modifier = modifier) {
        items(tasks) { task ->
            TaskItem(task = task)
        }
    }
}

@Composable
fun TaskItem(task: TaskEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)

            // Description (only show if not null or empty)
            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Priority and Due Date Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Priority: ${Priority.fromValue(task.priority)}",
                    fontWeight = FontWeight.Bold,
                    color = when (task.priority) {
                        Priority.HIGH.priorityValue -> Color.Red
                        Priority.MEDIUM.priorityValue -> Color.Yellow
                        else -> {
                            Color.Green
                        }
                    }
                )
                Text(text = "Due: ${task.dueDate}", color = Color.Blue)
            }
        }
    }
}