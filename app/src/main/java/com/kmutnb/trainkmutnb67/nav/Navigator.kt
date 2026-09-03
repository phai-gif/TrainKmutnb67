package com.kmutnb.trainkmutnb67.nav

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf

sealed class Screen(val root: Tab?) {
    // top-level tabs
    data object Home : Screen(Tab.HOME)
    data object Wallet : Screen(Tab.WALLET)
    data object Map : Screen(Tab.MAP)
    data object Trains : Screen(Tab.TRAINS)
    data object Profile : Screen(Tab.PROFILE)

    // pushed screens
    data object EditProfile : Screen(Tab.PROFILE)
    data object Promotions : Screen(Tab.PROFILE)
    data object News : Screen(Tab.HOME)
    data class NewsDetail(val id: String) : Screen(Tab.HOME)
    data object Rewards : Screen(Tab.HOME)
    data object ContactStaff : Screen(Tab.PROFILE)
    data object Support : Screen(Tab.PROFILE)
    data object Fare : Screen(Tab.TRAINS)
}

enum class Tab { HOME, WALLET, MAP, TRAINS, PROFILE }

class Navigator {
    private val stack: SnapshotStateList<Screen> = mutableStateListOf(Screen.Home)

    var currentTab by mutableStateOf(Tab.HOME)
        private set

    val current: Screen get() = stack.last()
    val canPop: Boolean get() = stack.size > 1

    fun push(screen: Screen) {
        stack.add(screen)
        screen.root?.let { currentTab = it }
    }

    fun pop() {
        if (stack.size > 1) {
            stack.removeAt(stack.lastIndex)
            stack.last().root?.let { currentTab = it }
        }
    }

    fun selectTab(tab: Tab) {
        currentTab = tab
        stack.clear()
        stack.add(
            when (tab) {
                Tab.HOME -> Screen.Home
                Tab.WALLET -> Screen.Wallet
                Tab.MAP -> Screen.Map
                Tab.TRAINS -> Screen.Trains
                Tab.PROFILE -> Screen.Profile
            }
        )
    }
}
