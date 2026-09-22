package com.kmutnb.trainkmutnb67.data

import androidx.compose.ui.geometry.Offset

/**
 * Schematic (not geographic) layout for the interactive line map, built like a
 * printed transit diagram: every anchor point sits on a 100-unit grid and most
 * segments between anchors run at a clean 0°/45°/90° angle. Each line is
 * pinned at a handful of "anchor" stations — its two termini, every station
 * marked `interchange = true`, plus the occasional pure geometry bend where a
 * straight anchor-to-anchor line wouldn't stay tidy. Every other station is
 * placed by evenly interpolating (by arc length) along the anchors/bends on
 * either side of it, in list order. Anchors that represent the same
 * real-world interchange (e.g. Siam on both BTS lines, or Lat Phrao on both
 * MRT Blue and MRT Yellow) intentionally reuse the same grid point, so the
 * drawn lines visually cross or touch there, the way printed transit maps
 * read.
 */
object MapLayout {
    // A little wider/taller than the grid the anchors actually use, so station
    // labels near the edges (e.g. Suvarnabhumi, Min Buri) have room to draw
    // without being clipped by the map card's rounded corner.
    // Width is the scarcer dimension (bounded by phone screen width), so it's
    // kept as tight as the layout allows; height is comparatively cheap (the
    // map card can be tall and the screen scrolls), so it gets more slack.
    const val MAP_WIDTH = 2900f
    const val MAP_HEIGHT = 3200f

    // Origin offset keeps every anchor away from the card edges (room for
    // labels and the corner radius) instead of hugging (0,0).
    private const val ORIGIN_X = 100f
    private const val ORIGIN_Y = 140f

    // 140 virtual units per grid cell — wider than the original 100 so station
    // labels have real breathing room and don't collide with neighbours or the
    // track lines themselves.
    private const val CELL = 140f
    private fun p(gx: Int, gy: Int) = Offset(ORIGIN_X + gx * CELL, ORIGIN_Y + gy * CELL)

    // Grid anchors — the "skeleton" of the map.
    private val anchors: Map<String, Offset> = mapOf(
        // ---- BTS Sukhumvit / BTS Silom / MRT Blue central cluster ----
        "N9" to p(8, 9),    // Mo Chit          == BL15 Chatuchak Park == DR1 Chatuchak (SRT)
        "CEN" to p(8, 14),  // Siam (Sukhumvit) == CEN2 Siam (Silom)
        "E4" to p(11, 14),  // Asok             == BL7 Sukhumvit
        "E9" to p(13, 16),  // On Nut (terminus)
        "W1" to p(5, 14),   // National Stadium (terminus)
        "CEN2" to p(8, 14), // Siam (Silom)
        "S2" to p(8, 16),   // Sala Daeng       == BL3 Si Lom
        "S8" to p(5, 19),   // Krung Thon Buri  == G1 Gold Line
        "S12" to p(4, 20),  // Bang Wa (terminus)
        "BL1" to p(10, 18), // Hua Lamphong (terminus)
        "BL3" to p(8, 16),  // Si Lom
        "BL7" to p(11, 14), // Sukhumvit
        "BL8" to p(13, 12), // Phetchaburi      == A3 Makkasan
        "BL10" to p(11, 10),// Thailand Cultural Centre
        "BL12" to p(10, 11), // Lat Phrao       == Y1 MRT Yellow — pulled a
                             // touch further from Mo Chit so the two hubs
                             // don't run into each other
        "BL15" to p(8, 9),  // Chatuchak Park

        // ---- MRT Purple ----
        "PP16" to p(5, 12), // Tao Poon (terminus)
        "PP15" to p(5, 11), // Bang Son         == LR1 SRT Light Red
        "PP11" to p(5, 10), // Nonthaburi Civic Centre == PK1 MRT Pink
        "PP05" to p(3, 8),  // Khlong Bang Phai (terminus)

        // ---- Airport Rail Link ----
        "A1" to p(15, 10),  // Phaya Thai (terminus)
        "A3" to p(13, 12),  // Makkasan
        "A5" to p(14, 13),  // Hua Mak          == Y10 MRT Yellow
        "A8" to p(16, 15),  // Suvarnabhumi (terminus)

        // ---- SRT Dark Red (Bang Sue - Rangsit) ----
        "DR1" to p(8, 9),   // Chatuchak, shares the Mo Chit / Chatuchak Park hub
        "DR7" to p(8, 0),   // Rangsit (terminus)

        // ---- SRT Light Red (Bang Sue - Taling Chan) ----
        "LR1" to p(5, 11),  // Bang Son, shares Purple's anchor
        "LR5" to p(1, 15),  // Taling Chan (terminus)

        // ---- APM Gold (Krung Thon Buri - Khlong San) ----
        "G1" to p(5, 19),   // Krung Thon Buri, shares Silom's anchor
        "G3" to p(3, 19),   // Khlong San (terminus)

        // ---- MRT Yellow (Lat Phrao - Samrong) ----
        "Y1" to p(10, 11),  // Lat Phrao, shares Blue's anchor — pulled a touch
                            // further from Mo Chit so the two hubs don't run
                            // into each other
        "Y5" to p(12, 13),  // Lat Phrao 83 — pure geometry bow so the 8
                            // stations between Lat Phrao and Hua Mak aren't
                            // packed onto one short, crowded diagonal
        "Y10" to p(14, 13), // Hua Mak, shares ARL's anchor
        "Y12" to p(16, 17), // Samrong (terminus)

        // ---- MRT Pink (Nonthaburi Civic Centre - Min Buri) ----
        "PK1" to p(5, 10),  // Nonthaburi Civic Centre, shares Purple's anchor
        "PK5" to p(5, 4),   // Wat Phra Sri Mahathat — turn north -> east
        "PK12" to p(17, 6), // Min Buri (terminus) — the diagonal run from
                            // Wat Phra Sri Mahathat gives the 7 stations along
                            // the way (PK6-PK11) room without pushing the
                            // whole map's bounding width out further than it
                            // needs to be (width is the scarce dimension)
    )

    // Extra grid bend inserted right after the named anchor, so that segment
    // still reads as two clean legs instead of one odd diagonal.
    private val bendsAfter: Map<String, Offset> = mapOf(
        "BL3" to p(9, 16), // Si Lom -> east, then NE up to Sukhumvit/Asok
    )

    /** stationId -> position in the virtual [0, MAP_WIDTH] x [0, MAP_HEIGHT] space. */
    val stationPositions: Map<String, Offset> by lazy { computePositions() }

    /**
     * stationId -> a unit direction, perpendicular to that station's own line at
     * that point, to nudge its label along. Labels always sitting to the right
     * of the dot tend to land right on top of the outgoing track (or the next
     * station) whenever a line runs rightward or diagonally; offsetting
     * perpendicular to the local direction instead keeps them off to the side.
     */
    val labelDirections: Map<String, Offset> by lazy { computeLabelDirections() }

    private fun computeLabelDirections(): Map<String, Offset> {
        val result = mutableMapOf<String, Offset>()
        MetroLine.entries.forEach { line ->
            val list = MockData.stationsOf(line)
            val pts = list.map { stationPositions[it.id] }
            for (i in list.indices) {
                val here = pts[i] ?: continue
                val prev = pts.getOrNull(i - 1)
                val next = pts.getOrNull(i + 1)
                val tangent = when {
                    prev != null && next != null -> next - prev
                    next != null -> next - here
                    prev != null -> here - prev
                    else -> Offset(1f, 0f)
                }
                val len = tangent.getDistance()
                val norm = if (len > 0.001f) Offset(tangent.x / len, tangent.y / len) else Offset(1f, 0f)
                // Rotate -90°: a rightward track gets a label above it; a
                // downward track gets a label to its right.
                var perp = Offset(norm.y, -norm.x)
                // Consecutive stations along a straight run all share the same
                // tangent, which would stack every label on the same side —
                // fine when they're far apart, but on a tightly-spaced run
                // (e.g. Chit Lom/Phloen Chit, one stop apart) their labels
                // would collide anyway. Alternating sides by position doubles
                // the effective clearance between neighbours.
                if (i % 2 == 1) perp = Offset(-perp.x, -perp.y)
                // First line to touch a shared interchange point wins, so the
                // choice stays stable regardless of which lines are filtered in.
                result.putIfAbsent(list[i].id, perp)
            }
        }
        return result
    }

    private fun computePositions(): Map<String, Offset> {
        val result = mutableMapOf<String, Offset>()
        MetroLine.entries.forEach { line ->
            val list = MockData.stationsOf(line)
            val anchorIdx = list.indices.filter { anchors.containsKey(list[it].id) }
            for (seg in 0 until anchorIdx.size - 1) {
                val startIdx = anchorIdx[seg]
                val endIdx = anchorIdx[seg + 1]
                val startPt = anchors.getValue(list[startIdx].id)
                val endPt = anchors.getValue(list[endIdx].id)
                val bend = bendsAfter[list[startIdx].id]
                val poly = if (bend != null) listOf(startPt, bend, endPt) else listOf(startPt, endPt)
                val span = endIdx - startIdx
                for (j in startIdx..endIdx) {
                    val t = if (span == 0) 0f else (j - startIdx).toFloat() / span
                    result[list[j].id] = pointAtFraction(poly, t)
                }
            }
        }
        return result
    }

    /** Walks a polyline by cumulative arc length and returns the point at fraction [t]. */
    private fun pointAtFraction(poly: List<Offset>, t: Float): Offset {
        if (poly.size == 1) return poly[0]
        val segLens = FloatArray(poly.size - 1) { i -> (poly[i + 1] - poly[i]).getDistance() }
        val total = segLens.sum()
        if (total <= 0f) return poly.first()
        val target = t.coerceIn(0f, 1f) * total
        var acc = 0f
        for (i in segLens.indices) {
            val segLen = segLens[i]
            if (acc + segLen >= target || i == segLens.lastIndex) {
                val localT = if (segLen > 0f) ((target - acc) / segLen).coerceIn(0f, 1f) else 0f
                return poly[i] + (poly[i + 1] - poly[i]) * localT
            }
            acc += segLen
        }
        return poly.last()
    }

    /** Ordered polyline points for a line, ready to draw as a path. */
    fun linePoints(line: MetroLine): List<Offset> =
        MockData.stationsOf(line).mapNotNull { stationPositions[it.id] }
}
