package douglas.viewmodel

import douglas.model.Task
import douglas.model.enums.Status
import douglas.repository.UserRepository
import douglas.service.UserService
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.internal.JSJoda.use
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement

class UserViewModel {
    private val userService = UserService()

    fun initPages() {
        when {
            window.location.pathname.contains("/pages/team.html") -> loadTeamCards()
            else -> loadUsers()
        }
    }

    fun loadUserProfile(userId: Int) {
        val user = userService.getUserProfile(userId)
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

    private fun loadUsers() {
        val users = userService.getAllUsers()
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

    private fun loadTeamCards() {
        val users = userService.getAllUsers()
        val desktopContainer = document.getElementById("team-desktop")
        val mobileContainer = document.getElementById("team-mobile")

        desktopContainer?.innerHTML = ""
        mobileContainer?.innerHTML = ""

        users.forEach { user ->
            val cardHtml = """
        <div class="team-card">
            <div class="card-avatar">
                <img src="${user.imageUrl.ifEmpty { "https://via.placeholder.com/400x300" }}" alt="${user.name}">
            </div>
            <div class="card-body">
                <h3>${user.name}</h3>
                <p class="role">${user.skills.getOrNull(0) ?: "Membro do Time"}</p>
                <div class="social-links">
                    <i class="fab fa-github"></i>
                    <i class="fab fa-linkedin-in"></i>
                </div>
            </div>
        </div>
        """

            desktopContainer?.innerHTML += cardHtml
            mobileContainer?.innerHTML += """<div class="swiper-slide">$cardHtml</div>"""
        }

        initSwiperAfterRender()
    }

    private fun initSwiperAfterRender() {
        js("""
    setTimeout(function() {
        if (typeof initSwiper === 'function') {
            initSwiper();
        } else if (window.Swiper && document.querySelector('.teamSwiper')) {
            new Swiper('.teamSwiper', {
                slidesPerView: 'auto',
                centeredSlides: true,
                spaceBetween: 20,
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },
                breakpoints: {
                    769: {
                        enabled: false
                    }
                }
            });
        }
    }, 100);
    """)
    }
}

