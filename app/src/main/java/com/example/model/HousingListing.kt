package com.example.model

enum class WaterSupplyType(val labelEn: String, val labelHi: String) {
  HOURS_24("24hr Water", "24 घंटे पानी"),
  SCHEDULED_2X("2x Daily Water", "रोज 2 समय पानी"),
  TUBEWELL("Borewell Water", "बोरवेल पानी")
}

enum class MeterType(val labelEn: String, val labelHi: String) {
  SEPARATE_SUBMETER("Separate Meter", "अलग सब-मीटर"),
  FIXED_CHARGES("Fixed ₹300/mo", "फिक्स बिजली ₹300"),
  INCLUDED("Electricity Included", "बिजली किराये में शामिल")
}

enum class ToiletType(val labelEn: String, val labelHi: String) {
  PERSONAL("Personal Toilet", "पर्सनल शौचालय"),
  SHARED("Shared Toilet", "साझा शौचालय")
}

data class HousingListing(
  val id: String,
  val titleEn: String,
  val titleHi: String,
  val locality: String,
  val addressEn: String,
  val addressHi: String,
  val landmarkEn: String,
  val landmarkHi: String,
  val rentAmount: Int,
  val securityDeposit: Int,
  val photoUrls: List<String>,
  val waterType: WaterSupplyType,
  val meterType: MeterType,
  val toiletType: ToiletType,
  val roomTypeEn: String,
  val roomTypeHi: String,
  val hasKitchenSlab: Boolean,
  val hasBikeParking: Boolean,
  val ownerName: String,
  val ownerPhone: String,
  val isVerified: Boolean = true,
  val rulesEn: String = "Family & working persons welcome. Gate closes at 10:30 PM.",
  val rulesHi: String = "परिवार एवं कामकाजी लोग स्वागत हैं। रात 10:30 बजे मुख्य गेट बंद।",
  val floorEn: String = "Ground Floor",
  val floorHi: String = "भूतल (ग्राउंड फ्लोर)"
)
