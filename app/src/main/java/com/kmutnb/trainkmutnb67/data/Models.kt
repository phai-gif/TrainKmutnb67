package com.kmutnb.trainkmutnb67.data

import androidx.compose.ui.graphics.Color
import com.kmutnb.trainkmutnb67.i18n.Lang
import com.kmutnb.trainkmutnb67.ui.theme.LineArl
import com.kmutnb.trainkmutnb67.ui.theme.LineMrtBlue
import com.kmutnb.trainkmutnb67.ui.theme.LineMrtPurple
import com.kmutnb.trainkmutnb67.ui.theme.LineSilom
import com.kmutnb.trainkmutnb67.ui.theme.LineSukhumvit

enum class PassengerType(val th: String, val en: String, val discountPct: Int) {
    GENERAL("ทั่วไป", "General", 0),
    STUDENT("นักเรียน/นักศึกษา", "Student", 30),
    ELDER("ผู้สูงอายุ", "Senior", 50),
    CHILD("เด็ก", "Child", 50),
    STAFF("พนักงาน", "Staff", 20);

    fun label(lang: Lang) = if (lang == Lang.TH) th else en
}

enum class MetroLine(
    val code: String,
    val th: String,
    val en: String,
    val color: Color,
    val firstTrain: String,
    val lastTrain: String,
    val peak: String,
    val offPeak: String,
) {
    SUKHUMVIT("BTS", "BTS สายสุขุมวิท", "BTS Sukhumvit", LineSukhumvit, "05:15", "00:24", "2-4", "5-8"),
    SILOM("BTS", "BTS สายสีลม", "BTS Silom", LineSilom, "05:30", "00:16", "3-5", "5-8"),
    MRT_BLUE("MRT", "MRT สายสีน้ำเงิน", "MRT Blue", LineMrtBlue, "05:30", "00:00", "3-5", "5-7"),
    MRT_PURPLE("MRT", "MRT สายสีม่วง", "MRT Purple", LineMrtPurple, "05:30", "00:00", "5-6", "8-10"),
    ARL("ARL", "Airport Rail Link", "Airport Rail Link", LineArl, "05:30", "00:00", "10", "12-15"),
    ;

    fun label(lang: Lang) = if (lang == Lang.TH) th else en
}

enum class StationStatus { NORMAL, CROWDED, CLOSED }

data class Station(
    val id: String,
    val th: String,
    val en: String,
    val line: MetroLine,
    val status: StationStatus = StationStatus.NORMAL,
    val interchange: Boolean = false,
    val exits: Int = 4,
    val hasParking: Boolean = false,
    val hasLift: Boolean = true,
) {
    fun label(lang: Lang) = if (lang == Lang.TH) th else en
}

enum class TrainCrowd { LIGHT, MEDIUM, HEAVY }

data class TrainRun(
    val id: String,
    val line: MetroLine,
    val headsignTh: String,
    val headsignEn: String,
    val currentStationId: String,
    val nextStationId: String,
    val etaMin: Int,
    val delayed: Boolean,
    val crowd: TrainCrowd,
) {
    fun headsign(lang: Lang) = if (lang == Lang.TH) headsignTh else headsignEn
}

enum class TxnKind { TRIP, TOPUP, REDEEM, PROMO }

data class Txn(
    val id: String,
    val kind: TxnKind,
    val titleTh: String,
    val titleEn: String,
    val subtitleTh: String,
    val subtitleEn: String,
    val dateTime: String,
    val amountBaht: Int,   // negative = spent, positive = added
    val pointsDelta: Int,
) {
    fun title(lang: Lang) = if (lang == Lang.TH) titleTh else titleEn
    fun subtitle(lang: Lang) = if (lang == Lang.TH) subtitleTh else subtitleEn
}

data class NewsItem(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val bodyTh: String,
    val bodyEn: String,
    val date: String,
    val urgent: Boolean,
    val emoji: String,
) {
    fun title(lang: Lang) = if (lang == Lang.TH) titleTh else titleEn
    fun body(lang: Lang) = if (lang == Lang.TH) bodyTh else bodyEn
}

data class Promotion(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val descTh: String,
    val descEn: String,
    val badge: String,
    val until: String,
    val emoji: String,
    val forType: PassengerType? = null,
) {
    fun title(lang: Lang) = if (lang == Lang.TH) titleTh else titleEn
    fun desc(lang: Lang) = if (lang == Lang.TH) descTh else descEn
}

data class Reward(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val cost: Int,
    val emoji: String,
) {
    fun title(lang: Lang) = if (lang == Lang.TH) titleTh else titleEn
}

data class Faq(
    val qTh: String,
    val qEn: String,
    val aTh: String,
    val aEn: String,
) {
    fun q(lang: Lang) = if (lang == Lang.TH) qTh else qEn
    fun a(lang: Lang) = if (lang == Lang.TH) aTh else aEn
}

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val type: PassengerType,
    val memberCode: String,
    val memberSince: String,
)

fun tierFor(points: Int): String = when {
    points >= 5000 -> "Platinum"
    points >= 2000 -> "Gold"
    points >= 500 -> "Silver"
    else -> "Bronze"
}
