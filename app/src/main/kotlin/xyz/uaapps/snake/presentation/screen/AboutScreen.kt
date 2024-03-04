package xyz.uaapps.snake.presentation.screen

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import xyz.uaapps.snake.R
import xyz.uaapps.snake.domain.base.REPO_URL
import xyz.uaapps.snake.presentation.component.AppButton
import xyz.uaapps.snake.presentation.theme.padding8dp

@Preview
@Composable
fun AboutScreen() {
    val context = LocalContext.current
    val builder = remember { CustomTabsIntent.Builder() }
    val customTabsIntent = remember { builder.build() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding8dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
        )
        Text(
            modifier = Modifier.padding(padding8dp),
            text = stringResource(R.string.about_game),
        )
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.source_code)
        ) { customTabsIntent.launchUrl(context, Uri.parse(REPO_URL)) }
    }
}
