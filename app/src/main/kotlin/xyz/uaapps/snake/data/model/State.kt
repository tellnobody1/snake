package xyz.uaapps.snake.data.model

typealias SnakePos = List<Pair<Int, Int>>

data class State(
    val food: Pair<Int, Int>,
    val snake: SnakePos,
    val currentDirection: Int
)