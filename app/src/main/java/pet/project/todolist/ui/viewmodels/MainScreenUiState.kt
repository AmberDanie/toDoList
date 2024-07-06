package pet.project.todolist.ui.viewmodels

import pet.project.todolist.data.LoadingState
import pet.project.todolist.data.TodoItem

data class MainScreenUiState(
    val showCompleted: Boolean = true,
    val itemsList: List<TodoItem> = listOf(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val showSnackbar: Boolean = true
)