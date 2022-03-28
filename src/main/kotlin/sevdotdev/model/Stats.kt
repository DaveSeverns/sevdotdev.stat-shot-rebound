package sevdotdev.model


import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Stats(
    @SerializedName("assists")
    val assists: Double = 0.0,
    @SerializedName("blocks")
    val blocks: Double = 0.0,
    @SerializedName("conceded_goals")
    val concededGoals: Double = 0.0,
    @SerializedName("contributed_goals")
    val contributedGoals: Double = 0.0,
    @SerializedName("faceoffs_lost")
    val faceoffsLost: Double = 0.0,
    @SerializedName("faceoffs_won")
    val faceoffsWon: Double = 0.0,
    @SerializedName("game_winning_goals")
    val gameWinningGoals: Double = 0.0,
    @SerializedName("games_played")
    val gamesPlayed: Double = 0.0,
    @SerializedName("goals")
    val goals: Double = 0.0,
    @SerializedName("losses")
    val losses: Double = 0.0,
    @SerializedName("passes")
    val passes: Double = 0.0,
    @SerializedName("possession_time_sec")
    val possessionTimeSec: Double = 0.0,
    @SerializedName("post_hits")
    val postHits: Double = 0.0,
    @SerializedName("primary_assists")
    val primaryAssists: Double = 0.0,
    @SerializedName("saves")
    val saves: Double = 0.0,
    @SerializedName("score")
    val score: Double = 0.0,
    @SerializedName("shots")
    val shots: Double = 0.0,
    @SerializedName("shutouts")
    val shutouts: Double = 0.0,
    @SerializedName("shutouts_against")
    val shutoutsAgainst: Double = 0.0,
    @SerializedName("takeaways")
    val takeaways: Double = 0.0,
    @SerializedName("turnovers")
    val turnovers: Double = 0.0,
    @SerializedName("wins")
    val wins: Double = 0.0,
    @SerializedName("player_id")
    val playerId: String? = null,
    @SerializedName("match_id")
    val matchId: UUID? = null
) {
    fun associateIds(playerId: String, matchId: UUID): Stats {
        return this.copy(playerId = playerId, matchId = matchId)
    }
}