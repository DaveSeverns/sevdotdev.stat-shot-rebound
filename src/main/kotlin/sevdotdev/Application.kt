package sevdotdev

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import sevdotdev.dao.matches.MatchDao
import sevdotdev.dao.matches.PlayerInMatchDao
import sevdotdev.dao.players.PlayerTableDao
import sevdotdev.dao.stats.StatsDao
import sevdotdev.diskutils.readFromDisk
import sevdotdev.plugins.configureRouting
import sevdotdev.plugins.configureSerialization
import sevdotdev.repository.StatShotDataRepository

const val DEFAULT_DB_URL = "h2:mem:test;DB_CLOSE_DELAY=-1;"
const val DEFAULT_DB_DRIVER = "org.h2.Driver"
fun main(args: Array<String>) {
    val port: Int = System.getenv("PORT")?.toInt() ?: 80
    val dbUrl: String = "jdbc:" + (System.getenv("DATABASE_URL") ?: DEFAULT_DB_URL)
    val dbDriver: String = System.getenv("JDBC_DRIVER")?: DEFAULT_DB_DRIVER
    embeddedServer(Netty, port = port) {
        val database = Database.connect(dbUrl, driver = dbDriver)
        val statsDao = StatsDao(database)

        val repository = StatShotDataRepository(
            MatchDao(database),
            PlayerInMatchDao(database),
            PlayerTableDao(database),
            statsDao
        )
        configureRouting()
        readFromDisk(extractDirectory(args), repository)
        configureSerialization(repository)
    }.start(wait = true)
}

private fun extractDirectory(args: Array<String>?): String {
    val directory: String? = args?.firstOrNull {
        it.contains("/") || it.contains("\\")
    }
    return directory ?: "./"
}
