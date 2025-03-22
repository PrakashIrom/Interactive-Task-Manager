package com.apui.interactivetaskmanager.utils

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.apui.interactivetaskmanager.ui.components.BounceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FABButton(
    onClick: () -> Unit
) {
    var currentState: BounceState by remember { mutableStateOf(BounceState.RELEASED) }
    val transition = updateTransition(targetState = currentState, label = "BounceTransition")
    val scale: Float by transition.animateFloat(
        transitionSpec = {
            spring(
                stiffness = 900f,
                dampingRatio = Spring.DampingRatioLowBouncy
            )
        },
        label = "ScaleAnimation"
    ) { state ->
        when (state) {
            BounceState.PRESSED -> 1.3f
            BounceState.RELEASED -> 1f
        }
    }

    val coroutineScope = rememberCoroutineScope { Dispatchers.Main }

    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                currentState = BounceState.PRESSED
                delay(100)
                onClick()
                currentState = BounceState.RELEASED
            }
        },
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = Color(0xFF9C4DB9),
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Button"
        )
    }
}