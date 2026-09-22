package com.kmutnb.trainkmutnb67.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

/** Primary teal -> blue gradient used on all primary buttons and headers. */
val BrandGradient: Brush
    get() = Brush.linearGradient(
        colors = listOf(BrandTeal, BrandBlue),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

/**
 * Header/balance-card background. Reads the theme-aware
 * [HeaderGradientStart]/[HeaderGradientEnd] (declared in Color.kt, flipped by
 * applyNeutralPalette) so it switches with the rest of the app instead of
 * staying a fixed navy in both themes.
 */
val HeaderGradient: Brush
    get() = Brush.linearGradient(listOf(HeaderGradientStart, HeaderGradientEnd))

/** Thin multi-colour bar shown at the very top of secondary screens. */
val RainbowBrush: Brush
    get() = Brush.horizontalGradient(
        listOf(
            LineArl, Warning, LineMrtYellow, LineSukhumvit,
            BrandTeal, LineMrtBlue, LineMrtPurple, LineMrtPink
        )
    )