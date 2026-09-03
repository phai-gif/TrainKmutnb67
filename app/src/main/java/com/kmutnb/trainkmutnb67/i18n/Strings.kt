package com.kmutnb.trainkmutnb67.i18n

import androidx.compose.runtime.staticCompositionLocalOf

enum class Lang { TH, EN }

/**
 * Tiny in-app localisation table. One entry per key, switched instantly at
 * runtime (no Activity recreation). Thai is the default.
 */
class Strings(val lang: Lang) {
    private fun t(th: String, en: String) = if (lang == Lang.TH) th else en

    // App / generic
    val appName get() = "Trainkmutnb67"
    val tagline get() = t("ระบบรถไฟฟ้าอัจฉริยะ", "Smart Metro System")
    val save get() = t("บันทึก", "Save")
    val cancel get() = t("ยกเลิก", "Cancel")
    val confirm get() = t("ยืนยัน", "Confirm")
    val close get() = t("ปิด", "Close")
    val send get() = t("ส่ง", "Send")
    val seeAll get() = t("ดูทั้งหมด", "See all")
    val baht get() = t("บาท", "THB")
    val points get() = t("แต้ม", "pts")
    val minute get() = t("นาที", "min")
    val comingSoon get() = t("อยู่ระหว่างพัฒนา", "Coming soon")

    // Auth
    val signIn get() = t("เข้าสู่ระบบ", "Sign in")
    val signUp get() = t("สมัครสมาชิก", "Sign up")
    val email get() = t("อีเมล", "Email")
    val password get() = t("รหัสผ่าน", "Password")
    val confirmPassword get() = t("ยืนยันรหัสผ่าน", "Confirm password")
    val fullName get() = t("ชื่อ-นามสกุล", "Full name")
    val fullNameHint get() = t("กรอกชื่อ-นามสกุล", "Enter your full name")
    val phone get() = t("เบอร์โทร", "Phone")
    val passengerType get() = t("ประเภทผู้โดยสาร", "Passenger type")
    val forgotPassword get() = t("ลืมรหัสผ่าน?", "Forgot password?")
    val useDemo get() = t("ใช้บัญชีทดลอง (demo@metro.th / demo123)", "Use demo account (demo@metro.th / demo123)")
    val resetPassword get() = t("รีเซ็ตรหัสผ่าน", "Reset password")
    val resetPasswordDesc get() = t(
        "กรอกอีเมลของคุณ ระบบจะส่งลิงก์รีเซ็ตรหัสผ่านไปให้ (จำลอง)",
        "Enter your email and we'll send a reset link (simulated)."
    )
    val resetPasswordSent get() = t("ส่งลิงก์รีเซ็ตแล้ว (จำลอง)", "Reset link sent (simulated)")
    val invalidCredentials get() = t("อีเมลหรือรหัสผ่านไม่ถูกต้อง", "Wrong email or password")
    val fillAllFields get() = t("กรุณากรอกข้อมูลให้ครบ", "Please fill in all fields")
    val passwordMismatch get() = t("รหัสผ่านไม่ตรงกัน", "Passwords do not match")
    val emailTaken get() = t("อีเมลนี้ถูกใช้แล้ว", "Email already registered")
    val registerSuccess get() = t("สมัครสมาชิกสำเร็จ", "Registration complete")

    // Passenger types
    val ptGeneral get() = t("ทั่วไป", "General")
    val ptStudent get() = t("นักเรียน/นักศึกษา", "Student")
    val ptElder get() = t("ผู้สูงอายุ", "Senior")
    val ptChild get() = t("เด็ก", "Child")
    val ptStaff get() = t("พนักงาน", "Staff")

    // Bottom nav
    val navHome get() = t("หน้าแรก", "Home")
    val navWallet get() = t("กระเป๋าเงิน", "Wallet")
    val navMap get() = t("แผนที่", "Map")
    val navTrains get() = t("รถไฟ", "Trains")
    val navProfile get() = t("โปรไฟล์", "Profile")

    // Home
    val greetingMorning get() = t("สวัสดีตอนเช้า", "Good morning")
    val greetingAfternoon get() = t("สวัสดีตอนบ่าย", "Good afternoon")
    val greetingEvening get() = t("สวัสดีตอนเย็น", "Good evening")
    val balance get() = t("ยอดคงเหลือ", "Balance")
    val pointsCollected get() = t("แต้มสะสม", "Points")
    val topUp get() = t("เติมเงิน", "Top up")
    val scanQr get() = t("สแกน QR", "Scan QR")
    val fareCalc get() = t("คำนวณค่าโดยสาร", "Fare calc")
    val quickMap get() = t("แผนที่", "Map")
    val quickStations get() = t("สถานะสถานี", "Stations")
    val quickRewards get() = t("แต้ม", "Rewards")
    val quickPromo get() = t("โปรโมชั่น", "Promotions")
    val nextTrains get() = t("รถไฟขบวนถัดไป", "Next trains")
    val newsAndAlerts get() = t("ข่าวสาร & แจ้งเตือน", "News & alerts")

    // Wallet
    val walletTitle get() = t("กระเป๋าเงิน", "Wallet")
    val walletBalance get() = t("ยอดเงินในกระเป๋า", "Wallet balance")
    val tier get() = t("ระดับ", "Tier")
    val tabTopUp get() = t("เติมเงิน", "Top up")
    val tabPayQr get() = t("QR ชำระ", "Pay QR")
    val tabHistory get() = t("ประวัติ", "History")
    val chooseAmount get() = t("เลือกจำนวนเงิน", "Choose amount")
    val customAmountHint get() = t("หรือกรอกจำนวนเอง (20-10,000)", "Or enter amount (20-10,000)")
    val paymentMethod get() = t("วิธีชำระเงิน", "Payment method")
    val pmCard get() = t("บัตรเครดิต/เดบิต", "Credit / Debit card")
    val pmPromptPay get() = t("PromptPay / QR", "PromptPay / QR")
    val pmBanking get() = t("Internet Banking", "Internet Banking")
    val topUpAmount get() = t("เติมเงิน", "Top up")
    val payQrHeader get() = t("แสดง QR Code ที่เครื่องอ่านบัตรที่ประตูทางเข้า", "Show this QR at the entrance gate reader")
    val memberCode get() = t("รหัสสมาชิก", "Member code")
    val payQrNote get() = t("QR นี้ใช้ได้ครั้งเดียว และหมดอายุใน 5 นาที", "Single-use QR, expires in 5 minutes")
    val refreshQr get() = t("รีเฟรช QR ใหม่", "Refresh QR")
    val qrExpiresIn get() = t("หมดอายุใน", "Expires in")
    val topUpSuccess get() = t("เติมเงินสำเร็จ", "Top-up successful")
    val amountOutOfRange get() = t("จำนวนเงินต้องอยู่ระหว่าง 20 - 10,000", "Amount must be 20 - 10,000")
    val topUpViaQr get() = t("เติมเงินผ่าน QR", "Top-up via QR")

    // Map
    val mapTitle get() = t("แผนที่", "Map")
    val allLines get() = t("ทุกสาย", "All lines")
    val stationList get() = t("รายชื่อสถานี", "Stations")
    val statusNormal get() = t("ปกติ", "Normal")
    val statusCrowded get() = t("แออัด", "Crowded")
    val statusClosed get() = t("ปิด", "Closed")
    val interchange get() = t("จุดเปลี่ยนสาย", "Interchange")
    val exits get() = t("ทางออก", "exits")

    // Trains
    val trainsTitle get() = t("รถไฟ", "Trains")
    val tabRealtime get() = t("เรียลไทม์", "Real-time")
    val tabStationStatus get() = t("สถานะสถานี", "Stations")
    val tabFare get() = t("คำนวณค่าโดยสาร", "Fare")
    val updatedEvery get() = t("อัปเดตทุก 10 วินาที", "Updates every 10s")
    val towards get() = t("ไป", "To")
    val currentStation get() = t("สถานีปัจจุบัน", "Current")
    val nextStation get() = t("สถานีถัดไป", "Next")
    val onTime get() = t("ตรงเวลา", "On time")
    val delayed get() = t("ล่าช้า", "Delayed")
    val crowdLight get() = t("โปร่งสบาย", "Light")
    val crowdMedium get() = t("ปานกลาง", "Medium")
    val crowdHeavy get() = t("แออัด", "Heavy")
    val from get() = t("จาก", "From")
    val to get() = t("ถึง", "To")
    val calculate get() = t("คำนวณ", "Calculate")
    val fareResult get() = t("ค่าโดยสาร", "Fare")
    val distanceStations get() = t("จำนวนสถานี", "Stations")
    val serviceHours get() = t("รอบวิ่งรถไฟ", "Service hours")
    val firstTrain get() = t("เปิดให้บริการ", "First train")
    val lastTrain get() = t("ขบวนสุดท้าย", "Last train")
    val peakFreq get() = t("ความถี่ช่วงพีค", "Peak frequency")
    val offPeakFreq get() = t("ความถี่นอกพีค", "Off-peak frequency")
    val everyRange get() = t("ทุก", "Every")
    val sameStation get() = t("สถานีต้นทางและปลายทางเหมือนกัน", "Origin and destination are the same")
    val payWithWallet get() = t("จ่ายด้วยกระเป๋าเงิน", "Pay with wallet")
    val notEnoughBalance get() = t("ยอดเงินไม่พอ กรุณาเติมเงิน", "Not enough balance, please top up")
    val tripPaid get() = t("ชำระค่าโดยสารสำเร็จ", "Trip paid")

    // Rewards
    val rewardsTitle get() = t("สะสมแต้ม", "Rewards")
    val yourPoints get() = t("แต้มของคุณ", "Your points")
    val redeemCatalog get() = t("แลกของรางวัล", "Redeem catalog")
    val redeem get() = t("แลก", "Redeem")
    val redeemed get() = t("แลกแล้ว", "Redeemed")
    val notEnoughPoints get() = t("แต้มไม่พอ", "Not enough points")
    val redeemSuccess get() = t("แลกรางวัลสำเร็จ", "Redeemed successfully")
    val pointsHint get() = t("รับ 1 แต้มต่อการเดินทางทุก 10 บาท", "Earn 1 point per 10 THB travelled")

    // Promotions
    val promoTitle get() = t("โปรโมชั่น", "Promotions")
    val until get() = t("ถึง", "Until")

    // News
    val newsTitle get() = t("ข่าวสาร", "News")
    val filterAll get() = t("ทั้งหมด", "All")
    val filterUrgent get() = t("ด่วน", "Urgent")
    val filterNews get() = t("ข่าว", "News")
    val tagUrgent get() = t("ด่วน", "URGENT")

    // Profile
    val profileTitle get() = t("โปรไฟล์", "Profile")
    val editProfile get() = t("แก้ไขโปรไฟล์", "Edit profile")
    val emailNotEditable get() = t("อีเมล (ไม่สามารถแก้ไขได้)", "Email (cannot be changed)")
    val language get() = t("ภาษา", "Language")
    val logout get() = t("ออกจากระบบ", "Log out")
    val myPromotions get() = t("โปรโมชั่นของฉัน", "My promotions")
    val contactStaff get() = t("ติดต่อเจ้าหน้าที่ประจำสถานี", "Contact station staff")
    val help get() = t("ช่วยเหลือ (Support)", "Help (Support)")
    val profileSaved get() = t("บันทึกโปรไฟล์แล้ว", "Profile saved")
    val memberSince get() = t("สมาชิกตั้งแต่", "Member since")

    // Contact staff
    val contactStaffNote get() = t(
        "ช่องทางนี้ใช้ติดต่อเจ้าหน้าที่ที่ประจำสถานีโดยตรง ไม่ใช่ฝ่าย Support กลาง",
        "This channel reaches on-site station staff directly, not central support."
    )
    val chooseStation get() = t("เลือกสถานี", "Choose station")
    val chooseStationPlaceholder get() = t("-- เลือกสถานี --", "-- Choose station --")
    val message get() = t("ข้อความ", "Message")
    val messageToStaffHint get() = t("พิมพ์ข้อความถึงเจ้าหน้าที่ที่สถานี...", "Type a message to station staff...")
    val emergencyChannels get() = t("ช่องทางฉุกเฉิน", "Emergency channels")
    val callCenter get() = t("Call Center", "Call Center")
    val emergency get() = t("ฉุกเฉิน", "Emergency")
    val allDay get() = t("ตลอด 24 ชั่วโมง", "24 hours")
    val staffReplyStub get() = t(
        "เจ้าหน้าที่สถานีได้รับข้อความแล้ว จะติดต่อกลับโดยเร็ว (จำลอง)",
        "Station staff received your message and will reply shortly (simulated)."
    )
    val messageSent get() = t("ส่งข้อความแล้ว", "Message sent")
    val selectStationFirst get() = t("กรุณาเลือกสถานีและพิมพ์ข้อความ", "Please pick a station and type a message")

    // Support
    val supportTitle get() = t("ช่วยเหลือ (Support)", "Help (Support)")
    val faq get() = t("คำถามที่พบบ่อย", "FAQ")
    val sendToSupport get() = t("ส่งข้อความหา Support", "Message support")
    val describeIssueHint get() = t("อธิบายปัญหาที่พบ...", "Describe your issue...")
    val sendMessage get() = t("ส่งข้อความ", "Send message")
}

val LocalStrings = staticCompositionLocalOf { Strings(Lang.TH) }
