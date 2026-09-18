package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.AccentYellowDark
import com.example.ui.theme.DeepBlueDark
import com.example.ui.theme.DeepBluePrimary

@Composable
fun HeaderSection(
  isHindi: Boolean,
  onToggleLanguage: () -> Unit,
  onOpenAddListing: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = DeepBluePrimary,
    shadowElevation = 4.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Logo & Title
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(AccentYellow),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Home,
              contentDescription = "Logo",
              tint = DeepBlueDark,
              modifier = Modifier.size(26.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = if (isHindi) "अपना कमरा जयपुर" else "Apna Kamra Jaipur",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.2.sp
              )
            }
            Text(
              text = if (isHindi) "किफायती कमरे • 0% दलाली" else "Affordable Rooms • 0% Brokerage",
              color = AccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Language toggle & Add listing button
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Language Switcher Chip
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(Color.White.copy(alpha = 0.15f))
              .clickable(onClick = onToggleLanguage)
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = "Language",
                tint = AccentYellow,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (isHindi) "हिंदी / ENG" else "ENG / हिंदी",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          // List Room Button
          Button(
            onClick = onOpenAddListing,
            colors = ButtonDefaults.buttonColors(
              containerColor = AccentYellow,
              contentColor = DeepBlueDark
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Add",
              modifier = Modifier.size(16.dp),
              tint = DeepBlueDark
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isHindi) "कमरा जोड़ें" else "+ Post Room",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Verification banner tag
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(Color.White.copy(alpha = 0.08f))
          .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Verified,
          contentDescription = "Verified",
          tint = AccentYellow,
          modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = if (isHindi)
            "सीधे मकान मालिक से बात करें | कोई दलाली या फीस नहीं"
          else
            "Direct Owner Contact | Zero Brokerage & No Hidden Fees",
          color = Color(0xFFE2E8F0),
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}
