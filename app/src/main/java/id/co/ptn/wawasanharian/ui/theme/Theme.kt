package id.co.ptn.wawasanharian.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Navy,
    onPrimary = TextLight,
    primaryContainer = NavySecondary,
    onPrimaryContainer = TextLight,
    secondary = GoldDark,
    onSecondary = TextLight,
    secondaryContainer = GoldContainerLight,
    onSecondaryContainer = Ink,
    background = Paper,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = PaperVariant,
    onSurfaceVariant = InkSecondary,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Paper,
    surfaceContainer = PaperContainer,
    surfaceContainerHigh = PaperContainerHigh,
    surfaceContainerHighest = PaperContainerHigh,
    error = ErrorLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = Navy,
    primaryContainer = NavySecondary,
    onPrimaryContainer = TextLight,
    secondary = Gold,
    onSecondary = Navy,
    secondaryContainer = NavySecondary,
    onSecondaryContainer = TextLight,
    background = NavyDeep,
    onBackground = TextLight,
    surface = NavyDeep,
    onSurface = TextLight,
    surfaceVariant = Navy,
    onSurfaceVariant = TextSecondaryLight,
    surfaceContainerLowest = NavyDeep,
    surfaceContainerLow = NavyDeep,
    surfaceContainer = Navy,
    surfaceContainerHigh = Navy,
    surfaceContainerHighest = NavySecondary,
    error = ErrorDark,
)

/**
 * App theme. Dynamic color is intentionally not used: the navy/gold hierarchy from the
 * product spec stays readable and consistent with the Home Screen widget.
 */
@Composable
fun WawasanHarianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
