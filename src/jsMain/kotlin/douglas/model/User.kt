package douglas.model

data class User(
    var id: Int,
    val name: String,
    val skills : List<String>,
    val imageUrl: String
)
