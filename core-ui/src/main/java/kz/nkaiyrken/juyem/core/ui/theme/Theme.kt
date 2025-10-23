package kz.nkaiyrken.juyem.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = CoreWhite,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,

    secondary = Green600,
    onSecondary = CoreWhite,
    secondaryContainer = Green50,
    onSecondaryContainer = Green900,

    tertiary = Blue600,
    onTertiary = CoreWhite,
    tertiaryContainer = Blue150,
    onTertiaryContainer = Blue900,

    error = Red600,
    onError = CoreWhite,
    errorContainer = Red50,
    onErrorContainer = Red900,

    background = LightGray100,
    onBackground = Gray900,

    surface = CoreWhite,
    onSurface = Gray900,
    surfaceVariant = LightGray200,
    onSurfaceVariant = Gray700,

    outline = Gray400,
    outlineVariant = Gray200,

    scrim = CoreBlack.copy(alpha = 0.32f),
    inverseSurface = Gray900,
    inverseOnSurface = LightGray100,
    inversePrimary = Blue300,
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue400,
    onPrimary = Blue900,
    primaryContainer = Blue800,
    onPrimaryContainer = Blue200,

    secondary = Green400,
    onSecondary = Green900,
    secondaryContainer = Green800,
    onSecondaryContainer = Green200,

    tertiary = Blue400,
    onTertiary = Blue950,
    tertiaryContainer = Blue800,
    onTertiaryContainer = Blue200,

    error = Red400,
    onError = Red900,
    errorContainer = Red800,
    onErrorContainer = Red200,

    background = Gray950,
    onBackground = Gray100,

    surface = Gray900,
    onSurface = Gray100,
    surfaceVariant = Gray800,
    onSurfaceVariant = Gray300,

    outline = Gray600,
    outlineVariant = Gray800,

    scrim = CoreBlack.copy(alpha = 0.5f),
    inverseSurface = Gray100,
    inverseOnSurface = Gray900,
    inversePrimary = Blue600,
)

@Composable
fun JuyemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Отключаем по умолчанию чтобы использовать наши цвета
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val additionalColors = if (darkTheme) additionalDarkColors else additionalLightColors

    CompositionLocalProvider(
        LocalAdditionalColors provides additionalColors,
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = JuyemTypography,
            shapes = JuyemShapes,
            content = content
        )
    }
}
