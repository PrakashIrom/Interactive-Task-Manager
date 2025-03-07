package com.apui.interactivetaskmanager.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


fun showToast(
    description: String,
    context: Context
) {
    Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
}