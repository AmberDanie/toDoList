package pet.project.todolist.data

import com.google.gson.annotations.SerializedName
import pet.project.todolist.network.TodoItemDto

data class ServerResponse(
    @SerializedName(value = "status")
    val status: String,
    @SerializedName(value = "list")
    val list: List<TodoItemDto>,
    @SerializedName(value = "revision")
    val revision: Int
)