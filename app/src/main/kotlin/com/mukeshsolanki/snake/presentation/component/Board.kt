package com.mukeshsolanki.snake.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mukeshsolanki.snake.data.model.State
import com.mukeshsolanki.snake.domain.game.GameEngine.Companion.BOARD_WIDTH
import com.mukeshsolanki.snake.presentation.theme.DarkGreen
import kotlin.math.floor

@Composable
fun Board(state: State, onSizeInit: (Int, Int) -> Unit) {
    BoxWithConstraints {
        val tileSize = with(LocalDensity.current) {
            floor(maxWidth.toPx() / BOARD_WIDTH).dp
        }
        with(LocalDensity.current) {
            val w = maxWidth.toPx()
            val h = maxHeight.toPx()
            val s = tileSize.toPx()
            onSizeInit(floor(w / s).toInt(), floor(h / s).toInt())
        }
        Box(Modifier.size(maxWidth, maxHeight))
        Box(
            Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(DarkGreen, CircleShape)
        )
        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(DarkGreen)
            )
        }
    }
}