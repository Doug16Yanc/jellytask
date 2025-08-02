package douglas.repository

import douglas.model.User

class UserRepository {
    val users = listOf(
        Triple(1, "Douglas", "https://media.licdn.com/dms/image/v2/D4D03AQHd8C2IYLV0kg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1721865916750?e=1756944000&v=beta&t=i2U2JV3HuoLv31z31l3ovlZzIz7FVhPLocB9sWtV38U"),
        Triple(2, "Kaiane", "https://media.licdn.com/dms/image/v2/D4D35AQEKgQE9VVupIg/profile-framedphoto-shrink_800_800/profile-framedphoto-shrink_800_800/0/1727046758291?e=1754686800&v=beta&t=LYBRqKJ2fIhpLvpkNHMi0nSr81Mx7WZ4u22nlnJKXWI"),
        Triple(3, "Lívia", "https://media.licdn.com/dms/image/v2/D4D35AQGS9M8Jc73Ztg/profile-framedphoto-shrink_800_800/profile-framedphoto-shrink_800_800/0/1699752296436?e=1754686800&v=beta&t=X-YyGgUhRwyPrLreOJoYUvjN8_Otz9xBhmqRYRbkXuc"),
        Triple(4, "Micaele", ""),
        Triple(5, "Wesley", "https://media.licdn.com/dms/image/v2/D4D03AQH1M-Zd6fJYBA/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1708198673788?e=1756944000&v=beta&t=MvtzxH9woF0p_gp7iCryqyjU0s-lun-G_ku4VKG-SmU"),
    ).map { (id, name, imageUrl) -> User(id = id, name = name, imageUrl = imageUrl) }

    fun getUserProfile(id : Int) : User? {
        return users.firstOrNull { it.id == id}
    }

    fun getAllUsers() : List<User> {
        return users.toList()
    }
}