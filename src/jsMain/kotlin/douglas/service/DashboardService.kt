package douglas.service

import douglas.model.enums.Identity
import douglas.model.enums.Priority
import douglas.model.enums.Status
import douglas.repository.TaskRepository
import douglas.repository.UserRepository

class DashboardService {
    private val taskRepository = TaskRepository()
    private val userRepository = UserRepository()

    fun countPolyps() : Int = userRepository.users.count { it.identity == Identity.PÓLIPO }
    fun countJellyfishs() : Int = userRepository.users.count { it.identity == Identity.MEDUSA }

    fun countByPriority(priority: Priority) : Int = taskRepository.tasks.count { it.priority ==  priority}
    fun countByStatus(status: Status) : Int = taskRepository.tasks.count { it.status == status}
}