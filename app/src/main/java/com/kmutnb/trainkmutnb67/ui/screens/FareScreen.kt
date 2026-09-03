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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.AppState
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.data.Station
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.GradientButton
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary

@Composable
fun FareScreen(nav: Navigator) {
    val s = LocalStrings.current
    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        ScreenHeader(s.fareCalc, onBack = { nav.pop() })
        FareContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FareContent() {
    val s = LocalStrings.current
    val lang = s.lang
    val all = MockData.stations
    var from by remember { mutableStateOf(all.first()) }
    var to by remember { mutableStateOf(all[6]) }
    var result by remember { mutableStateOf<FareResult?>(null) }
    var toast by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StationDropdown(s.from, from, all, lang) { from = it; result = null }
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("↕", color = TextSecondary, fontSize = 18.sp,
                modifier = Modifier.padding(4.dp))
        }
        StationDropdown(s.to, to, all, lang) { to = it; result = null }

        GradientButton(s.calculate) {
            if (from.id == to.id) { toast = s.sameStation; return@GradientButton }
            val gap = kotlin.math.abs(all.indexOf(from) - all.indexOf(to)).coerceAtLeast(1)
            val base = MockData.baseFare(gap)
            val disc = AppState.currentUser?.type?.discountPct ?: 0
            val net = (base * (100 - disc) / 100)
            result = FareResult(base, disc, net, gap)
            toast = null
        }

        toast?.let { Text(it, color = Danger, fontSize = 13.sp) }

        result?.let { r ->
            CardSurface {
                FareRow(s.distanceStations, "${r.gap}")
                FareRow(s.fareResult, "฿${r.base}")
                if (r.discountPct > 0) FareRow("${AppState.currentUser?.type?.label(lang)} -${r.discountPct}%", "-฿${r.base - r.net}")
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(s.payWithWallet, color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("฿${r.net}", color = BrandTeal, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
            GradientButton(s.payWithWallet + " ฿${r.net}") {
                val ok = AppState.payTrip(
                    from.line, from.th, from.en, to.th, to.en, r.net,
                )
                toast = if (ok) s.tripPaid else s.notEnoughBalance
                if (ok) result = null
            }
            toast?.let {
                Text(it, color = if (it == s.tripPaid) Success else Danger, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(8.dp))
        CardSurface {
            Text(s.serviceHours, color = TextPrimary, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            FareRow(s.firstTrain, "${from.line.firstTrain} น.")
            FareRow(s.lastTrain, "${from.line.lastTrain} น.")
            FareRow(s.peakFreq, "${s.everyRange} ${from.line.peak} ${s.minute}")
            FareRow(s.offPeakFreq, "${s.everyRange} ${from.line.offPeak} ${s.minute}")
        }
    }
}

data class FareResult(val base: Int, val discountPct: Int, val net: Int, val gap: Int)

@Composable
private fun FareRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 3.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Text(value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StationDropdown(
    label: String,
    selected: Station,
    all: List<Station>,
    lang: com.kmutnb.trainkmutnb67.i18n.Lang,
    onSelect: (Station) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = "${selected.label(lang)} (${selected.line.label(lang)})",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                all.forEach { st ->
                    DropdownMenuItem(
                        text = { Text("${st.label(lang)} · ${st.line.label(lang)}") },
                        onClick = { onSelect(st); expanded = false },
                    )
                }
            }
        }
    }
}
