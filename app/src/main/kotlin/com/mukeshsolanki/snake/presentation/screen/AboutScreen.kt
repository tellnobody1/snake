package com.mukeshsolanki.snake.presentation.screen

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mukeshsolanki.snake.BuildConfig
import com.mukeshsolanki.snake.R
import com.mukeshsolanki.snake.domain.base.REPO_URL
import com.mukeshsolanki.snake.presentation.component.*
import com.mukeshsolanki.snake.presentation.theme.padding16dp
import com.mukeshsolanki.snake.presentation.theme.padding8dp
import com.mukeshsolanki.snake.presentation.theme.width248dp

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
        DisplayLarge(text = stringResource(R.string.app_name))
        TitleLarge(
            modifier = Modifier.padding(padding8dp),
            text = BuildConfig.VERSION_NAME
        )
        BodyLarge(
            modifier = Modifier.padding(padding8dp),
            text = stringResource(R.string.about_game),
            textAlign = TextAlign.Justify
        )
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.source_code)
        ) { customTabsIntent.launchUrl(context, Uri.parse(REPO_URL)) }
    }
}