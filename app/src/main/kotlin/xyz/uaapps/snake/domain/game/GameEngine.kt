package xyz.uaapps.snake.domain.game

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.uaapps.snake.data.model.SnakePos
import xyz.uaapps.snake.data.model.State
import java.util.Random

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
    var boardWidth = BOARD_WIDTH
    var boardHeight = BOARD_WIDTH
    var paused = false

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
                if (paused) continue
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
                            nextPosition(poz)
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
                        snake = if (newPosition == it.food) moveSnake(
                            nextPosition(newPosition),
                            moveSnake(newPosition, it.snake, snakeLength),
                            snakeLength)
                        else moveSnake(newPosition, it.snake, snakeLength),
                        currentDirection = currentDirection.value,
                    )
                }
            }
        }
    }

    private fun moveSnake(
        newPosition: Pair<Int, Int>,
        snake: SnakePos,
        snakeLength: Int
    ) = listOf(newPosition) + snake.take(snakeLength - 1)

    private fun nextPosition(poz: Pair<Int, Int>) = Pair(
        (poz.first + move.first + boardWidth) % boardWidth,
        (poz.second + move.second + boardHeight) % boardHeight
    )

    companion object {
        const val BOARD_WIDTH = 32
    }
}
