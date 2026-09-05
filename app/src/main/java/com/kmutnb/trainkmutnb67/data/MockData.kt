package com.kmutnb.trainkmutnb67.data

import com.kmutnb.trainkmutnb67.data.StationStatus.CROWDED
import com.kmutnb.trainkmutnb67.data.StationStatus.NORMAL

object MockData {

    // ---------------------------------------------------------------- Stations
    // Ordered north/west -> south/east along each line (subset of the real lines).
    val stations: List<Station> = buildList {
        // BTS Sukhumvit
        val sukhumvit = listOf(
            s("N9", "หมอชิต", "Mo Chit", MetroLine.SUKHUMVIT, NORMAL, interchange = true, exits = 4, parking = true),
            s("N7", "สะพานควาย", "Saphan Khwai", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N5", "อารีย์", "Ari", MetroLine.SUKHUMVIT, CROWDED, exits = 4),
            s("N4", "สนามเป้า", "Sanam Pao", MetroLine.SUKHUMVIT, NORMAL, exits = 2),
            s("N3", "อนุสาวรีย์ชัยสมรภูมิ", "Victory Monument", MetroLine.SUKHUMVIT, CROWDED, exits = 4),
            s("N1", "ราชเทวี", "Ratchathewi", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("CEN", "สยาม", "Siam", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 8),
            s("E1", "ชิดลม", "Chit Lom", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E2", "เพลินจิต", "Phloen Chit", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E3", "นานา", "Nana", MetroLine.SUKHUMVIT, CROWDED, exits = 4),
            s("E4", "อโศก", "Asok", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 6),
            s("E5", "พร้อมพงษ์", "Phrom Phong", MetroLine.SUKHUMVIT, NORMAL, exits = 6),
            s("E6", "ทองหล่อ", "Thong Lo", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E7", "เอกมัย", "Ekkamai", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E8", "พระโขนง", "Phra Khanong", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E9", "อ่อนนุช", "On Nut", MetroLine.SUKHUMVIT, CROWDED, exits = 6, parking = true),
        )
        addAll(sukhumvit)
        // BTS Silom
        addAll(
            listOf(
                s("W1", "สนามกีฬาแห่งชาติ", "National Stadium", MetroLine.SILOM, NORMAL, exits = 2),
                s("CEN2", "สยาม", "Siam", MetroLine.SILOM, CROWDED, interchange = true, exits = 8),
                s("S1", "ราชดำริ", "Ratchadamri", MetroLine.SILOM, NORMAL, exits = 4),
                s("S2", "ศาลาแดง", "Sala Daeng", MetroLine.SILOM, CROWDED, interchange = true, exits = 4),
                s("S3", "ช่องนนทรี", "Chong Nonsi", MetroLine.SILOM, NORMAL, exits = 4),
                s("S5", "สุรศักดิ์", "Surasak", MetroLine.SILOM, NORMAL, exits = 3),
                s("S6", "สะพานตากสิน", "Saphan Taksin", MetroLine.SILOM, CROWDED, exits = 2),
                s("S8", "กรุงธนบุรี", "Krung Thon Buri", MetroLine.SILOM, NORMAL, interchange = true, exits = 4),
                s("S9", "วงเวียนใหญ่", "Wongwian Yai", MetroLine.SILOM, NORMAL, exits = 4),
                s("S12", "บางหว้า", "Bang Wa", MetroLine.SILOM, NORMAL, interchange = true, exits = 4, parking = true),
            )
        )
        // MRT Blue
        addAll(
            listOf(
                s("BL1", "หัวลำโพง", "Hua Lamphong", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL2", "สามย่าน", "Sam Yan", MetroLine.MRT_BLUE, NORMAL, exits = 2),
                s("BL3", "สีลม", "Si Lom", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 4),
                s("BL4", "ลุมพินี", "Lumphini", MetroLine.MRT_BLUE, NORMAL, exits = 3),
                s("BL5", "คลองเตย", "Khlong Toei", MetroLine.MRT_BLUE, NORMAL, exits = 3),
                s("BL6", "ศูนย์การประชุมแห่งชาติสิริกิติ์", "QSNCC", MetroLine.MRT_BLUE, NORMAL, exits = 3),
                s("BL7", "สุขุมวิท", "Sukhumvit", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 3),
                s("BL8", "เพชรบุรี", "Phetchaburi", MetroLine.MRT_BLUE, NORMAL, interchange = true, exits = 2),
                s("BL9", "พระราม 9", "Phra Ram 9", MetroLine.MRT_BLUE, CROWDED, exits = 3, parking = true),
                s("BL10", "ศูนย์วัฒนธรรมแห่งประเทศไทย", "Thailand Cultural Centre", MetroLine.MRT_BLUE, NORMAL, interchange = true, exits = 4),
                s("BL12", "ลาดพร้าว", "Lat Phrao", MetroLine.MRT_BLUE, NORMAL, interchange = true, exits = 4),
                s("BL15", "จตุจักร", "Chatuchak Park", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 3, parking = true),
            )
        )
        // MRT Purple
        addAll(
            listOf(
                s("PP16", "เตาปูน", "Tao Poon", MetroLine.MRT_PURPLE, NORMAL, interchange = true, exits = 4),
                s("PP15", "บางซ่อน", "Bang Son", MetroLine.MRT_PURPLE, NORMAL, interchange = true, exits = 3),
                s("PP14", "วงศ์สว่าง", "Wong Sawang", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP13", "แยกติวานนท์", "Yaek Tiwanon", MetroLine.MRT_PURPLE, NORMAL, exits = 2),
                s("PP11", "ศูนย์ราชการนนทบุรี", "Nonthaburi Civic Centre", MetroLine.MRT_PURPLE, NORMAL, interchange = true, exits = 4, parking = true),
                s("PP10", "บางกระสอ", "Bang Krasor", MetroLine.MRT_PURPLE, NORMAL, exits = 2),
                s("PP08", "บางพลู", "Bang Phlu", MetroLine.MRT_PURPLE, NORMAL, exits = 2),
                s("PP05", "คลองบางไผ่", "Khlong Bang Phai", MetroLine.MRT_PURPLE, NORMAL, exits = 2, parking = true),
            )
        )
        // Airport Rail Link
        addAll(
            listOf(
                s("A1", "พญาไท", "Phaya Thai", MetroLine.ARL, CROWDED, interchange = true, exits = 4),
                s("A2", "ราชปรารภ", "Ratchaprarop", MetroLine.ARL, NORMAL, exits = 2),
                s("A3", "มักกะสัน", "Makkasan", MetroLine.ARL, NORMAL, interchange = true, exits = 3, parking = true),
                s("A4", "รามคำแหง", "Ramkhamhaeng", MetroLine.ARL, NORMAL, exits = 3),
                s("A5", "หัวหมาก", "Hua Mak", MetroLine.ARL, NORMAL, interchange = true, exits = 2),
                s("A6", "บ้านทับช้าง", "Ban Thap Chang", MetroLine.ARL, NORMAL, exits = 2),
                s("A7", "ลาดกระบัง", "Lat Krabang", MetroLine.ARL, NORMAL, exits = 2, parking = true),
                s("A8", "สุวรรณภูมิ", "Suvarnabhumi", MetroLine.ARL, CROWDED, exits = 2),
            )
        )
        // SRT Dark Red Line
        addAll(
            listOf(
                s("DR1", "จตุจักร", "Chatuchak", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("DR2", "วัดเสมียนนารี", "Wat Samian Nari", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("DR3", "บางเขน", "Bang Khen", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("DR4", "หลักสี่", "Lak Si", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("DR5", "ดอนเมือง", "Don Mueang", MetroLine.SRT_DARK_RED, CROWDED, exits = 3, parking = true),
                s("DR6", "หลักหก", "Lak Hok", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("DR7", "รังสิต", "Rangsit", MetroLine.SRT_DARK_RED, NORMAL, exits = 3, parking = true),
            )
        )
        // SRT Light Red Line
        addAll(
            listOf(
                s("LR1", "บางซ่อน", "Bang Son", MetroLine.SRT_LIGHT_RED, NORMAL, interchange = true, exits = 3),
                s("LR2", "บางบำหรุ", "Bang Bamru", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2),
                s("LR3", "บางพลัด", "Bang Phlat", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2),
                s("LR4", "บางอ้อ", "Bang O", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2),
                s("LR5", "ตลิ่งชัน", "Taling Chan", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2, parking = true),
            )
        )
        // APM Gold Line
        addAll(
            listOf(
                s("G1", "กรุงธนบุรี", "Krung Thon Buri", MetroLine.APM_GOLD, NORMAL, interchange = true, exits = 4),
                s("G2", "เจริญนคร", "Charoen Nakhon", MetroLine.APM_GOLD, NORMAL, exits = 2),
                s("G3", "คลองสาน", "Khlong San", MetroLine.APM_GOLD, NORMAL, exits = 2),
            )
        )
        // MRT Yellow Line
        addAll(
            listOf(
                s("Y1", "ลาดพร้าว", "Lat Phrao", MetroLine.MRT_YELLOW, NORMAL, interchange = true, exits = 4),
                s("Y2", "ภาวนา", "Phawana", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y3", "โชคชัย 4", "Chok Chai 4", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y4", "ลาดพร้าว 71", "Lat Phrao 71", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y5", "ลาดพร้าว 83", "Lat Phrao 83", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y6", "มหาดไทย", "Mahat Thai", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y7", "ลาดพร้าว 101", "Lat Phrao 101", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y8", "บางกะปิ", "Bang Kapi", MetroLine.MRT_YELLOW, CROWDED, exits = 3),
                s("Y9", "แยกลำสาลี", "Yaek Lam Sali", MetroLine.MRT_YELLOW, NORMAL, interchange = true, exits = 3),
                s("Y10", "หัวหมาก", "Hua Mak", MetroLine.MRT_YELLOW, NORMAL, interchange = true, exits = 2),
                s("Y11", "ศรีลาซาล", "Si Lasalle", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("Y12", "สำโรง", "Samrong", MetroLine.MRT_YELLOW, CROWDED, exits = 3, parking = true),
            )
        )
        // MRT Pink Line
        addAll(
            listOf(
                s("PK1", "ศูนย์ราชการนนทบุรี", "Nonthaburi Civic Centre", MetroLine.MRT_PINK, NORMAL, interchange = true, exits = 4),
                s("PK2", "แคราย", "Khae Rai", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK3", "สนามบินน้ำ", "Sanambin Nam", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK4", "สามัคคี", "Samakkhi", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK5", "วัดพระศรีมหาธาตุ", "Wat Phra Sri Mahathat", MetroLine.MRT_PINK, NORMAL, interchange = true, exits = 3),
                s("PK6", "รามอินทรา 3", "Ram Inthra 3", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK7", "ลาดปลาเค้า", "Lat Pla Khao", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK8", "วัชรพล", "Vachiraphayaban", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK9", "คู้บอน", "Khu Bon", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK10", "นพรัตน์", "Nopparat", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK11", "ตลาดมีนบุรี", "Min Buri Market", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK12", "มีนบุรี", "Min Buri", MetroLine.MRT_PINK, CROWDED, exits = 3, parking = true),
            )
        )
    }

    private fun s(
        id: String, th: String, en: String, line: MetroLine,
        status: StationStatus, interchange: Boolean = false, exits: Int = 4,
        parking: Boolean = false,
    ) = Station(id, th, en, line, status, interchange, exits, parking)

    fun stationsOf(line: MetroLine) = stations.filter { it.line == line }
    fun station(id: String) = stations.first { it.id == id }

    // ---------------------------------------------------------------- Trains
    val trains: List<TrainRun> = listOf(
        TrainRun("t1", MetroLine.SUKHUMVIT, "ไปเคหะฯ", "to Kheha", "E4", "E5", 2, false, TrainCrowd.MEDIUM),
        TrainRun("t2", MetroLine.SUKHUMVIT, "ไปหมอชิต", "to Mo Chit", "CEN", "E1", 4, true, TrainCrowd.HEAVY),
        TrainRun("t3", MetroLine.SILOM, "ไปบางหว้า", "to Bang Wa", "S2", "S3", 3, true, TrainCrowd.LIGHT),
        TrainRun("t4", MetroLine.MRT_BLUE, "ไปหลักสอง", "to Lak Song", "BL7", "BL8", 1, false, TrainCrowd.HEAVY),
        TrainRun("t5", MetroLine.MRT_BLUE, "ไปท่าพระ", "to Tha Phra", "BL10", "BL9", 5, false, TrainCrowd.MEDIUM),
        TrainRun("t6", MetroLine.MRT_PURPLE, "ไปคลองบางไผ่", "to Khlong Bang Phai", "PP13", "PP14", 6, false, TrainCrowd.LIGHT),
        TrainRun("t7", MetroLine.ARL, "ไปสุวรรณภูมิ", "to Suvarnabhumi", "A3", "A4", 8, false, TrainCrowd.MEDIUM),
    )

    // ---------------------------------------------------------------- News
    val news: List<NewsItem> = listOf(
        NewsItem(
            "n1", "ปิดปรับปรุงสถานีอโศก", "Asok station partial closure",
            "ปิดทางออก 3 และ 4 ชั่วคราว วันที่ 12-15 ก.ย. เพื่อซ่อมบำรุงบันไดเลื่อน",
            "Exits 3 and 4 closed 12-15 Sep for escalator maintenance.",
            "2026-09-01", true, "🚧",
        ),
        NewsItem(
            "n2", "เปิดสถานีใหม่ บางนา", "New station: Bang Na",
            "เตรียมพบกับสถานีบางนา สายสุขุมวิท เปิดให้บริการเดือนพฤศจิกายน 2026",
            "Bang Na on the Sukhumvit Line opens November 2026.",
            "2026-08-28", false, "🆕",
        ),
        NewsItem(
            "n3", "รถไฟล่าช้าสายสีลม", "Silom Line delays",
            "เนื่องจากการปรับปรุงราง รถไฟสายสีลมอาจล่าช้า 5-10 นาที ในช่วงเช้า",
            "Track works may delay Silom Line trains 5-10 min in the morning.",
            "2026-09-02", true, "🔧",
        ),
        NewsItem(
            "n4", "โปรโมชั่นเติมเงินรับ 2 เท่า", "Double points top-up",
            "เติมเงินวันเสาร์-อาทิตย์ รับแต้ม 2 เท่า ถึง 30 ก.ย. 2026",
            "Top up on weekends for double points until 30 Sep 2026.",
            "2026-08-30", false, "🎉",
        ),
    )

    // ---------------------------------------------------------------- Promotions
    val promotions: List<Promotion> = listOf(
        Promotion("p2", "นักศึกษา ลด 30%", "Student 30% off",
            "นักเรียน/นักศึกษาที่มีบัตรนักศึกษา รับส่วนลด 30% ทุกเส้นทาง",
            "Verified students get 30% off on all lines.",
            "30%", "2026-12-31", "🎓", PassengerType.STUDENT),
        Promotion("p3", "ผู้สูงอายุ เดินทางฟรีนอกพีค", "Seniors ride free off-peak",
            "ผู้สูงอายุ 60 ปีขึ้นไป เดินทางฟรีในช่วงเวลานอกพีค (10:00-16:00)",
            "Passengers 60+ ride free during off-peak (10:00-16:00).",
            "FREE", "2026-12-31", "🧓", PassengerType.ELDER),
        Promotion("p4", "เด็กสูงไม่เกิน 90 ซม. ฟรี", "Children under 90 cm free",
            "เด็กที่ความสูงไม่เกิน 90 ซม. เดินทางฟรีเมื่อมากับผู้ปกครอง",
            "Children up to 90 cm travel free with a guardian.",
            "FREE", "2026-12-31", "👶", PassengerType.CHILD),
        Promotion("p5", "พนักงาน SCB ลด 20%", "SCB staff 20% off",
            "พนักงานที่เชื่อมบัตรพนักงาน SCB รับส่วนลด 20%",
            "SCB staff who link their staff card get 20% off.",
            "20%", "2026-12-31", "🏢", PassengerType.STAFF),
    )

    // ---------------------------------------------------------------- Rewards
    val rewards: List<Reward> = listOf(
        Reward("r1", "เที่ยวเดินทางฟรี 1 เที่ยว", "1 free trip", 500, "🎟️"),
        Reward("r2", "ส่วนลดค่าเดินทาง 20 บาท", "THB 20 travel credit", 250, "💰"),
        Reward("r3", "กระเป๋าผ้า Trainkmutnb67", "Tote bag", 1200, "👜"),
        Reward("r4", "คูปองกาแฟในสถานี", "Station coffee coupon", 300, "☕"),
        Reward("r5", "เที่ยวเดินทางฟรี 5 เที่ยว", "5 free trips", 2200, "🎫"),
        Reward("r6", "ร่มพับ Limited Edition", "Limited edition umbrella", 900, "☂️"),
    )

    // ---------------------------------------------------------------- FAQ
    val faqs: List<Faq> = listOf(
        Faq("ลืมรหัสผ่านทำอย่างไร?", "How do I reset my password?",
            "กดปุ่ม \"ลืมรหัสผ่าน\" ที่หน้าเข้าสู่ระบบ แล้วกรอกอีเมล ระบบจะส่งลิงก์ให้ (ในเวอร์ชันจำลองนี้จะแสดงข้อความยืนยันทันที)",
            "Tap \"Forgot password\" on the sign-in screen and enter your email. In this demo a confirmation shows immediately."),
        Faq("เติมเงินแล้วไม่เข้ากระเป๋าเงิน?", "Top-up didn't reach my wallet?",
            "ปกติเงินเข้าทันที หากไม่เข้าให้ปิด-เปิดแอปใหม่ หรือติดต่อ Support พร้อมเลขรายการ",
            "Top-ups are instant. If it doesn't appear, restart the app or contact support with the transaction id."),
        Faq("แต้มหายไป?", "My points disappeared?",
            "แต้มจะรีเฟรชทุกเที่ยงคืน การแลกของรางวัลจะหักแต้มทันที ตรวจสอบได้ที่หน้าประวัติ",
            "Points refresh at midnight; redemptions deduct instantly. Check the history tab."),
        Faq("QR ใช้ไม่ได้?", "QR won't scan?",
            "QR ชำระมีอายุ 5 นาที กด\"รีเฟรช QR ใหม่\" เพื่อสร้างรหัสใหม่ และเพิ่มความสว่างหน้าจอ",
            "The pay QR lasts 5 minutes. Tap \"Refresh QR\" for a new code and raise screen brightness."),
    )

    // ---------------------------------------------------------------- Seed history
    fun seedTxns(): List<Txn> = listOf(
        Txn("x1", TxnKind.TRIP, "BTS สายสุขุมวิท", "BTS Sukhumvit", "หมอชิต → อโศก", "Mo Chit → Asok", "2026-09-02 08:32", -42, 4),
        Txn("x2", TxnKind.TOPUP, "เติมเงินผ่าน QR", "Top-up via QR", "PromptPay", "PromptPay", "2026-09-01 18:00", 500, 50),
        Txn("x3", TxnKind.TRIP, "BTS สายสีลม", "BTS Silom", "สยาม → ศาลาแดง", "Siam → Sala Daeng", "2026-09-01 12:15", -33, 3),
        Txn("x4", TxnKind.REDEEM, "แลก: เที่ยวเดินทางฟรี 1 เที่ยว", "Redeemed: 1 free trip", "แต้ม -500", "-500 pts", "2026-08-31 14:22", 0, -500),
        Txn("x5", TxnKind.TRIP, "Airport Rail Link", "Airport Rail Link", "พญาไท → สุวรรณภูมิ", "Phaya Thai → Suvarnabhumi", "2026-08-30 09:00", -45, 4),
    )

    // ---------------------------------------------------------------- Fare
    /** Simple distance-based fare: THB 17 base + 3 per station gap, capped 62. */
    fun baseFare(fromIndexGap: Int): Int = (17 + 3 * fromIndexGap).coerceIn(17, 62)
}
