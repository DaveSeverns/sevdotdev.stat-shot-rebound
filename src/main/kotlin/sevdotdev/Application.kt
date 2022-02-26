package sevdotdev

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import sevdotdev.dao.stats.StatsDao
import sevdotdev.diskutils.readFromDisk
import sevdotdev.plugins.configureRouting
import sevdotdev.plugins.configureSerialization

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        val dao = StatsDao(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver"))
        dao.init()
        configureRouting()
        readFromDisk(dao, extractDirectory(args))
        configureSerialization(dao)
    }.start(wait = true)
}

private fun extractDirectory(args: Array<String>?): String {
    val directory: String? = args?.firstOrNull {
        it.contains("/") || it.contains("\\")
    }
    return directory ?: "/Users"
}
