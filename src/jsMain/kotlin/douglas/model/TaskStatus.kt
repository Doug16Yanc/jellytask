package douglas.model

data class TaskStatus(
    val completedPercent: Int,
    val inProgressPercent: Int,
    val notStartedPercent: Int
)
