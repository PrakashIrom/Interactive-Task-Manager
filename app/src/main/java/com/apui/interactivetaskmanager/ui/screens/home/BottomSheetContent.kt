package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BottomSheetContent(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(10.dp),
    ) {
        SortContent(selectedOption, onOptionSelected)
        Spacer(modifier = Modifier.height(10.dp))
        FilterContent()
    }
}

@Composable
fun SortContent(selectedOption: String, onOptionSelected: (String) -> Unit) {
    val options = listOf("Sort by Priority", "Sort by Due Date", "Sort by Alphabetical Order")

    Column {
        Text(
            text = "Sort By",
            fontWeight = FontWeight.W500,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = null
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FilterContent(taskListViewModel: TaskListViewModel = koinViewModel()) {
    val filterMapStatus by taskListViewModel.filterMapStatus.collectAsState()

    Column {
        Text(
            text = "Filter By",
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        filterMapStatus.forEach { (status, isChecked) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(status.name, modifier = Modifier.weight(1f))
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        taskListViewModel.updateFilterStatus(status, checked)
                    }
                )
            }
        }
    }

    // Observe changes inside mutableStateMapOf
    LaunchedEffect(filterMapStatus.entries.toList()) {
        val selectedStatuses = filterMapStatus.filterValues { it }.keys
        taskListViewModel.filterTasksByStatus(selectedStatuses)
    }
}
