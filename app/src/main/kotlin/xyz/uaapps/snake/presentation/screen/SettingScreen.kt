package xyz.uaapps.snake.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.uaapps.snake.R
import xyz.uaapps.snake.data.cache.GameCache
import xyz.uaapps.snake.presentation.component.AppBar
import xyz.uaapps.snake.presentation.component.AppButton
import xyz.uaapps.snake.presentation.component.TitleLarge
import xyz.uaapps.snake.presentation.theme.border2dp
import xyz.uaapps.snake.presentation.theme.padding16dp
import xyz.uaapps.snake.presentation.theme.padding64dp
import xyz.uaapps.snake.presentation.theme.width248dp

@Composable
fun SettingScreen(navController: NavHostController) {
    val dataStore = GameCache(LocalContext.current)
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    AppBar(
        title = stringResource(R.string.title_settings)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                )
                .verticalScroll(ScrollState(0))
                .border(width = border2dp, color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleLarge(
                modifier = Modifier.padding(
                    top = padding64dp,
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                ),
                text = stringResource(R.string.player_name),
                textAlign = TextAlign.Center
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(),
                singleLine = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(horizontal = padding64dp)
                    .border(width = border2dp, color = MaterialTheme.colorScheme.onBackground)
            )
            AppButton(
                text = stringResource(R.string.save), modifier = Modifier
                    .width(width248dp)
                    .padding(padding16dp)
            ) {
                scope.launch {
                    dataStore.savePlayerName(text.text.trim())
                    Toast.makeText(context, R.string.player_name_updated, Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }
        }
    }
}