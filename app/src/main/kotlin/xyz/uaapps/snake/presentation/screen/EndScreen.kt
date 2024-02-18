package xyz.uaapps.snake.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import xyz.uaapps.snake.R
import xyz.uaapps.snake.presentation.component.AppBar
import xyz.uaapps.snake.presentation.component.AppButton
import xyz.uaapps.snake.presentation.theme.padding8dp

@Composable
fun EndScreen(score: Int, onTryAgain: () -> Unit) {
    AppBar(title = "") { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(padding8dp),
                text = stringResource(R.string.game_over),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                modifier = Modifier.padding(padding8dp),
                text = stringResource(R.string.your_score, score),
                style = MaterialTheme.typography.titleLarge,
            )
            AppButton(text = stringResource(R.string.try_again)) { onTryAgain.invoke() }
        }
    }
}