package com.apui.interactivetaskmanager.data.model

enum class Priority(val priorityValue: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    companion object {
        fun fromValue(value: Int): Priority {
            return entries.find { it.priorityValue == value }
                ?: MEDIUM // Default to MEDIUM if not found
        }
    }
}