package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.theme.WhatsAppGreen

@Composable
fun RoomDetailDialog(
  listing: HousingListing,
  isHindi: Boolean,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  var selectedPhotoIndex by remember { mutableIntStateOf(0) }

  fun makeCall(phone: String) {
    try {
      val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Phone: $phone", Toast.LENGTH_SHORT).show()
    }
  }

  fun sendWhatsApp(phone: String, listingTitle: String) {
    try {
      val cleanPhone = phone.replace("+", "").replace(" ", "")
      val message = Uri.encode(
        "नमस्ते, मैं 'अपना कमरा जयपुर' ऐप से यह कमरा देखना चाहता हूँ: $listingTitle (किराया: ₹${listing.rentAmount}/माह, ${listing.locality})।"
      )
      val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://wa.me/$cleanPhone?text=$message")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "WhatsApp: $phone", Toast.LENGTH_SHORT).show()
    }
  }

  fun shareRoom() {
    try {
      val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
          Intent.EXTRA_TEXT,
          "किफायती कमरा जयपुर में: ₹${listing.rentAmount}/माह, ${listing.locality}, ${listing.addressHi}. सीधे मालिक से बात करें: ${listing.ownerPhone} (0% दलाली)"
        )
      }
      context.startActivity(Intent.createChooser(shareIntent, "कमरा शेयर करें"))
    } catch (_: Exception) {}
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.96f)
        .fillMaxHeight(0.92f),
      shape = RoundedCornerShape(16.dp),
      color = SurfaceLight
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Modal Top Navigation
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(DeepBluePrimary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = if (isHindi) listing.titleHi else listing.titleEn,
              color = Color.White,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1
            )
            Text(
              text = "${listing.locality}, Jaipur",
              color = AccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )
          }

          Row {
            IconButton(onClick = { shareRoom() }) {
              Icon(Icons.Default.Share, "Share", tint = Color.White)
            }
            IconButton(onClick = onDismiss) {
              Icon(Icons.Default.Close, "Close", tint = Color.White)
            }
          }
        }

        // Scrollable Room Details
        val scrollState = rememberScrollState()
        Column(
          modifier = Modifier
            .weight(1f)
            .verticalScroll(scrollState)
            .padding(16.dp)
        ) {
          // Large Photo with Gallery switcher
          val currentPhoto = listing.photoUrls.getOrNull(selectedPhotoIndex) ?: listing.photoUrls.firstOrNull() ?: ""
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(210.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFFCBD5E1))
          ) {
            AsyncImage(
              model = currentPhoto,
              contentDescription = "Room Photo",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )

            // Direct owner tag
            Box(
              modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(BadgeVerifiedBg)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, null, tint = BadgeVerifiedText, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (isHindi) "सीधे मालिक - ₹0 दलाली" else "Direct Owner - 0% Brokerage",
                  color = BadgeVerifiedText,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          // Photo thumbnails
          if (listing.photoUrls.size > 1) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              listing.photoUrls.forEachIndexed { index, url ->
                val isSelected = selectedPhotoIndex == index
                Box(
                  modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                      width = if (isSelected) 2.5.dp else 1.dp,
                      color = if (isSelected) DeepBluePrimary else BorderSubtle,
                      shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { selectedPhotoIndex = index }
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

          // Pricing Card
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFFF1F5F9))
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (isHindi) "मासिक किराया (Monthly Rent)" else "Monthly Rent",
                fontSize = 11.sp,
                color = TextMuted
              )
              Text(
                text = "₹${listing.rentAmount}/माह",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DeepBluePrimary
              )
            }

            Column(horizontalAlignment = Alignment.End) {
              Text(
                text = if (isHindi) "सिक्योरिटी डिपॉजिट" else "Security Deposit",
                fontSize = 11.sp,
                color = TextMuted
              )
              Text(
                text = if (listing.securityDeposit > 0) "₹${listing.securityDeposit}" else "₹0 (Zero Deposit)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Location & Landmark
          Text(
            text = if (isHindi) "कमरे का सटीक पता व लैंडमार्क" else "Exact Address & Landmark",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBluePrimary
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(verticalAlignment = Alignment.Top) {
            Icon(Icons.Default.LocationOn, null, tint = AccentYellowDark, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Column {
              Text(
                text = if (isHindi) listing.addressHi else listing.addressEn,
                fontSize = 13.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = if (isHindi) "लैंडमार्क: ${listing.landmarkHi}" else "Landmark: ${listing.landmarkEn}",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Amenities Grid
          Text(
            text = if (isHindi) "मूलभूत सुविधाएं (Basic Amenities)" else "Basic Amenities",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBluePrimary
          )
          Spacer(modifier = Modifier.height(8.dp))

          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
              .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.WaterDrop, null, tint = Color(0xFF0284C7), modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (isHindi) "पानी की व्यवस्था: ${listing.waterType.labelHi}" else "Water Supply: ${listing.waterType.labelEn}",
                fontSize = 12.5.sp,
                color = TextPrimary
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.ElectricMeter, null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (isHindi) "बिजली मीटर: ${listing.meterType.labelHi}" else "Electricity: ${listing.meterType.labelEn}",
                fontSize = 12.5.sp,
                color = TextPrimary
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Bathtub, null, tint = Color(0xFF9333EA), modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (isHindi) "शौचालय: ${listing.toiletType.labelHi}" else "Toilet: ${listing.toiletType.labelEn}",
                fontSize = 12.5.sp,
                color = TextPrimary
              )
            }

            if (listing.hasKitchenSlab) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Restaurant, null, tint = Color(0xFFB45309), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (isHindi) "रसोई: अलग किचन स्लैब उपलब्ध" else "Kitchen: Dedicated Cooking Slab",
                  fontSize = 12.5.sp,
                  color = TextPrimary
                )
              }
            }

            if (listing.hasBikeParking) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.PedalBike, null, tint = Color(0xFF374151), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (isHindi) "पार्किंग: सुरक्षित दोपहिया पार्किंग" else "Parking: Safe 2-Wheeler Parking",
                  fontSize = 12.5.sp,
                  color = TextPrimary
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // House Rules & Notes
          Text(
            text = if (isHindi) "नियम व शर्तें (House Rules)" else "House Rules & Guidelines",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBluePrimary
          )
          Spacer(modifier = Modifier.height(6.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFFEFCE8))
              .border(1.dp, Color(0xFFFEF08A), RoundedCornerShape(8.dp))
              .padding(10.dp)
          ) {
            Text(
              text = if (isHindi) listing.rulesHi else listing.rulesEn,
              fontSize = 12.sp,
              color = Color(0xFF713F12),
              lineHeight = 16.sp
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Owner Card
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFFF8FAFC))
              .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (isHindi) "सत्यापित मकान मालिक" else "Verified Owner",
                fontSize = 11.sp,
                color = CallGreen,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = listing.ownerName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = listing.ownerPhone,
                fontSize = 12.sp,
                color = TextSecondary
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(AccentYellowLight)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = if (isHindi) "सीधे मालिक" else "Direct Owner",
                color = AccentYellowDark,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Bottom Action Bar: Call Owner & WhatsApp buttons
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, BorderSubtle)
            .padding(14.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Call Owner Button
          Button(
            onClick = { makeCall(listing.ownerPhone) },
            modifier = Modifier
              .weight(1f)
              .height(50.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = CallGreen,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(Icons.Default.Call, null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isHindi) "कॉल करें (Call Owner)" else "Call Owner Now",
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }

          // WhatsApp Button
          Button(
            onClick = { sendWhatsApp(listing.ownerPhone, listing.titleEn) },
            modifier = Modifier
              .weight(1f)
              .height(50.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF25D366),
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(Icons.Default.Chat, null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "WhatsApp Chat",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
