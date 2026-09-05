package com.kmutnb.trainkmutnb67.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kmutnb.trainkmutnb67.data.AppState

private fun colorSchemeFor(dark: Boolean) = if (dark) {
    darkColorScheme(
        primary = BrandTeal,
        onPrimary = Color(0xFF04120E),
        secondary = BrandBlue,
        onSecondary = TextPrimary,
        tertiary = BrandTealDim,
        background = BgDark,
        onBackground = TextPrimary,
        surface = Surface1,
        onSurface = TextPrimary,
        surfaceVariant = Surface2,
        onSurfaceVariant = TextSecondary,
        outline = CardBorder,
        error = Danger,
        onError = TextPrimary,
    )
} else {
    lightColorScheme(
        primary = BrandTeal,
        onPrimary = Color.White,
        secondary = BrandBlue,
        onSecondary = Color.White,
        tertiary = BrandTealDim,
        background = BgDark,
        onBackground = TextPrimary,
        surface = Surface1,
        onSurface = TextPrimary,
        surfaceVariant = Surface2,
        onSurfaceVariant = TextSecondary,
        outline = CardBorder,
        error = Danger,
        onError = Color.White,
    )
}

@Composable
fun Trainkmutnb67Theme(
    darkTheme: Boolean = AppState.isDarkTheme,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Screens read the neutral colors (BgDark, Surface1, TextPrimary, ...) as
    // plain top-level vals rather than through MaterialTheme.colorScheme, so
    // flip the shared palette here whenever the theme flag changes.
    SideEffect { applyNeutralPalette(darkTheme) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorSchemeFor(darkTheme),
        typography = Typography,
        content = content
    )
}
