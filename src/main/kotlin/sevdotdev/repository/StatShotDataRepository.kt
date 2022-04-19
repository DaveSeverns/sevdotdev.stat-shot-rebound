package sevdotdev.repository

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import sevdotdev.dao.anyAs
import sevdotdev.dao.matches.MatchDao
import sevdotdev.dao.matches.PlayerInMatchDao
import sevdotdev.dao.players.PlayerTableDao
import sevdotdev.dao.stats.StatsDao
import sevdotdev.dao.stats.StatsTable
import sevdotdev.dto.PlayerInMatchDto
import sevdotdev.model.Match
import sevdotdev.model.Player
import java.util.UUID

class StatShotDataRepository(
    val matchDao: MatchDao,
    val playerInMatchDao: PlayerInMatchDao,
    val playerDao: PlayerTableDao,
    val statsDao: StatsDao
) {

    init {
        matchDao.init()
        playerInMatchDao.init()
        playerDao.init()
        statsDao.init()
    }

    fun saveMatches(matches: List<Match>): Boolean {
        matches.forEach { match ->
            val matchId = matchDao.create(match).anyAs<UUID>()
            matchId?.let { idOfMatch ->
                match.players?.forEach { player ->
                    player?.let {
                        playerInMatchDao.create(PlayerInMatchDto(idOfMatch, player.gameUserId, player.team))
                        playerDao.create(player)
                        statsDao.createWithMetaData(player.stats!!, player.gameUserId!!, idOfMatch)
                    }
                }
            }

        }
        return  true
    }

    fun readMatches(): List<Match> {
        val matches = matchDao.getAll().map { match ->
            val players = mutableListOf<Player>()
            val id = match.id
            id?.let {
                val playersInMatch = playerInMatchDao.getByMatchId(id)
                playersInMatch.forEach { pIM ->
                    val stats = statsDao.query {
                        StatsTable.select {
                            (StatsTable.playerId eq pIM.playerId) and (StatsTable.matchId eq pIM.matchId)
                        }.map {
                            statsDao.rowToObject(it)
                        }.singleOrNull()
                    }
                    val playerToAdd = playerDao.get(pIM.playerId!!)?.copy(stats = stats, team = pIM.team)
                    playerToAdd?.let { players.add(playerToAdd) }
                }
            }
            match.copy(players = players)
        }
        return matches
    }
}