package xyz.uaapps.snake.presentation.activity

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.uaapps.snake.R
import xyz.uaapps.snake.data.cache.GameCache
import xyz.uaapps.snake.data.model.HighScore
import xyz.uaapps.snake.domain.base.BaseActivity
import xyz.uaapps.snake.domain.base.TOP_10
import xyz.uaapps.snake.domain.game.Direction
import xyz.uaapps.snake.domain.game.GameEngine
import xyz.uaapps.snake.presentation.screen.EndScreen
import xyz.uaapps.snake.presentation.screen.GameScreen

class GameActivity : BaseActivity() {
    private lateinit var dataStore: GameCache
    private val isPlaying = mutableStateOf(true)
    private var score = mutableIntStateOf(0)
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
        onFoodEaten = { score.intValue++ },
    )

    @Composable
    override fun Content() {
        scope = rememberCoroutineScope()
        dataStore = GameCache(applicationContext)
        playerName =
            dataStore.getPlayerName.collectAsState(initial = stringResource(R.string.default_player_name)).value
        highScores = dataStore.getHighScores.collectAsState(initial = listOf()).value.plus(
            HighScore(playerName, score.intValue)
        ).sortedByDescending { it.score }.take(TOP_10)

        Column {
            if (isPlaying.value) {
                GameScreen(gameEngine) { w, h ->
                    gameEngine.boardWidth = w
                    gameEngine.boardHeight = h
                }
            } else {
                EndScreen(score.intValue) {
                    score.intValue = 0
                    gameEngine.reset()
                    isPlaying.value = true
                }
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val x = super.onKeyUp(keyCode, event)
        if (x) {
            return x
        } else {
            return when (keyCode) {
                KeyEvent.KEYCODE_DPAD_UP -> {
                    gameEngine.addMove(Direction.UP)
                    true
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    gameEngine.addMove(Direction.RIGHT)
                    true
                }
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    gameEngine.addMove(Direction.DOWN)
                    true
                }
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    gameEngine.addMove(Direction.LEFT)
                    true
                }
                else -> false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        gameEngine.paused = true
    }

    override fun onStop() {
        super.onStop()
        gameEngine.paused = true
    }

    override fun onResume() {
        super.onResume()
        gameEngine.paused = false
    }

    override fun onStart() {
        super.onStart()
        gameEngine.paused = false
    }
}
