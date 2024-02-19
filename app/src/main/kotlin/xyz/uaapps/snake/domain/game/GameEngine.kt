package xyz.uaapps.snake.domain.game

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
import java.util.Deque
import java.util.LinkedList
import java.util.Random
import kotlin.math.abs

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
            )
        )
    val state: Flow<State> = mutableState
    var boardWidth = BOARD_WIDTH
    var boardHeight = BOARD_WIDTH
    var paused = false

    private var move: Deque<Direction> = LinkedList(listOf())
    private var lastMove: Direction = Direction.RIGHT

    fun addMove(value: Direction) {
        scope.launch {
            mutex.withLock {
                val last = toVector(move.peekLast() ?: lastMove)
                val new = toVector(value)
                if (!(abs(last.first) == abs(new.first) && abs(last.second) == abs(new.second))) {
                    move.offer(value)
                    lastMove = value
                }
            }
        }
    }

    fun reset() {
        mutableState.update {
            it.copy(
                food = Pair(5, 5),
                snake = listOf(Pair(7, 7)),
            )
        }
        move = LinkedList(listOf())
        lastMove = Direction.RIGHT
    }

    init {
        scope.launch {
            var snakeLength = 2
            while (true) {
                delay(150)
                if (paused) continue
                mutableState.update {
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
                            mutex.withLock { nextPosition(newPosition) },
                            moveSnake(newPosition, it.snake, snakeLength),
                            snakeLength)
                        else moveSnake(newPosition, it.snake, snakeLength),
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

    private fun nextPosition(poz: Pair<Int, Int>) = run {
        val v = toVector(move.poll() ?: lastMove)
        Pair(
            (poz.first + v.first + boardWidth) % boardWidth,
            (poz.second + v.second + boardHeight) % boardHeight
        )
    }

    private fun toVector(x: Direction) = when(x) {
        Direction.RIGHT -> Pair(1, 0)
        Direction.LEFT -> Pair(-1, 0)
        Direction.UP -> Pair(0, -1)
        Direction.DOWN -> Pair(0, 1)
    }

    companion object {
        const val BOARD_WIDTH = 32
    }
}
