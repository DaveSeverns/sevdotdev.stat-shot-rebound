package sevdotdev.dao.players

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import sevdotdev.dao.ExposedDao
import sevdotdev.model.Player

class PlayerTableDao(database: Database): ExposedDao<Player, String, PlayerTable>(database) {
    override val table: PlayerTable
        get() = PlayerTable
    override fun create(entity: Player, id: String?) = transaction(database){
        entity.gameUserId?.let {
            if (get(it) != null) return@transaction
            table.insert {
                it[table.gameUserId] = entity.gameUserId
                it[table.username] = entity.username
            }
        }

    }

    override fun delete(id: String) = transaction(database) {
        table.deleteWhere {
            PlayerTable.id eq id
        }
        true
    }

    override fun get(id: String): Player? = transaction(database) {
        table.select{
            table.id eq id
        }.map {
            rowToObject(it)
        }.singleOrNull()
    }



    override fun rowToObject(row: ResultRow): Player {
        return Player(
            gameUserId = row[table.gameUserId].value,
            username = row[table.username],
            stats = null,
            team = null
        )
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}