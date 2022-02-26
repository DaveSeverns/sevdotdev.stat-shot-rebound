package sevdotdev.model


import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("game_user_id")
    val gameUserId: String?,
    @SerializedName("stats")
    val stats: Stats?,
    @SerializedName("team")
    val team: String?,
    @SerializedName("username")
    val username: String?
)