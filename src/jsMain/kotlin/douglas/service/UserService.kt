package douglas.service

import douglas.model.User
import douglas.model.enums.Identity
import douglas.repository.UserRepository

class UserService {

    private val userRepository = UserRepository()

    fun getUserProfile(id : Int) : User? {
        return userRepository.users.firstOrNull { it.id == id}
    }

    fun getAllUsers() : List<User> {
        return userRepository.users.toList()
    }

    fun login(email: String, password: String) : User? {
        return userRepository.getUserEmailAndPassword(email, password)
    }
}