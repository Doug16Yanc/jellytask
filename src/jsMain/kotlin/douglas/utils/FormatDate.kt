package douglas.utils

fun formatDate(dateString: String): String {
    val parts = dateString.split("-")
    if (parts.size == 3) {
        return "${parts[2]}/${parts[1]}/${parts[0]}"
    }
    return dateString
}
