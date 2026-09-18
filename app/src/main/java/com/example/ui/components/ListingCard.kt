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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.HousingListing
import com.example.model.MeterType
import com.example.model.ToiletType
import com.example.model.WaterSupplyType
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.AccentYellowDark
import com.example.ui.theme.AccentYellowLight
import com.example.ui.theme.BadgeMeterBg
import com.example.ui.theme.BadgeMeterText
import com.example.ui.theme.BadgeToiletBg
import com.example.ui.theme.BadgeToiletText
import com.example.ui.theme.BadgeVerifiedBg
import com.example.ui.theme.BadgeVerifiedText
import com.example.ui.theme.BadgeWaterBg
import com.example.ui.theme.BadgeWaterText
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CallGreen
import com.example.ui.theme.DeepBlueDark
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListingCard(
  listing: HousingListing,
  isHindi: Boolean,
  onClickDetails: (HousingListing) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  fun makeCall(phone: String) {
    try {
      val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Phone dialer: $phone", Toast.LENGTH_SHORT).show()
    }
  }

  fun sendWhatsApp(phone: String, listingTitle: String) {
    try {
      val cleanPhone = phone.replace("+", "").replace(" ", "")
      val message = Uri.encode(
        "नमस्ते, मैंने 'अपना कमरा जयपुर' ऐप पर आपका कमरा ($listingTitle - किराया: ₹${listing.rentAmount}) देखा। क्या यह अभी खाली है?"
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

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClickDetails(listing) },
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // 1. Room Image with Overlay Badges
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .background(Color(0xFFE2E8F0))
      ) {
        val primaryPhoto = listing.photoUrls.firstOrNull() ?: ""
        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(primaryPhoto)
            .crossfade(true)
            .build(),
          contentDescription = listing.titleEn,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxWidth()
        )

        // Gradient overlay for bottom of photo
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .align(Alignment.BottomCenter)
            .background(
              androidx.compose.ui.graphics.Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
              )
            )
        )

        // Top Left: Locality Badge
        Box(
          modifier = Modifier
            .padding(10.dp)
            .align(Alignment.TopStart)
            .clip(RoundedCornerShape(6.dp))
            .background(DeepBluePrimary.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = AccentYellow,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = listing.locality,
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Top Right: Direct Owner / Verified Badge
        Box(
          modifier = Modifier
            .padding(10.dp)
            .align(Alignment.TopEnd)
            .clip(RoundedCornerShape(6.dp))
            .background(BadgeVerifiedBg.copy(alpha = 0.95f))
            .border(1.dp, BadgeVerifiedText.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = "Verified",
              tint = BadgeVerifiedText,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = if (isHindi) "सत्यापित मालिक" else "Verified Owner",
              color = BadgeVerifiedText,
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Bottom on image: Room Type & Floor
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
            .padding(horizontal = 10.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isHindi) listing.roomTypeHi else listing.roomTypeEn,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )

          Text(
            text = if (isHindi) listing.floorHi else listing.floorEn,
            color = AccentYellow,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      // 2. Card Content
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp)
      ) {
        // Rent & Security Deposit Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Bottom
        ) {
          Column {
            Text(
              text = if (isHindi) "मासिक किराया" else "Monthly Rent",
              fontSize = 11.sp,
              color = TextMuted,
              fontWeight = FontWeight.Medium
            )
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "₹${listing.rentAmount}",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DeepBluePrimary
              )
              Text(
                text = if (isHindi) "/महीना" else "/mo",
                fontSize = 13.sp,
                color = TextSecondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 2.dp, start = 2.dp)
              )
            }
          }

          // Security deposit badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFFF1F5F9))
              .border(1.dp, BorderSubtle, RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 5.dp)
          ) {
            Column(horizontalAlignment = Alignment.End) {
              Text(
                text = if (isHindi) "जमानत राशि (Deposit)" else "Security Deposit",
                fontSize = 10.sp,
                color = TextMuted,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = if (listing.securityDeposit > 0) "₹${listing.securityDeposit}" else if (isHindi) "₹0 (कोई जमा नहीं)" else "₹0 (Zero Deposit)",
                fontSize = 12.5.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Title
        Text(
          text = if (isHindi) listing.titleHi else listing.titleEn,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Exact Address with Landmark
        Row(verticalAlignment = Alignment.Top) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Address",
            tint = AccentYellowDark,
            modifier = Modifier
              .size(16.dp)
              .padding(top = 2.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Column {
            Text(
              text = if (isHindi) listing.addressHi else listing.addressEn,
              fontSize = 12.5.sp,
              color = TextSecondary,
              fontWeight = FontWeight.Normal,
              lineHeight = 16.sp
            )
            Text(
              text = if (isHindi) "लैंडमार्क: ${listing.landmarkHi}" else "Landmark: ${listing.landmarkEn}",
              fontSize = 11.5.sp,
              color = TextMuted,
              fontWeight = FontWeight.Medium,
              lineHeight = 15.sp,
              modifier = Modifier.padding(top = 2.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3. Basic Amenities Badges
        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          // Water Badge
          AmenityBadge(
            icon = Icons.Default.WaterDrop,
            text = if (isHindi) listing.waterType.labelHi else listing.waterType.labelEn,
            bgColor = BadgeWaterBg,
            textColor = BadgeWaterText
          )

          // Electricity Meter Badge
          AmenityBadge(
            icon = Icons.Default.ElectricMeter,
            text = if (isHindi) listing.meterType.labelHi else listing.meterType.labelEn,
            bgColor = BadgeMeterBg,
            textColor = BadgeMeterText
          )

          // Toilet Badge (Shared / Personal)
          AmenityBadge(
            icon = Icons.Default.Wc,
            text = if (isHindi) listing.toiletType.labelHi else listing.toiletType.labelEn,
            bgColor = BadgeToiletBg,
            textColor = BadgeToiletText
          )

          // Kitchen Slab
          if (listing.hasKitchenSlab) {
            AmenityBadge(
              icon = Icons.Default.Restaurant,
              text = if (isHindi) "किचन स्लैब" else "Kitchen Slab",
              bgColor = Color(0xFFFEF3C7),
              textColor = Color(0xFF92400E)
            )
          }

          // Bike Parking
          if (listing.hasBikeParking) {
            AmenityBadge(
              icon = Icons.Default.PedalBike,
              text = if (isHindi) "बाइक पार्किंग" else "Bike Parking",
              bgColor = Color(0xFFF3F4F6),
              textColor = Color(0xFF374151)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Owner Info Note
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFF8FAFC))
            .padding(horizontal = 8.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = if (isHindi) "मकान मालिक:" else "Owner Name:",
              fontSize = 10.5.sp,
              color = TextMuted
            )
            Text(
              text = listing.ownerName,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          Text(
            text = if (isHindi) "दलाली: ₹0 (मुफ्त)" else "Brokerage: ₹0 Free",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = CallGreen
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Action Buttons: Big Green 'Call Owner' button + WhatsApp
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Big Green Call Owner Button
          Button(
            onClick = { makeCall(listing.ownerPhone) },
            modifier = Modifier
              .weight(1f)
              .height(48.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = CallGreen,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = "Call",
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (isHindi) "मालिक को कॉल करें" else "Call Owner Now",
              fontSize = 14.5.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }

          // WhatsApp Button
          OutlinedButton(
            onClick = { sendWhatsApp(listing.ownerPhone, listing.titleEn) },
            modifier = Modifier.height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
              containerColor = Color(0xFFF0FDF4),
              contentColor = Color(0xFF15803D)
            ),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF22C55E))
          ) {
            Icon(
              imageVector = Icons.Default.Chat,
              contentDescription = "WhatsApp",
              tint = Color(0xFF16A34A),
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "WhatsApp",
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF15803D)
            )
          }
        }
      }
    }
  }
}

@Composable
fun AmenityBadge(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  text: String,
  bgColor: Color,
  textColor: Color
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(6.dp))
      .background(bgColor)
      .padding(horizontal = 7.dp, vertical = 3.5.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = textColor,
        modifier = Modifier.size(13.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = text,
        color = textColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
