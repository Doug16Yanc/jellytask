package douglas.utils

external interface SortableOptions {
    var group: String
    var animation: Int
    var delay: Int
    var delayOnTouchOnly: Boolean
    var touchStartThreshold: Int
    var fallbackOnBody: Boolean
    var onEnd: ((dynamic) -> Unit)?
}
