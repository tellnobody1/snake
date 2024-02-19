package xyz.uaapps.snake.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.Locale
import xyz.uaapps.snake.R

private val appFontFamily = run {
    FontFamily(Font(when (Locale.current.language) {
        "uk" -> R.font.hardpixel
        else -> R.font.nokia
    }))
}

val Typography = Typography().copy(
    displayLarge = Typography().displayLarge.copy(fontFamily = appFontFamily),
    displayMedium = Typography().displayMedium.copy(fontFamily = appFontFamily),
    displaySmall = Typography().displaySmall.copy(fontFamily = appFontFamily),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = appFontFamily),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = appFontFamily),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = appFontFamily),
    titleLarge = Typography().titleLarge.copy(fontFamily = appFontFamily),
    titleMedium = Typography().titleMedium.copy(fontFamily = appFontFamily),
    titleSmall = Typography().titleSmall.copy(fontFamily = appFontFamily),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = appFontFamily),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = appFontFamily),
    bodySmall = Typography().bodySmall.copy(fontFamily = appFontFamily),
    labelLarge = Typography().labelLarge.copy(fontFamily = appFontFamily),
    labelMedium = Typography().labelMedium.copy(fontFamily = appFontFamily),
    labelSmall = Typography().labelSmall.copy(fontFamily = appFontFamily),
)
