package com.kmutnb.trainkmutnb67.data

import androidx.compose.ui.geometry.Offset

/**
 * Hand-tunable schematic map positions. Every station has its own explicit
 * coordinate, so moving a station never redistributes neighboring stations.
 * Interchange station IDs shared by multiple lines intentionally use one point.
 */
object MapLayout {
    const val MAP_WIDTH = 12000f
    const val MAP_HEIGHT = 11700f

    /** stationId -> position in the virtual map coordinate space. */
    val stationPositions: Map<String, Offset> = mapOf(
        "N24" to Offset(5670.0f, 560.0f),
        "N23" to Offset(5670.0f, 750.0f),
        "N22" to Offset(5670.0f, 940.0f),
        "N21" to Offset(5670.0f, 1130.0f),
        "N20" to Offset(5670.0f, 1320.0f),
        "N19" to Offset(5670.0f, 1510.0f),
        "N18" to Offset(5670.0f, 1700.0f),
        "N17" to Offset(5670.0f, 1890.0f),
        "N16" to Offset(5765.0f, 2270.0f),
        "N15" to Offset(5860.0f, 2650.0f),
        "N14" to Offset(5955.0f, 3030.0f),
        "N13" to Offset(6050.0f, 3410.0f),
        "N12" to Offset(6145.0f, 3790.0f),
        "N11" to Offset(6300.0f, 4050.0f),
        "N10" to Offset(6500.0f, 4250.0f),
        "N9" to Offset(6100.0f, 4550.0f),
        "N8" to Offset(5670.0f, 4550.0f),
        "N7" to Offset(5594.0f, 4816.0f),
        "N5" to Offset(5518.0f, 5082.0f),
        "N4" to Offset(5442.0f, 5348.0f),
        "N3" to Offset(5366.0f, 5614.0f),
        "N2" to Offset(5290.0f, 5880.0f),
        "N1" to Offset(5575.0f, 6355.0f),
        "CEN" to Offset(5860.0f, 6830.0f),
        "E1" to Offset(6097.5f, 6830.0f),
        "E2" to Offset(6335.0f, 6830.0f),
        "E3" to Offset(6572.5f, 6830.0f),
        "E4" to Offset(6810.0f, 6830.0f),
        "E5" to Offset(7034.5f, 7089.1f),
        "E6" to Offset(7259.1f, 7348.2f),
        "E7" to Offset(7483.6f, 7607.3f),
        "E8" to Offset(7708.2f, 7866.4f),
        "E9" to Offset(7932.7f, 8125.5f),
        "E10" to Offset(8157.3f, 8384.5f),
        "E11" to Offset(8381.8f, 8643.6f),
        "E12" to Offset(8606.4f, 8902.7f),
        "E13" to Offset(8830.9f, 9161.8f),
        "E14" to Offset(9055.5f, 9420.9f),
        "E15" to Offset(9280.0f, 9680.0f),
        "E16" to Offset(9375.0f, 9870.0f),
        "E17" to Offset(9470.0f, 10060.0f),
        "E18" to Offset(9565.0f, 10250.0f),
        "E19" to Offset(9660.0f, 10440.0f),
        "E20" to Offset(9755.0f, 10630.0f),
        "E21" to Offset(9850.0f, 10820.0f),
        "E22" to Offset(9945.0f, 11010.0f),
        "E23" to Offset(10040.0f, 11200.0f),
        "W1" to Offset(5100.0f, 6830.0f),
        "S1" to Offset(5860.0f, 7305.0f),
        "S2" to Offset(5860.0f, 7780.0f),
        "S3" to Offset(5856.0f, 8470.0f),
        "S4" to Offset(5502.0f, 8560.0f),
        "S5" to Offset(5148.0f, 8650.0f),
        "S6" to Offset(4744.0f, 8690.0f),
        "S7" to Offset(4340.0f, 8730.0f),
        "S8" to Offset(4298.0f, 9458.0f),
        "S9" to Offset(3956.0f, 9686.0f),
        "S10" to Offset(3564.0f, 9814.0f),
        "S11" to Offset(3122.0f, 9892.0f),
        "S12" to Offset(2630.0f, 9870.0f),
        "BL01" to Offset(3100.0f, 9100.0f),
        "BL02" to Offset(3140.0f, 8500.0f),
        "BL03" to Offset(3180.0f, 7900.0f),
        "BL04" to Offset(3220.0f, 7300.0f),
        "BL05" to Offset(3260.0f, 6700.0f),
        "BL06" to Offset(3300.0f, 6100.0f),
        "BL07" to Offset(3340.0f, 5500.0f),
        "BL08" to Offset(3380.0f, 4900.0f),
        "BL09" to Offset(3420.0f, 4450.0f),
        "BL10" to Offset(3490.0f, 4000.0f),
        "BL11" to Offset(4200.0f, 3850.0f),
        "BL12" to Offset(5000.0f, 4100.0f),
        "BL13" to Offset(5670.0f, 4550.0f),
        "BL14" to Offset(6100.0f, 4550.0f),
        "BL15" to Offset(6600.0f, 4550.0f),
        "BL16" to Offset(7000.0f, 4122.5f),
        "BL17" to Offset(7190.0f, 4455.0f),
        "BL18" to Offset(7380.0f, 4787.5f),
        "BL19" to Offset(7570.0f, 5120.0f),
        "BL20" to Offset(7475.0f, 5500.0f),
        "BL21" to Offset(7380.0f, 5880.0f),
        "BL22" to Offset(6810.0f, 6830.0f),
        "BL23" to Offset(6572.5f, 7067.5f),
        "BL24" to Offset(6335.0f, 7305.0f),
        "BL25" to Offset(6097.5f, 7542.5f),
        "BL26" to Offset(5860.0f, 7780.0f),
        "BL27" to Offset(5356.2f, 7631.2f),
        "BL28" to Offset(4802.5f, 7692.5f),
        "BL29" to Offset(4298.8f, 7803.8f),
        "BL30" to Offset(3845.0f, 8015.0f),
        "BL31" to Offset(3491.2f, 8326.2f),
        "BL32" to Offset(3300.0f, 8700.0f),
        "BL33" to Offset(2900.0f, 9500.0f),
        "BL34" to Offset(2630.0f, 9870.0f),
        "BL35" to Offset(2345.0f, 10155.0f),
        "BL36" to Offset(2060.0f, 10440.0f),
        "BL37" to Offset(1775.0f, 10725.0f),
        "BL38" to Offset(1490.0f, 11010.0f),
        "PP01" to Offset(350.0f, 2760.0f),
        "PP02" to Offset(540.0f, 2770.0f),
        "PP03" to Offset(730.0f, 2800.0f),
        "PP04" to Offset(920.0f, 2850.0f),
        "PP05" to Offset(1110.0f, 2920.0f),
        "PP06" to Offset(1300.0f, 3010.0f),
        "PP07" to Offset(1490.0f, 3100.0f),
        "PP08" to Offset(1680.0f, 3160.0f),
        "PP09" to Offset(1870.0f, 3200.0f),
        "PP10" to Offset(2060.0f, 3220.0f),
        "PP11" to Offset(2250.0f, 3220.0f),
        "PP12" to Offset(2440.0f, 3362.5f),
        "PP13" to Offset(2630.0f, 3505.0f),
        "PP14" to Offset(2820.0f, 3647.5f),
        "PP15" to Offset(3010.0f, 3790.0f),
        "PP16" to Offset(3490.0f, 4000.0f),
        "A1" to Offset(11180.0f, 8160.0f),
        "A2" to Offset(10420.0f, 7716.7f),
        "A3" to Offset(9660.0f, 7273.3f),
        "A4" to Offset(8900.0f, 6830.0f),
        "A5" to Offset(8140.0f, 6355.0f),
        "A6" to Offset(7380.0f, 5880.0f),
        "A7" to Offset(6335.0f, 5880.0f),
        "A8" to Offset(5290.0f, 5880.0f),
        "RN01" to Offset(4200.0f, 3850.0f),
        "RN02" to Offset(4606.0f, 3486.0f),
        "RN03" to Offset(4682.0f, 3182.0f),
        "RN04" to Offset(4758.0f, 2878.0f),
        "RN05" to Offset(4834.0f, 2574.0f),
        "RN06" to Offset(4910.0f, 2270.0f),
        "RN07" to Offset(4910.0f, 1795.0f),
        "RN08" to Offset(4910.0f, 1320.0f),
        "RN09" to Offset(4910.0f, 845.0f),
        "RN10" to Offset(4910.0f, 370.0f),
        "RW01" to Offset(4200.0f, 3850.0f),
        "RW02" to Offset(3010.0f, 3790.0f),
        "RW03" to Offset(2630.0f, 4265.0f),
        "RW04" to Offset(2250.0f, 4740.0f),
        "RW05" to Offset(1870.0f, 5215.0f),
        "RW06" to Offset(1490.0f, 5690.0f),
        "G1" to Offset(4340.0f, 8730.0f),
        "G2" to Offset(4625.0f, 8350.0f),
        "G3" to Offset(4910.0f, 7970.0f),
        "YL01" to Offset(6600.0f, 4550.0f),
        "YL02" to Offset(6900.0f, 3900.0f),
        "YL03" to Offset(7350.0f, 4000.0f),
        "YL04" to Offset(7800.0f, 4500.0f),
        "YL05" to Offset(7957.0f, 4833.0f),
        "YL06" to Offset(8114.0f, 5166.0f),
        "YL07" to Offset(8271.0f, 5500.0f),
        "YL08" to Offset(8429.0f, 5833.0f),
        "YL09" to Offset(8586.0f, 6166.0f),
        "YL10" to Offset(8743.0f, 6500.0f),
        "YL11" to Offset(8900.0f, 6830.0f),
        "YL12" to Offset(8931.7f, 7067.5f),
        "YL13" to Offset(8963.3f, 7305.0f),
        "YL14" to Offset(8995.0f, 7542.5f),
        "YL15" to Offset(9026.7f, 7780.0f),
        "YL16" to Offset(9058.3f, 8017.5f),
        "YL17" to Offset(9090.0f, 8255.0f),
        "YL18" to Offset(9121.7f, 8492.5f),
        "YL19" to Offset(9153.3f, 8730.0f),
        "YL20" to Offset(9185.0f, 8967.5f),
        "YL21" to Offset(9216.7f, 9205.0f),
        "YL22" to Offset(9248.3f, 9442.5f),
        "YL23" to Offset(9280.0f, 9680.0f),
        "PK01" to Offset(2250.0f, 3220.0f),
        "PK02" to Offset(2491.8f, 3133.6f),
        "PK03" to Offset(2733.6f, 3047.3f),
        "PK04" to Offset(2975.5f, 2960.9f),
        "PK05" to Offset(3217.3f, 2874.5f),
        "PK06" to Offset(3459.1f, 2788.2f),
        "PK07" to Offset(3700.9f, 2701.8f),
        "PK08" to Offset(3942.7f, 2615.5f),
        "PK09" to Offset(4184.5f, 2529.1f),
        "PK10" to Offset(4426.4f, 2442.7f),
        "PK11" to Offset(4668.2f, 2356.4f),
        "PK12" to Offset(4910.0f, 2270.0f),
        "PK13" to Offset(5290.0f, 2080.0f),
        "PK14" to Offset(5670.0f, 1890.0f),
        "PK15" to Offset(6090.7f, 1862.9f),
        "PK16" to Offset(6511.4f, 1835.7f),
        "PK17" to Offset(6932.1f, 1808.6f),
        "PK18" to Offset(7352.9f, 1781.4f),
        "PK19" to Offset(7773.6f, 1754.3f),
        "PK20" to Offset(8194.3f, 1727.1f),
        "PK21" to Offset(8615.0f, 1700.0f),
        "PK22" to Offset(9035.7f, 1672.9f),
        "PK23" to Offset(9456.4f, 1645.7f),
        "PK24" to Offset(9877.1f, 1618.6f),
        "PK25" to Offset(10297.9f, 1591.4f),
        "PK26" to Offset(10718.6f, 1564.3f),
        "PK27" to Offset(11139.3f, 1537.1f),
        "PK28" to Offset(11560.0f, 1510.0f),
        "PK29" to Offset(4100.0f, 2150.0f),
        "PK30" to Offset(3780.0f, 1920.0f),
    )

    /**
     * Local line direction used to place station labels clear of the track.
     */
    val labelDirections: Map<String, Offset> by lazy { computeLabelDirections() }

    /** Station ID paths split where the network branches or loops. */
    private fun stationIdPaths(line: MetroLine): List<List<String>> = when (line) {
        MetroLine.MRT_PINK -> listOf(
            (1..28).map { "PK%02d".format(it) },
            listOf("PK10", "PK29", "PK30"),
        )
        MetroLine.MRT_BLUE -> listOf(
            listOf("BL01") + (2..32).map { "BL%02d".format(it) } + "BL01",
            listOf("BL01") + (33..38).map { "BL%02d".format(it) },
        )
        else -> listOf(MockData.stationsOf(line).map { it.id })
    }

    private fun computeLabelDirections(): Map<String, Offset> {
        val result = mutableMapOf<String, Offset>()
        MetroLine.entries.forEach { line ->
            stationIdPaths(line).forEach { ids ->
                for (i in ids.indices) {
                    val here = stationPositions[ids[i]] ?: continue
                    val prev = ids.getOrNull(i - 1)?.let { stationPositions[it] }
                    val next = ids.getOrNull(i + 1)?.let { stationPositions[it] }
                    val tangent = when {
                        prev != null && next != null -> next - prev
                        next != null -> next - here
                        prev != null -> here - prev
                        else -> Offset(1f, 0f)
                    }
                    val length = tangent.getDistance()
                    val unit = if (length > 0.001f) Offset(tangent.x / length, tangent.y / length) else Offset(1f, 0f)
                    result.putIfAbsent(ids[i], Offset(unit.y, -unit.x))
                }
            }
        }
        return result
    }

    /** Draw routes with separate paths at branches and schematic line offsets. */
    fun linePaths(line: MetroLine): List<List<Offset>> = stationIdPaths(line).map { ids ->
        val points = ids.mapNotNull { stationPositions[it] }
        if (line != MetroLine.SUKHUMVIT) return@map points

        // BTS and MRT share the Mo Chit/Chatuchak Park and Ha Yaek Lat Phrao/
        // Phahon Yothin interchange dots. Keep the BTS segment between them
        // slightly south of the MRT segment so the two routes remain visible.
        val segmentIndex = ids.zipWithNext().indexOfFirst { (from, to) -> from == "N9" && to == "N8" }
        if (segmentIndex < 0 || segmentIndex + 1 >= points.size) return@map points
        val start = points[segmentIndex]
        val end = points[segmentIndex + 1]
        val tangent = end - start
        val length = tangent.getDistance()
        if (length <= 0.001f) return@map points
        val unit = Offset(tangent.x / length, tangent.y / length)
        val southSide = Offset(unit.y, -unit.x)
        points.toMutableList().apply {
            add(segmentIndex + 1, (start + end) * 0.5f + southSide * 150f)
        }
    }.filter { it.size >= 2 }

    /** Ordered station coordinates for a line's listed station sequence. */
    fun linePoints(line: MetroLine): List<Offset> =
        MockData.stationsOf(line).mapNotNull { stationPositions[it.id] }
}
