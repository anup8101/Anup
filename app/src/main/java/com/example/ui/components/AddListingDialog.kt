package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.model.HousingListing
import com.example.model.MeterType
import com.example.model.ToiletType
import com.example.model.WaterSupplyType
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.AccentYellowDark
import com.example.ui.theme.AccentYellowLight
import com.example.ui.theme.BadgeVerifiedBg
import com.example.ui.theme.BadgeVerifiedText
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CallGreen
import com.example.ui.theme.DeepBlueDark
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddListingDialog(
  isHindi: Boolean,
  onDismiss: () -> Unit,
  onListingAdded: (HousingListing) -> Unit
) {
  val context = LocalContext.current
  var currentStep by remember { mutableIntStateOf(1) }

  // Step 1: Owner Details & Verification
  var ownerName by remember { mutableStateOf("") }
  var ownerMobile by remember { mutableStateOf("") }
  var aadhaarNumber by remember { mutableStateOf("") }
  var isOtpSent by remember { mutableStateOf(false) }
  var otpInput by remember { mutableStateOf("") }
  var isVerified by remember { mutableStateOf(false) }
  var isVerifying by remember { mutableStateOf(false) }

  // Step 2: Room Location & Price
  var locality by remember { mutableStateOf("Sanganer") }
  var address by remember { mutableStateOf("") }
  var landmark by remember { mutableStateOf("") }
  var rentPrice by remember { mutableStateOf("2500") }
  var depositAmount by remember { mutableStateOf("2000") }
  var roomType by remember { mutableStateOf("1 Room") }
  var floorType by remember { mutableStateOf("Ground Floor") }

  // Step 3: Amenities & Photos
  var waterType by remember { mutableStateOf(WaterSupplyType.HOURS_24) }
  var meterType by remember { mutableStateOf(MeterType.SEPARATE_SUBMETER) }
  var toiletType by remember { mutableStateOf(ToiletType.SHARED) }
  var hasKitchenSlab by remember { mutableStateOf(true) }
  var hasBikeParking by remember { mutableStateOf(true) }

  // Photos (2 photos uploaded / picked)
  var photo1Url by remember {
    mutableStateOf("https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=600&auto=format&fit=crop&q=80")
  }
  var photo2Url by remember {
    mutableStateOf("https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?w=600&auto=format&fit=crop&q=80")
  }

  val samplePhotos = listOf(
    "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=600&auto=format&fit=crop&q=80",
    "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?w=600&auto=format&fit=crop&q=80",
    "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=600&auto=format&fit=crop&q=80",
    "https://images.unsplash.com/photo-1540518614846-7ede433c4ef9?w=600&auto=format&fit=crop&q=80"
  )

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxHeight(0.92f),
      shape = RoundedCornerShape(16.dp),
      color = SurfaceLight
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Modal Header
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(DeepBluePrimary)
            .padding(horizontal = 16.dp, vertical = 14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = if (isHindi) "मकान मालिक: कमरा लिस्ट करें" else "Owner: Add New Room Listing",
              color = Color.White,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = if (isHindi) "चरण $currentStep / 3 • 100% फ्री लिस्टिंग" else "Step $currentStep of 3 • 100% Free",
              color = AccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = Color.White
            )
          }
        }

        // Step Progress Bar
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE2E8F0))
            .height(4.dp)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(currentStep / 3f)
              .fillMaxHeight()
              .background(AccentYellow)
          )
        }

        // Form Content (Scrollable)
        val scrollState = rememberScrollState()
        Column(
          modifier = Modifier
            .weight(1f)
            .verticalScroll(scrollState)
            .padding(16.dp)
        ) {
          when (currentStep) {
            1 -> {
              // STEP 1: Owner Details & Aadhaar / Phone OTP
              Text(
                text = if (isHindi) "1. मालिक विवरण व पहचान सत्यापन" else "1. Owner Details & Verification",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBluePrimary
              )
              Text(
                text = if (isHindi)
                  "किरायेदारों का भरोसा बढ़ाने हेतु आधार या फोन ओटीपी सत्यापन आवश्यक है।"
                else
                  "Verification helps low-income tenants trust direct listings without brokers.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
              )

              // Owner Name
              OutlinedTextField(
                value = ownerName,
                onValueChange = { ownerName = it },
                label = { Text(if (isHindi) "आपका पूरा नाम (Name) *" else "Your Full Name *") },
                leadingIcon = { Icon(Icons.Default.Person, null, tint = DeepBluePrimary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
              )

              Spacer(modifier = Modifier.height(12.dp))

              // Owner Mobile Number
              OutlinedTextField(
                value = ownerMobile,
                onValueChange = { if (it.length <= 10) ownerMobile = it.filter { char -> char.isDigit() } },
                label = { Text(if (isHindi) "10 अंकों का मोबाइल नंबर *" else "10-Digit Mobile Number *") },
                prefix = { Text("+91 ", fontWeight = FontWeight.Bold, color = DeepBluePrimary) },
                leadingIcon = { Icon(Icons.Default.Phone, null, tint = DeepBluePrimary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
              )

              Spacer(modifier = Modifier.height(16.dp))

              // Aadhaar / Phone OTP Verification Box
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (isVerified) BadgeVerifiedBg else Color(0xFFF8FAFC))
                  .border(
                    1.dp,
                    if (isVerified) BadgeVerifiedText else BorderSubtle,
                    RoundedCornerShape(12.dp)
                  )
                  .padding(12.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = if (isVerified) Icons.Default.CheckCircle else Icons.Default.Security,
                    contentDescription = null,
                    tint = if (isVerified) BadgeVerifiedText else DeepBluePrimary,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = if (isVerified)
                      (if (isHindi) "✓ मालिक सत्यापित (Aadhaar/OTP Verified)" else "✓ Owner Identity Verified")
                    else
                      (if (isHindi) "आधार या फोन ओटीपी सत्यापन (OTP Verification)" else "Aadhaar / Phone OTP Verification"),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isVerified) BadgeVerifiedText else DeepBluePrimary
                  )
                }

                if (!isVerified) {
                  Spacer(modifier = Modifier.height(8.dp))
                  OutlinedTextField(
                    value = aadhaarNumber,
                    onValueChange = { if (it.length <= 12) aadhaarNumber = it.filter { c -> c.isDigit() } },
                    label = { Text(if (isHindi) "12 अंकों का आधार नंबर (वैकल्पिक)" else "12-Digit Aadhaar (Optional)") },
                    placeholder = { Text("XXXX-XXXX-XXXX") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                  )

                  Spacer(modifier = Modifier.height(8.dp))

                  if (!isOtpSent) {
                    Button(
                      onClick = {
                        if (ownerMobile.length < 10) {
                          Toast.makeText(context, if (isHindi) "कृपया 10 अंकों का फोन नंबर दर्ज करें" else "Enter valid 10-digit phone", Toast.LENGTH_SHORT).show()
                        } else {
                          isOtpSent = true
                          otpInput = "1234" // preset for quick convenience
                          Toast.makeText(context, if (isHindi) "ओटीपी भेजा गया: 1234" else "Demo OTP sent: 1234", Toast.LENGTH_LONG).show()
                        }
                      },
                      colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                      shape = RoundedCornerShape(8.dp),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Icon(Icons.Default.VerifiedUser, null, modifier = Modifier.size(16.dp))
                      Spacer(modifier = Modifier.width(6.dp))
                      Text(if (isHindi) "ओटीपी भेजें (Get OTP)" else "Send OTP via SMS")
                    }
                  } else {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.spacedBy(8.dp),
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      OutlinedTextField(
                        value = otpInput,
                        onValueChange = { otpInput = it },
                        placeholder = { Text("Enter OTP") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                      )

                      Button(
                        onClick = {
                          isVerified = true
                          Toast.makeText(context, if (isHindi) "सत्यापन सफल!" else "Verification Successful!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CallGreen),
                        shape = RoundedCornerShape(8.dp)
                      ) {
                        Text(if (isHindi) "सत्यापित करें" else "Verify")
                      }
                    }
                  }
                }
              }
            }

            2 -> {
              // STEP 2: Location & Price
              Text(
                text = if (isHindi) "2. कमरे का स्थान एवं किराया विवरण" else "2. Room Location & Pricing",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBluePrimary
              )
              Text(
                text = if (isHindi) "किफायती कमरा: ₹2,000 से ₹5,000 के बीच सेट करें" else "Target affordable bracket: ₹2,000 to ₹5,000/mo",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
              )

              // Locality Dropdown / Selection
              Text(
                text = if (isHindi) "जयपुर क्षेत्र (Locality) *" else "Jaipur Locality *",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Spacer(modifier = Modifier.height(4.dp))
              FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                listOf("Sanganer", "VKI", "Jhalana", "Sitapura", "Mansarovar", "Pratap Nagar").forEach { loc ->
                  val isSel = locality == loc
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(8.dp))
                      .background(if (isSel) DeepBluePrimary else Color(0xFFF1F5F9))
                      .clickable { locality = loc }
                      .padding(horizontal = 12.dp, vertical = 7.dp)
                  ) {
                    Text(
                      text = loc,
                      color = if (isSel) Color.White else TextPrimary,
                      fontSize = 12.5.sp,
                      fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Address
              OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(if (isHindi) "सटीक पता (मकान नं., गली, सड़क) *" else "Exact Address *") },
                placeholder = { Text(if (isHindi) "उदा: प्लॉट 12, रोड 4..." else "e.g., Plot 12, Street 4...") },
                leadingIcon = { Icon(Icons.Default.Home, null, tint = DeepBluePrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
              )

              Spacer(modifier = Modifier.height(10.dp))

              // Landmark
              OutlinedTextField(
                value = landmark,
                onValueChange = { landmark = it },
                label = { Text(if (isHindi) "नजदीकी लैंडमार्क (लैंडमार्क/बस स्टॉप) *" else "Nearest Landmark *") },
                placeholder = { Text(if (isHindi) "उदा: शिव मंदिर के पास..." else "e.g., Near Shiv Mandir...") },
                leadingIcon = { Icon(Icons.Default.LocationOn, null, tint = DeepBluePrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
              )

              Spacer(modifier = Modifier.height(12.dp))

              // Rent & Deposit
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                OutlinedTextField(
                  value = rentPrice,
                  onValueChange = { rentPrice = it.filter { c -> c.isDigit() } },
                  label = { Text(if (isHindi) "किराया (₹/माह) *" else "Rent (₹/mo) *") },
                  prefix = { Text("₹", fontWeight = FontWeight.Bold) },
                  modifier = Modifier.weight(1f),
                  singleLine = true,
                  shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                  value = depositAmount,
                  onValueChange = { depositAmount = it.filter { c -> c.isDigit() } },
                  label = { Text(if (isHindi) "सिक्योरिटी (₹)" else "Deposit (₹)") },
                  prefix = { Text("₹", fontWeight = FontWeight.Bold) },
                  modifier = Modifier.weight(1f),
                  singleLine = true,
                  shape = RoundedCornerShape(10.dp)
                )
              }
            }

            3 -> {
              // STEP 3: Amenities & Photos
              Text(
                text = if (isHindi) "3. सुविधाएं एवं कमरे की 2 फोटो" else "3. Amenities & 2 Room Photos",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBluePrimary
              )
              Text(
                text = if (isHindi) "2 स्पष्ट फोटो अपलोड करें ताकि किरायेदार तुरंत पसंद करें।" else "Upload 2 clear photos of the room and courtyard.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
              )

              // Photos preview row
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                // Photo 1
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                ) {
                  AsyncImage(
                    model = photo1Url,
                    contentDescription = "Photo 1",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                  )
                  Box(
                    modifier = Modifier
                      .align(Alignment.BottomCenter)
                      .fillMaxWidth()
                      .background(Color.Black.copy(alpha = 0.6f))
                      .padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = if (isHindi) "फोटो 1 (कमरा)" else "Photo 1 (Room)",
                      color = Color.White,
                      fontSize = 10.5.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }

                // Photo 2
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                ) {
                  AsyncImage(
                    model = photo2Url,
                    contentDescription = "Photo 2",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                  )
                  Box(
                    modifier = Modifier
                      .align(Alignment.BottomCenter)
                      .fillMaxWidth()
                      .background(Color.Black.copy(alpha = 0.6f))
                      .padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = if (isHindi) "फोटो 2 (सुविधा)" else "Photo 2 (Facility)",
                      color = Color.White,
                      fontSize = 10.5.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              // Change / Switch sample photos
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = if (isHindi) "कैमरा / फोटो सैंपल चुनें:" else "Select or switch photos:",
                  fontSize = 11.5.sp,
                  color = TextMuted
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  samplePhotos.take(3).forEachIndexed { idx, url ->
                    Box(
                      modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                          if (idx % 2 == 0) photo1Url = url else photo2Url = url
                          Toast.makeText(context, "Photo updated", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                      AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                      )
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Amenities Checklist
              Text(
                text = if (isHindi) "सुविधाएं (Amenities):" else "Available Amenities:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )

              // Toilet Type selection
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = if (isHindi) "शौचालय:" else "Toilet:",
                  fontSize = 12.5.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.width(70.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                  RadioButton(
                    selected = toiletType == ToiletType.SHARED,
                    onClick = { toiletType = ToiletType.SHARED },
                    colors = RadioButtonDefaults.colors(selectedColor = DeepBluePrimary)
                  )
                  Text(if (isHindi) "साझा" else "Shared", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                  RadioButton(
                    selected = toiletType == ToiletType.PERSONAL,
                    onClick = { toiletType = ToiletType.PERSONAL },
                    colors = RadioButtonDefaults.colors(selectedColor = DeepBluePrimary)
                  )
                  Text(if (isHindi) "पर्सनल" else "Personal", fontSize = 12.sp)
                }
              }

              // Water Type selection
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = if (isHindi) "पानी:" else "Water:",
                  fontSize = 12.5.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.width(70.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                  RadioButton(
                    selected = waterType == WaterSupplyType.HOURS_24,
                    onClick = { waterType = WaterSupplyType.HOURS_24 },
                    colors = RadioButtonDefaults.colors(selectedColor = DeepBluePrimary)
                  )
                  Text(if (isHindi) "24 घंटे" else "24h Supply", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                  RadioButton(
                    selected = waterType == WaterSupplyType.SCHEDULED_2X,
                    onClick = { waterType = WaterSupplyType.SCHEDULED_2X },
                    colors = RadioButtonDefaults.colors(selectedColor = DeepBluePrimary)
                  )
                  Text(if (isHindi) "2 समय" else "2x Daily", fontSize = 12.sp)
                }
              }

              // Checkbox: Separate meter
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                  meterType = if (meterType == MeterType.SEPARATE_SUBMETER) MeterType.FIXED_CHARGES else MeterType.SEPARATE_SUBMETER
                }
              ) {
                Checkbox(
                  checked = meterType == MeterType.SEPARATE_SUBMETER,
                  onCheckedChange = {
                    meterType = if (it) MeterType.SEPARATE_SUBMETER else MeterType.FIXED_CHARGES
                  },
                  colors = CheckboxDefaults.colors(checkedColor = DeepBluePrimary)
                )
                Text(
                  text = if (isHindi) "अलग बिजली सब-मीटर उपलब्ध है" else "Separate electricity sub-meter installed",
                  fontSize = 12.5.sp
                )
              }

              // Checkbox: Kitchen slab
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { hasKitchenSlab = !hasKitchenSlab }
              ) {
                Checkbox(
                  checked = hasKitchenSlab,
                  onCheckedChange = { hasKitchenSlab = it },
                  colors = CheckboxDefaults.colors(checkedColor = DeepBluePrimary)
                )
                Text(
                  text = if (isHindi) "खाना बनाने हेतु किचन स्लैब / जगह है" else "Kitchen cooking slab available",
                  fontSize = 12.5.sp
                )
              }

              // Checkbox: Bike parking
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { hasBikeParking = !hasBikeParking }
              ) {
                Checkbox(
                  checked = hasBikeParking,
                  onCheckedChange = { hasBikeParking = it },
                  colors = CheckboxDefaults.colors(checkedColor = DeepBluePrimary)
                )
                Text(
                  text = if (isHindi) "सुरक्षित बाइक / साइकिल पार्किंग" else "Secure bike / bicycle parking",
                  fontSize = 12.5.sp
                )
              }
            }
          }
        }

        // Bottom Navigation Buttons
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC))
            .border(1.dp, BorderSubtle)
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (currentStep > 1) {
            OutlinedButton(
              onClick = { currentStep -= 1 },
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(if (isHindi) "पीछे (Back)" else "Back")
            }
          } else {
            Spacer(modifier = Modifier.width(1.dp))
          }

          if (currentStep < 3) {
            Button(
              onClick = {
                if (currentStep == 1) {
                  if (ownerName.isBlank()) {
                    Toast.makeText(context, if (isHindi) "कृपया नाम भरें" else "Enter owner name", Toast.LENGTH_SHORT).show()
                    return@Button
                  }
                  if (ownerMobile.length < 10) {
                    Toast.makeText(context, if (isHindi) "कृपया मोबाइल नंबर भरें" else "Enter 10-digit mobile", Toast.LENGTH_SHORT).show()
                    return@Button
                  }
                }
                if (currentStep == 2) {
                  if (address.isBlank()) {
                    Toast.makeText(context, if (isHindi) "कृपया पता दर्ज करें" else "Enter exact address", Toast.LENGTH_SHORT).show()
                    return@Button
                  }
                }
                currentStep += 1
              },
              colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(if (isHindi) "आगे बढ़ें (Next)" else "Next Step")
            }
          } else {
            // Final Submit Button
            Button(
              onClick = {
                val parsedRent = rentPrice.toIntOrNull() ?: 2500
                val parsedDeposit = depositAmount.toIntOrNull() ?: 2000
                val newListing = HousingListing(
                  id = "jp-new-${UUID.randomUUID().toString().take(6)}",
                  titleEn = if (roomType.isNotEmpty()) "$roomType in $locality" else "Clean Affordable Room in $locality",
                  titleHi = "$locality में साफ हवादार कमरा",
                  locality = locality,
                  addressEn = address.ifBlank { "Street 3, $locality, Jaipur" },
                  addressHi = address.ifBlank { "गली 3, $locality, जयपुर" },
                  landmarkEn = landmark.ifBlank { "Near main bus stop" },
                  landmarkHi = landmark.ifBlank { "मुख्य बस स्टैंड के पास" },
                  rentAmount = parsedRent,
                  securityDeposit = parsedDeposit,
                  photoUrls = listOf(photo1Url, photo2Url),
                  waterType = waterType,
                  meterType = meterType,
                  toiletType = toiletType,
                  roomTypeEn = "1 Room",
                  roomTypeHi = "1 कमरा",
                  hasKitchenSlab = hasKitchenSlab,
                  hasBikeParking = hasBikeParking,
                  ownerName = ownerName.ifBlank { "मकान मालिक (Owner)" },
                  ownerPhone = if (ownerMobile.isNotBlank()) "+91$ownerMobile" else "+919829099999",
                  isVerified = true,
                  rulesEn = "Respectful tenants. Families or factory workers welcome.",
                  rulesHi = "सभ्य किरायेदार, परिवार अथवा कामगार भाई स्वागत हैं।"
                )
                onListingAdded(newListing)
                Toast.makeText(context, if (isHindi) "कमरा सफलतापूर्वक लिस्ट हो गया!" else "Room listed successfully!", Toast.LENGTH_LONG).show()
                onDismiss()
              },
              colors = ButtonDefaults.buttonColors(containerColor = CallGreen),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (isHindi) "फ्री में लिस्ट करें" else "Publish Listing",
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}
