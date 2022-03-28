package sevdotdev.dto

import java.util.*

data class PlayerInMatchDto(
    val matchId: UUID?,
    val playerId: String?,
    val team: String?
)
