package xyz.uaapps.snake.domain.game

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import xyz.uaapps.snake.data.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import kotlin.math.floor

class GameEngine(
    private val scope: CoroutineScope,
    private val onGameEnded: () -> Unit,
    private val onFoodEaten: () -> Unit,
) {
    private val mutex = Mutex()
    private val mutableState =
        MutableStateFlow(
            State(
                food = Pair(5, 5),
                snake = listOf(Pair(7, 7)),
                currentDirection = SnakeDirection.Right
            )
        )
    val state: Flow<State> = mutableState
    private val currentDirection = mutableStateOf(SnakeDirection.Right)
    private var boardWidth = BOARD_WIDTH
    private var boardHeight = BOARD_WIDTH

    fun setSize(w: Int, h: Int) {
        boardWidth = w
        boardHeight = h
    }

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    fun reset() {
        mutableState.update {
            it.copy(
                food = Pair(5, 5),
                snake = listOf(Pair(7, 7)),
                currentDirection = SnakeDirection.Right
            )
        }
        currentDirection.value = SnakeDirection.Right
        move = Pair(1, 0)
    }

    init {
        scope.launch {
            var snakeLength = 2
            while (true) {
                delay(150)
                mutableState.update {
                    if (move.first == 0 && move.second == -1) {
                        currentDirection.value = SnakeDirection.Up
                    } else if (move.first == -1 && move.second == 0) {
                        currentDirection.value = SnakeDirection.Left
                    } else if (move.first == 1 && move.second == 0) {
                        currentDirection.value = SnakeDirection.Right
                    } else if (move.first == 0 && move.second == 1) {
                        currentDirection.value = SnakeDirection.Down
                    }
                    val newPosition = it.snake.first().let { poz ->
                        mutex.withLock {
                            Pair(
                                (poz.first + move.first + boardWidth) % boardWidth,
                                (poz.second + move.second + boardHeight) % boardHeight
                            )
                        }
                    }
                    if (newPosition == it.food) {
                        onFoodEaten.invoke()
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)) {
                        snakeLength = 2
                        onGameEnded.invoke()
                    }

                    it.copy(
                        food = if (newPosition == it.food) Pair(
                            Random().nextInt(boardWidth),
                            Random().nextInt(boardHeight)
                        ) else it.food,
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1),
                        currentDirection = currentDirection.value,
                    )
                }
            }
        }
    }

    companion object {
        const val BOARD_WIDTH = 32
    }
}