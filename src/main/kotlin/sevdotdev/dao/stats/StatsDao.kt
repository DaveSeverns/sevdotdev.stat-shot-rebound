package sevdotdev.dao.stats

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import sevdotdev.dao.ExposedDao
import sevdotdev.model.Stats
import java.util.*

class StatsDao(database: Database) : ExposedDao<Stats, UUID, StatsTable>(database) {

    override val table: StatsTable
        get() = StatsTable

    override fun create(stats: Stats, id: UUID?) = transaction(database) {
        table.insert {
            it[StatsTable.playerId] = stats.playerId
            it[StatsTable.matchId] = stats.matchId
            it[StatsTable.assists] = stats.assists
            it[StatsTable.blocks] = stats.blocks
            it[StatsTable.concededGoals] = stats.concededGoals
            it[StatsTable.contributedGoals] = stats.contributedGoals
            it[StatsTable.faceoffsLost] = stats.faceoffsLost
            it[StatsTable.faceoffsWon] = stats.faceoffsWon
            it[StatsTable.gameWinningGoals] = stats.gameWinningGoals
            it[StatsTable.gamesPlayed] = stats.gamesPlayed
            it[StatsTable.goals] = stats.goals
            it[StatsTable.losses] = stats.losses
            it[StatsTable.passes] = stats.passes
            it[StatsTable.possessionTimeSec] = stats.possessionTimeSec
            it[StatsTable.postHits] = stats.postHits
            it[StatsTable.primaryAssists] = stats.primaryAssists
            it[StatsTable.saves] = stats.saves
            it[StatsTable.score] = stats.score
            it[StatsTable.shots] = stats.shots
            it[StatsTable.shutouts] = stats.shutouts
            it[StatsTable.shutoutsAgainst] = stats.shutoutsAgainst
            it[StatsTable.takeaways] = stats.takeaways
            it[StatsTable.turnovers] = stats.turnovers
            it[StatsTable.wins] = stats.wins
        }
        Unit
    }

    fun createWithMetaData(stats: Stats, playerId: String, matchId: UUID) {
        transaction(database) {
            table.insert {
                it[StatsTable.playerId] = playerId
                it[StatsTable.matchId] = matchId
                it[StatsTable.assists] = stats.assists
                it[StatsTable.blocks] = stats.blocks
                it[StatsTable.concededGoals] = stats.concededGoals
                it[StatsTable.contributedGoals] = stats.contributedGoals
                it[StatsTable.faceoffsLost] = stats.faceoffsLost
                it[StatsTable.faceoffsWon] = stats.faceoffsWon
                it[StatsTable.gameWinningGoals] = stats.gameWinningGoals
                it[StatsTable.gamesPlayed] = stats.gamesPlayed
                it[StatsTable.goals] = stats.goals
                it[StatsTable.losses] = stats.losses
                it[StatsTable.passes] = stats.passes
                it[StatsTable.possessionTimeSec] = stats.possessionTimeSec
                it[StatsTable.postHits] = stats.postHits
                it[StatsTable.primaryAssists] = stats.primaryAssists
                it[StatsTable.saves] = stats.saves
                it[StatsTable.score] = stats.score
                it[StatsTable.shots] = stats.shots
                it[StatsTable.shutouts] = stats.shutouts
                it[StatsTable.shutoutsAgainst] = stats.shutoutsAgainst
                it[StatsTable.takeaways] = stats.takeaways
                it[StatsTable.turnovers] = stats.turnovers
                it[StatsTable.wins] = stats.wins
            }
        }
    }

    override fun delete(id: UUID) = transaction(database) {
        table.deleteWhere {
            StatsTable.id eq id
        }
        true
    }

    override fun get(id: UUID): Stats? = transaction(database) {
        val exp = StatsTable.id eq id
        table.select {
            exp
        }.map {
            rowToObject(row = it)
        }.singleOrNull()
    }

    override fun close() {

    }

    override fun rowToObject(row: ResultRow): Stats =
        Stats(
            assists = row[StatsTable.assists],
            blocks = row[StatsTable.blocks],
            concededGoals = row[StatsTable.concededGoals],
            contributedGoals = row[StatsTable.contributedGoals],
            faceoffsLost = row[StatsTable.faceoffsLost],
            faceoffsWon = row[StatsTable.faceoffsWon],
            gameWinningGoals = row[StatsTable.gameWinningGoals],
            gamesPlayed = row[StatsTable.gamesPlayed],
            goals = row[StatsTable.goals],
            losses = row[StatsTable.losses],
            passes = row[StatsTable.passes],
            possessionTimeSec = row[StatsTable.possessionTimeSec],
            postHits = row[StatsTable.postHits],
            primaryAssists = row[StatsTable.primaryAssists],
            saves = row[StatsTable.saves],
            score = row[StatsTable.score],
            shots = row[StatsTable.shots],
            shutouts = row[StatsTable.shutouts],
            shutoutsAgainst = row[StatsTable.shutoutsAgainst],
            takeaways = row[StatsTable.takeaways],
            turnovers = row[StatsTable.turnovers],
            wins = row[StatsTable.wins],
        )

    fun query(transaction: () -> Stats?): Stats? = transaction(database) {
        transaction.invoke()
    }
}