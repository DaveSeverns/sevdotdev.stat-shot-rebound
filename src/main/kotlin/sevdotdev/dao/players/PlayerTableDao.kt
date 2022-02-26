package sevdotdev.dao.players

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import sevdotdev.dao.ExposedDao
import sevdotdev.model.Player

class PlayerTableDao(database: Database): ExposedDao<Player, String, PlayerTable>(database) {
    override val table: PlayerTable
        get() = PlayerTable
    override fun create(entity: Player, id: String?) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        table.deleteWhere {
            PlayerTable.id eq id
        }
        val function = table.select{
            table.id eq id
        }.map {
            rowToObject(it)
        }.singleOrNull()
    }

    private fun passFunction(function: List<Player>) {

    }

    override fun get(id: String): Player? = transaction(database) {
        table.select{
            table.id eq id
        }.map {
            rowToObject(it)
        }.singleOrNull()
    }



    override fun rowToObject(resultRow: ResultRow): Player {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}