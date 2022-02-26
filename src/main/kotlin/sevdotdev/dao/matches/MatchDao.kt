package sevdotdev.dao.matches

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import sevdotdev.dao.ExposedDao
import sevdotdev.dao.players.PlayerTable
import sevdotdev.dao.stats.StatsDao
import sevdotdev.model.Match
import sevdotdev.model.Score
import java.util.*

class MatchDao(
    database: Database,
    val statsDao: StatsDao = StatsDao(database)
) : ExposedDao<Match, UUID, UUIDTable>(database) {
    override val table: MatchTable
        get() = MatchTable
    override fun create(entity: Match, id: UUID?)  = transaction(database) {
        val resultMatchId = table.insertAndGetId {
            it[arena]  = entity.arena
            it[winner] = entity.winner
            it[homeScore] = entity.score?.home
            it[awayScore] = entity.score?.away
            it[currentPeriod] = entity.currentPeriod
            it[customMercyRule] = entity.customMercyRule
            it[periodsEnabled] = entity.periodsEnabled
            it[type] = entity.type
        }
        entity.players?.forEach { activePlayer ->
            val resultPlayerId = PlayerTable.insertAndGetId {
                it[gameUserId] = activePlayer.gameUserId ?: ""
                it[username] = activePlayer.username
            }

            PlayerInMatchTable.insert {
                it[matchId] = resultMatchId.value
                it[playerId] = resultPlayerId.value
                it[team] = activePlayer.team
            }

            statsDao.createWithMetaData(activePlayer.stats!!,
            resultPlayerId.value,
            resultMatchId.value)

        }
        Unit
    }

    override fun delete(id: UUID) {

    }

    override fun get(id: UUID): Match? {
       return null
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