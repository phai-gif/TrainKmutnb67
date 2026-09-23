package com.kmutnb.trainkmutnb67.ui.components

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
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

// scale = 1.0 already means "the whole map exactly fills the box" (that's what
// baseScale computes), so the minimum must not go below that — anything lower
// just shrinks the map into a small island surrounded by empty space instead
// of filling the screen the way "zoomed all the way out" should.
private const val ZOOM_MIN = 1f
private const val ZOOM_MAX = 8f
private const val DEFAULT_ZOOM = 7f
// Below this, station names are hidden — only the coloured lines/dots show;
// past it (including the default view, which sits well above it) names appear.

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
    val mapBackground = MaterialTheme.colorScheme.background
    val mapBorder = MaterialTheme.colorScheme.outlineVariant
    val mapLabelColor = MaterialTheme.colorScheme.onBackground
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
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

    // Fit the visible routes to the viewport. The source coordinates use a
    // shared virtual canvas, but each filter view should frame its own line.
    val visibleBounds = remember(lines) {
        val points = lines.flatMap { line -> MapLayout.linePaths(line).flatten() }
        if (points.isEmpty()) Offset.Zero to Size(1f, 1f) else {
            val minX = points.minOf { it.x }
            val maxX = points.maxOf { it.x }
            val minY = points.minOf { it.y }
            val maxY = points.maxOf { it.y }
            val padding = 420f
            val width = maxX - minX + padding * 2
            val height = maxY - minY + padding * 2
            Offset(minX - padding, minY - padding) to Size(width.coerceAtLeast(1f), height.coerceAtLeast(1f))
        }
    }
    val mapOrigin = visibleBounds.first
    val mapWidth = visibleBounds.second.width
    val mapHeight = visibleBounds.second.height

    fun baseScale(size: Size): Float =
        if (size.width <= 0f || size.height <= 0f) 0f
        else min(size.width / mapWidth, size.height / mapHeight)

    // Keeps the map snug in the viewport: when the diagram is bigger than the
    // box it can only pan until its edge reaches the box edge (never drifting
    // off into empty space); when it's smaller (zoomed out) it's simply
    // centered and can't be panned around at all.
    fun clampOffset(candidate: Offset, bs: Float): Offset {
        if (bs <= 0f || canvasSize.width <= 0f || canvasSize.height <= 0f) return candidate
        val contentW = mapWidth * bs
        val contentH = mapHeight * bs
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

    // The default view frames the currently visible routes. Filtering a line
    // recomputes the bounds so the selected route fills the same map viewport.
    fun recenter() {
        val bs = baseScale(canvasSize)
        if (bs <= 0f) return
        scale = ZOOM_MIN
        offset = clampOffset(Offset.Zero, bs * ZOOM_MIN)
    }

    LaunchedEffect(canvasSize, lines) {
        if (canvasSize.width > 0f && canvasSize.height > 0f) recenter()
    }

    Box(
        modifier
            .fillMaxWidth()
            .height(560.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(mapBackground)
            .border(1.dp, mapBorder, RoundedCornerShape(16.dp))
            .onSizeChanged { canvasSize = Size(it.width.toFloat(), it.height.toFloat()) }
            .pointerInput(stations) {
                detectTapGestures { tapPos ->
                    val bs = baseScale(canvasSize) * scale
                    if (bs <= 0f) return@detectTapGestures
                    var nearest: Station? = null
                    var nearestDist = Float.MAX_VALUE
                    stations.forEach { st ->
                        val p = MapLayout.stationPositions[st.id] ?: return@forEach
                        val screenPt = Offset(offset.x + (p.x - mapOrigin.x) * bs, offset.y + (p.y - mapOrigin.y) * bs)
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

            fun toScreen(p: Offset) = Offset(offset.x + (p.x - mapOrigin.x) * bs, offset.y + (p.y - mapOrigin.y) * bs)
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
            // At the overview scale, show only interchange names to keep the
            // network readable. A single-line view or zoomed-in view shows all stops.
            val showAllLabels = if (lines.size == 1) scale >= 2.8f else scale >= 3.4f

            // Lines, drawn as rounded polylines so bends read as smooth turns
            // rather than sharp corners — the hallmark of a printed transit map.
            lines.forEach { line ->
                MapLayout.linePaths(line).forEach { route ->
                    val pts = route.map(::toScreen)
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
            }

            val trackSegments = lines.flatMap { line ->
                MapLayout.linePaths(line).flatMap { route ->
                    route.map(::toScreen).zipWithNext()
                }
            }

            val labelPaint = Paint().apply {
                isAntiAlias = true
                // Keep labels legible without letting their size grow as fast
                // as the route spacing when zooming.
                textSize = 9.5.sp.toPx() * scale.coerceIn(0.9f, 1.45f)
                color = mapLabelColor.toArgb()
            }
            val nativeCanvas = drawContext.canvas.nativeCanvas
            val placedLabels = mutableListOf<RectF>()
            val markerClearanceRects = dotGroups.map { (point, group) ->
                val center = toScreen(point)
                val isHub = group.size > 1 || group.any { it.interchange }
                val radius = (if (isHub) 8.5.dp.toPx() else 4.5.dp.toPx()) * zoomFactor + 3.dp.toPx()
                RectF(center.x - radius, center.y - radius, center.x + radius, center.y + radius)
            }

            // Station markers: small white-cored rings in the line color for
            // regular stops, bigger neutral hub rings where lines interchange.
            dotGroups.sortedByDescending { (_, group) -> group.size > 1 || group.any { it.interchange } }
                .forEach { (point, group) ->
                    val sp = toScreen(point)
                    val isInterchange = group.size > 1 || group.any { it.interchange }
                    val isSelected = group.any { it.id == selectedStationId }
                    val radius = (if (isInterchange) 8.5.dp.toPx() else 4.5.dp.toPx()) * zoomFactor
                    val ringColor = if (isInterchange) mapBorder else group.first().line.color
                    val ringWidth = (if (isInterchange) 3.dp else 2.2.dp).toPx() * zoomFactor

                    if (isSelected) {
                        drawCircle(BrandTeal.copy(alpha = 0.30f), radius = radius + 9.dp.toPx() * zoomFactor, center = sp)
                    }
                    drawCircle(mapBackground, radius = radius, center = sp)
                    drawCircle(ringColor, radius = radius, center = sp, style = Stroke(width = ringWidth))
                    if (isSelected) {
                        drawCircle(BrandTeal, radius = radius + 3.dp.toPx() * zoomFactor, center = sp, style = Stroke(width = 2.dp.toPx() * zoomFactor))
                    }

                    if (showAllLabels || isInterchange || isSelected) {
                        // Shared interchanges can have different names on each line
                        // (for example Asok/Sukhumvit), so retain each unique label.
                        group.map { it.label(lang) }.distinct().forEach { label ->
                            val width = labelPaint.measureText(label)
                            val metrics = labelPaint.fontMetrics
                            val gap = radius + 12.dp.toPx()
                            val direction = MapLayout.labelDirections[group.first().id] ?: Offset(1f, 0f)
                            val align = if (direction.x < -0.25f) Paint.Align.RIGHT else Paint.Align.LEFT
                            val candidates = listOf(gap, gap + metrics.descent - metrics.ascent + 5.dp.toPx()).map { distance ->
                                val center = sp + direction * distance
                                Triple(align, center.x, center.y - (metrics.ascent + metrics.descent) / 2f)
                            }
                            var best: Triple<Paint.Align, Float, Float>? = null
                            var bestRect: RectF? = null
                            var bestScore = Float.MAX_VALUE
                            candidates.forEach { (align, x, baseline) ->
                                val left = when (align) {
                                    Paint.Align.LEFT -> x
                                    Paint.Align.RIGHT -> x - width
                                    else -> x - width / 2f
                                }
                                val textRect = RectF(left, baseline + metrics.ascent, left + width, baseline + metrics.descent)
                                val rect = RectF(textRect).apply { inset(-3.dp.toPx(), -3.dp.toPx()) }
                                var score = 0f
                                placedLabels.forEach { used ->
                                    val overlapW = (min(rect.right, used.right) - maxOf(rect.left, used.left)).coerceAtLeast(0f)
                                    val overlapH = (min(rect.bottom, used.bottom) - maxOf(rect.top, used.top)).coerceAtLeast(0f)
                                    score += overlapW * overlapH * 10f
                                }
                                markerClearanceRects.forEach { marker ->
                                    if (RectF.intersects(rect, marker)) score += 100000f
                                }
                                val trackRect = RectF(rect).apply { inset(-2.dp.toPx(), -2.dp.toPx()) }
                                if (trackSegments.any { (start, end) -> segmentIntersectsRect(start, end, trackRect) }) {
                                    score += 100000f
                                }
                                if (rect.left < 2f || rect.right > size.width - 2f || rect.top < 2f || rect.bottom > size.height - 2f) score += 100000f
                                if (score < bestScore) {
                                    bestScore = score
                                    best = Triple(align, x, baseline)
                                    bestRect = rect
                                }
                            }
                            // Suppress labels that cannot fit cleanly; they appear after zooming closer.
                            if (bestScore < 1f) {
                                best?.let { (align, x, baseline) ->
                                    labelPaint.textAlign = align
                                    bestRect?.let(placedLabels::add)
                                    nativeCanvas.drawText(label, x, baseline, labelPaint)
                                }
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


/** Line key placed in the page flow below the map, so it never covers routes. */
@Composable
fun MetroMapLegend(lines: List<MetroLine>, lang: Lang, modifier: Modifier = Modifier) {
    val legendBackground = MaterialTheme.colorScheme.surface
    val legendBorder = MaterialTheme.colorScheme.outlineVariant
    val legendText = MaterialTheme.colorScheme.onSurface
    val columns = if (lines.size > 5) lines.chunked((lines.size + 1) / 2) else listOf(lines)
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(legendBackground)
            .border(1.dp, legendBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        columns.forEach { column ->
            Column(modifier = Modifier.weight(1f)) {
                column.forEach { line ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp),
                    ) {
                        Box(Modifier.width(18.dp).height(4.dp).clip(RoundedCornerShape(2.dp)).background(line.color))
                        Spacer(Modifier.width(8.dp))
                        androidx.compose.material3.Text(
                            line.label(lang),
                            color = legendText,
                            fontSize = 11.sp,
                        )
                    }
                }
            }
        }
    }
}

/** Returns true when a route segment crosses a label's reserved rectangle. */
private fun segmentIntersectsRect(start: Offset, end: Offset, rect: RectF): Boolean {
    val dx = end.x - start.x
    val dy = end.y - start.y
    var tMin = 0f
    var tMax = 1f

    fun clip(p: Float, q: Float): Boolean {
        if (p == 0f) return q >= 0f
        val t = q / p
        if (p < 0f) {
            if (t > tMax) return false
            if (t > tMin) tMin = t
        } else {
            if (t < tMin) return false
            if (t < tMax) tMax = t
        }
        return true
    }

    return clip(-dx, start.x - rect.left) &&
            clip(dx, rect.right - start.x) &&
            clip(-dy, start.y - rect.top) &&
            clip(dy, rect.bottom - start.y) &&
            tMin <= tMax
}
