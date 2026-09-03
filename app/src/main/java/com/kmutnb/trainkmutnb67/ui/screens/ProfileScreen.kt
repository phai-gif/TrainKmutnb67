package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.kmutnb.trainkmutnb67.i18n.Lang
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.nav.Screen
import com.kmutnb.trainkmutnb67.ui.components.CardSurface
import com.kmutnb.trainkmutnb67.ui.components.RainbowTopLine
import com.kmutnb.trainkmutnb67.ui.components.SegmentedTabs
import com.kmutnb.trainkmutnb67.ui.theme.BrandGradient
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary
import com.kmutnb.trainkmutnb67.ui.theme.Warning

@Composable
fun ProfileScreen(nav: Navigator) {
    val s = LocalStrings.current
    val lang = s.lang
    val user = AppState.currentUser ?: return

    Column(
        Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
    ) {
        RainbowTopLine()
        Text(
            s.profileTitle,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp),
        )
        Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            CardSurface {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(56.dp).clip(CircleShape).background(BrandGradient),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            user.name.take(1),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                    }
                    Column(Modifier.weight(1f).padding(start = 14.dp)) {
                        Text(user.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text(user.email, color = TextSecondary, fontSize = 12.sp)
                        Text("${user.memberCode} · ${user.type.label(lang)}", color = TextMuted, fontSize = 11.sp)
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Text("● ${AppState.points} ${s.points}", color = BrandTeal, fontSize = 12.sp)
                    Text("◆ ${s.tier}: ${AppState.tier}", color = Warning, fontSize = 12.sp)
                    Text("${s.memberSince} ${user.memberSince}", color = TextMuted, fontSize = 12.sp)
                }
            }

            CardSurface {
                Text(s.language, color = TextSecondary, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                SegmentedTabs(
                    options = listOf("ไทย", "English"),
                    selectedIndex = if (lang == Lang.TH) 0 else 1,
                    onSelect = { AppState.setLanguage(if (it == 0) Lang.TH else Lang.EN) },
                )
            }

            MenuRow("✏️", s.editProfile) { nav.push(Screen.EditProfile) }
            MenuRow("🏷️", s.myPromotions) { nav.push(Screen.Promotions) }
            MenuRow("🎁", s.rewardsTitle) { nav.push(Screen.Rewards) }
            MenuRow("💬", s.contactStaff) { nav.push(Screen.ContactStaff) }
            MenuRow("❓", s.help) { nav.push(Screen.Support) }
            MenuRow("🚪", s.logout, danger = true) { AppState.logout() }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MenuRow(emoji: String, label: String, danger: Boolean = false, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Surface1)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(emoji, fontSize = 16.sp)
        Spacer(Modifier.height(0.dp))
        Text(
            label,
            color = if (danger) Danger else TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f).padding(start = 12.dp),
        )
        Text("›", color = TextMuted, fontSize = 18.sp)
    }
}
