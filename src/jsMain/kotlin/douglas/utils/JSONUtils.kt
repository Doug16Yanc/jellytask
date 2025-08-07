package douglas.utils

import douglas.model.User
import douglas.model.enums.Identity
import kotlin.js.json

object JSONUtils {
    fun parseUser(jsonString: String): User {
        val obj = JSON.parse<dynamic>(jsonString)
        return User(
            id = obj.id as Int,
            name = obj.name as String,
            email = obj.email as String,
            password = obj.password as String,
            imageUrl = obj.imageUrl as String,
            identity = Identity.valueOf(obj.identity as String),
            skills = (obj.skills as Array<*>).map { it as String }
        )
    }

    fun userToJson(user: User): String {
        return JSON.stringify(
            json(
                "id" to user.id,
                "name" to user.name,
                "email" to user.email,
                "password" to user.password,
                "imageUrl" to user.imageUrl,
                "identity" to user.identity.name,
                "skills" to user.skills.toTypedArray()
            )
        )
    }
}