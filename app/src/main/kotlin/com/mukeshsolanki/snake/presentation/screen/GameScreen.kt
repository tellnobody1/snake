package com.mukeshsolanki.snake.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.mukeshsolanki.snake.domain.game.GameEngine
import com.mukeshsolanki.snake.presentation.activity.GameActivity
import com.mukeshsolanki.snake.presentation.component.Board

@Composable
fun GameScreen(gameEngine: GameEngine, score: Int, onSizeInit: (Int, Int) -> Unit) {
    val state = gameEngine.state.collectAsState(initial = null)
    val activity = LocalContext.current as GameActivity
    state.value?.let { Board(it, onSizeInit) }
//    AppBar(
//        title = stringResource(id = R.string.your_score, score),
//        onBackClicked = { activity.finish() }) { contentPadding ->
//        Column(
//            modifier = Modifier.padding(contentPadding),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            state.value?.let { Board(it) }
//            Controller {
//                when (it) {
//                    SnakeDirection.Up -> gameEngine.move = Pair(0, -1)
//                    SnakeDirection.Left -> gameEngine.move = Pair(-1, 0)
//                    SnakeDirection.Right -> gameEngine.move = Pair(1, 0)
//                    SnakeDirection.Down -> gameEngine.move = Pair(0, 1)
//                }
//            }
//        }
//    }
}