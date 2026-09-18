package com.example.data

import com.example.model.HousingListing
import com.example.model.MeterType
import com.example.model.ToiletType
import com.example.model.WaterSupplyType

object SampleData {
  val initialListings = listOf(
    HousingListing(
      id = "jp-01",
      titleEn = "Clean 1 Room with Fan & Window",
      titleHi = "हवादार साफ कमरा, पंखा व खिड़की सहित",
      locality = "Sanganer",
      addressEn = "Plot 42, Goshala Road, Near Sanganer Bus Stand",
      addressHi = "प्लॉट 42, गौशाला रोड, सांगानेर बस स्टैंड के पास",
      landmarkEn = "Opposite Jain Temple & Bus Stop (2 min walk)",
      landmarkHi = "जैन मंदिर एवं बस स्टैंड के सामने (2 मिनट पैदल)",
      rentAmount = 2800,
      securityDeposit = 2000,
      photoUrls = listOf(
        "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=600&auto=format&fit=crop&q=80",
        "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?w=600&auto=format&fit=crop&q=80"
      ),
      waterType = WaterSupplyType.HOURS_24,
      meterType = MeterType.SEPARATE_SUBMETER,
      toiletType = ToiletType.SHARED,
      roomTypeEn = "1 Room (Separate Entry)",
      roomTypeHi = "1 कमरा (अलग प्रवेश द्वार)",
      hasKitchenSlab = true,
      hasBikeParking = true,
      ownerName = "श्री रामेश्वर प्रसाद शर्मा",
      ownerPhone = "+919829012345",
      isVerified = true,
      rulesEn = "Single workers or small family. Drinking/smoking prohibited. Peaceful colony.",
      rulesHi = "कामकाजी भाई या छोटा परिवार। नशा व शोर-शराबा सख्त मना। शांत माहौल।",
      floorEn = "Ground Floor",
      floorHi = "भूतल (ग्राउंड फ्लोर)"
    ),
    HousingListing(
      id = "jp-02",
      titleEn = "Worker Budget Room near Factories",
      titleHi = "मजदूर व कारीगरों हेतु किफायती कमरा",
      locality = "VKI",
      addressEn = "Street 4, Near Road No. 9, Vishwakarma Industrial Area",
      addressHi = "गली नंबर 4, रोड नंबर 9 के पास, वी.के.आई. इंडस्ट्रियल एरिया",
      landmarkEn = "Near RIICO Canteen & Sikar Road Junction",
      landmarkHi = "रीको कैंटीन एवं सीकर रोड चौराहे के पास",
      rentAmount = 2300,
      securityDeposit = 1500,
      photoUrls = listOf(
        "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?w=600&auto=format&fit=crop&q=80",
        "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=600&auto=format&fit=crop&q=80"
      ),
      waterType = WaterSupplyType.HOURS_24,
      meterType = MeterType.SEPARATE_SUBMETER,
      toiletType = ToiletType.SHARED,
      roomTypeEn = "1 Room (Tiled Floor)",
      roomTypeHi = "1 कमra (पक्के टाइल फर्श वाला)",
      hasKitchenSlab = false,
      hasBikeParking = true,
      ownerName = "श्री बनवारी लाल जी",
      ownerPhone = "+919414023456",
      isVerified = true,
      rulesEn = "Gate open till 11 PM for shift workers. Clean environment.",
      rulesHi = "नाइट शिफ्ट वाले भाइयों हेतु रात 11 बजे तक मुख्य द्वार खुला रहता है।",
      floorEn = "1st Floor",
      floorHi = "पहली मंजिल"
    ),
    HousingListing(
      id = "jp-03",
      titleEn = "Spacious Room with Attached Bathroom",
      titleHi = "पर्सनल लेट-बाथ सहित बड़ा हवादार कमरा",
      locality = "Jhalana",
      addressEn = "Doordarshan Colony, Near Jhalana Dungri RTO",
      addressHi = "दूरदर्शन कॉलोनी, झालाना डूंगरी आर.टी.ओ. के पास",
      landmarkEn = "Behind Forest Office, Near JLN Marg Cut",
      landmarkHi = "वन विभाग कार्यालय के पीछे, जेएलएन मार्ग कट के नजदीक",
      rentAmount = 3500,
      securityDeposit = 3000,
      photoUrls = listOf(
        "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=600&auto=format&fit=crop&q=80",
        "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600&auto=format&fit=crop&q=80"
      ),
      waterType = WaterSupplyType.HOURS_24,
      meterType = MeterType.SEPARATE_SUBMETER,
      toiletType = ToiletType.PERSONAL,
      roomTypeEn = "1 Room + Attached Bath",
      roomTypeHi = "1 कमरा + पर्सनल बाथरूम",
      hasKitchenSlab = true,
      hasBikeParking = true,
      ownerName = "श्री कैलाश नारायण मीणा",
      ownerPhone = "+919784034567",
      isVerified = true,
      rulesEn = "Family or 2 students/job seekers. 24h municipal sweet water.",
      rulesHi = "परिवार या 2 नौकरीपेशा भाई। 24 घंटे मीठा पानी उपलब्ध।",
      floorEn = "Ground Floor",
      floorHi = "भूतल (ग्राउंड फ्लोर)"
    ),
    HousingListing(
      id = "jp-04",
      titleEn = "Affordable Room for Factory Workers",
      titleHi = "फैक्ट्री कर्मचारियों हेतु शांत कमरा",
      locality = "Sitapura",
      addressEn = "Sector 3, Near RIICO Flyover, Sitapura Industrial Area",
      addressHi = "सेक्टर 3, रीको फ्लाईओवर के पास, सीतापुरा",
      landmarkEn = "Near Gate No. 2 & Mahindra World City Bus Stop",
      landmarkHi = "गेट नं. 2 एवं महिंद्रा वर्ल्ड सिटी बस स्टॉप के नजदीक",
      rentAmount = 2600,
      securityDeposit = 2000,
      photoUrls = listOf(
        "https://images.unsplash.com/photo-1540518614846-7ede433c4ef9?w=600&auto=format&fit=crop&q=80",
        "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=600&auto=format&fit=crop&q=80"
      ),
      waterType = WaterSupplyType.SCHEDULED_2X,
      meterType = MeterType.FIXED_CHARGES,
      toiletType = ToiletType.SHARED,
      roomTypeEn = "1 Room with Balcony",
      roomTypeHi = "1 कमरा बालकनी सहित",
      hasKitchenSlab = true,
      hasBikeParking = true,
      ownerName = "श्रीमती संतोषी देवी",
      ownerPhone = "+919602045678",
      isVerified = true,
      rulesEn = "Only verified ID holders. Zero broker commission. Advance 1 month.",
      rulesHi = "केवल आधार कार्ड सत्यापन आवश्यक। दलाली बिल्कुल नहीं। 1 माह अग्रिम।",
      floorEn = "2nd Floor",
      floorHi = "दूसरी मंजिल"
    ),
    HousingListing(
      id = "jp-05",
      titleEn = "Complete 1 RK (Room + Kitchen) for Family",
      titleHi = "परिवार हेतु पूरा 1 आर.के. (कमरा + किचन)",
      locality = "Mansarovar",
      addressEn = "Shipra Path, Near Mansarovar Metro Pillar 44",
      addressHi = "शिप्रा पथ, मानसरोवर मेट्रो पिलर 44 के पास",
      landmarkEn = "Near RIICO Kanta & Vegetable Mandi",
      landmarkHi = "रीको कांटा एवं सब्जी मंडी के नजदीक",
      rentAmount = 4500,
      securityDeposit = 4000,
      photoUrls = listOf(
        "https://images.unsplash.com/photo-1505691938895-1758d7feb511?w=600&auto=format&fit=crop&q=80",
        "https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=600&auto=format&fit=crop&q=80"
      ),
      waterType = WaterSupplyType.HOURS_24,
      meterType = MeterType.SEPARATE_SUBMETER,
      toiletType = ToiletType.PERSONAL,
      roomTypeEn = "1 RK (Hall + Kitchen + Bath)",
      roomTypeHi = "1 आर.के. (कमरा + अलग रसोई + बाथरूम)",
      hasKitchenSlab = true,
      hasBikeParking = true,
      ownerName = "श्री राजेश कुमार अग्रवाल",
      ownerPhone = "+919828056789",
      isVerified = true,
      rulesEn = "Small family or working couple. Safe neighborhood with CCTV.",
      rulesHi = "छोटा परिवार या कामकाजी दंपत्ति। सीसीटीवी से सुरक्षित कॉलोनी।",
      floorEn = "Ground Floor",
      floorHi = "भूतल (ग्राउंड फ्लोर)"
    ),
    HousingListing(
      id = "jp-06",
      titleEn = "Low Budget Room with Water Tank",
      titleHi = "सस्ता कमरा, पानी की टंकी व बिजली मीटर सहित",
      locality = "Pratap Nagar",
      addressEn = "Sector 8, Near Kumbha Marg, Pratap Nagar",
      addressHi = "सेक्टर 8, कुंभा मार्ग के पास, प्रताप नगर",
      landmarkEn = "Near Coaching Hub & Tonk Road Flyover",
      landmarkHi = "कोचिंग हब एवं टोंक रोड फ्लाईओवर के पास",
      rentAmount = 2900,
      securityDeposit = 2500,
      photoUrls = listOf(
        "https://images.unsplash.com/photo-1484154218962-a197022b5858?w=600&auto=format&fit=crop&q=80",
        "https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?w=600&auto=format&fit=crop&q=80"
      ),
      waterType = WaterSupplyType.HOURS_24,
      meterType = MeterType.SEPARATE_SUBMETER,
      toiletType = ToiletType.SHARED,
      roomTypeEn = "1 Room (Sunny & Ventilated)",
      roomTypeHi = "1 कमरा (धूप व हवादार)",
      hasKitchenSlab = true,
      hasBikeParking = true,
      ownerName = "श्री महेश चंद्र जोशी",
      ownerPhone = "+919314067890",
      isVerified = true,
      rulesEn = "Respectful tenants. Student or factory employee preferred.",
      rulesHi = "सभ्य किरायेदार चाहिए। छात्र अथवा फैक्ट्री कर्मचारी को प्राथमिकता।",
      floorEn = "1st Floor",
      floorHi = "पहली मंजिल"
    )
  )

  val jaipurLocalities = listOf(
    "All" to "सभी क्षेत्र",
    "Sanganer" to "सांगानेर",
    "VKI" to "वी.के.आई (VKI)",
    "Jhalana" to "झालाना",
    "Sitapura" to "सीतापुरा",
    "Mansarovar" to "मानसरोवर",
    "Pratap Nagar" to "प्रताप नगर"
  )
}
