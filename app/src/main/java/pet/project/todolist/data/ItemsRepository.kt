package pet.project.todolist.data

import kotlinx.coroutines.flow.StateFlow

/* part 2 */

interface ItemsRepository<Item> {
    suspend fun changeMadeStatus(item: Item)
    suspend fun updateItemInList(oldItem: Item, newItem: Item)
    suspend fun removeTodoItem(item: Item)
    suspend fun addItemToList(item: Item)
    fun returnTodoItemsList(): StateFlow<List<Item>>
}