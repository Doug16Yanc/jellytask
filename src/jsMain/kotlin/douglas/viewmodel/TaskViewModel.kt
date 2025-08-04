package douglas.viewmodel

import douglas.model.Task
import douglas.model.enums.Priority
import douglas.model.enums.Status
import douglas.service.TaskService
import douglas.utils.external.Sortable
import douglas.utils.SortableOptions
import douglas.utils.formatDate
import douglas.utils.showAlertDialog
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.*
import org.w3c.dom.events.Event

class TaskViewModel {
    private val taskService = TaskService()

    init {
        setupEventListeners()
    }

    fun setupSortableColumns() {
        setupSortableColumn("todo-column")
        setupSortableColumn("in-progress-column")
        setupSortableColumn("done-column")
    }

    fun renderTasks() {
        renderTaskColumn(Status.EM_FILA, "todo-column")
        renderTaskColumn(Status.EM_ANDAMENTO, "in-progress-column")
        renderTaskColumn(Status.CONCLUÍDA, "done-column")
        setupEventListeners()

        setupSortableColumns()
    }

    private fun setupSortableColumn(columnId: String) {
        val column = document.getElementById(columnId) ?: return

        val options = js("{}").unsafeCast<SortableOptions>().apply {
            group = "tasks"
            animation = 150
            delay = 300
            delayOnTouchOnly = true
            touchStartThreshold = 5
            fallbackOnBody = true
            onEnd = { evt: dynamic ->
                try {
                    val taskId = evt.item.getAttribute("data-task-id").toString().toInt()
                    val newStatus = when (evt.to.id) {
                        "todo-column" -> Status.EM_FILA
                        "in-progress-column" -> Status.EM_ANDAMENTO
                        else -> Status.CONCLUÍDA
                    }

                    this@TaskViewModel.taskService.moveTask(taskId, newStatus)

                } catch (e: Exception) {
                    showAlertDialog(e.message ?: "Erro desconhecido ao mover tarefa.")
                } finally {
                    this@TaskViewModel.renderTasks()
                }
            }
        }

        Sortable(column, options)
    }

    private fun renderTaskColumn(status: Status, columnId: String) {
        val column = document.getElementById(columnId) as? HTMLDivElement ?: return
        val addButton = columnId == "todo-column"
        val title = when (columnId) {
            "todo-column" -> "Em fila"
            "in-progress-column" -> "Em andamento"
            "done-column" -> "Concluída"
            else -> ""
        }
        column.innerHTML = buildString {
            append("<div class=\"column-header\">")
            append("<h3>$title</h3>")
            if (addButton) {
                append("<button class=\"add-task-btn\" data-column=\"todo\">+</button>")
            }
            append("</div>")
        }

        val tasks = taskService.getTasksByStatus(status)

        tasks.forEach { task ->
            val taskElement = createTaskElement(task)
            column.appendChild(taskElement)
        }

        setupColumnEventListeners(columnId)
    }

    private fun createTaskElement(task: Task): HTMLDivElement {
        val taskElement = document.createElement("div") as HTMLDivElement
        taskElement.className = "task-item"
        taskElement.setAttribute("data-priority", task.priority.name)
        taskElement.setAttribute("data-task-id", task.id.toString())

        val formattedInitialDate = formatDate(task.initialDate)
        val formattedFinalDate = formatDate(task.finalDate)

        taskElement.innerHTML = """
            <div class="task-title">${task.name}</div>
            ${if (task.description.isNotEmpty()) "<div class='task-desc'>${task.description}</div>" else ""}
            <div class="task-dates">
                <span>Início:$formattedInitialDate</span> - <span>Fim: $formattedFinalDate</span>
            </div>
        """
        return taskElement
    }

    private fun setupEventListeners() {
        val buttons = document.querySelectorAll(".add-task-btn")

        (0 until buttons.length).forEach { i ->
            val button = buttons.item(i) as? HTMLElement ?: return@forEach
            button.addEventListener("click", { event ->
                val target = event.currentTarget as? HTMLElement
                val column = target?.getAttribute("data-column") ?: return@addEventListener
                showAddTaskModal(column)
            })
        }
    }

    private fun setupColumnEventListeners(columnId: String) {
        val column = document.getElementById(columnId) as? HTMLDivElement ?: return

        column.addEventListener("dragover", { event ->
            event.preventDefault()
        })

        column.addEventListener("drop", { event ->
            val dropEvent = event as? DragEvent ?: return@addEventListener
            dropEvent.preventDefault()
            val taskId = dropEvent.dataTransfer?.getData("text/plain")?.toIntOrNull() ?: return@addEventListener

            val newStatus = when (columnId) {
                "todo-column" -> Status.EM_FILA
                "in-progress-column" -> Status.EM_ANDAMENTO
                "done-column" -> Status.CONCLUÍDA
                else -> return@addEventListener
            }

            try {
                taskService.moveTask(taskId, newStatus)
                renderTasks()
            } catch (e: Exception) {
                showAlertDialog(e.message ?: "Erro desconhecido ao mover tarefa.")
            }
        })
    }

    private fun showAddTaskModal(column: String) {
        val overlay = document.createElement("div")
        overlay.className = "modal-overlay"

        overlay.innerHTML = """
        <div class="task-modal">
            <div class="modal-content">
                <span class="close-modal">&times;</span>
                <h3>Adicione uma nova tarefa</h3>
                <form id="task-form">
                    <label for="task-title">Título</label>
                    <input type="text" id="task-title" required>
                    <label for="task-desc" class="label-desc">Descrição</label>
                    <textarea id="task-desc"></textarea>
                    <label for="custom-priority">Prioridade</label>
                    <div class="custom-select" id="custom-priority">
                        <div class="selected">Selecione...</div>
                        <ul class="options">
                            <li data-value="BAIXA">Baixa</li>
                            <li data-value="MÉDIA">Média</li>
                            <li data-value="ALTA">Alta</li>
                            <li data-value="CRÍTICA">Crítica</li>
                        </ul>
                    </div>
                   <div class="date-inputs">
                        <label for="task-start-date">Data de Início</label>
                        <input type="text" id="task-start-date" required placeholder="dia/mês/ano">
                        <label for="task-end-date">Data de Fim</label>
                        <input type="text" id="task-end-date" required placeholder="dia/mês/ano">
                    </div>
                    <button type="submit">Adicionar</button>
                </form>
            </div>
        </div>
    """
        document.body?.appendChild(overlay)

        val customSelect = document.getElementById("custom-priority") as HTMLElement
        val selected = customSelect.querySelector(".selected") as HTMLElement
        val options = customSelect.querySelector(".options") as HTMLElement

        customSelect.addEventListener("click", {
            customSelect.classList.toggle("open")
        })

        options.querySelectorAll("li").asList().forEach { node ->
            val option = node as? HTMLLIElement ?: return@forEach
            option.addEventListener("click", {event ->
                event.stopPropagation()
                val value = option.getAttribute("data-value") ?: ""
                selected.textContent = option.textContent
                customSelect.classList.remove("open")
                customSelect.setAttribute("data-selected-value", value)
            })
        }

        document.addEventListener("click", { event ->
            val target = event.target as? HTMLElement ?: return@addEventListener
            val select = document.getElementById("custom-priority") as? HTMLElement ?: return@addEventListener

            if (!select.contains(target)) {
                select.classList.remove("open")
            }
        })

        val flatpickr = window.asDynamic().flatpickr
        val ptLocale = window.asDynamic().flatpickr.l10ns.pt

        window.setTimeout({
            js("flatpickr('#task-start-date', { locale: 'pt', dateFormat: 'd/m/Y' })")
            js("flatpickr('#task-end-date', { locale: 'pt', dateFormat: 'd/m/Y' })")
        }, 100)

        overlay.querySelector(".close-modal")?.addEventListener("click", {
            overlay.remove()
        })

        overlay.querySelector("#task-form")?.addEventListener("submit", { event: Event ->
            event.preventDefault()

            val title = (document.getElementById("task-title") as? HTMLInputElement)?.value ?: ""
            val description = (document.getElementById("task-desc") as? HTMLTextAreaElement)?.value ?: ""
            val priority = (document.getElementById("custom-priority") as? HTMLElement)
                ?.getAttribute("data-selected-value") ?: ""
            val startDate = (document.getElementById("task-start-date") as? HTMLInputElement)?.value ?: ""
            val endDate = (document.getElementById("task-end-date") as? HTMLInputElement)?.value ?: ""

            val status = when (column) {
                "todo" -> Status.EM_FILA
                "in-progress" -> Status.EM_ANDAMENTO
                "done" -> Status.CONCLUÍDA
                else -> Status.EM_FILA
            }

            try {
                val selectedPriority = priority.ifEmpty { "" }
                taskService.addTask(
                    title = title,
                    description = description,
                    priority = Priority.valueOf(selectedPriority),
                    startDate = startDate,
                    endDate = endDate,
                    status = status
                )
                renderTasks()
                overlay.remove()
            } catch (e: Exception) {
                showAlertDialog(e.message ?: "Erro desconhecido ao adicionar tarefa.")
            }
        })
    }
}
