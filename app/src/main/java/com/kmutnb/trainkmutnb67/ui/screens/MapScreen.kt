package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.Dialog
import com.kmutnb.trainkmutnb67.data.AppState
import com.kmutnb.trainkmutnb67.data.MetroLine
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.data.Station
import com.kmutnb.trainkmutnb67.data.StationStatus
import com.kmutnb.trainkmutnb67.i18n.Lang
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.nav.Screen
import com.kmutnb.trainkmutnb67.ui.components.Badge
import com.kmutnb.trainkmutnb67.ui.components.Chip
import com.kmutnb.trainkmutnb67.ui.components.MetroMapView
import com.kmutnb.trainkmutnb67.ui.components.MetroMapLegend
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import com.kmutnb.trainkmutnb67.ui.theme.Warning

@Composable
fun MapScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    var line by remember { mutableStateOf<MetroLine?>(null) }
    var selected by remember { mutableStateOf<Station?>(null) }

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
                MetroMapView(
                    lines = shownLines,
                    stations = stations,
                    selectedStationId = selected?.id,
                    lang = lang,
                    onSelectStation = { selected = it },
                )
                Spacer(Modifier.height(6.dp))
                Text(s.pinchToZoomHint, color = TextMuted, fontSize = 11.sp)
                Spacer(Modifier.height(10.dp))
                MetroMapLegend(lines = shownLines, lang = lang)
            }
        }

    }

    selected?.let { st ->
        StationActionDialog(
            station = st,
            lang = lang,
            onDismiss = { selected = null },
            onChooseOrigin = {
                AppState.setFareOrigin(st.id)
                selected = null
                nav.push(Screen.Fare)
            },
            onChooseDestination = {
                AppState.setFareDestination(st.id)
                selected = null
                nav.push(Screen.Fare)
            },
        )
    }
}

@Composable
private fun StationActionDialog(
    station: Station,
    lang: Lang,
    onDismiss: () -> Unit,
    onChooseOrigin: () -> Unit,
    onChooseDestination: () -> Unit,
) {
    val s = LocalStrings.current
    var showInfo by remember(station.id) { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Surface1)
                .padding(20.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Badge(station.line.code, station.line.color)
                Spacer(Modifier.width(8.dp))
                Badge(station.id, Surface2, fg = TextPrimary)
            }
            Spacer(Modifier.height(10.dp))
            Text(station.label(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                if (lang == Lang.TH) station.en else station.th,
                color = TextSecondary,
                fontSize = 13.sp,
            )
            Spacer(Modifier.height(16.dp))

            DialogActionRow("🟢", s.chooseAsOrigin, onChooseOrigin)
            Spacer(Modifier.height(8.dp))
            DialogActionRow("📍", s.chooseAsDestination, onChooseDestination)
            Spacer(Modifier.height(8.dp))
            DialogActionRow("ℹ️", s.viewStationInfo) { showInfo = !showInfo }

            if (showInfo) {
                Spacer(Modifier.height(12.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Surface2)
                        .padding(12.dp),
                ) {
                    Row(Modifier.fillMaxWidth().padding(vertical = 3.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(station.line.label(lang), color = TextSecondary, fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(8.dp).clip(CircleShape).background(statusColor(station.status)))
                            Spacer(Modifier.width(6.dp))
                            Text(statusLabel(station.status, s), color = statusColor(station.status), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    InfoRow(s.interchange, if (station.interchange) "✓" else "—")
                    InfoRow(s.exits, "${station.exits}")
                }
            }

            Spacer(Modifier.height(14.dp))
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text(s.close, color = TextSecondary)
            }
        }
    }
}

@Composable
private fun DialogActionRow(emoji: String, label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Surface2)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(emoji, fontSize = 16.sp)
        Spacer(Modifier.width(10.dp))
        Text(
            label,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        Text("›", color = TextMuted, fontSize = 16.sp)
    }
}

@Composable
private fun InfoRow(label: String, value: String, valueColor: Color = TextPrimary) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, color = TextSecondary, fontSize = 12.sp)
        Text(value, color = valueColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
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
