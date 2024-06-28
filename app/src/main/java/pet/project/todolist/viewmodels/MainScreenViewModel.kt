package pet.project.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pet.project.todolist.data.TodoItem
import pet.project.todolist.data.TodoItemsRepository

/* part 2 */

class MainScreenViewModel(private val repository: TodoItemsRepository = TodoItemsRepository()) : ViewModel() {

    private val _msState = MutableStateFlow(MainScreenUiState())
    val msState = _msState.asStateFlow()

    fun showOrHideCompletedTasks() {
        _msState.update {
            it.copy(
                showCompleted = !it.showCompleted
            )
        }
    }

    fun updateCurrentItem(item: TodoItem) {
        _msState.update {
            it.copy(
                currentItem = item
            )
        }
    }

    fun resetCurrentItem() {
        _msState.update {
            it.copy(
                currentItem = null
            )
        }
    }

    fun updateItemInList(oldItem: TodoItem, newItem: TodoItem) {
        viewModelScope.launch {
            repository.updateItemInList(oldItem, newItem)
            resetCurrentItem()
        }
    }

    fun addTodoItem(item: TodoItem) {
        viewModelScope.launch {
            repository.addItemToList(item)
            resetCurrentItem()
        }
    }

    fun removeTodoItem(item: TodoItem) {
        viewModelScope.launch {
            repository.removeTodoItem(item)
        }
        resetCurrentItem()
    }

    fun checkboxClick(item: TodoItem) {
        viewModelScope.launch {
            repository.changeMadeStatus(item)
        }
    }

    init {
        viewModelScope.launch {
            repository.returnTodoItemsList().collect { value ->
                _msState.update {
                    it.copy(
                        itemsList = value
                    )
                }
            }
        }
    }
}