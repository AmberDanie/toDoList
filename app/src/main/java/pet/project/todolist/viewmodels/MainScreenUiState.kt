package pet.project.todolist.viewmodels

import pet.project.todolist.data.TodoItem

/* part 2 */

data class MainScreenUiState(
    val showCompleted: Boolean = true,
    val itemsList: List<TodoItem> = listOf(),
    val currentItem: TodoItem? = null
)