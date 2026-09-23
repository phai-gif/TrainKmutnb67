package com.kmutnb.trainkmutnb67.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.kmutnb.trainkmutnb67.i18n.Lang
import kotlin.random.Random

/**
 * Process-wide, in-memory app state for the prototype. No backend, no database —
 * everything resets when the process is killed. Survives configuration changes
 * because it is a singleton object.
 */
object AppState {

    // ---- language ----
    var lang by mutableStateOf(Lang.TH)
        private set

    fun setLanguage(l: Lang) { lang = l }
    fun toggleLang() { lang = if (lang == Lang.TH) Lang.EN else Lang.TH }

    // ---- theme ----
    var isDarkTheme by mutableStateOf(false)
        private set

    fun setTheme(dark: Boolean) { isDarkTheme = dark }
    fun toggleTheme() { isDarkTheme = !isDarkTheme }

    // ---- pending fare-screen prefill (set by tapping a station on the map) ----
    private var pendingFareFromId: String? = null
    private var pendingFareToId: String? = null

    fun setFareOrigin(stationId: String) { pendingFareFromId = stationId }
    fun setFareDestination(stationId: String) { pendingFareToId = stationId }

    /** Reads and clears the pending prefill so it only applies once. */
    fun consumePendingFare(): Pair<String?, String?> {
        val result = pendingFareFromId to pendingFareToId
        pendingFareFromId = null
        pendingFareToId = null
        return result
    }

    // ---- accounts (mock) ----
    private data class Account(val user: User, val password: String)

    private val accounts = mutableStateListOf(
        Account(
            User(
                name = "สมชาย ใจดี",
                email = "demo@metro.th",
                phone = "0812345678",
                type = PassengerType.GENERAL,
                memberCode = "MT-998584",
                memberSince = "2024-06",
            ),
            password = "demo123",
        )
    )

    // ---- session ----
    var currentUser by mutableStateOf<User?>(null)
        private set

    val isLoggedIn: Boolean get() = currentUser != null

    var balanceBaht by mutableIntStateOf(350)
        private set

    var points by mutableIntStateOf(820)
        private set

    val tier: String get() = tierFor(points)

    val transactions: SnapshotStateList<Txn> = mutableStateListOf<Txn>().apply {
        addAll(MockData.seedTxns())
    }

    val redeemedRewardIds: SnapshotStateList<String> = mutableStateListOf()

    // ---- auth actions ----
    sealed interface AuthResult {
        data object Ok : AuthResult
        data class Error(val messageKey: AuthError) : AuthResult
    }

    enum class AuthError { INVALID, FIELDS, MISMATCH, EMAIL_TAKEN }

    fun login(email: String, password: String): AuthResult {
        if (email.isBlank() || password.isBlank()) return AuthResult.Error(AuthError.FIELDS)
        val acc = accounts.firstOrNull {
            it.user.email.equals(email.trim(), ignoreCase = true) && it.password == password
        } ?: return AuthResult.Error(AuthError.INVALID)
        currentUser = acc.user
        return AuthResult.Ok
    }

    fun loginDemo() { login("demo@metro.th", "demo123") }

    fun register(
        name: String, phone: String, type: PassengerType,
        email: String, password: String, confirm: String,
    ): AuthResult {
        if (name.isBlank() || phone.isBlank() || email.isBlank() || password.isBlank()) {
            return AuthResult.Error(AuthError.FIELDS)
        }
        if (password != confirm) return AuthResult.Error(AuthError.MISMATCH)
        if (accounts.any { it.user.email.equals(email.trim(), ignoreCase = true) }) {
            return AuthResult.Error(AuthError.EMAIL_TAKEN)
        }
        val user = User(
            name = name.trim(),
            email = email.trim(),
            phone = phone.trim(),
            type = type,
            memberCode = "MT-" + Random.nextInt(100000, 999999),
            memberSince = "2026-09",
        )
        accounts.add(Account(user, password))
        currentUser = user
        return AuthResult.Ok
    }

    fun logout() { currentUser = null }

    fun updateProfile(name: String, phone: String) {
        val u = currentUser ?: return
        currentUser = u.copy(name = name.trim(), phone = phone.trim())
    }

    // ---- wallet actions ----
    fun topUp(amount: Int, viaQr: Boolean) {
        balanceBaht += amount
        val earned = amount / 10
        points += earned
        transactions.add(
            0,
            Txn(
                id = "t" + System.currentTimeMillis(),
                kind = TxnKind.TOPUP,
                titleTh = if (viaQr) "เติมเงินผ่าน QR" else "เติมเงิน",
                titleEn = if (viaQr) "Top-up via QR" else "Top up",
                subtitleTh = "PromptPay", subtitleEn = "PromptPay",
                dateTime = now(),
                amountBaht = amount,
                pointsDelta = earned,
            )
        )
    }

    fun payTrip(line: MetroLine, fromTh: String, fromEn: String, toTh: String, toEn: String, fare: Int): Boolean {
        if (fare > balanceBaht) return false
        balanceBaht -= fare
        val earned = fare / 10
        points += earned
        transactions.add(
            0,
            Txn(
                id = "t" + System.currentTimeMillis(),
                kind = TxnKind.TRIP,
                titleTh = line.th, titleEn = line.en,
                subtitleTh = "$fromTh → $toTh", subtitleEn = "$fromEn → $toEn",
                dateTime = now(),
                amountBaht = -fare,
                pointsDelta = earned,
            )
        )
        return true
    }

    fun redeem(reward: Reward): Boolean {
        if (reward.cost > points) return false
        points -= reward.cost
        redeemedRewardIds.add(reward.id)
        transactions.add(
            0,
            Txn(
                id = "t" + System.currentTimeMillis(),
                kind = TxnKind.REDEEM,
                titleTh = "แลก: " + reward.titleTh,
                titleEn = "Redeemed: " + reward.titleEn,
                subtitleTh = "แต้ม -${reward.cost}",
                subtitleEn = "-${reward.cost} pts",
                dateTime = now(),
                amountBaht = 0,
                pointsDelta = -reward.cost,
            )
        )
        return true
    }

    private fun now(): String {
        val d = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.US)
        return d.format(java.util.Date())
    }
}
