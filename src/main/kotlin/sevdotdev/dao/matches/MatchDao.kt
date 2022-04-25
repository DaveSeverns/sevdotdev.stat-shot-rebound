package sevdotdev.dao.matches

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import sevdotdev.dao.ExposedDao
import sevdotdev.dao.players.PlayerTable
import sevdotdev.dao.stats.StatsDao
import sevdotdev.model.Match
import sevdotdev.model.Score
import java.util.*

class MatchDao(
    database: Database,
) : ExposedDao<Match, UUID, UUIDTable>(database) {
    override val table: MatchTable
        get() = MatchTable

    override fun create(entity: Match, id: UUID?) = transaction(database) {
        val resultMatchId = table.insertAndGetId {
            it[arena] = entity.arena
            it[winner] = entity.winner
            it[homeScore] = entity.score?.home
            it[awayScore] = entity.score?.away
            it[currentPeriod] = entity.currentPeriod
            it[customMercyRule] = entity.customMercyRule
            it[periodsEnabled] = entity.periodsEnabled
            it[type] = entity.type
        }
        resultMatchId.value
    }

    override fun delete(id: UUID) = transaction(database) {
        table.deleteWhere {
            table.id eq id
        }
        true
    }

    override fun get(id: UUID): Match? = transaction(database) {
        table.select {
            table.id eq id
        }.map {
            rowToObject(it)
        }.singleOrNull()
    }


    override fun rowToObject(row: ResultRow): Match {
        return Match(
            arena = row[table.arena],
            winner = row[table.winner],
            score = Score(
                home = row[table.homeScore] ?: 0,
                away = row[table.awayScore] ?: 0
            ),
            currentPeriod = row[table.currentPeriod],
            customMercyRule = row[table.customMercyRule],
            type = row[table.type],
            periodsEnabled = row[table.periodsEnabled],
            players = emptyList(),
            id = row[table.id].value
        )
    }

    override fun close() {

    }
}