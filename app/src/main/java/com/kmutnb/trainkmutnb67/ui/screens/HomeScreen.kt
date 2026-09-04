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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.kmutnb.trainkmutnb67.nav.Screen
import com.kmutnb.trainkmutnb67.nav.Tab
import com.kmutnb.trainkmutnb67.ui.components.Badge
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.Chip
import com.kmutnb.trainkmutnb67.ui.components.SectionHeader
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.HeaderGradient
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.Surface2
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import com.kmutnb.trainkmutnb67.ui.theme.Warning
import java.util.Calendar

@Composable
fun HomeScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    val user = AppState.currentUser
    var newsFilter by remember { mutableIntStateOf(0) }

    val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 5..11 -> s.greetingMorning
        in 12..17 -> s.greetingAfternoon
        else -> s.greetingEvening
    }

    val filteredNews = when (newsFilter) {
        1 -> MockData.news.filter { it.urgent }
        2 -> MockData.news.filter { !it.urgent }
        else -> MockData.news
    }

    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("$greeting 👋", color = TextSecondary, fontSize = 13.sp)
                    Text(
                        user?.name ?: "",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
                Box(
                    Modifier.size(40.dp).clip(CircleShape).background(Surface1),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("🔔", fontSize = 18.sp)
                    Box(
                        Modifier.align(Alignment.TopEnd).size(16.dp).clip(CircleShape).background(Danger),
                        contentAlignment = Alignment.Center,
                    ) { Text("2", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold) }
                }
            }
        }

        item {
            Column(Modifier.padding(horizontal = 16.dp)) {
                BalanceCard(nav)
            }
        }

        item {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                QuickTile("🗺️", s.quickMap, Modifier.weight(1f)) { nav.selectTab(Tab.MAP) }
                QuickTile("🚉", s.quickStations, Modifier.weight(1f)) { nav.selectTab(Tab.TRAINS) }
                QuickTile("🎁", s.quickRewards, Modifier.weight(1f)) { nav.push(Screen.Rewards) }
                QuickTile("🏷️", s.quickPromo, Modifier.weight(1f)) { nav.push(Screen.Promotions) }
            }
        }

        item {
            Column(Modifier.padding(horizontal = 16.dp)) {
                SectionHeader("🚆 " + s.nextTrains, s.seeAll) { nav.selectTab(Tab.TRAINS) }
            }
        }
        items3(MockData.trains.take(3)) { t ->
            val cur = MockData.station(t.currentStationId)
            val next = MockData.station(t.nextStationId)
            Column(Modifier.padding(horizontal = 16.dp)) {
                CardSurface {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.width(4.dp).height(40.dp).clip(RoundedCornerShape(2.dp)).background(t.line.color))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(t.line.label(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("→ ${next.label(lang)}", color = TextSecondary, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("${t.etaMin} ${s.minute}", color = BrandTeal, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Badge(
                                if (t.delayed) s.delayed else s.onTime,
                                if (t.delayed) Warning else Surface2,
                                if (t.delayed) Color.Black else TextSecondary,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
        }

        item {
            Column(Modifier.padding(horizontal = 16.dp)) {
                SectionHeader("📰 " + s.newsAndAlerts, s.seeAll) { nav.push(Screen.News) }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Chip(s.filterAll, newsFilter == 0, { newsFilter = 0 })
                    Chip(s.filterUrgent, newsFilter == 1, { newsFilter = 1 })
                    Chip(s.filterNews, newsFilter == 2, { newsFilter = 2 })
                }
                Spacer(Modifier.height(8.dp))
            }
        }
        items3(filteredNews) { n ->
            Column(Modifier.padding(horizontal = 16.dp)) {
                CardSurface(Modifier.clickable { nav.push(Screen.NewsDetail(n.id)) }) {
                    Row {
                        Text(n.emoji, fontSize = 20.sp)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(n.title(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                if (n.urgent) {
                                    Spacer(Modifier.width(6.dp))
                                    Badge(s.tagUrgent, Danger)
                                }
                            }
                            Text(n.body(lang), color = TextSecondary, fontSize = 12.sp, maxLines = 2)
                            Text(n.date, color = TextMuted, fontSize = 11.sp)
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

private fun <T> LazyListScope.items3(
    list: List<T>,
    content: @Composable (T) -> Unit,
) {
    items(list) { item -> content(item) }
}

@Composable
private fun BalanceCard(nav: Navigator) {
    val s = LocalStrings.current
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(HeaderGradient)
            .padding(18.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(s.balance, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Text("฿${AppState.balanceBaht}.00", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 26.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(s.pointsCollected, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Text("${AppState.points} pts", color = BrandTeal, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HeaderAction("＋", s.topUp, Modifier.weight(1f)) { nav.selectTab(Tab.WALLET) }
            HeaderAction("▣", s.scanQr, Modifier.weight(1f)) { nav.selectTab(Tab.WALLET) }
            HeaderAction("↗", s.fareCalc, Modifier.weight(1f)) { nav.push(Screen.Fare) }
        }
    }
}

@Composable
private fun HeaderAction(icon: String, label: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.12f))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(icon, color = Color.White, fontSize = 16.sp)
        Spacer(Modifier.height(4.dp))
        Text(label, color = Color.White, fontSize = 11.sp)
    }
}

@Composable
private fun QuickTile(emoji: String, label: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Surface1)
            .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(Modifier.height(6.dp))
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}