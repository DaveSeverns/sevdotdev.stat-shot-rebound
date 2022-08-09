package sevdotdev.dao.matches

import com.google.gson.annotations.SerializedName
import org.jetbrains.exposed.dao.id.UUIDTable

object MatchTable : UUIDTable() {
    @SerializedName("arena")
    val arena = text("arena").nullable()

    @SerializedName("current_period")
    val currentPeriod = text(name = "current_period").nullable()

    @SerializedName("custom_mercy_rule")
    val customMercyRule = text(name = "custom_mercy_rule").nullable()

    @SerializedName("periods_enabled")
    val periodsEnabled = text(name = "periods_enabled").nullable()
    val homeScore = integer(name = "home_score").nullable()
    val awayScore = integer(name = "away_score").nullable()

    @SerializedName("type")
    val type = text(name = "type").nullable()

    @SerializedName("winner")
    val winner = text(name = "winner").nullable()

    @SerializedName("timestamp")
    val timestamp = long(name = "timestamp").nullable()
}