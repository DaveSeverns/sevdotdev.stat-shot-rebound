package sevdotdev.plugins

import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import sevdotdev.dao.stats.StatsDao
import sevdotdev.repository.StatShotDataRepository

fun Application.configureSerialization(repository: StatShotDataRepository) {
    install(ContentNegotiation) {
        gson {
            }
        json()
    }

    routing {
        get("/all-matches") {
            call.respond(mapOf("matches" to repository.readMatches()))
        }
        get("/json/gson") {
                call.respond(mapOf("hello" to "world"))
            }
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
