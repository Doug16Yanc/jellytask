package douglas.viewmodel

import douglas.repository.UserRepository
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLImageElement

class UserViewModel {
    private val userRepository = UserRepository()

    fun loadUserProfile(userId: Int) {
        val user = userRepository.getUserProfile(userId)
        val userProfileContainer = document.getElementById("user-profile") as? HTMLDivElement ?: return

        user?.let {
            val img = document.createElement("img") as HTMLImageElement
            img.src = it.imageUrl.ifEmpty { "https://via.placeholder.com/150" }
            img.alt = it.name
            img.className = "profile-image"

            val name = document.createElement("h2")
            name.textContent = it.name

            userProfileContainer.innerHTML = ""
            userProfileContainer.appendChild(img)
            userProfileContainer.appendChild(name)
        }
    }

    fun loadUsers() {
        val users = userRepository.getAllUsers()
        val container = document.getElementById("team") as? HTMLDivElement

        container?.innerHTML = ""

        users.forEach { user ->
            val memberContainer = document.createElement("div").apply {
                className = "team-member"
            }

            val img = document.createElement("img") as HTMLImageElement
            img.src = user.imageUrl.ifEmpty { "https://via.placeholder.com/150" }
            img.className = "team-avatar"

            val name = document.createElement("span").apply {
                className = "team-name"
                textContent = user.name.split(" ").first()
            }

            memberContainer.appendChild(img)
            memberContainer.appendChild(name)
            container?.appendChild(memberContainer)
        }
    }
}

