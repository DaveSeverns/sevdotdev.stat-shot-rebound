package sevdotdev.diskutils

import com.google.gson.Gson
import io.ktor.application.Application
import sevdotdev.model.Match
import sevdotdev.repository.StatShotDataRepository
import java.io.File

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
                games.add(gameToAdd.copy(timestamp = file.lastModified()))
            }
            println(file)
        }

    }
    repository.saveMatches(games)
}
