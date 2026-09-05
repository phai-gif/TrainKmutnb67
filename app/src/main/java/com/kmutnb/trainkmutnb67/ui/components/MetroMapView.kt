package com.kmutnb.trainkmutnb67.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.MapLayout
import com.kmutnb.trainkmutnb67.data.MetroLine
import com.kmutnb.trainkmutnb67.data.Station
import com.kmutnb.trainkmutnb67.i18n.Lang
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import kotlin.math.min

// The map is always drawn on a bright, printed-map-style backdrop, independent
// of the app's own dark/light theme — that's what makes it read like a real
// transit diagram instead of app chrome.
private val MapBg = Color(0xFFFBFBFC)
private val MapGridBorder = Color(0xFFE4E7EC)
private val MapLabelColor = Color(0xFF374151) // slate-700, fixed regardless of app theme
private val MapStationRing = Color(0xFF6B7280) // neutral interchange ring
// scale = 1.0 already means "the whole map exactly fills the box" (that's what
// baseScale computes), so the minimum must not go below that — anything lower
// just shrinks the map into a small island surrounded by empty space instead
// of filling the screen the way "zoomed all the way out" should.
private const val ZOOM_MIN = 1f
private const val ZOOM_MAX = 10f
private const val DEFAULT_ZOOM = 7f
// Below this, station names are hidden — only the coloured lines/dots show;
// past it (including the default view, which sits well above it) names appear.
private const val LABEL_ZOOM_THRESHOLD = 3f

/**
 * Vector-drawn, pannable & pinch-zoomable schematic map styled after printed
 * BTS/MRT line-map diagrams: rounded line joins, ringed station markers, and a
 * legend. Coordinates come from [MapLayout]; everything is redrawn each frame
 * at the current zoom, so it stays crisp at any scale instead of pixelating
 * like a bitmap would. Tapping a station dot reports it via [onSelectStation].
 */
@Composable
fun MetroMapView(
    lines: List<MetroLine>,
    stations: List<Station>,
    selectedStationId: String?,
    lang: Lang,
    onSelectStation: (Station) -> Unit,
    modifier: Modifier = Modifier,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    var initialized by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    // Stations that share the exact same point (real interchanges between two
    // drawn lines, e.g. Siam) are grouped so they're drawn as one neutral hub
    // marker instead of two overlapping colored circles.
    val dotGroups = remember(stations) {
        stations
            .mapNotNull { st -> MapLayout.stationPositions[st.id]?.let { it to st } }
            .groupBy({ it.first }, { it.second })
            .map { (point, group) -> point to group }
    }

    fun baseScale(size: Size): Float =
        if (size.width <= 0f || size.height <= 0f) 0f
        else min(size.width / MapLayout.MAP_WIDTH, size.height / MapLayout.MAP_HEIGHT)

    // Keeps the map snug in the viewport: when the diagram is bigger than the
    // box it can only pan until its edge reaches the box edge (never drifting
    // off into empty space); when it's smaller (zoomed out) it's simply
    // centered and can't be panned around at all.
    fun clampOffset(candidate: Offset, bs: Float): Offset {
        if (bs <= 0f || canvasSize.width <= 0f || canvasSize.height <= 0f) return candidate
        val contentW = MapLayout.MAP_WIDTH * bs
        val contentH = MapLayout.MAP_HEIGHT * bs
        val x = if (contentW <= canvasSize.width) {
            (canvasSize.width - contentW) / 2f
        } else {
            candidate.x.coerceIn(canvasSize.width - contentW, 0f)
        }
        val y = if (contentH <= canvasSize.height) {
            (canvasSize.height - contentH) / 2f
        } else {
            candidate.y.coerceIn(canvasSize.height - contentH, 0f)
        }
        return Offset(x, y)
    }

    // Zooms in/out around the middle of the viewport (not the top-left corner)
    // so the +/- buttons feel like the pinch gesture, which already does this
    // naturally via its own centroid.
    fun zoomAroundCenter(factor: Float) {
        val oldScale = scale
        val newScale = (oldScale * factor).coerceIn(ZOOM_MIN, ZOOM_MAX)
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val newOffset = center - (center - offset) * (newScale / oldScale)
        scale = newScale
        offset = clampOffset(newOffset, baseScale(canvasSize) * newScale)
    }

    // The default view opens zoomed into the busiest interchange (Siam)
    // rather than "fit everything" — with 93 stations across 10 lines,
    // shrinking the whole network onto a phone-width screen leaves station
    // labels too small to read no matter how much spacing the layout itself
    // has. This is how real map apps behave too: they open on a comfortable,
    // readable view, not a zoomed-out overview of the entire system. Zooming
    // out (pinch, or the − button — down to 0.35x) still reaches the full
    // 10-line network in one glance when that's what's wanted.
    fun recenter() {
        val bs = baseScale(canvasSize)
        if (bs <= 0f) return
        scale = DEFAULT_ZOOM
        val focus = MapLayout.stationPositions["CEN"]
            ?: Offset(MapLayout.MAP_WIDTH / 2f, MapLayout.MAP_HEIGHT / 2f)
        val totalScale = bs * DEFAULT_ZOOM
        offset = clampOffset(
            Offset(
                canvasSize.width / 2f - focus.x * totalScale,
                canvasSize.height / 2f - focus.y * totalScale,
            ),
            totalScale,
        )
    }

    LaunchedEffect(canvasSize) {
        if (!initialized && canvasSize.width > 0f && canvasSize.height > 0f) {
            recenter()
            initialized = true
        }
    }

    Box(
        modifier
            .fillMaxWidth()
            .height(760.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MapBg)
            .border(1.dp, MapGridBorder, RoundedCornerShape(16.dp))
            .onSizeChanged { canvasSize = Size(it.width.toFloat(), it.height.toFloat()) }
            .pointerInput(stations) {
                detectTapGestures { tapPos ->
                    val bs = baseScale(canvasSize) * scale
                    if (bs <= 0f) return@detectTapGestures
                    var nearest: Station? = null
                    var nearestDist = Float.MAX_VALUE
                    stations.forEach { st ->
                        val p = MapLayout.stationPositions[st.id] ?: return@forEach
                        val screenPt = Offset(offset.x + p.x * bs, offset.y + p.y * bs)
                        val d = (screenPt - tapPos).getDistance()
                        if (d < nearestDist) { nearestDist = d; nearest = st }
                    }
                    val hitRadiusPx = with(density) { 26.dp.toPx() }
                    if (nearestDist <= hitRadiusPx) nearest?.let(onSelectStation)
                }
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    val newScale = (scale * zoom).coerceIn(ZOOM_MIN, ZOOM_MAX)
                    scale = newScale
                    offset = clampOffset(offset + pan, baseScale(canvasSize) * newScale)
                }
            },
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val bs = baseScale(size) * scale
            if (bs <= 0f) return@Canvas

            fun toScreen(p: Offset) = Offset(offset.x + p.x * bs, offset.y + p.y * bs)
            // Capped well below the new higher DEFAULT_ZOOM on purpose: station
            // spacing (which tracks the full `scale`) grows faster than the
            // dots/text themselves, so the default view actually gains visible
            // breathing room between stations instead of just scaling up as
            // one uniform, still-crowded picture.
            val zoomFactor = scale.coerceIn(0.55f, 2.6f)
            // Station names only earn their keep once you've zoomed in close
            // enough to actually read them — at the fully-zoomed-out overview
            // there are 93 of them fighting for space, so hide the text
            // entirely there and let just the coloured lines/dots show the
            // shape of the network; zooming in past this point reveals names.
            val showLabels = scale >= LABEL_ZOOM_THRESHOLD

            // Lines, drawn as rounded polylines so bends read as smooth turns
            // rather than sharp corners — the hallmark of a printed transit map.
            lines.forEach { line ->
                val pts = MapLayout.linePoints(line).map(::toScreen)
                if (pts.size >= 2) {
                    val path = roundedPolyline(pts, cornerRadius = 22.dp.toPx() * zoomFactor)
                    drawPath(
                        path,
                        color = line.color,
                        style = Stroke(
                            width = 5.5.dp.toPx() * zoomFactor,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                        ),
                    )
                }
            }

            val labelPaint = Paint().apply {
                isAntiAlias = true
                textSize = 10.5.sp.toPx() * zoomFactor
                color = MapLabelColor.toArgb()
            }

            // Station markers: small white-cored rings in the line color for
            // regular stops, bigger neutral hub rings where lines interchange.
            dotGroups.forEach { (point, group) ->
                val sp = toScreen(point)
                val isInterchange = group.size > 1 || group.any { it.interchange }
                val isSelected = group.any { it.id == selectedStationId }
                val radius = (if (isInterchange) 8.5.dp.toPx() else 4.5.dp.toPx()) * zoomFactor
                val ringColor = if (isInterchange) MapStationRing else group.first().line.color
                val ringWidth = (if (isInterchange) 3.dp else 2.2.dp).toPx() * zoomFactor

                if (isSelected) {
                    drawCircle(BrandTeal.copy(alpha = 0.30f), radius = radius + 9.dp.toPx() * zoomFactor, center = sp)
                }
                drawCircle(Color.White, radius = radius, center = sp)
                drawCircle(ringColor, radius = radius, center = sp, style = Stroke(width = ringWidth))
                if (isSelected) {
                    drawCircle(BrandTeal, radius = radius + 3.dp.toPx() * zoomFactor, center = sp, style = Stroke(width = 2.dp.toPx() * zoomFactor))
                }

                if (showLabels) {
                    // Nudge the label along the perpendicular of the station's
                    // own track instead of always dropping it to the right, so
                    // it doesn't sit on top of the line or the next station.
                    val label = group.first().label(lang)
                    val perp = MapLayout.labelDirections[group.first().id] ?: Offset(1f, 0f)
                    val labelDist = radius + 12.dp.toPx() * zoomFactor
                    val anchorX = sp.x + perp.x * labelDist
                    val anchorY = sp.y + perp.y * labelDist
                    val nativeCanvas = drawContext.canvas.nativeCanvas

                    // On a genuinely diagonal run (e.g. the Purple Line's
                    // northwest stretch) tilt the text to follow the track,
                    // exactly like a printed transit map does — that reads far
                    // better than fighting to fit a horizontal label into a
                    // diagonal gap between tightly-spaced stations.
                    val norm = Offset(-perp.y, perp.x)
                    var angleDeg = Math.toDegrees(kotlin.math.atan2(norm.y, norm.x).toDouble()).toFloat()
                    if (angleDeg > 90f) angleDeg -= 180f
                    if (angleDeg <= -90f) angleDeg += 180f
                    val isDiagonal = kotlin.math.abs(angleDeg) in 20f..70f

                    when {
                        isDiagonal -> {
                            labelPaint.textAlign = if (perp.x >= 0) Paint.Align.LEFT else Paint.Align.RIGHT
                            nativeCanvas.save()
                            nativeCanvas.rotate(angleDeg, anchorX, anchorY)
                            nativeCanvas.drawText(label, anchorX, anchorY + 3.5.dp.toPx() * zoomFactor, labelPaint)
                            nativeCanvas.restore()
                        }
                        kotlin.math.abs(perp.x) >= kotlin.math.abs(perp.y) -> {
                            labelPaint.textAlign = if (perp.x >= 0) Paint.Align.LEFT else Paint.Align.RIGHT
                            nativeCanvas.drawText(label, anchorX, anchorY + 3.5.dp.toPx() * zoomFactor, labelPaint)
                        }
                        else -> {
                            labelPaint.textAlign = Paint.Align.CENTER
                            val vPad = if (perp.y < 0) -4.dp.toPx() * zoomFactor else 12.dp.toPx() * zoomFactor
                            nativeCanvas.drawText(label, anchorX, anchorY + vPad, labelPaint)
                        }
                    }
                }
            }
        }

        // Legend, bottom-left — same spot a printed line map keeps its key.
        // Split into two columns once there are enough lines to make one tall
        // column awkward (e.g. the "all lines" view with all 10 systems).
        Row(
            Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.95f))
                .border(1.dp, MapGridBorder, RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
        ) {
            val columns = if (lines.size > 5) lines.chunked((lines.size + 1) / 2) else listOf(lines)
            columns.forEachIndexed { i, col ->
                if (i > 0) Spacer(Modifier.width(14.dp))
                Column {
                    col.forEach { line ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp),
                        ) {
                            Box(Modifier.size(9.dp).clip(CircleShape).background(line.color))
                            Spacer(Modifier.width(6.dp))
                            Text(line.label(lang), color = MapLabelColor, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        Column(
            Modifier.align(Alignment.TopEnd).padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MapZoomButton("+") { zoomAroundCenter(1.3f) }
            MapZoomButton("–") { zoomAroundCenter(1f / 1.3f) }
            MapZoomButton("⟲") { recenter() }
        }
    }
}

/** Builds a polyline with each interior corner rounded into a short arc. */
private fun roundedPolyline(points: List<Offset>, cornerRadius: Float): Path {
    val path = Path()
    if (points.size < 2) return path
    path.moveTo(points.first().x, points.first().y)
    if (points.size == 2) {
        path.lineTo(points[1].x, points[1].y)
        return path
    }
    for (i in 1 until points.size - 1) {
        val prev = points[i - 1]
        val curr = points[i]
        val next = points[i + 1]
        val toPrevLen = (prev - curr).getDistance()
        val toNextLen = (next - curr).getDistance()
        if (toPrevLen < 0.001f || toNextLen < 0.001f) continue
        val r = min(cornerRadius, min(toPrevLen, toNextLen) / 2f)
        val p1 = curr + (prev - curr) * (r / toPrevLen)
        val p2 = curr + (next - curr) * (r / toNextLen)
        path.lineTo(p1.x, p1.y)
        path.quadraticTo(curr.x, curr.y, p2.x, p2.y)
    }
    path.lineTo(points.last().x, points.last().y)
    return path
}

@Composable
private fun MapZoomButton(label: String, onClick: () -> Unit) {
    Box(
        Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Surface2)
            .border(1.dp, CardBorder, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, color = TextPrimary, fontSize = 16.sp)
    }
}
