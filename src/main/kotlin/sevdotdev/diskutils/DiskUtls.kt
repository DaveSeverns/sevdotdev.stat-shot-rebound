package sevdotdev.diskutils

import com.google.gson.Gson
import io.ktor.application.*
import org.h2.engine.Database
import sevdotdev.dao.stats.StatsDao
import sevdotdev.model.Match
import sevdotdev.repository.StatShotDataRepository
import java.io.File
import java.util.*

fun Application.readFromDisk(directoryPath: String, repository: StatShotDataRepository) {
    val games = mutableListOf<Match>()
    val gson = Gson()
    val dir = File(directoryPath)
    dir.walk().forEach { file ->
        if (file.name.contains(".json")) {
            val jsonString = file.bufferedReader().use {
                it.readText()
            }
            val gameToAdd = try {
                gson.fromJson(jsonString, Match::class.java)
            } catch (e: Exception) {
                null
            }
            gameToAdd?.let {
                games.add(gameToAdd)
            }
            println(file)
        }

    }
    repository.saveMatches(games)
}
