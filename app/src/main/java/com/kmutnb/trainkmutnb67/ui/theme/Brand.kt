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

val HeaderGradient: Brush
    get() = Brush.linearGradient(listOf(Color0f, Color1f))

private val Color0f = androidx.compose.ui.graphics.Color(0xFF0E5C4E)
private val Color1f = androidx.compose.ui.graphics.Color(0xFF123A6B)

/** Thin multi-colour bar shown at the very top of secondary screens. */
val RainbowBrush: Brush
    get() = Brush.horizontalGradient(
        listOf(
            LineArl, Warning, LineYellow, LineSukhumvit,
            BrandTeal, LineMrtBlue, LineMrtPurple, LinePink
        )
    )
