package xyz.uaapps.snake.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import xyz.uaapps.snake.presentation.theme.appFontFamily
import xyz.uaapps.snake.presentation.theme.corner4dp
import xyz.uaapps.snake.presentation.theme.size64dp

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) { Text(text = text, fontFamily = appFontFamily) }
}
