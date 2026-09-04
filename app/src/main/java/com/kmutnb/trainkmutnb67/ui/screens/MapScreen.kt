package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.kmutnb.trainkmutnb67.data.MetroLine
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.data.Station
import com.kmutnb.trainkmutnb67.data.StationStatus
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.Chip
import com.kmutnb.trainkmutnb67.ui.components.RainbowTopLine
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import com.kmutnb.trainkmutnb67.ui.theme.Warning

@Composable
fun MapScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    var line by remember { mutableStateOf<MetroLine?>(null) }

    val shownLines = line?.let { listOf(it) } ?: MetroLine.entries.toList()
    val stations = line?.let { MockData.stationsOf(it) } ?: MockData.stations

    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 24.dp),
    ) {
        item {
            Text(
                s.mapTitle,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Chip(s.allLines, line == null, { line = null })
                MetroLine.entries.forEach { l ->
                    Chip(l.label(lang), line == l, { line = l }, leadingColor = l.color)
                }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }

        item {
            Column(Modifier.padding(horizontal = 16.dp)) {
                CardSurface {
                    shownLines.forEach { l ->
                        LineStrip(l, lang)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }

        item {
            Text(
                "${s.stationList} (${stations.size})",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                stations.forEach { st -> StationRow(st, lang) }
            }
        }
    }
}

@Composable
private fun LineStrip(line: MetroLine, lang: com.kmutnb.trainkmutnb67.i18n.Lang) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(14.dp).clip(CircleShape).background(line.color))
            Spacer(Modifier.width(8.dp))
            Text(line.label(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Spacer(Modifier.height(6.dp))
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.Top,
        ) {
            MockData.stationsOf(line).forEach { st ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(58.dp),
                ) {
                    Box(
                        Modifier
                            .size(if (st.interchange) 16.dp else 12.dp)
                            .clip(CircleShape)
                            .background(line.color),
                    )
                    Spacer(Modifier.height(3.dp))
                    Box(Modifier.size(6.dp).clip(CircleShape).background(statusColor(st.status)))
                    Spacer(Modifier.height(3.dp))
                    Text(
                        st.label(lang),
                        color = TextSecondary,
                        fontSize = 8.sp,
                        maxLines = 2,
                    )
                }
            }
        }
    }
}

@Composable
private fun StationRow(st: Station, lang: com.kmutnb.trainkmutnb67.i18n.Lang) {
    val s = LocalStrings.current
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Surface1)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.width(4.dp).height(34.dp).clip(RoundedCornerShape(2.dp)).background(st.line.color))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(st.label(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                if (st.interchange) {
                    Spacer(Modifier.width(6.dp))
                    Text("⇄", color = TextMuted, fontSize = 12.sp)
                }
            }
            Text(
                "${if (lang == com.kmutnb.trainkmutnb67.i18n.Lang.TH) st.en else st.th} · ${st.line.label(lang)}",
                color = TextSecondary,
                fontSize = 11.sp,
            )
        }
        Box(Modifier.size(9.dp).clip(CircleShape).background(statusColor(st.status)))
        Spacer(Modifier.width(6.dp))
        Text(statusLabel(st.status, s), color = statusColor(st.status), fontSize = 10.sp)
    }
}

fun statusColor(status: StationStatus): Color = when (status) {
    StationStatus.NORMAL -> Success
    StationStatus.CROWDED -> Warning
    StationStatus.CLOSED -> Color(0xFFF05252)
}

fun statusLabel(status: StationStatus, s: com.kmutnb.trainkmutnb67.i18n.Strings): String = when (status) {
    StationStatus.NORMAL -> s.statusNormal
    StationStatus.CROWDED -> s.statusCrowded
    StationStatus.CLOSED -> s.statusClosed
}
