package xyz.uaapps.snake.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.uaapps.snake.R
import xyz.uaapps.snake.data.model.HighScore
import xyz.uaapps.snake.domain.base.DATASTORE_KEY_HIGH_SCORES
import xyz.uaapps.snake.domain.base.DATASTORE_KEY_PLAYER_NAME
import xyz.uaapps.snake.domain.base.DATASTORE_NAME

class GameCache(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)
        val HIGH_SCORES_KEY = stringPreferencesKey(DATASTORE_KEY_HIGH_SCORES)
        val PLAYER_NAME_KEY = stringPreferencesKey(DATASTORE_KEY_PLAYER_NAME)
    }

    val getHighScores: Flow<List<HighScore>> = context.dataStore.data.map { preferences ->
        val scores = preferences[HIGH_SCORES_KEY].orEmpty()
        scores.split("\n").flatMap { line ->
            val fields = line.split("\t")
            if (fields.size == 2) listOf(HighScore(fields[0], fields[1].toInt()))
            else listOf()
        }
    }

    val getPlayerName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PLAYER_NAME_KEY] ?: context.getString(R.string.default_player_name)
    }

    suspend fun saveHighScore(highScores: List<HighScore>) {
        context.dataStore.edit { preferences ->
            preferences[HIGH_SCORES_KEY] = highScores.joinToString("\n") { obj ->
                "${obj.playerName}\t${obj.score}"
            }
        }
    }

    suspend fun savePlayerName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_NAME_KEY] = name
        }
    }
}