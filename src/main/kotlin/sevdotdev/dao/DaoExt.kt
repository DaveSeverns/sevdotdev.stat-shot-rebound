package sevdotdev.dao

inline fun <reified T> Any.asOrNull(): T? {
    return if (this is T) {
        this
    } else null
}