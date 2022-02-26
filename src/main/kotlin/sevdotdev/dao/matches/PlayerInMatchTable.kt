package sevdotdev.dao.matches

import org.jetbrains.exposed.dao.id.UUIDTable

object PlayerInMatchTable: UUIDTable() {
    val matchId = uuid("matchId")
    val playerId = text("playerId")
    val team = text("team").nullable()
}