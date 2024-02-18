package com.mukeshsolanki.snake.presentation.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mukeshsolanki.snake.R
import com.mukeshsolanki.snake.domain.extension.launchActivity
import com.mukeshsolanki.snake.domain.navigation.Screen
import com.mukeshsolanki.snake.presentation.activity.GameActivity
import com.mukeshsolanki.snake.presentation.component.AppButton
import com.mukeshsolanki.snake.presentation.component.DisplayLarge
import com.mukeshsolanki.snake.presentation.theme.border2dp
import com.mukeshsolanki.snake.presentation.theme.padding16dp
import com.mukeshsolanki.snake.presentation.theme.padding64dp
import com.mukeshsolanki.snake.presentation.theme.padding8dp
import com.mukeshsolanki.snake.presentation.theme.width248dp

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding8dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current
        DisplayLarge(text = stringResource(R.string.app_name))
        AppButton(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.new_game)) {
            context.launchActivity<GameActivity>()
        }
        AppButton(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.high_score)) {
            navController.navigate(Screen.HighScores.route)
        }
        AppButton(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.settings)) {
            navController.navigate(Screen.Settings.route)
        }
        AppButton(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.about)) {
            navController.navigate(Screen.About.route)
        }
    }
}