package sevdotdev.dao.matches

import org.jetbrains.exposed.dao.id.UUIDTable

object PlayerInMatchTable: UUIDTable() {
    val matchId = uuid("matchId").nullable()
    val playerId = text("playerId").nullable()
    val team = text("team").nullable()
}