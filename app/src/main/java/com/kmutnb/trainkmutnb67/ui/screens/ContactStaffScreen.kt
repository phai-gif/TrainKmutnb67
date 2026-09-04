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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.data.Station
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.GradientButton
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.BrandBlue
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactStaffScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    var station by remember { mutableStateOf<Station?>(null) }
    var msg by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val thread = remember { mutableStateListOf<Pair<Boolean, String>>() }

    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    .background(BrandBlue.copy(alpha = 0.14f)).padding(12.dp),
            ) {
                Text("ℹ️ ", fontSize = 13.sp)
                Text(s.contactStaffNote, color = TextSecondary, fontSize = 12.sp)
            }

            Column {
                Text(s.chooseStation, color = TextSecondary, fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(
                        value = station?.let { "${it.label(lang)} · ${it.line.label(lang)}" } ?: s.chooseStationPlaceholder,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        MockData.stations.forEach { st ->
                            DropdownMenuItem(
                                text = { Text("${st.label(lang)} · ${st.line.label(lang)}") },
                                onClick = { station = st; expanded = false },
                            )
                        }
                    }
                }
            }

            Column {
                Text(s.message, color = TextSecondary, fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = msg,
                    onValueChange = { msg = it },
                    placeholder = { Text(s.messageToStaffHint, color = TextMuted) },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            GradientButton(s.send) {
                if (station != null && msg.isNotBlank()) {
                    thread.add(true to msg)
                    thread.add(false to s.staffReplyStub)
                    msg = ""
                }
            }

            thread.forEach { (mine, text) ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = if (mine) Arrangement.End else Arrangement.Start) {
                    Text(
                        text,
                        color = if (mine) TextPrimary else TextSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (mine) BrandTeal.copy(alpha = 0.18f) else Surface2)
                            .padding(10.dp),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(s.emergencyChannels, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            EmergencyRow("📞", s.callCenter, s.allDay, "02-617-7300")
            EmergencyRow("🆘", s.emergency, s.allDay, "02-617-7341")
        }
    }
}

@Composable
private fun EmergencyRow(emoji: String, title: String, sub: String, phone: String) {
    CardSurface {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("$emoji ", fontSize = 16.sp)
            Column(Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(sub, color = TextMuted, fontSize = 11.sp)
            }
            Text(
                phone,
                color = Success,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Success.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }
    }
}
