package com.mukeshsolanki.snake.presentation.activity

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.mukeshsolanki.snake.R
import com.mukeshsolanki.snake.data.cache.GameCache
import com.mukeshsolanki.snake.data.model.HighScore
import com.mukeshsolanki.snake.domain.base.BaseActivity
import com.mukeshsolanki.snake.domain.base.TOP_10
import com.mukeshsolanki.snake.domain.game.GameEngine
import com.mukeshsolanki.snake.presentation.screen.EndScreen
import com.mukeshsolanki.snake.presentation.screen.GameScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GameActivity : BaseActivity() {
    private lateinit var dataStore: GameCache
    private val isPlaying = mutableStateOf(true)
    private var score = mutableStateOf(0)
    private lateinit var scope: CoroutineScope
    private lateinit var playerName: String
    private lateinit var highScores: List<HighScore>
    private val gameEngine: GameEngine = GameEngine(
        scope = lifecycleScope,
        onGameEnded = {
            if (isPlaying.value) {
                isPlaying.value = false
                scope.launch { dataStore.saveHighScore(highScores) }
            }
        },
        onFoodEaten = { score.value++ },
    )

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val x = super.onKeyUp(keyCode, event)
        if (x) {
            return x
        } else {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                return if (gameEngine.move != Pair(0, -1) && gameEngine.move != Pair(0, 1)) {
                    gameEngine.move = Pair(0, -1)
                    true
                } else {
                    false
                }
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                return if (gameEngine.move != Pair(1, 0) && gameEngine.move != Pair(-1, 0)) {
                    gameEngine.move = Pair(1, 0)
                    true
                } else {
                    false
                }
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                return if (gameEngine.move != Pair(0, -1) && gameEngine.move != Pair(0, 1)) {
                    gameEngine.move = Pair(0, 1)
                    true
                } else {
                    false
                }
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                return if (gameEngine.move != Pair(1, 0) && gameEngine.move != Pair(-1, 0)) {
                    gameEngine.move = Pair(-1, 0)
                    true
                } else {
                    false
                }
            } else {
                return false
            }
        }
    }

    @Composable
    override fun Content() {
        scope = rememberCoroutineScope()
        dataStore = GameCache(applicationContext)
        playerName =
            dataStore.getPlayerName.collectAsState(initial = stringResource(id = R.string.default_player_name)).value
        highScores = dataStore.getHighScores.collectAsState(initial = listOf()).value.plus(
            HighScore(playerName, score.value)
        ).sortedByDescending { it.score }.take(TOP_10)

        Column {
            if (isPlaying.value) {
                GameScreen(gameEngine, score.value, onSizeInit = { w, h -> gameEngine.setSize(w, h) })
            } else {
                EndScreen(score.value) {
                    score.value = 0
                    gameEngine.reset()
                    isPlaying.value = true
                }
            }
        }
    }
}