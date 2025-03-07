package com.apui.interactivetaskmanager.utils

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape,
    colors: ButtonColors
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors
    ) {
        Text(
            text = text,
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.W300
        )
    }
}