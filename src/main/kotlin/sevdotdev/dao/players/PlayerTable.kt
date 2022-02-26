package sevdotdev.dao.players

import com.google.gson.annotations.SerializedName
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object PlayerTable : IdTable<String>() {
    @SerializedName("game_user_id")
    val gameUserId = text(name = "game_user_id").entityId()

    @SerializedName("username")
    val username = text(name = "username").nullable()
    override val id: Column<EntityID<String>>
        get() = gameUserId
}