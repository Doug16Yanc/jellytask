package douglas.viewmodel

import douglas.model.Task
import douglas.model.TaskStatus
import douglas.model.enums.Status
import douglas.repository.TaskRepository
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

class TaskViewModel {
    private val taskRepository = TaskRepository()

    fun renderTasks() {
        renderTaskColumn(Status.EM_FILA, "todo-column")
        renderTaskColumn(Status.EM_ANDAMENTO, "in-progress-column")
        renderTaskColumn(Status.CONCLUÍDA, "done-column")
    }

    private fun renderTaskColumn(status: Status, columnId: String) {
        val column = document.getElementById(columnId) as? HTMLDivElement ?: return
        val tasks = taskRepository.getTasksByStatus(status)

        tasks.forEach { task ->
            val taskElement = createTaskElement(task)
            column.appendChild(taskElement)
        }
    }

    private fun createTaskElement(task: Task): HTMLDivElement {
        val taskElement = document.createElement("div") as HTMLDivElement
        taskElement.className = "task-item"
        taskElement.setAttribute("data-priority", task.priority.name)
        taskElement.setAttribute("data-task-id", task.id.toString())

        taskElement.innerHTML = """
            <div class="task-title">${task.name}</div>
            ${if (task.description.isNotEmpty()) "<div class='task-desc'>${task.description}</div>" else ""}
            <div class="task-dates">
                <span> Início : ${task.initialDate}</span> - <span> Fim : ${task.finalDate}</span>
            </div>
        """

        return taskElement
    }
}