package sevdotdev.model


import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("away")
    val away: Int = 0,
    @SerializedName("home")
    val home: Int = 0
)