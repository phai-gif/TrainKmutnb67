package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.data.TrainCrowd
import com.kmutnb.trainkmutnb67.data.TrainRun
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.Badge
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.RainbowTopLine
import com.kmutnb.trainkmutnb67.ui.components.SegmentedTabs
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import com.kmutnb.trainkmutnb67.ui.theme.Warning
import kotlinx.coroutines.delay

@Composable
fun TrainsScreen(nav: Navigator) {
    val s = LocalStrings.current
    var tab by remember { mutableIntStateOf(0) }

    Column(Modifier.fillMaxWidth()) {
        RainbowTopLine()
        Text(
            s.trainsTitle,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp),
        )
        Column(Modifier.padding(horizontal = 16.dp)) {
            SegmentedTabs(
                options = listOf(s.tabRealtime, s.tabStationStatus, s.tabFare),
                selectedIndex = tab,
                onSelect = { tab = it },
            )
            Spacer(Modifier.height(12.dp))
        }
        when (tab) {
            0 -> RealtimeTab()
            1 -> StationStatusTab()
            else -> FareContent()
        }
    }
}

@Composable
private fun RealtimeTab() {
    val s = LocalStrings.current
    val lang = s.lang
    var tick by remember { mutableIntStateOf(1) }
    // jitter the ETAs a little to feel "live"
    var etas by remember { mutableStateOf(MockData.trains.associate { it.id to it.etaMin }) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(10_000)
            tick++
            etas = MockData.trains.associate {
                it.id to (it.etaMin + (-1..2).random()).coerceAtLeast(0)
            }
        }
    }

    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 0.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("● ${s.updatedEvery} · #$tick", color = Success, fontSize = 12.sp)
        }
        items(MockData.trains) { t ->
            val cur = MockData.station(t.currentStationId)
            val next = MockData.station(t.nextStationId)
            val eta = etas[t.id] ?: t.etaMin
            CardSurface {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.width(4.dp).height(38.dp).clip(RoundedCornerShape(2.dp)).background(t.line.color))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(t.line.label(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(t.headsign(lang), color = TextSecondary, fontSize = 12.sp)
                    }
                    Badge(
                        if (t.delayed) s.delayed else s.onTime,
                        if (t.delayed) Warning else Surface2,
                        if (t.delayed) Color.Black else TextSecondary,
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MiniStation(s.currentStation, cur.label(lang), Modifier.weight(1f))
                    Text("  →  ", color = TextMuted)
                    MiniStation(s.nextStation, next.label(lang), Modifier.weight(1f), highlight = true)
                }
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⏱ $eta ${s.minute}", color = BrandTeal, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(Modifier.width(12.dp))
                    CrowdDots(t.crowd)
                    Spacer(Modifier.width(6.dp))
                    Text(crowdLabel(t.crowd, s), color = TextSecondary, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun MiniStation(label: String, name: String, modifier: Modifier, highlight: Boolean = false) {
    Column(
        modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (highlight) BrandTeal.copy(alpha = 0.12f) else Surface2)
            .padding(10.dp),
    ) {
        Text(label, color = TextMuted, fontSize = 10.sp)
        Text(name, color = if (highlight) BrandTeal else TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

@Composable
private fun CrowdDots(crowd: TrainCrowd) {
    val filled = when (crowd) { TrainCrowd.LIGHT -> 1; TrainCrowd.MEDIUM -> 2; TrainCrowd.HEAVY -> 3 }
    val color = when (crowd) { TrainCrowd.LIGHT -> Success; TrainCrowd.MEDIUM -> Warning; TrainCrowd.HEAVY -> Color(0xFFF05252) }
    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        repeat(3) { i ->
            Box(
                Modifier.size(10.dp).clip(RoundedCornerShape(3.dp))
                    .background(if (i < filled) color else Surface2),
            )
        }
    }
}

fun crowdLabel(crowd: TrainCrowd, s: com.kmutnb.trainkmutnb67.i18n.Strings) = when (crowd) {
    TrainCrowd.LIGHT -> s.crowdLight
    TrainCrowd.MEDIUM -> s.crowdMedium
    TrainCrowd.HEAVY -> s.crowdHeavy
}

@Composable
private fun StationStatusTab() {
    val s = LocalStrings.current
    val lang = s.lang
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 0.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(MockData.stations) { st ->
            Row(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Surface1).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.width(4.dp).height(34.dp).clip(RoundedCornerShape(2.dp)).background(st.line.color))
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(st.label(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(Modifier.width(6.dp))
                        Badge(
                            statusLabel(st.status, s),
                            statusColor(st.status).copy(alpha = 0.2f),
                            statusColor(st.status),
                        )
                    }
                    Text(
                        "${st.line.label(lang)} · ${st.exits} ${s.exits}",
                        color = TextSecondary,
                        fontSize = 11.sp,
                    )
                }
                if (st.hasParking) Text("🅿️", fontSize = 13.sp)
                Spacer(Modifier.width(4.dp))
                if (st.hasLift) Text("♿", fontSize = 13.sp)
            }
        }
    }
}
