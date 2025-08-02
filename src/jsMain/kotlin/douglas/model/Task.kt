package douglas.model

import kotlinx.datetime.*

import douglas.model.enums.Priority
import douglas.model.enums.Status

data class Task(
    val id : Int,
    val name: String,
    val description: String,
    val priority: Priority,
    val status: Status,
    val initialDate: String,
    val finalDate: String
)