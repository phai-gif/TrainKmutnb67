package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kmutnb.trainkmutnb67.data.AppState
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.HeaderGradient
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun RewardsScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    var toast by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(toast) { if (toast != null) { delay(2000); toast = null } }

    Column(Modifier.fillMaxWidth()) {
        ScreenHeader(s.rewardsTitle, onBack = { nav.pop() })
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 0.dp, 16.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                        .background(HeaderGradient).padding(18.dp),
                ) {
                    Text(s.yourPoints, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    Text("${AppState.points} pts", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                    Text("${s.tier}: ${AppState.tier}", color = BrandTeal, fontSize = 12.sp)
                }
            }
            item { Text(s.pointsHint, color = TextMuted, fontSize = 12.sp) }
            toast?.let { t -> item { Text(t, color = if (t == s.redeemSuccess) Success else Danger, fontSize = 13.sp) } }
            item {
                Text(s.redeemCatalog, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            items(MockData.rewards) { r ->
                val done = AppState.redeemedRewardIds.contains(r.id)
                CardSurface {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(r.emoji, fontSize = 24.sp)
                        Spacer(Modifier.height(0.dp))
                        Column(Modifier.weight(1f).padding(start = 12.dp)) {
                            Text(r.title(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("${r.cost} pts", color = TextSecondary, fontSize = 12.sp)
                        }
                        val enabled = !done && AppState.points >= r.cost
                        Text(
                            when {
                                done -> s.redeemed
                                AppState.points < r.cost -> s.notEnoughPoints
                                else -> s.redeem
                            },
                            color = if (enabled) BrandTeal else TextMuted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (enabled) BrandTeal.copy(alpha = 0.15f) else Color.Transparent)
                                .then(
                                    if (enabled) Modifier.clickable {
                                        toast = if (AppState.redeem(r)) s.redeemSuccess else s.notEnoughPoints
                                    } else Modifier
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                        )
                    }
                }
            }
        }
    }
}
