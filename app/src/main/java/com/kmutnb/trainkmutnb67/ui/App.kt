package com.kmutnb.trainkmutnb67.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.AppState
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.i18n.Strings
import com.kmutnb.trainkmutnb67.nav.Navigator
import com.kmutnb.trainkmutnb67.nav.Screen
import com.kmutnb.trainkmutnb67.nav.Tab
import com.kmutnb.trainkmutnb67.ui.screens.AuthScreen
import com.kmutnb.trainkmutnb67.ui.screens.ContactStaffScreen
import com.kmutnb.trainkmutnb67.ui.screens.EditProfileScreen
import com.kmutnb.trainkmutnb67.ui.screens.FareScreen
import com.kmutnb.trainkmutnb67.ui.screens.HomeScreen
import com.kmutnb.trainkmutnb67.ui.screens.MapScreen
import com.kmutnb.trainkmutnb67.ui.screens.NewsScreen
import com.kmutnb.trainkmutnb67.ui.screens.ProfileScreen
import com.kmutnb.trainkmutnb67.ui.screens.PromotionsScreen
import com.kmutnb.trainkmutnb67.ui.screens.RewardsScreen
import com.kmutnb.trainkmutnb67.ui.screens.SupportScreen
import com.kmutnb.trainkmutnb67.ui.screens.TrainsScreen
import com.kmutnb.trainkmutnb67.ui.screens.WalletScreen
import com.kmutnb.trainkmutnb67.ui.theme.BgDark
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.TextMuted

@Composable
fun AppRoot() {
    val strings = Strings(AppState.lang)
    CompositionLocalProvider(LocalStrings provides strings) {
        if (!AppState.isLoggedIn) {
            AuthScreen()
        } else {
            MainShell()
        }
    }
}

@Composable
private fun MainShell() {
    val nav = remember { Navigator() }
    val s = LocalStrings.current

    BackHandler(enabled = nav.canPop) { nav.pop() }

    Scaffold(
        containerColor = BgDark,
        bottomBar = { BottomBar(nav) },
    ) { inner ->
        Box(Modifier.fillMaxSize().padding(inner)) {
            when (val screen = nav.current) {
                Screen.Home -> HomeScreen(nav)
                is Screen.Wallet -> WalletScreen(nav)
                Screen.Map -> MapScreen(nav)
                Screen.Trains -> TrainsScreen(nav)
                Screen.Profile -> ProfileScreen(nav)
                Screen.EditProfile -> EditProfileScreen(nav)
                Screen.Promotions -> PromotionsScreen(nav)
                Screen.News -> NewsScreen(nav)
                is Screen.NewsDetail -> NewsScreen(nav, focusId = screen.id)
                Screen.Rewards -> RewardsScreen(nav)
                Screen.ContactStaff -> ContactStaffScreen(nav)
                Screen.Support -> SupportScreen(nav)
                Screen.Fare -> FareScreen(nav)
            }
        }
    }
}

@Composable
private fun BottomBar(nav: Navigator) {
    val s = LocalStrings.current
    val items = listOf(
        Triple(Tab.HOME, "🏠", s.navHome),
        Triple(Tab.WALLET, "👛", s.navWallet),
        Triple(Tab.MAP, "🗺️", s.navMap),
        Triple(Tab.TRAINS, "🚆", s.navTrains),
        Triple(Tab.PROFILE, "👤", s.navProfile),
    )
    NavigationBar(containerColor = Surface1) {
        items.forEach { (tab, emoji, label) ->
            val selected = nav.currentTab == tab
            NavigationBarItem(
                selected = selected,
                onClick = { nav.selectTab(tab) },
                icon = { Text(emoji, fontSize = 18.sp) },
                label = {
                    Text(
                        label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandTeal,
                    selectedTextColor = BrandTeal,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Surface1,
                ),
            )
        }
    }
}