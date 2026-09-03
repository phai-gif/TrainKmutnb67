package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.MockData
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.ui.components.Badge
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary

@Composable
fun NewsScreen(nav: Navigator, focusId: String? = null) {
    val s = LocalStrings.current
    val lang = s.lang
    val ordered = MockData.news.sortedByDescending { it.id == focusId }

    Column(Modifier.fillMaxWidth()) {
        ScreenHeader(s.newsTitle, onBack = { nav.pop() })
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 0.dp, 16.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(ordered) { n ->
                CardSurface {
                    Row {
                        Text(n.emoji, fontSize = 22.sp)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(n.title(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                if (n.urgent) {
                                    Spacer(Modifier.width(6.dp))
                                    Badge(s.tagUrgent, Danger)
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(n.body(lang), color = TextSecondary, fontSize = 13.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(n.date, color = TextMuted, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
