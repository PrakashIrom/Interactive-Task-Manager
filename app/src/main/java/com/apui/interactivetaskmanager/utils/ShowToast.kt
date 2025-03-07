package com.apui.interactivetaskmanager.utils

import android.content.Context
import android.widget.Toast

fun showToast(
    description: String,
    context: Context
) {
    Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
}