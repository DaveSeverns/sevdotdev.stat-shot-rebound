package sevdotdev.diskutils

import com.google.gson.Gson
import io.ktor.application.*
import sevdotdev.dao.stats.StatsDao
import sevdotdev.model.Match
import java.io.File
import java.util.*

fun Application.readFromDisk(dao: StatsDao, directoryPath: String) {
    val games = mutableListOf<Match>()
    val gson = Gson()
    val dir = File(directoryPath)
    dir.walk().forEach { file ->
        if (file.name.contains(".json")) {
            val jsonString = file.bufferedReader().use {
                it.readText()
            }
            games.add(gson.fromJson(jsonString, Match::class.java))
            println(file)
        }

    }
    games.forEach {
        val playerId = "5036"
        it.players?.let { players ->
            val playerToWatch = players.first { player ->
                player.gameUserId == playerId
            }
            playerToWatch?.stats?.let { stats ->
                dao.create(stats, UUID.randomUUID())
            }

        }

    }
}