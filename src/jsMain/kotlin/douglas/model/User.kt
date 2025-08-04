package douglas.model

import douglas.model.enums.Identity

data class User(
    var id: Int,
    val name: String,
    val identity: Identity,
    val skills : List<String>,
    val imageUrl: String
)
