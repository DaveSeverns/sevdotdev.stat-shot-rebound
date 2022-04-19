package sevdotdev.model

import com.google.gson.annotations.SerializedName

data class MatchJson(
    @SerializedName("matches")
    val matches: List<Match> = emptyList()
)