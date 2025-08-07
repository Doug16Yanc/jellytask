package douglas.viewmodel

import douglas.service.UserService
import douglas.utils.JSONUtils
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement

class LoginViewModel {
    private val userService = UserService()
    private val currentUserKey = "current_user"

    fun setupLoginPage() {
        setupPasswordToggle()
        setupLoginForm()
    }

    private fun setupPasswordToggle() {
        val togglePassword = document.getElementById("togglePassword")
        val passwordInput = document.getElementById("password") as? HTMLInputElement

        togglePassword?.addEventListener("click", {
            if (passwordInput != null) {
                val type = if (passwordInput.type == "password") "text" else "password"
                passwordInput.type = type

                // Atualizar ícone
                val icon = togglePassword.querySelector("i")
                icon?.classList?.apply {
                    if (contains("fa-eye")) {
                        remove("fa-eye")
                        add("fa-eye-slash")
                    } else {
                        remove("fa-eye-slash")
                        add("fa-eye")
                    }
                }
            }
        })
    }

    private fun setupLoginForm() {
        val loginForm = document.querySelector("form")
        loginForm?.addEventListener("submit", { event ->
            event.preventDefault()
            handleLogin()
        })
    }

    private fun handleLogin() {
        val emailInput = document.getElementById("email") as? HTMLInputElement
        val passwordInput = document.getElementById("password") as? HTMLInputElement
        val loginButton = document.querySelector(".btn-login") as? HTMLButtonElement

        val email = emailInput?.value ?: ""
        val password = passwordInput?.value ?: ""

        if (email.isEmpty() || password.isEmpty()) {
            window.alert("Por favor, preencha todos os campos.")
            return
        }

        // Mostrar estado de carregamento
        loginButton?.innerHTML = "<i class=\"fas fa-spinner fa-spin\"></i> Entrando..."
        loginButton?.disabled = true

        window.setTimeout({
            val user = userService.login(email, password)

            if (user != null) {
                localStorage.setItem("current_user", JSONUtils.userToJson(user))
                window.location.href = "/pages/home.html"
            } else {
                // Tratamento de erro
            }
        }, 500)
    }
}