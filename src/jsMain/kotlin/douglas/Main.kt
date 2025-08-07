package douglas

import douglas.viewmodel.DashboardViewModel
import douglas.viewmodel.LoginViewModel
import douglas.viewmodel.TaskViewModel
import douglas.viewmodel.UserViewModel
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import kotlin.js.Date

fun main() {
    document.addEventListener("DOMContentLoaded", { _ ->
        when {
            window.location.pathname.contains("/") ||
                    window.location.pathname == "/pages/home.html" -> {
                LoginViewModel().setupLoginPage()
            }
        }

        setupCurrentDate()
        setupMobileMenu()
        setupLogoutBtn()

        when {
            window.location.pathname.contains("/pages/home.html") -> {
                UserViewModel().initPages()
                UserViewModel().loadUserProfile()

                TaskViewModel().renderTasks()
            }
        }
        when {
            window.location.pathname.contains("/pages/team.html") -> {
                UserViewModel().initPages()

            }
        }


        val dashboardVM = DashboardViewModel()
        dashboardVM.renderDashboard()

        val taskVM = TaskViewModel()


        window.setTimeout({
            taskVM.setupSortableColumns()
        }, 100)
    })
}

fun setupLogoutBtn() {
    val nodeList = document.querySelectorAll(".logout-btn")
    val btnsList: List<HTMLElement> = nodeList.asList().map { it as HTMLElement }
    btnsList.forEach { btn ->
        btn.addEventListener("click", {
            localStorage.removeItem("current_user")
            window.location.href = "/index.html"
        })
    }
}

fun setupCurrentDate() {
    val daysOfWeek = arrayOf(
        "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira",
        "Quinta-feira", "Sexta-feira", "Sábado"
    )

    val now = Date()
    val weekday = daysOfWeek[now.getDay()]
    val dateFormatted = "${now.getDate().toString().padStart(2, '0')}/" +
            "${(now.getMonth() + 1).toString().padStart(2, '0')}/" +
            "${now.getFullYear()}"

    document.getElementById("weekday")?.textContent = weekday
    document.getElementById("today")?.textContent = dateFormatted
}

fun setupMobileMenu() {
    val sidebar = document.querySelector(".sidebar") as? HTMLElement ?: return
    val overlay = document.querySelector(".sidebar-overlay") as? HTMLElement ?: return
    val toggle = document.querySelector(".mobile-menu-toggle") as? HTMLElement ?: return

    toggle.addEventListener("click", { _ ->
        sidebar.classList.toggle("active")
        overlay.classList.toggle("active")

        if (sidebar.classList.contains("active")) {
            sidebar.style.display = "block"
        }
    })

    overlay.addEventListener("click", { _ ->
        sidebar.classList.remove("active")
        overlay.classList.remove("active")
    })
}