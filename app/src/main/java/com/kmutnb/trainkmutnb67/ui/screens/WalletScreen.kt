package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.kmutnb.trainkmutnb67.data.AppState
import com.kmutnb.trainkmutnb67.data.TxnKind
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.GradientButton
import com.kmutnb.trainkmutnb67.ui.components.RainbowTopLine
import com.kmutnb.trainkmutnb67.ui.components.SecondaryButton
import com.kmutnb.trainkmutnb67.ui.components.SegmentedTabs
import com.kmutnb.trainkmutnb67.ui.components.QrCode
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.HeaderGradient
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import com.kmutnb.trainkmutnb67.ui.theme.Warning
import kotlinx.coroutines.delay

@Composable
fun WalletScreen(nav: Navigator) {
    val s = LocalStrings.current
    var tab by remember { mutableIntStateOf(0) }
    var toast by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(toast) {
        if (toast != null) { delay(2200); toast = null }
    }

    Column(Modifier.fillMaxWidth()) {
        Text(
            s.walletTitle,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp),
        )
        Column(Modifier.padding(horizontal = 16.dp)) {
            WalletBalanceCard()
            Spacer(Modifier.height(14.dp))
            SegmentedTabs(
                options = listOf(s.tabTopUp, s.tabPayQr, s.tabHistory),
                selectedIndex = tab,
                onSelect = { tab = it },
            )
            Spacer(Modifier.height(14.dp))
        }

        Box(Modifier.fillMaxWidth()) {
            when (tab) {
                0 -> TopUpTab { toast = it }
                1 -> PayQrTab()
                else -> HistoryTab()
            }
            if (toast != null) {
                Box(
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Success)
                        .padding(12.dp),
                ) { Text(toast!!, color = Color.White, fontWeight = FontWeight.Medium) }
            }
        }
    }
}

@Composable
private fun WalletBalanceCard() {
    val s = LocalStrings.current
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(HeaderGradient)
            .padding(18.dp),
    ) {
        Text(s.walletBalance, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        Text("฿${AppState.balanceBaht}.00", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("● ${s.points} ${AppState.points}", color = BrandTeal, fontSize = 12.sp)
            Text("◆ ${s.tier}: ${AppState.tier}", color = Warning, fontSize = 12.sp)
        }
    }
}

private val quickAmounts = listOf(100, 200, 500, 1000)

@Composable
private fun TopUpTab(onDone: (String) -> Unit) {
    val s = LocalStrings.current
    var selected by remember { mutableIntStateOf(2) } // 500
    var custom by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val amount = custom.toIntOrNull() ?: quickAmounts[selected]
    val memberCode = AppState.currentUser?.memberCode ?: "MT-000000"

    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 4.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Text(s.chooseAmount, color = TextPrimary, fontWeight = FontWeight.Bold) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                quickAmounts.forEachIndexed { i, amt ->
                    val sel = custom.isBlank() && selected == i
                    Box(
                        Modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (sel) BrandTeal.copy(alpha = 0.18f) else Surface1)
                            .border(1.dp, if (sel) BrandTeal else CardBorder, RoundedCornerShape(12.dp))
                            .clickable { selected = i; custom = "" },
                        contentAlignment = Alignment.Center,
                    ) { Text("฿$amt", color = if (sel) BrandTeal else TextSecondary, fontWeight = FontWeight.Bold) }
                }
            }
        }
        item {
            OutlinedTextField(
                value = custom,
                onValueChange = { custom = it.filter { c -> c.isDigit() } },
                placeholder = { Text(s.customAmountHint, color = TextMuted) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item { Text(s.paymentMethod, color = TextSecondary, fontSize = 13.sp) }
        item { RadioRow("📱", s.pmPromptPay, selected = true, onClick = {}) }

        item {
            CardSurface {
                Text(s.tabTopUp + " · QR", color = TextSecondary, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    QrCode("TRAINKMUTNB67-TOPUP:$memberCode", size = 180.dp)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    "🔒 QR ประจำบัญชี — ใช้ซ้ำได้ตลอด / Fixed account QR — reusable",
                    color = TextMuted, fontSize = 11.sp,
                )
            }
        }

        if (error != null) item { Text(error!!, color = Danger, fontSize = 13.sp) }

        item {
            GradientButton("${s.topUpAmount} ฿$amount") {
                if (amount < 20 || amount > 10000) {
                    error = s.amountOutOfRange
                } else {
                    error = null
                    AppState.topUp(amount, viaQr = true)
                    onDone(s.topUpSuccess)
                    custom = ""
                }
            }
        }
    }
}

@Composable
private fun RadioRow(emoji: String, label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, if (selected) BrandTeal else CardBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(18.dp).clip(CircleShape)
                .border(2.dp, if (selected) BrandTeal else TextMuted, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) Box(Modifier.size(9.dp).clip(CircleShape).background(BrandTeal))
        }
        Spacer(Modifier.width(10.dp))
        Text("$emoji  $label", color = TextPrimary, fontSize = 14.sp)
    }
}

@Composable
private fun PayQrTab() {
    val s = LocalStrings.current
    val memberCode = AppState.currentUser?.memberCode ?: "MT-000000"
    val validityMs = 5 * 60 * 1000L
    val rotateMs = 30 * 1000L

    var issuedAt by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var nonce by remember { mutableIntStateOf((100000..999999).random()) }
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            now = System.currentTimeMillis()
            if (now - issuedAt >= rotateMs) {
                issuedAt = now
                nonce = (100000..999999).random()
            }
        }
    }

    val remaining = ((validityMs - (now - issuedAt)).coerceAtLeast(0) / 1000)
    val mm = remaining / 60
    val ss = remaining % 60
    val payload = "TRAINKMUTNB67-PAY:$memberCode:${issuedAt / 1000}:$nonce"

    Column(
        Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(s.payQrHeader, color = TextSecondary, fontSize = 13.sp)
        QrCode(payload, size = 210.dp)
        CardSurface {
            Text(s.memberCode, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.fillMaxWidth())
            Text(
                memberCode,
                color = BrandTeal,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(BrandTeal.copy(alpha = 0.12f))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("⚡", fontSize = 14.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                "${s.payQrNote}  ·  ${s.qrExpiresIn} %d:%02d".format(mm, ss),
                color = BrandTeal,
                fontSize = 12.sp,
            )
        }
        SecondaryButton(s.refreshQr) {
            issuedAt = System.currentTimeMillis()
            nonce = (100000..999999).random()
        }
    }
}

@Composable
private fun HistoryTab() {
    val s = LocalStrings.current
    val lang = s.lang
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 4.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(AppState.transactions) { txn ->
            CardSurface {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(38.dp).clip(RoundedCornerShape(10.dp)).background(Surface2),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            when (txn.kind) {
                                TxnKind.TRIP -> "🚇"; TxnKind.TOPUP -> "⬆️"
                                TxnKind.REDEEM -> "🎁"; TxnKind.PROMO -> "🏷️"
                            },
                            fontSize = 16.sp,
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(txn.title(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(txn.subtitle(lang), color = TextSecondary, fontSize = 12.sp)
                        Text(txn.dateTime, color = TextMuted, fontSize = 11.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        if (txn.amountBaht != 0) {
                            Text(
                                (if (txn.amountBaht > 0) "+฿${txn.amountBaht}" else "-฿${-txn.amountBaht}"),
                                color = if (txn.amountBaht > 0) Success else Danger,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                            )
                        }
                        if (txn.pointsDelta != 0) {
                            Text(
                                (if (txn.pointsDelta > 0) "+${txn.pointsDelta} pts" else "${txn.pointsDelta} pts"),
                                color = if (txn.pointsDelta > 0) Success else Warning,
                                fontSize = 11.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}
