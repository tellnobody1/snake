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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.uaapps.snake.data.model.State
import xyz.uaapps.snake.presentation.theme.DarkGreen
import xyz.uaapps.snake.presentation.theme.border2dp

@Composable
fun Board(state: State, onSizeInit: (Int, Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        BoxWithConstraints {
            val tileSize = 16.dp
            val w = ((maxWidth - border2dp) / tileSize).toInt()
            val h = ((maxHeight - border2dp) / tileSize).toInt()
            onSizeInit(w, h)
            Box(
                Modifier
                    .size(tileSize * w + border2dp, tileSize * h + border2dp)
                    .border(border2dp, DarkGreen)
            )
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
}