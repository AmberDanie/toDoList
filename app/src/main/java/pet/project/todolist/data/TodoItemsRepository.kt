package pet.project.todolist.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Date

/* part 2 */

class TodoItemsRepository : ItemsRepository<TodoItem> {
    // Список захардкожен внутри стейт флоу
    private val _itemsState = MutableStateFlow(
        listOf(
            TodoItem(
                id = "0",
                text = "Купить что-то",
                isMade = true,
                deadline = LocalDate.parse("2024-06-25"),
                creationDate = Date()
            ),
            TodoItem(
                id = "1",
                text = "Купить что-то",
                importance = TaskImportance.LOW,
                isMade = true,
                creationDate = Date()
            ),
            TodoItem(
                id = "2",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "3",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно, " +
                        "но точно чтобы показать как образовывается список из элементов",
                isMade = false,
                deadline = LocalDate.parse("2024-06-29"),
                creationDate = Date()
            ),
            TodoItem(
                id = "4",
                text = "Купить что-то",
                importance = TaskImportance.HIGH,
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "5",
                text = "Купить что-то",
                isMade = false,
                importance = TaskImportance.LOW,
                creationDate = Date()
            ),
            TodoItem(
                id = "6",
                text = "Доделать ДЗ",
                isMade = true,
                deadline = LocalDate.parse("2024-06-22"),
                importance = TaskImportance.HIGH,
                creationDate = Date()
            ),
            TodoItem(
                id = "7",
                text = "Купить что-то",
                isMade = true,
                creationDate = Date()
            ),
            TodoItem(
                id = "8",
                text = "Купить что-то",
                isMade = true,
                creationDate = Date()
            ),
            TodoItem(
                id = "9",
                text = "Купить что-то",
                importance = TaskImportance.HIGH,
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "10",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "11",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "12",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "13",
                text = "Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit. " +
                        "Duis vehicula et mauris sit amet bibendum. " +
                        "Aenean nec rutrum ante. Maecenas a " +
                        "ultricies nulla. Cras diam urna, " +
                        "mollis eget malesuada eget, eleifend eget mi. " +
                        "Morbi eget luctus leo. Vivamus ac felis nisl. " +
                        "Fusce ullamcorper condimentum mollis. Vivamus consectetur " +
                        "leo placerat nulla congue, eget consectetur sapien viverra. " +
                        "Morbi tincidunt, mi eget efficitur placerat, augue lectus blandit " +
                        "lorem, vel dapibus massa eros vel sapien. Vestibulum tristique nisi ipsum, " +
                        "eget semper ex feugiat eu",
                isMade = true,
                importance = TaskImportance.HIGH,
                creationDate = Date()),
            TodoItem(
                id = "14",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                importance = TaskImportance.HIGH,
                deadline = LocalDate.parse("2024-06-22"),
                isMade = true,
                creationDate = Date()
            ),
            TodoItem(
                id = "15",
                text = "Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit. " +
                        "Duis vehicula et mauris sit amet bibendum. " +
                        "Aenean nec rutrum ante. Maecenas a " +
                        "ultricies nulla. Cras diam urna, " +
                        "mollis eget malesuada eget, eleifend eget mi. " +
                        "Morbi eget luctus leo. Vivamus ac felis nisl. " +
                        "Fusce ullamcorper condimentum mollis. Vivamus consectetur " +
                        "leo placerat nulla congue, eget consectetur sapien viverra. " +
                        "Morbi tincidunt, mi eget efficitur placerat, augue lectus blandit " +
                        "lorem, vel dapibus massa eros vel sapien. Vestibulum tristique nisi ipsum, " +
                        "eget semper ex feugiat eu" +
                        "Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit. " +
                        "Duis vehicula et mauris sit amet bibendum. " +
                        "Aenean nec rutrum ante. Maecenas a " +
                        "ultricies nulla. Cras diam urna, " +
                        "mollis eget malesuada eget, eleifend eget mi. " +
                        "Morbi eget luctus leo. Vivamus ac felis nisl. " +
                        "Fusce ullamcorper condimentum mollis. Vivamus consectetur " +
                        "leo placerat nulla congue, eget consectetur sapien viverra. " +
                        "Morbi tincidunt, mi eget efficitur placerat, augue lectus blandit " +
                        "lorem, vel dapibus massa eros vel sapien. Vestibulum tristique nisi ipsum, " +
                        "eget semper ex feugiat eu" +
                        "Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit. " +
                        "Duis vehicula et mauris sit amet bibendum. " +
                        "Aenean nec rutrum ante. Maecenas a " +
                        "ultricies nulla. Cras diam urna, " +
                        "mollis eget malesuada eget, eleifend eget mi. " +
                        "Morbi eget luctus leo. Vivamus ac felis nisl. " +
                        "Fusce ullamcorper condimentum mollis. Vivamus consectetur " +
                        "leo placerat nulla congue, eget consectetur sapien viverra. " +
                        "Morbi tincidunt, mi eget efficitur placerat, augue lectus blandit " +
                        "lorem, vel dapibus massa eros vel sapien. Vestibulum tristique nisi ipsum, " +
                        "eget semper ex feugiat eu",
                isMade = false,
                importance = TaskImportance.LOW,
                deadline = LocalDate.parse("2024-12-24"),
                creationDate = Date()),
            TodoItem(
                id = "16",
                text = "Купить что-то",
                importance = TaskImportance.HIGH,
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "17",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "18",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "19",
                text = "Купить что-то, где-то, зачем-то, но зачем не очень понятно",
                isMade = false,
                creationDate = Date()
            ),
            TodoItem(
                id = "20",
                text = "Устать",
                isMade = true,
                creationDate = Date()
            )
        )
    )

    override suspend fun changeMadeStatus(item: TodoItem) {
        withContext(Dispatchers.IO) {
            val list = _itemsState.value.toMutableList()
            list[list.indexOf(item)] = item.copy(isMade = !item.isMade)
            _itemsState.update {
                list
            }
        }
    }

    override suspend fun updateItemInList(oldItem: TodoItem, newItem: TodoItem) {
        withContext(Dispatchers.IO) {
            val list = _itemsState.value.toMutableList()
            list[list.indexOf(oldItem)] = newItem
            _itemsState.update {
                list
            }
        }
    }

    override suspend fun removeTodoItem(item: TodoItem) {
        withContext(Dispatchers.IO) {
            val list = _itemsState.value.toMutableList()
            list.remove(item)
            _itemsState.update {
                list
            }
        }
    }

    override suspend fun addItemToList(item: TodoItem) {
        withContext(Dispatchers.IO) {
            _itemsState.update {
                it + item
            }
        }
    }

    override fun returnTodoItemsList(): StateFlow<List<TodoItem>> {
        return _itemsState.asStateFlow()
    }
}