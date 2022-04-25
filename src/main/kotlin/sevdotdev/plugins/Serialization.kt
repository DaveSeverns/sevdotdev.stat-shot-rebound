package sevdotdev.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import sevdotdev.model.MatchJson
import sevdotdev.repository.StatShotDataRepository

fun Application.configureSerialization(repository: StatShotDataRepository) {
    install(ContentNegotiation) {
        gson {
        }
        json()
    }

    routing {
        get("/all-matches") {
            call.respond(mapOf("matches" to repository.readAllMatches()))
        }
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
        post("/add_match") {
            val body = call.receive<MatchJson>()
            repository.saveMatches(body.matches)
            call.respondText("Match Data saved", status = HttpStatusCode.OK)
        }
        get(path = "/{$PLAYER_ID}/matches") {
            val playerId = call.parameters[PLAYER_ID] ?: ""
            call.respond(
                mapOf(
                    "matches" to repository.readMatchesByPlayer(playerId)
                )
            )
        }
        get(path = "/{$PLAYER_ID}/stats") {
            val playerId = call.parameters[PLAYER_ID] ?: ""
            call.respond(
                mapOf(
                    "stats" to repository.getPlayerStatsById(playerId)
                )
            )
        }
    }
}

const val PLAYER_ID = "playerId"