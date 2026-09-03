package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.kmutnb.trainkmutnb67.ui.components.Badge
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.ScreenHeader
import com.kmutnb.trainkmutnb67.ui.theme.BrandBlue
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.Success
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary

@Composable
fun PromotionsScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    val myType = AppState.currentUser?.type

    Column(Modifier.fillMaxWidth()) {
        ScreenHeader(s.promoTitle, onBack = { nav.pop() })
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp, 0.dp, 16.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(MockData.promotions) { p ->
                val forMe = p.forType == null || p.forType == myType
                CardSurface {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(p.emoji, fontSize = 24.sp)
                        Column(Modifier.weight(1f).padding(start = 12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(p.title(lang), color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Spacer(Modifier.width(6.dp))
                                Badge(p.badge, if (p.badge == "FREE") Success else BrandBlue)
                            }
                            Text(p.desc(lang), color = TextSecondary, fontSize = 12.sp)
                            Text("${s.until} ${p.until}", color = TextMuted, fontSize = 11.sp)
                            if (forMe && p.forType != null) {
                                Text("✓ ${p.forType.label(lang)}", color = BrandTeal, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
