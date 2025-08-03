package douglas.utils

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLParagraphElement

fun showAlertDialog(message: String) {
    document.getElementById("alert-dialog")?.remove()

    val overlay = document.createElement("div") as HTMLDivElement
        overlay.id = "alert-dialog"
        overlay.style.position = "fixed"
        overlay.style.top = "0"
        overlay.style.left = "0"
        overlay.style.width = "100vw"
        overlay.style.height = "100vh"
        overlay.style.backgroundColor = "rgba(0,0,0,0.6)"
        overlay.style.display = "flex"
        overlay.style.alignItems = "center"
        overlay.style.justifyContent = "center"
        overlay.style.zIndex = "100000"

    val dialog = document.createElement("div") as HTMLDivElement
        dialog.style.backgroundColor = "var(--bg-transparent-dark)"
        dialog.style.color = "var(--text-light)"
        dialog.style.padding = "24px"
        dialog.style.borderRadius = "12px"
        dialog.style.maxWidth = "500px"
        dialog.style.width = "90%"
        dialog.style.boxShadow = "0 4px 12px var(--box-shadow)"
        dialog.style.textAlign = "center"
        dialog.style.fontSize = "1.7rem"
        dialog.style.position = "relative"
        dialog.style.transition = "all 0.3s ease-in-out"
        dialog.style.transform = "scale(0.95)"
        dialog.style.opacity = "0"


    val messageElem = document.createElement("p") as HTMLParagraphElement
        messageElem.textContent = message
        messageElem.style.marginBottom = "20px"

    val button = document.createElement("button") as HTMLElement
        button.textContent = "OK"
        button.style.backgroundColor = "var(--primary-purple)"
        button.style.color = "var(--text-white)"
        button.style.border = "none"
        button.style.fontSize = "1.6rem"
        button.style.padding = "10px 20px"
        button.style.borderRadius = "8px"
        button.style.cursor = "pointer"
        button.addEventListener("click", {
            overlay.remove()
        })


    dialog.appendChild(messageElem)
    dialog.appendChild(button)
    overlay.appendChild(dialog)
    document.body?.appendChild(overlay)

    window.requestAnimationFrame {
        dialog.style.transform = "scale(1)"
        dialog.style.opacity = "1"
    }

}
