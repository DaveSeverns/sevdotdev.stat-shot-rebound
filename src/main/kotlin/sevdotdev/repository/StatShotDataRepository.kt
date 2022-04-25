package sevdotdev.repository

import jdk.jfr.internal.LogLevel
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
import sevdotdev.model.Stats
import sevdotdev.model.combineStats
import java.util.UUID
import java.util.logging.Level
import java.util.logging.Logger

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
        return true
    }

    fun readAllMatches(): List<Match> {
        return matchDao.getAll().map {
            fillMatchDetails(it)
        }
    }

    fun readMatchesByPlayer(playerId: String): List<Match> {
        val matchIds = playerInMatchDao.getByPlayerId(playerId)
        return try {
            matchIds.map {
                val match = matchDao.get(it.matchId!!)
                fillMatchDetails(match!!)
            }

        } catch (e: java.lang.NullPointerException) {
            Logger.getGlobal().log(Level.WARNING, e.message)
            emptyList()
        }
    }

    private fun fillMatchDetails(match: Match): Match {
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
        return match.copy(players = players)
    }

    fun getPlayerStatsById(playerId: String): Stats {
        return statsDao.getStatsByPlayer(playerId).combineStats()
    }
}