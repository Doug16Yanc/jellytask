package douglas

import douglas.viewmodel.TaskViewModel
import douglas.viewmodel.UserViewModel
import kotlinx.browser.document
import kotlin.js.Date
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
fun main() {
    document.addEventListener("DOMContentLoaded", { _ ->
        setupResponsiveSidebar()
        setupCurrentDate()
        setupMobileMenu()

        UserViewModel().loadUserProfile(1)
        UserViewModel().loadUsers()
        TaskViewModel().renderTasks()
    })
}

fun setupResponsiveSidebar() {
    val sidebar = document.querySelector(".sidebar") as? HTMLElement ?: return

    if (window.innerWidth < 768) {
        sidebar.style.display = "none"
    }

    window.addEventListener("resize", {
        sidebar.style.display = if (window.innerWidth < 768) "none" else "block"
    })
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

/*
fun setupMobileMenu() {
    val sidebar = document.querySelector(".sidebar") as? HTMLElement ?: return
    val overlay = document.querySelector(".sidebar-overlay") as? HTMLElement ?: return
    val toggle = document.querySelector(".mobile-menu-toggle") as? HTMLElement ?: return

    sidebar.classList.add("mobile-ready")

    // Fecha tudo ao carregar (garantia)
    sidebar.classList.remove("active")
    overlay.classList.remove("active")

    toggle.addEventListener("click", { _ ->
        sidebar.classList.toggle("active")
        overlay.classList.toggle("active")

        // Bloqueia scroll do body quando menu aberto
        document.body?.style?.overflowWrap = if (sidebar.classList.contains("active")) "hidden" else "auto"
    })

    overlay.addEventListener("click", { _ ->
        sidebar.classList.remove("active")
        overlay.classList.remove("active")
        document.body?.style?.overflowWrap = "auto"
    })
} */


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