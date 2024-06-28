package pet.project.todolist.data

import androidx.annotation.StringRes
import pet.project.todolist.R
import java.time.LocalDate
import java.util.Date

/* part 2 */

enum class TaskImportance(@StringRes val importanceString: Int) {
    LOW(R.string.Low), DEFAULT(R.string.Default), HIGH(R.string.High)
}

data class TodoItem(
    val id: String,
    val text: String,
    val importance: TaskImportance = TaskImportance.DEFAULT,
    val deadline: LocalDate? = null,
    val isMade: Boolean = false,
    val creationDate: Date,
    val changeDate: Date? = null
)