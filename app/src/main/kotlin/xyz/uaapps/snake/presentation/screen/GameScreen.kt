package xyz.uaapps.snake.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import xyz.uaapps.snake.domain.game.GameEngine
import xyz.uaapps.snake.presentation.component.Board

@Composable
fun GameScreen(gameEngine: GameEngine, onSizeInit: (Int, Int) -> Unit) {
    val state = gameEngine.state.collectAsState(initial = null)
    state.value?.let { Board(it, onSizeInit) }
}