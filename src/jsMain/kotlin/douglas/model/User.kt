package douglas.model

import douglas.model.enums.Identity
import kotlin.js.json

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val imageUrl: String,
    val identity: Identity,
    val skills: List<String>,
) {
    companion object {
        fun fromDynamic(dynamic: dynamic): User {
            return fromDynamic(dynamic)
        }

        fun toDynamic(user: User): dynamic {
            return json(
                "id" to user.id,
                "name" to user.name,
                "email" to user.email,
                "password" to user.password,
                "imageUrl" to user.imageUrl,
                "identity" to user.identity.name,
                "skills" to user.skills.toTypedArray()
            )
        }
    }
}
