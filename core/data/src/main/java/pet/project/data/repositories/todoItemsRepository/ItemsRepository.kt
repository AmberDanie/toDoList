package pet.project.data.repositories.todoItemsRepository

import kotlinx.coroutines.flow.Flow
import pet.project.domain.TodoItem
import pet.project.domain.TodoItemDto

interface ItemsRepository {
    suspend fun changeMadeStatus(itemId: String)
    suspend fun getItemById(itemId: String): TodoItemDto
    suspend fun updateItem(oldItemId: String, newItem: TodoItem)
    suspend fun updateList(itemList: List<TodoItem>)
    suspend fun removeItem(itemId: String)
    suspend fun addItem(item: TodoItem)
    suspend fun getItemsFlow(): Pair<Flow<List<TodoItemDto>>, Boolean>
}
