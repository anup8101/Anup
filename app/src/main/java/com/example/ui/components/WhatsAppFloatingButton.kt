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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CallGreen
import com.example.ui.theme.DeepBlueDark
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.theme.WhatsAppGreenDark

@Composable
fun WhatsAppStickyBottomBar(
  isHindi: Boolean,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var showHelpDialog by remember { mutableStateOf(false) }

  fun openWhatsAppSupport() {
    try {
      val message = Uri.encode(
        if (isHindi)
          "नमस्ते अपना कमरा जयपुर टीम, मुझे जयपुर में ₹2000-₹5000 के बजट में किराए का कमरा खोजने में मदद चाहिए।"
        else
          "Hello Apna Kamra Jaipur Team, I need help finding an affordable rental room in Jaipur (Budget: ₹2,000 - ₹5,000/mo)."
      )
      val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://wa.me/919829011223?text=$message")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      showHelpDialog = true
    }
  }

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = DeepBlueDark,
    shadowElevation = 8.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Main clickable button matching the exact prompt requirement:
      // "A sticky floating button at the bottom: 'Need Help on WhatsApp? Click here to chat'."
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFF25D366))
          .clickable { openWhatsAppSupport() }
          .padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Chat,
              contentDescription = "WhatsApp",
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = if (isHindi)
                "व्हाट्सएप पर सहायता चाहिए? चैट करें"
              else
                "Need Help on WhatsApp? Click here to chat",
              color = Color.White,
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold
            )
            Text(
              text = if (isHindi)
                "फ्री सहायता • 24x7 हेल्पलाइन (Click to open)"
              else
                "Free Help • 0% Brokerage Support",
              color = Color(0xFFDCFCE7),
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      // FAQ / Direct Helpline trigger
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(Color.White.copy(alpha = 0.12f))
          .clickable { showHelpDialog = true }
          .padding(horizontal = 10.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.HelpOutline,
          contentDescription = "Helpline & FAQ",
          tint = AccentYellow,
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }

  if (showHelpDialog) {
    WhatsAppHelpModal(
      isHindi = isHindi,
      onDismiss = { showHelpDialog = false },
      onOpenWhatsApp = {
        showHelpDialog = false
        openWhatsAppSupport()
      }
    )
  }
}

@Composable
fun WhatsAppHelpModal(
  isHindi: Boolean,
  onDismiss: () -> Unit,
  onOpenWhatsApp: () -> Unit
) {
  val context = LocalContext.current

  fun callTollFree() {
    try {
      val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:+919829011223")
      }
      context.startActivity(intent)
    } catch (_: Exception) {}
  }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f),
      shape = RoundedCornerShape(16.dp),
      color = SurfaceLight
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SupportAgent,
              contentDescription = null,
              tint = DeepBluePrimary,
              modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (isHindi) "सहायता केंद्र (Jaipur Help)" else "Jaipur Worker Helpline",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = DeepBluePrimary
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, null, tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // WhatsApp action card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenWhatsApp() },
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Chat, null, tint = Color(0xFF16A34A), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (isHindi) "व्हाट्सएप चैट शुरू करें" else "Chat on WhatsApp Support",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF166534)
              )
              Text(
                text = if (isHindi) "कमरा खोजने या मालिक से बात करने में मदद" else "Instant help finding rooms & talking to owners",
                fontSize = 11.5.sp,
                color = Color(0xFF15803D)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Helpline Phone Call Card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { callTollFree() },
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE))
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Call, null, tint = DeepBluePrimary, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (isHindi) "हेल्पलाइन पर सीधे कॉल करें" else "Direct Phone Call Helpline",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBluePrimary
              )
              Text(
                text = "+91 98290 11223 (सुबह 8 से रात 9 बजे तक)",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Key Points for Low-income tenants
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF8FAFC))
            .padding(10.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = if (isHindi) "• 100% दलाली मुक्त: किसी को कोई कमीशन न दें" else "• 100% Zero Brokerage: Never pay brokers",
            fontSize = 11.5.sp,
            color = TextSecondary
          )
          Text(
            text = if (isHindi) "• कमरा खुद जाकर देखें तभी किराया तय करें" else "• Always visit the room in person before paying rent",
            fontSize = 11.5.sp,
            color = TextSecondary
          )
          Text(
            text = if (isHindi) "• बिजली सब-मीटर की रीडिंग देखकर ही रसीद लें" else "• Note down sub-meter electricity reading first",
            fontSize = 11.5.sp,
            color = TextSecondary
          )
        }
      }
    }
  }
}
