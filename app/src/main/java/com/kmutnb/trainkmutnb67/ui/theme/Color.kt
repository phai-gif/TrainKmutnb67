package com.kmutnb.trainkmutnb67.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// ---- Neutral palettes (background / surfaces / text) ----
// Every screen reads the vars below directly (no CompositionLocal), so they're
// backed by Compose state: flipping them via applyNeutralPalette() recomposes
// every screen automatically, the same way AppState's mutableStateOf fields do.
private class NeutralPalette(
    val bg: Color,
    val bgGradientTop: Color,
    val surface1: Color,
    val surface2: Color,
    val cardBorder: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val headerGradientStart: Color,
    val headerGradientEnd: Color,
)

private val DarkPalette = NeutralPalette(
    bg = Color(0xFF1A1D20),
    bgGradientTop = Color(0xFF0E1524),
    surface1 = Color(0xFF1E293B),
    surface2 = Color(0xFF172236),
    cardBorder = Color(0xFF223049),
    textPrimary = Color(0xFFF2F5FA),
    textSecondary = Color(0xFF93A1B8),
    textMuted = Color(0xFF63728C),
    // Brand-navy header, same as before — flat single-tone gradient.
    headerGradientStart = Color(0xFF1E293B),
    headerGradientEnd = Color(0xFF1E293B),
)

private val LightPalette = NeutralPalette(
    bg = Color(0xFFF3F5F9),
    bgGradientTop = Color(0xFFE9EDF5),
    surface1 = Color(0xFFFFFFFF),
    surface2 = Color(0xFFF0F2F7),
    cardBorder = Color(0xFFE1E5EE),
    textPrimary = Color(0xFF12151C),
    textSecondary = Color(0xFF4B5568),
    textMuted = Color(0xFF8A93A3),
    // Light theme: header now matches the same white -> light-gray tone as
    // every other card (CardSurface), so it blends in instead of staying navy.
    headerGradientStart = Color(0xFFFFFFFF),
    headerGradientEnd = Color(0xFFF0F2F7),
)

var BgDark by mutableStateOf(DarkPalette.bg)
    private set
var BgLight by mutableStateOf(LightPalette.bg)
var BgGradientTop by mutableStateOf(DarkPalette.bgGradientTop)
    private set
var Surface1 by mutableStateOf(DarkPalette.surface1)
    private set
var Surface2 by mutableStateOf(DarkPalette.surface2)
    private set
var CardBorder by mutableStateOf(DarkPalette.cardBorder)
    private set
var TextPrimary by mutableStateOf(DarkPalette.textPrimary)
    private set
var TextSecondary by mutableStateOf(DarkPalette.textSecondary)
    private set
var TextMuted by mutableStateOf(DarkPalette.textMuted)
    private set
var HeaderGradientStart by mutableStateOf(DarkPalette.headerGradientStart)
    private set
var HeaderGradientEnd by mutableStateOf(DarkPalette.headerGradientEnd)
    private set

/** Swaps every neutral color to the light or dark palette. Called from [Trainkmutnb67Theme]. */
internal fun applyNeutralPalette(dark: Boolean) {
    val p = if (dark) DarkPalette else LightPalette
    BgLight = p.bg
    BgDark = p.bg
    BgGradientTop = p.bgGradientTop
    Surface1 = p.surface1
    Surface2 = p.surface2
    CardBorder = p.cardBorder
    TextPrimary = p.textPrimary
    TextSecondary = p.textSecondary
    TextMuted = p.textMuted
    HeaderGradientStart = p.headerGradientStart
    HeaderGradientEnd = p.headerGradientEnd
}

// ---- Accent (same in both themes) ----
val BrandTeal = Color(0xFFFF6B00)
val BrandTealDim = Color(0xFFFF6B00)
val BrandBlue = Color(0xFFFF6B00)
val BrandBlueDim = Color(0xFF1E293B)
val Danger = Color(0xFFF05252)
val Warning = Color(0xFFF7A50B)
val Success = Color(0xFF93A1B8)

// ---- Metro line colours (real BTS/MRT palette) ----
val LineSukhumvit = Color(0xFF6EBE4A) // BTS light green
val LineSilom = Color(0xFF0B7A3E)     // BTS dark green
val LineMrtBlue = Color(0xFF1E4E9C)   // MRT blue
val LineMrtPurple = Color(0xFF7E1F86) // MRT purple
val LineArl = Color(0xFFE2231A)       // Airport Rail Link red
val LineSrtDarkRed = Color(0xFF8B1A2B)  // SRT Dark Red Line
val LineSrtLightRed = Color(0xFFF08A8A) // SRT Light Red Line
val LineApmGold = Color(0xFFB8860B)     // APM Gold Line
val LineMrtYellow = Color(0xFFFFC107)   // MRT Yellow Line
val LineMrtPink = Color(0xFFEC4899)     // MRT Pink Line

// Legacy names kept so the generated Theme.kt still compiles until replaced
val Purple80 = BrandTeal
val PurpleGrey80 = TextSecondary
val Pink80 = BrandBlue
val Purple40 = BrandTealDim
val PurpleGrey40 = TextMuted
val Pink40 = BrandBlueDim