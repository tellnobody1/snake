package xyz.uaapps.snake.presentation.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import xyz.uaapps.snake.R
import xyz.uaapps.snake.domain.extension.launchActivity
import xyz.uaapps.snake.domain.navigation.Screen
import xyz.uaapps.snake.presentation.activity.GameActivity
import xyz.uaapps.snake.presentation.component.AppButton
import xyz.uaapps.snake.presentation.theme.padding8dp

@Composable
fun MenuScreen(navController: NavHostController) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding8dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.displayLarge)
        AppButton(modifier = Modifier.fillMaxWidth().focusRequester(focusRequester), text = stringResource(R.string.new_game)) {
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
