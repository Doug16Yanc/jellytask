package douglas.service

import douglas.model.Task
import douglas.model.enums.Priority
import douglas.model.enums.Status
import douglas.repository.TaskRepository

class TaskService {
    private val taskRepository = TaskRepository()

    fun addTask(
        title: String,
        description: String,
        priority: Priority,
        startDate: String,
        endDate: String,
        status: Status
    ): Task {

        require(title.isNotBlank()) { "Título não pode ser vazio" }
        require(startDate <= endDate) { "Data de finalização precisa ser após a data de início" }

        return taskRepository.addTask(
            title = title,
            description = description,
            priority = priority,
            startDate = startDate,
            endDate = endDate,
            status = status
        )
    }

    fun moveTask(taskId: Int, newStatus: Status) {
        val task = getTaskById(taskId) ?: throw IllegalArgumentException("Tarefa não encontrada")

        if (task.status == Status.CONCLUÍDA && newStatus != Status.CONCLUÍDA) {
            throw IllegalStateException("Tarefa concluída não pode ser movida de volta. Caso queiras ajustar, crie uma nova tarefa em fila.")
        }

        taskRepository.updateTaskStatus(taskId, newStatus)
    }

    fun getTaskById(id: Int): Task? {
        return taskRepository.tasks.firstOrNull { it.id == id }
    }

    fun getAllTasks(): List<Task> {
        return taskRepository.tasks
    }

    fun getHighPriorityTasks(): List<Task> {
        return taskRepository.tasks.filter { it.priority == Priority.ALTA || it.priority == Priority.CRÍTICA }
    }

    fun getTasksByStatus(status: Status): List<Task> {
        return taskRepository.tasks.filter { it.status == status }
    }
}