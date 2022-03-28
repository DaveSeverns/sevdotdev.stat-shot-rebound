package sevdotdev.dao

inline fun <reified T> Any.anyAs(): T? {
    return if (this is T) {
        this
    } else null
}