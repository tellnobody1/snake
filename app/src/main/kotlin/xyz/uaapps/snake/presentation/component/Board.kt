package xyz.uaapps.snake.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.uaapps.snake.data.model.State
import xyz.uaapps.snake.presentation.theme.DarkGreen
import xyz.uaapps.snake.presentation.theme.border2dp
import xyz.uaapps.snake.presentation.theme.corner4dp
import xyz.uaapps.snake.presentation.theme.tile16dp

@Composable
fun Board(state: State, onSizeInit: (Int, Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        BoxWithConstraints {
            val w = ((maxWidth - border2dp) / tile16dp).toInt()
            val h = ((maxHeight - border2dp) / tile16dp).toInt()
            onSizeInit(w, h)
            Box(
                Modifier
                    .size(tile16dp * w + border2dp, tile16dp * h + border2dp)
                    .border(border2dp, DarkGreen)
            )
            Box(
                Modifier
                    .offset(x = tile16dp * state.food.first, y = tile16dp * state.food.second)
                    .size(tile16dp)
                    .background(DarkGreen, CircleShape)
            )
            state.snake.forEach {
                Box(
                    modifier = Modifier
                        .offset(x = tile16dp * it.first, y = tile16dp * it.second)
                        .size(tile16dp)
                        .background(DarkGreen, RoundedCornerShape(corner4dp))
                )
            }
        }
    }
}