package xyz.uaapps.snake.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = darkColorScheme(
    primary = Color(0xFF2D321D),
    onPrimary = Color.White,
    secondary = Color(0xFF4DB6AC),
    onSecondary = Color(0xFF004D40),
    background = Color(0xFF8DA25A),
    onBackground = Color(0xFF2D321D),
)

@Composable
fun SnakeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
