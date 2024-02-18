package xyz.uaapps.snake.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.uaapps.snake.presentation.theme.padding16dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    content: @Composable (padding: PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
            modifier = Modifier.padding(horizontal = padding16dp),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            )
        )
    }) { contentPadding -> content.invoke(contentPadding) }
}