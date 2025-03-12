package com.apui.interactivetaskmanager.ui.screens.taskcreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.apui.interactivetaskmanager.data.model.Priority
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import com.apui.interactivetaskmanager.utils.CustomButton
import com.apui.interactivetaskmanager.utils.DatePickerModal
import com.apui.interactivetaskmanager.utils.OutlineTextField
import com.apui.interactivetaskmanager.utils.Screens
import com.apui.interactivetaskmanager.utils.TaskDropDownMenu
import com.apui.interactivetaskmanager.utils.convertMillisToDate
import com.apui.interactivetaskmanager.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskCreationScreen(
    viewModel: TopBarViewModel = koinViewModel(),
    taskListViewModel: TaskInsertViewModel = koinViewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) { viewModel.currentTop(Screens.TASK_CREATION) }
    TaskCreationContent { title,
                          description,
                          priority,
                          dueDate ->
        CoroutineScope(Dispatchers.Main).launch {
            taskListViewModel.insertTask(
                TaskEntity(
                    title = title,
                    description = description,
                    priority = priority.priorityValue,
                    dueDate = dueDate
                )
            )
            navController.navigateUp()
        }
    }
}

@Composable
fun TaskCreationContent(onTaskAdded: (String, String, Priority, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }
    val dueDate = remember { mutableStateOf("Select Date") }
    var showToast by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(showToast) {
        if (showToast) {
            showToast("Please enter Task Title, it cannot be empty!", context)
            showToast = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Create Task", style = MaterialTheme.typography.headlineMedium)

        TaskInputField("Task Title *", title) { title = it }
        TaskInputField("Description (Optional)", description) {
            description = it
        }

        PriorityDropdown(selectedPriority) { selectedPriority = it }
        DatePickerField(dueDate)

        Spacer(modifier = Modifier.height(3.dp))

        CustomButton(
            onClick = {
                if (title.isNotEmpty()) {
                    onTaskAdded(
                        title,
                        description,
                        selectedPriority,
                        dueDate.value
                    )
                    showToast = false
                } else {
                    showToast = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            text = "Add Task",
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008080),
                contentColor = Color(0xFFFFFFFF)
            )
        )
    }
}

@Composable
fun TaskInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlineTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun PriorityDropdown(selectedPriority: Priority, onPrioritySelected: (Priority) -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Priority: $selectedPriority")
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        TaskDropDownMenu(
            expanded = expanded,
            onSelect = onPrioritySelected,
            items = Priority.entries
        )
    }
}

@Composable
fun DatePickerField(dueDate: MutableState<String>) {

    val showDate = remember { mutableStateOf(false) }

    if (showDate.value) DatePickerModal(
        onDismiss = { showDate.value = false },
        onDateSelected = { selectedDate ->
            selectedDate?.let {
                dueDate.value = it.convertMillisToDate()
            }
        }
    )

    OutlinedButton(
        onClick = {
            showDate.value = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(dueDate.value)
    }
}
