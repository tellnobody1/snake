package xyz.uaapps.snake.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import xyz.uaapps.snake.R
import xyz.uaapps.snake.data.cache.GameCache
import xyz.uaapps.snake.data.model.HighScore
import xyz.uaapps.snake.domain.base.TOP_10
import xyz.uaapps.snake.presentation.component.AppBar
import xyz.uaapps.snake.presentation.theme.border2dp
import xyz.uaapps.snake.presentation.theme.padding16dp
import xyz.uaapps.snake.presentation.theme.padding8dp

@Preview
@Composable
fun HighScoreScreen() {
    val dataStore = GameCache(LocalContext.current)
    val highScores =
        dataStore.getHighScores.collectAsState(initial = listOf()).value.sortedByDescending { it.score }
            .take(TOP_10)
    AppBar(
        title = stringResource(R.string.high_score)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                )
                .border(width = border2dp, color = MaterialTheme.colorScheme.onBackground),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(padding16dp)
            ) {
                Text(
                    text = stringResource(R.string.player_name_table),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(R.string.score),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = highScores) {
                    HighScoreItem(it)
                }
            }
        }
    }

}

@Composable
private fun HighScoreItem(highScore: HighScore) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(padding8dp)
    ) {
        Text(
            text = highScore.playerName,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = highScore.score.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}