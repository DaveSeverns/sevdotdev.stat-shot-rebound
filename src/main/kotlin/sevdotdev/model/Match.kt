package sevdotdev.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class Match(
    val id: UUID? = null,
    @SerializedName("arena")
    val arena: String?,
    @SerializedName("current_period")
    val currentPeriod: String?,
    @SerializedName("custom_mercy_rule")
    val customMercyRule: String?,
    @SerializedName("periods_enabled")
    val periodsEnabled: String?,
    @SerializedName("players")
    val players: List<Player>?,
    @SerializedName("score")
    val score: Score?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("winner")
    val winner: String?
)