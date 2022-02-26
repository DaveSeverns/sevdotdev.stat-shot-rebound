package sevdotdev.plugins

import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import sevdotdev.dao.stats.StatsDao

fun Application.configureSerialization(dao: StatsDao) {
    install(ContentNegotiation) {
        gson {
            }
        json()
    }

    routing {
        get("/all-stats") {
            call.respond(mapOf("stats" to dao.getAll()))
        }
        get("/json/gson") {
                call.respond(mapOf("hello" to "world"))
            }
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
