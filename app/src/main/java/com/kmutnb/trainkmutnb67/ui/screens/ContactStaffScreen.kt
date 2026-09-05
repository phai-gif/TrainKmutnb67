package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.data.Station
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.BrandGradient
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class ChatMessage(val mine: Boolean, val text: String, val time: String)

private fun nowTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactStaffScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    var station by remember { mutableStateOf<Station?>(null) }
    var msg by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var staffTyping by remember { mutableStateOf(false) }
    val thread = remember { mutableStateListOf<ChatMessage>() }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun send() {
        if (station == null || msg.isBlank()) return
        thread.add(ChatMessage(mine = true, text = msg, time = nowTime()))
        msg = ""
        staffTyping = true
        scope.launch {
            delay(900)
            staffTyping = false
            thread.add(ChatMessage(mine = false, text = s.staffReplyStub, time = nowTime()))
        }
    }

    LaunchedEffect(thread.size, staffTyping) {
        val lastIndex = thread.size // +1 for the info banner item, -1 to be 0-indexed = thread.size
        if (lastIndex > 0) listState.animateScrollToItem(lastIndex)
    }

    Column(Modifier.fillMaxSize()) {
        ScreenHeader(s.contactStaff, onBack = { nav.pop() })

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 10.dp),
        ) {
            OutlinedTextField(
                value = station?.let { "${it.label(lang)} · ${it.line.label(lang)}" } ?: s.chooseStationPlaceholder,
                onValueChange = {},
                readOnly = true,
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
                label = { Text(s.chooseStation, fontSize = 12.sp) },
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

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Row(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                        .background(Surface2).padding(12.dp),
                ) {
                    Text("ℹ️ ", fontSize = 13.sp)
                    Text(s.contactStaffNote, color = TextSecondary, fontSize = 12.sp)
                }
            }
            items(thread) { m -> ChatBubble(m) }
            if (staffTyping) item { TypingBubble() }
        }

        ChatInputBar(
            value = msg,
            onValueChange = { msg = it },
            onSend = ::send,
            enabled = station != null,
        )
    }
}

@Composable
private fun ChatBubble(m: ChatMessage) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = if (m.mine) Arrangement.End else Arrangement.Start,
    ) {
        if (!m.mine) {
            Box(
                Modifier.size(28.dp).clip(CircleShape).background(Surface2),
                contentAlignment = Alignment.Center,
            ) { Text("👮", fontSize = 14.sp) }
            Spacer(Modifier.width(8.dp))
        }
        Column(horizontalAlignment = if (m.mine) Alignment.End else Alignment.Start) {
            Text(
                m.text,
                color = if (m.mine) Color.White else TextPrimary,
                fontSize = 13.sp,
                modifier = Modifier
                    .widthIn(max = 260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (m.mine) BrandTeal else Surface2)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
            )
            Spacer(Modifier.height(2.dp))
            Text(m.time, color = TextMuted, fontSize = 10.sp)
        }
    }
}

@Composable
private fun TypingBubble() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Box(
            Modifier.size(28.dp).clip(CircleShape).background(Surface2),
            contentAlignment = Alignment.Center,
        ) { Text("👮", fontSize = 14.sp) }
        Spacer(Modifier.width(8.dp))
        Text(
            "…",
            color = TextMuted,
            fontSize = 13.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Surface2)
                .padding(horizontal = 14.dp, vertical = 10.dp),
        )
    }
}

@Composable
private fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    enabled: Boolean,
) {
    val s = LocalStrings.current
    val canSend = enabled && value.isNotBlank()
    Row(
        Modifier
            .fillMaxWidth()
            .background(Surface1)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            placeholder = { Text(s.messageToStaffHint, color = TextMuted, fontSize = 13.sp) },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(8.dp))
        Box(
            Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(if (canSend) BrandGradient else SolidColor(CardBorder))
                .clickable(enabled = canSend, onClick = onSend),
            contentAlignment = Alignment.Center,
        ) { Text("➤", color = Color.White, fontSize = 18.sp) }
    }
}
