package com.kmutnb.trainkmutnb67.data

import com.kmutnb.trainkmutnb67.data.StationStatus.CROWDED
import com.kmutnb.trainkmutnb67.data.StationStatus.NORMAL

object MockData {

    // ---------------------------------------------------------------- Stations
    // Ordered north/west -> south/east along each line (subset of the real lines).
    val stations: List<Station> = buildList {
        // 1. BTS Sukhumvit Line (N24 - E23)
        val sukhumvit = listOf(
            s("N24", "คูคต", "Khu Khot", MetroLine.SUKHUMVIT, NORMAL, exits = 4, parking = true),
            s("N23", "แยก คปอ.", "Yaek Kor Por Aor", MetroLine.SUKHUMVIT, NORMAL, exits = 4, parking = true),
            s("N22", "พิพิธภัณฑ์กองทัพอากาศ", "Royal Thai Air Force Museum", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N21", "โรงเรียนนายเรืออากาศ", "Royal Thai Air Force Academy", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N20", "สะพานใหม่", "Saphan Mai", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N19", "สายหยุด", "Sai Yud", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N18", "พหลโยธิน 59", "Phahon Yothin 59", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N17", "วัดพระศรีมหาธาตุ", "Wat Phra Sri Mahathat", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 4),
            s("N16", "กรมทหารราบที่ 11", "11th Infantry Regiment", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N15", "บางบัว", "Bang Bua", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N14", "กรมป่าไม้", "Royal Forest Department", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N13", "มหาวิทยาลัยเกษตรศาสตร์", "Kasetsart University", MetroLine.SUKHUMVIT, CROWDED, exits = 4),
            s("N12", "เสนานิคม", "Sena Nikhom", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N11", "รัชโยธิน", "Ratchayothin", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N10", "พหลโยธิน 24", "Phahon Yothin 24", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N9", "ห้าแยกลาดพร้าว", "Ha Yaek Lat Phrao", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 4),
            s("N8", "หมอชิต", "Mo Chit", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 4, parking = true),
            s("N7", "สะพานควาย", "Saphan Khwai", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N5", "อารีย์", "Ari", MetroLine.SUKHUMVIT, CROWDED, exits = 4),
            s("N4", "สนามเป้า", "Sanam Pao", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("N3", "อนุสาวรีย์ชัยสมรภูมิ", "Victory Monument", MetroLine.SUKHUMVIT, CROWDED, exits = 4),
            s("N2", "พญาไท", "Phaya Thai", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 4),
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
            s("E10", "บางจาก", "Bang Chak", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E11", "ปุณณวิถี", "Punnawithi", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E12", "อุดมสุข", "Udom Suk", MetroLine.SUKHUMVIT, CROWDED, exits = 5),
            s("E13", "บางนา", "Bang Na", MetroLine.SUKHUMVIT, NORMAL, exits = 6),
            s("E14", "แบริ่ง", "Bearing", MetroLine.SUKHUMVIT, NORMAL, exits = 5),
            s("E15", "สำโรง", "Samrong", MetroLine.SUKHUMVIT, CROWDED, interchange = true, exits = 6),
            s("E16", "ปู่เจ้า", "Pu Chao", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E17", "ช้างเอราวัณ", "Chang Erawan", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E18", "โรงเรียนนายเรือ", "Royal Thai Naval Academy", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E19", "ปากน้ำ", "Pak Nam", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E20", "ศรีนครินทร์", "Srinagarindra", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E21", "แพรกษา", "Phraek Sa", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E22", "สายลวด", "Sai Luat", MetroLine.SUKHUMVIT, NORMAL, exits = 4),
            s("E23", "เคหะฯ", "Kheha", MetroLine.SUKHUMVIT, NORMAL, exits = 4, parking = true)
        )
        addAll(sukhumvit)

        // 2. BTS Silom Line (W1 - S12)
        addAll(
            listOf(
                s("W1", "สนามกีฬาแห่งชาติ", "National Stadium", MetroLine.SILOM, NORMAL, exits = 2),
                s("CEN", "สยาม", "Siam", MetroLine.SILOM, CROWDED, interchange = true, exits = 8),
                s("S1", "ราชดำริ", "Ratchadamri", MetroLine.SILOM, NORMAL, exits = 4),
                s("S2", "ศาลาแดง", "Sala Daeng", MetroLine.SILOM, CROWDED, interchange = true, exits = 4),
                s("S3", "ช่องนนทรี", "Chong Nonsi", MetroLine.SILOM, NORMAL, exits = 4),
                s("S4", "เซนต์หลุยส์", "Saint Louis", MetroLine.SILOM, NORMAL, exits = 4),
                s("S5", "สุรศักดิ์", "Surasak", MetroLine.SILOM, NORMAL, exits = 3),
                s("S6", "สะพานตากสิน", "Saphan Taksin", MetroLine.SILOM, CROWDED, exits = 2),
                s("S7", "กรุงธนบุรี", "Krung Thon Buri", MetroLine.SILOM, NORMAL, interchange = true, exits = 4),
                s("S8", "วงเวียนใหญ่", "Wongwian Yai", MetroLine.SILOM, NORMAL, exits = 4),
                s("S9", "โพธิ์นิมิตร", "Pho Nimit", MetroLine.SILOM, NORMAL, exits = 4),
                s("S10", "ตลาดพลู", "Talat Phlu", MetroLine.SILOM, NORMAL, exits = 4),
                s("S11", "วุฒากาศ", "Wutthakat", MetroLine.SILOM, NORMAL, exits = 4),
                s("S12", "บางหว้า", "Bang Wa", MetroLine.SILOM, NORMAL, interchange = true, exits = 4, parking = true)
            )
        )

        // 3. MRT Blue Line (BL01 - BL38)
        addAll(
            listOf(
                s("BL01", "ท่าพระ", "Tha Phra", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 4),
                s("BL02", "จรัญฯ 13", "Charan 13", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL03", "ไฟฉาย", "Fai Chai", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL04", "บางขุนนนท์", "Bang Khun Non", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL05", "บางยี่ขัน", "Bang Yi Khan", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL06", "สิรินธร", "Sirindhorn", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL07", "บางพลัด", "Bang Phlat", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL08", "บางอ้อ", "Bang O", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL09", "บางโพ", "Bang Pho", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL10", "เตาปูน", "Tao Poon", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 4),
                s("BL11", "บางซื่อ", "Bang Sue", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 2),
                s("BL12", "กำแพงเพชร", "Kamphaeng Phet", MetroLine.MRT_BLUE, NORMAL, exits = 3),
                s("BL13", "สวนจตุจักร", "Chatuchak Park", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 3, parking = true),
                s("BL14", "พหลโยธิน", "Phahon Yothin", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 5),
                s("BL15", "ลาดพร้าว", "Lat Phrao", MetroLine.MRT_BLUE, NORMAL, interchange = true, exits = 4, parking = true),
                s("BL16", "รัชดาภิเษก", "Ratchadaphisek", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL17", "สุทธิสาร", "Sutthisan", MetroLine.MRT_BLUE, CROWDED, exits = 4),
                s("BL18", "ห้วยขวาง", "Huai Khwang", MetroLine.MRT_BLUE, CROWDED, exits = 4),
                s("BL19", "ศูนย์วัฒนธรรมแห่งประเทศไทย", "Thailand Cultural Centre", MetroLine.MRT_BLUE, NORMAL, interchange = true, exits = 4, parking = true),
                s("BL20", "พระราม 9", "Phra Ram 9", MetroLine.MRT_BLUE, CROWDED, exits = 3),
                s("BL21", "เพชรบุรี", "Phetchaburi", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 3),
                s("BL22", "สุขุมวิท", "Sukhumvit", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 3),
                s("BL23", "ศูนย์การประชุมแห่งชาติสิริกิติ์", "Queen Sirikit National Convention Centre", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL24", "คลองเตย", "Khlong Toei", MetroLine.MRT_BLUE, NORMAL, exits = 2),
                s("BL25", "ลุมพินี", "Lumpini", MetroLine.MRT_BLUE, NORMAL, exits = 3),
                s("BL26", "สีลม", "Si Lom", MetroLine.MRT_BLUE, CROWDED, interchange = true, exits = 2),
                s("BL27", "สามย่าน", "Sam Yan", MetroLine.MRT_BLUE, NORMAL, exits = 2),
                s("BL28", "หัวลำโพง", "Hua Lamphong", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL29", "วัดมังกร", "Wat Mangkon", MetroLine.MRT_BLUE, CROWDED, exits = 3),
                s("BL30", "สามยอด", "Sam Yot", MetroLine.MRT_BLUE, NORMAL, exits = 3),
                s("BL31", "สนามไชย", "Sanam Chai", MetroLine.MRT_BLUE, NORMAL, exits = 5),
                s("BL32", "อิสรภาพ", "Itsaraphap", MetroLine.MRT_BLUE, NORMAL, exits = 2),
                s("BL33", "บางไผ่", "Bang Phai", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL34", "บางหว้า", "Bang Wa", MetroLine.MRT_BLUE, NORMAL, interchange = true, exits = 4),
                s("BL35", "เพชรเกษม 48", "Phetkasem 48", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL36", "ภาษีเจริญ", "Phasi Charoen", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL37", "บางแค", "Bang Khae", MetroLine.MRT_BLUE, NORMAL, exits = 4),
                s("BL38", "หลักสอง", "Lak Song", MetroLine.MRT_BLUE, CROWDED, exits = 4, parking = true)
            )
        )

        // 4. MRT Purple Line (PP01 - PP16)
        addAll(
            listOf(
                s("PP01", "คลองบางไผ่", "Khlong Bang Phai", MetroLine.MRT_PURPLE, NORMAL, exits = 4, parking = true),
                s("PP02", "ตลาดบางใหญ่", "Talad Bang Yai", MetroLine.MRT_PURPLE, CROWDED, exits = 4),
                s("PP03", "สามแยกบางใหญ่", "Sam Yaek Bang Yai", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP04", "บางพลู", "Bang Phu", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP05", "บางรักใหญ่", "Bang Rak Yai", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP06", "บางรักน้อยท่าอิฐ", "Bang Rak Noi Tha It", MetroLine.MRT_PURPLE, NORMAL, exits = 4, parking = true),
                s("PP07", "ไทรม้า", "Sai Ma", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP08", "สะพานพระนั่งเกล้า", "Phra Nang Klao Bridge", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP09", "แยกนนทบุรี 1", "Yaek Nonthaburi 1", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP10", "บางกระสอ", "Bang Krasor", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP11", "ศูนย์ราชการนนทบุรี", "Nonthaburi Civic Center", MetroLine.MRT_PURPLE, NORMAL, interchange = true, exits = 4),
                s("PP12", "กระทรวงสาธารณสุข", "Ministry of Public Health", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP13", "แยกติวานนท์", "Yaek Tiwanon", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP14", "วงศ์สว่าง", "Wong Sawang", MetroLine.MRT_PURPLE, NORMAL, exits = 4),
                s("PP15", "บางซ่อน", "Bang Son", MetroLine.MRT_PURPLE, NORMAL, interchange = true, exits = 4),
                s("PP16", "เตาปูน", "Tao Poon", MetroLine.MRT_PURPLE, CROWDED, interchange = true, exits = 4)
            )
        )

        // 5. Airport Rail Link (A1 - A8)
        addAll(
            listOf(
                s("A1", "สุวรรณภูมิ", "Suvarnabhumi", MetroLine.ARL, CROWDED, exits = 2),
                s("A2", "ลาดกระบัง", "Lat Krabang", MetroLine.ARL, NORMAL, exits = 2, parking = true),
                s("A3", "บ้านทับช้าง", "Ban Thap Chang", MetroLine.ARL, NORMAL, exits = 2),
                s("A4", "หัวหมาก", "Hua Mak", MetroLine.ARL, NORMAL, interchange = true, exits = 2),
                s("A5", "รามคำแหง", "Ramkhamhaeng", MetroLine.ARL, NORMAL, exits = 2),
                s("A6", "มักกะสัน", "Makkasan", MetroLine.ARL, NORMAL, interchange = true, exits = 4, parking = true),
                s("A7", "ราชปรารภ", "Ratchaprarop", MetroLine.ARL, NORMAL, exits = 2),
                s("A8", "พญาไท", "Phaya Thai", MetroLine.ARL, CROWDED, interchange = true, exits = 2)
            )
        )

        // 6. SRT Dark Red Line (RN01 - RN10)
        addAll(
            listOf(
                s("RN01", "กรุงเทพอภิวัฒน์", "Krung Thep Aphiwat", MetroLine.SRT_DARK_RED, CROWDED, interchange = true, exits = 4),
                s("RN02", "จตุจักร", "Chatuchak", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("RN03", "วัดเสมียนนารี", "Wat Samian Nari", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("RN04", "บางเขน", "Bang Khen", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("RN05", "ทุ่งสองห้อง", "Thung Song Hong", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("RN06", "หลักสี่", "Lak Si", MetroLine.SRT_DARK_RED, NORMAL, interchange = true, exits = 2),
                s("RN07", "การเคหะ", "Kan Kheha", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("RN08", "ดอนเมือง", "Don Mueang", MetroLine.SRT_DARK_RED, CROWDED, exits = 3, parking = true),
                s("RN09", "หลักหก", "Lak Hok", MetroLine.SRT_DARK_RED, NORMAL, exits = 2),
                s("RN10", "รังสิต", "Rangsit", MetroLine.SRT_DARK_RED, NORMAL, exits = 3, parking = true)
            )
        )

        // 7. SRT Light Red Line (RW01 - RW06)
        addAll(
            listOf(
                s("RW01", "กรุงเทพอภิวัฒน์", "Krung Thep Aphiwat", MetroLine.SRT_LIGHT_RED, CROWDED, interchange = true, exits = 4),
                s("RW02", "บางซ่อน", "Bang Son", MetroLine.SRT_LIGHT_RED, NORMAL, interchange = true, exits = 2),
                s("RW03", "พระราม 6", "Rama VI", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2),
                s("RW04", "บางกรวย-กฟผ.", "Bang Kruai - EGAT", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2),
                s("RW05", "บางบำหรุ", "Bang Bamru", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2),
                s("RW06", "ตลิ่งชัน", "Taling Chan", MetroLine.SRT_LIGHT_RED, NORMAL, exits = 2, parking = true)
            )
        )

        // 8. APM Gold Line (G1 - G3)
        addAll(
            listOf(
                s("G1", "กรุงธนบุรี", "Krung Thon Buri", MetroLine.APM_GOLD, NORMAL, interchange = true, exits = 4),
                s("G2", "เจริญนคร", "Charoen Nakhon", MetroLine.APM_GOLD, CROWDED, exits = 2),
                s("G3", "คลองสาน", "Khlong San", MetroLine.APM_GOLD, NORMAL, exits = 2)
            )
        )

        // 9. MRT Yellow Line (YL01 - YL23)
        addAll(
            listOf(
                s("YL01", "ลาดพร้าว", "Lat Phrao", MetroLine.MRT_YELLOW, NORMAL, interchange = true, exits = 4, parking = true),
                s("YL02", "ภาวนา", "Phawana", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL03", "โชคชัย 4", "Chok Chai 4", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL04", "ลาดพร้าว 71", "Lat Phrao 71", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL05", "ลาดพร้าว 83", "Lat Phrao 83", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL06", "มหาดไทย", "Mahat Thai", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL07", "ลาดพร้าว 101", "Lat Phrao 101", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL08", "บางกะปิ", "Bang Kapi", MetroLine.MRT_YELLOW, CROWDED, exits = 3),
                s("YL09", "แยกลำสาลี", "Yaek Lam Sali", MetroLine.MRT_YELLOW, NORMAL, interchange = true, exits = 3),
                s("YL10", "ศรีกรีฑา", "Si Kritha", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL11", "หัวหมาก", "Hua Mak", MetroLine.MRT_YELLOW, NORMAL, interchange = true, exits = 2),
                s("YL12", "กลันตัน", "Kalantan", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL13", "ศรีพัฒนา", "Si Phatthana", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL14", "ศรีนครินทร์ 38", "Srinagarindra 38", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL15", "สวนหลวง ร.9", "Suan Luang Rama IX", MetroLine.MRT_YELLOW, CROWDED, exits = 2),
                s("YL16", "ศรีอุดม", "Si Udom", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL17", "ศรีเอี่ยม", "Si Iam", MetroLine.MRT_YELLOW, NORMAL, exits = 2, parking = true),
                s("YL18", "ศรีลาซาล", "Si La Salle", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL19", "ศรีแบริ่ง", "Si Bearing", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL20", "ศรีด่าน", "Si Dan", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL21", "ศรีเทพา", "Si Thepha", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL22", "ทิพพาวาลย์", "Thipphawan", MetroLine.MRT_YELLOW, NORMAL, exits = 2),
                s("YL23", "สำโรง", "Samrong", MetroLine.MRT_YELLOW, CROWDED, interchange = true, exits = 3)
            )
        )

        // 10. MRT Pink Line (PK01 - PK30)
        addAll(
            listOf(
                s("PK01", "ศูนย์ราชการนนทบุรี", "Nonthaburi Civic Center", MetroLine.MRT_PINK, NORMAL, interchange = true, exits = 4),
                s("PK02", "แคราย", "Khae Rai", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK03", "สนามบินน้ำ", "Sanam Bin Nam", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK04", "สามเล็ด", "Sam Yaek Sanambinnam", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK05", "ชลประทาน", "Cholprathan", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK06", "ปากเกร็ด", "Pak Kret", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK07", "แจ้งวัฒนะ-ปากเกร็ด 28", "Chaeng Watthana - Pak Kret 28", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK08", "เมืองทองธานี", "Muang Thong Thani", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK09", "แจ้งวัฒนะ 14", "Chaeng Watthana 14", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK10", "ศูนย์ราชการเฉลิมพระเกียรติ", "Government Complex", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK11", "โทรคมนาคมแห่งชาติ", "National Telecom", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK12", "หลักสี่", "Lak Si", MetroLine.MRT_PINK, NORMAL, interchange = true, exits = 2),
                s("PK13", "ราชภัฏพระนคร", "Rajabhat Phra Nakhon", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK14", "วัดพระศรีมหาธาตุ", "Wat Phra Sri Mahathat", MetroLine.MRT_PINK, NORMAL, interchange = true, exits = 4),
                s("PK15", "รามอินทรา 3", "Ram Inthra 3", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK16", "ลาดปลาเค้า", "Lat Pla Khao", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK17", "รามอินทรา กม.4", "Ram Inthra Kor Mor 4", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK18", "มัยลาภ", "Maiyalap", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK19", "วัชรพล", "Vacharaphol", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK20", "รามอินทรา กม.6", "Ram Inthra Kor Mor 6", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK21", "คู้บอน", "Khu Bon", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK22", "รามอินทรา กม.9", "Ram Inthra Kor Mor 9", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK23", "วงแหวนรามอินทรา", "Outer Ring Road - Ram Inthra", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK24", "นพรัตน์", "Nopparat", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK25", "บางชัน", "Bang Chan", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK26", "เศรษฐบุตรบำเพ็ญ", "Setthabutbamphen", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK27", "ตลาดมีนบุรี", "Min Buri Market", MetroLine.MRT_PINK, NORMAL, exits = 2),
                s("PK28", "มีนบุรี", "Min Buri", MetroLine.MRT_PINK, CROWDED, exits = 3, parking = true),
                s("PK29", "อิมแพ็ค เมืองทองธานี", "IMPACT Muang Thong Thani", MetroLine.MRT_PINK, CROWDED, exits = 2),
                s("PK30", "ทะเลสาบเมืองทองธานี", "Muang Thong Thani Lake", MetroLine.MRT_PINK, NORMAL, exits = 2)
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
