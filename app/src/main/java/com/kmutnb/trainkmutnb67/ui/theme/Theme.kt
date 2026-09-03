package com.kmutnb.trainkmutnb67.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = darkColorScheme(
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

@Composable
fun Trainkmutnb67Theme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
