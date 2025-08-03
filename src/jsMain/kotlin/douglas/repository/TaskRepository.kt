package douglas.repository

import douglas.model.Task
import douglas.model.enums.Priority
import douglas.model.enums.Status

class TaskRepository {
    private val _tasks = mutableListOf(
        Task(
            id = 1,
            name = "Criar pipeline com Terraform, AWS e Jenkins",
            description = "Implementar pipeline CI/CD completo em um servidor EC2 da AWS por meio de IaC com Terraform e construir build, testes e lint com Jenkins.",
            priority = Priority.ALTA,
            status = Status.EM_ANDAMENTO,
            initialDate = "01/08/2025",
            finalDate = "10/08/2025"
        ),
        Task(
            id = 2,
            name = "Criar os triggers para as relações mais críticas",
            description = "Desenvolver os devidos triggers no PostgreSQL para relações críticas e que armazenam informações voláteis, além de remover agregações das visões.",
            priority = Priority.MÉDIA,
            status = Status.CONCLUÍDA,
            initialDate = "31/05/2025",
            finalDate = "05/06/2025"
        ),
        Task(
            id = 3,
            name = "Testar as funcionalidades do épico 0.1.0 no servidor de produção",
            description = "Realizar os testes no servidor EC2 com JUnit para o backend e Cypress para o frontend.",
            priority = Priority.CRÍTICA,
            status = Status.EM_FILA,
            initialDate = "01/08/2025",
            finalDate = "15/08/2025"
        ),
        Task(
            id = 4,
            name = "Criar as visões para consultas rápidas",
            description = "Criar todas as visões para permitir consultas rápidas no banco de dados, não inserir agregações para não comprometer a performance.",
            priority = Priority.BAIXA,
            status = Status.CONCLUÍDA,
            initialDate = "01/08/2025",
            finalDate = "15/08/2025"
        ),
        Task(
            id = 5,
            name = "Implementar mensageria com Apache Kafka",
            description = "Implementar mensageria com os seguintes tópicos no Apache Kafka: topico.mensagens, topico.pagamento, topico.avaliacoes.",
            priority = Priority.BAIXA,
            status = Status.EM_FILA,
            initialDate = "01/08/2025",
            finalDate = "15/08/2025"
        ),
        Task(
            id = 6,
            name = "Criar dropdown customizado e implementar em todo o frontend.",
            description = "Criar o componente com Angular Material e importar este em todos os locais que o sistema usa este recurso.",
            priority = Priority.MÉDIA,
            status = Status.EM_ANDAMENTO,
            initialDate = "01/08/2025",
            finalDate = "15/08/2025"
        ),
    )

    val tasks: List<Task> get() = _tasks.toList()

    private fun generateId(): Int = (_tasks.maxOfOrNull { it.id } ?: 0) + 1

    fun addTask(
        title: String,
        description: String,
        priority: Priority,
        startDate: String,
        endDate: String,
        status: Status
    ): Task {
        val newTask = Task(
            id = generateId(),
            name = title,
            description = description,
            priority = priority,
            initialDate = startDate,
            finalDate = endDate,
            status = status
        )
        _tasks.add(newTask)
        return newTask
    }

    fun updateTaskStatus(taskId: Int, newStatus: Status) {
        _tasks.find { it.id == taskId }?.status = newStatus
    }

    fun getTasksByStatus(status: Status): List<Task> {
        return _tasks.filter { it.status == status }
    }
}