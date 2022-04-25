package sevdotdev.dao.matches

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import sevdotdev.dao.ExposedDao
import sevdotdev.dto.PlayerInMatchDto
import java.util.*

class PlayerInMatchDao(database: Database) : ExposedDao<PlayerInMatchDto, UUID, PlayerInMatchTable>(database) {
    override val table: PlayerInMatchTable
        get() = PlayerInMatchTable

    override fun create(entity: PlayerInMatchDto, id: UUID?): Any? = transaction(database) {
        table.insert {
            it[table.matchId] = entity.matchId
            it[table.playerId] = entity.playerId
            it[table.team] = entity.team
        }
    }

    override fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(id: UUID): PlayerInMatchDto? {
        TODO("Not yet implemented")
    }

    override fun rowToObject(row: ResultRow): PlayerInMatchDto =
        PlayerInMatchDto(
            matchId = row[table.matchId],
            playerId = row[table.playerId],
            team = row[table.team]
        )


    override fun close() {
        TODO("Not yet implemented")
    }

    fun getByMatchId(matchId: UUID): List<PlayerInMatchDto> = transaction(database) {
        table.select {
            table.matchId eq matchId
        }.map {
            rowToObject(it)
        }
    }

    fun getByPlayerId(playerId: String): List<PlayerInMatchDto> = transaction(database) {
        table.select {
            table.playerId eq playerId
        }.map {
            rowToObject(it)
        }
    }


}