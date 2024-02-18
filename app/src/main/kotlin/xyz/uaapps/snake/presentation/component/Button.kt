package xyz.uaapps.snake.presentation.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppButton(modifier: Modifier = Modifier, isSelected: Boolean = false, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary,
        )
    ) { Text(text = text) }
}

@Preview
@Composable
fun PreviewAppButton() {
    AppButton(
        text = "Click Me",
        onClick = {},
    )
}

@Preview
@Composable
fun PreviewSelectedAppButton() {
    AppButton(
        text = "Click Me",
        onClick = {},
        isSelected = true,
    )
}
