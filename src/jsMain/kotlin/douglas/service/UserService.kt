package douglas.service

import douglas.model.User
import douglas.repository.UserRepository

class UserService {

    private val userRepository = UserRepository()

    fun getUserProfile(id : Int) : User? {
        return userRepository.users.firstOrNull { it.id == id}
    }

    fun getAllUsers() : List<User> {
        return userRepository.users.toList()
    }
}