package douglas.repository

import douglas.model.User
import douglas.model.enums.Identity
import kotlinx.browser.localStorage
import kotlin.js.json

class UserRepository {
    private val storageKey = "jellytask_users"

    val _users = mutableListOf(
        User(1, "Douglas", "douglas@gmail.com", "douglas123",  "https://media.licdn.com/dms/image/v2/D4D03AQHd8C2IYLV0kg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1721865916750?e=1756944000&v=beta&t=i2U2JV3HuoLv31z31l3ovlZzIz7FVhPLocB9sWtV38U", Identity.PÓLIPO, listOf("Dev Fullstack")),
        User(2, "Kaiane", "kaiane@gmail.com", "kaiane123","https://media.licdn.com/dms/image/v2/D4D35AQEKgQE9VVupIg/profile-framedphoto-shrink_800_800/profile-framedphoto-shrink_800_800/0/1727046758291?e=1754686800&v=beta&t=LYBRqKJ2fIhpLvpkNHMi0nSr81Mx7WZ4u22nlnJKXWI", Identity.MEDUSA, listOf("Dev Frontend")),
        User(3, "Lívia", "livia@gmail.com", "livia123", "https://media.licdn.com/dms/image/v2/D4D35AQGS9M8Jc73Ztg/profile-framedphoto-shrink_800_800/profile-framedphoto-shrink_800_800/0/1699752296436?e=1754686800&v=beta&t=X-YyGgUhRwyPrLreOJoYUvjN8_Otz9xBhmqRYRbkXuc", Identity.MEDUSA, listOf("UI/UX Designer")),
        User(5, "Wesley",  "wesley@gmail.com", "wesley123", "https://media.licdn.com/dms/image/v2/D4D03AQH1M-Zd6fJYBA/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1708198673788?e=1756944000&v=beta&t=MvtzxH9woF0p_gp7iCryqyjU0s-lun-G_ku4VKG-SmU", Identity.PÓLIPO, listOf("Dev Fullstack")),
    )

    init {
        if (localStorage.getItem(storageKey) == null) {
            val defaultUsers = _users.toList()
            saveUsers(defaultUsers)
        }
    }

    var users: List<User>
        get() {
            val stored = localStorage.getItem(storageKey) ?: return emptyList()
            val parsed = JSON.parse<Array<dynamic>>(stored)
            return parsed.map { obj ->
                User(
                    id = obj.id as Int,
                    name = obj.name as String,
                    email = obj.email as String,
                    password = obj.password as String,
                    imageUrl = obj.imageUrl as String,
                    identity = Identity.valueOf(obj.identity as String),
                    skills = (obj.skills as Array<*>).map { it as String }
                )
            }
        }
        set(value) {
            saveUsers(value)
        }


    fun getUserEmailAndPassword(email: String, password: String) : User? {
        return _users.firstOrNull { it.email == email && it.password == password }
    }

    private fun saveUsers(users: List<User>) {
        val jsArray = users.map { user ->
            json(
                "id" to user.id,
                "name" to user.name,
                "email" to user.email,
                "password" to user.password,
                "imageUrl" to user.imageUrl,
                "identity" to user.identity.name,
                "skills" to user.skills.toTypedArray()
            )
        }.toTypedArray()

        localStorage.setItem(storageKey, JSON.stringify(jsArray))
    }
}