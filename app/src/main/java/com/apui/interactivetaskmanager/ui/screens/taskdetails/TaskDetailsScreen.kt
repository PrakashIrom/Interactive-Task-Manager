package com.apui.interactivetaskmanager.ui.screens.taskdetails

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskDetailsScreen(
    taskDetailsViewModel: TaskDetailsViewModel = koinViewModel(),
    progressBarViewModel: ProgressBarViewModel = koinViewModel(),
) {
    val progress by progressBarViewModel.taskProgress.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Task Progress", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgressBar(progress = progress)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { progressBarViewModel.updateProgress() }) {
            Text("Increase Progress")
        }
    }
}

@Composable
fun CircularProgressBar(
    progress: Float, // Progress from 0f to 1f
    modifier: Modifier = Modifier.size(150.dp),
    strokeWidth: Dp = 12.dp
) {
    // Animate the progress change smoothly
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "Progress Indicator"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Background Circle
        Canvas(modifier = modifier) {
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        // Progress Arc
        Canvas(modifier = modifier) {
            drawArc(
                color = Color.Blue,
                startAngle = -90f,
                sweepAngle = animatedProgress.value * 360,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        // Show Percentage in Center
        Text(
            text = "${(animatedProgress.value * 100).toInt()}%",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
