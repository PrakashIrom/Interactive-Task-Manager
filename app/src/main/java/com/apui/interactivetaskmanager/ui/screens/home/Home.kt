package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apui.interactivetaskmanager.data.model.TaskEntity
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
                key(task.id) {
                    TaskItem(task = task, onDeleteClick = onDeleteClick) { taskId, taskStatus ->
                        taskListViewModel.updateTaskStatus(taskId, taskStatus)
                    }
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