package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.GradientButton
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SupportScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    var open by remember { mutableIntStateOf(-1) }
    var msg by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }
    LaunchedEffect(sent) { if (sent) { delay(1800); sent = false } }

    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        ScreenHeader(s.supportTitle, onBack = { nav.pop() })
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(s.faq, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            MockData.faqs.forEachIndexed { i, f ->
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Surface1)
                        .clickable { open = if (open == i) -1 else i }.padding(14.dp),
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(f.q(lang), color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f))
                        Text(if (open == i) "▲" else "▼", color = TextMuted, fontSize = 11.sp)
                    }
                    AnimatedVisibility(visible = open == i) {
                        Text(f.a(lang), color = TextSecondary, fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(s.sendToSupport, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            OutlinedTextField(
                value = msg,
                onValueChange = { msg = it },
                placeholder = { Text(s.describeIssueHint, color = TextMuted) },
                minLines = 4,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
            )
            if (sent) Text("✓ ${s.messageSent}", color = Success, fontSize = 13.sp)
            GradientButton(s.sendMessage) {
                if (msg.isNotBlank()) { sent = true; msg = "" }
            }
        }
    }
}
