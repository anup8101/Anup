package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.AccentYellowDark
import com.example.ui.theme.AccentYellowLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.DeepBlueDark
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAndFilterSection(
  isHindi: Boolean,
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  selectedLocality: String,
  onSelectLocality: (String) -> Unit,
  maxBudget: Float,
  onBudgetChange: (Float) -> Unit,
  filterPersonalToiletOnly: Boolean,
  onToggleToiletFilter: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showBudgetExpanded by remember { mutableStateOf(false) }

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = SurfaceLight,
    shadowElevation = 2.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      // 1. Prominent Locality Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
          Text(
            text = if (isHindi)
              "इलाका खोजें (सांगानेर, वी.के.आई, झालाना...)"
            else
              "Search locality (Sanganer, VKI, Jhalana...)",
            fontSize = 14.sp,
            color = TextMuted
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = DeepBluePrimary,
            modifier = Modifier.size(24.dp)
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { onSearchQueryChange("") }) {
              Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        },
        colors = TextFieldDefaults.colors(
          focusedContainerColor = Color(0xFFF8FAFC),
          unfocusedContainerColor = Color(0xFFF8FAFC),
          focusedIndicatorColor = DeepBluePrimary,
          unfocusedIndicatorColor = BorderSubtle,
          focusedTextColor = TextPrimary,
          unfocusedTextColor = TextPrimary
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Locality Quick Chips (Horizontal scroll)
      val scrollState = rememberScrollState()
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        SampleData.jaipurLocalities.forEach { (key, labelHi) ->
          val isSelected = selectedLocality.equals(key, ignoreCase = true)
          val label = if (isHindi) labelHi else key

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(
                if (isSelected) DeepBluePrimary else Color(0xFFF1F5F9)
              )
              .border(
                width = 1.dp,
                color = if (isSelected) DeepBluePrimary else BorderSubtle,
                shape = RoundedCornerShape(20.dp)
              )
              .clickable { onSelectLocality(key) }
              .padding(horizontal = 14.dp, vertical = 7.dp),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              if (key != "All") {
                Icon(
                  imageVector = Icons.Default.LocationOn,
                  contentDescription = null,
                  tint = if (isSelected) AccentYellow else TextMuted,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
              }
              Text(
                text = label,
                fontSize = 12.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else TextPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 3. Budget Slider Filter (₹2,000 to ₹5,000/month)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFFF8FAFC))
          .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CurrencyRupee,
              contentDescription = "Budget",
              tint = DeepBluePrimary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isHindi) "अधिकतम किराया बजट:" else "Max Monthly Budget:",
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(AccentYellowLight)
              .border(1.dp, AccentYellow, RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "₹${maxBudget.toInt()}/mo",
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold,
              color = AccentYellowDark
            )
          }
        }

        Slider(
          value = maxBudget,
          onValueChange = onBudgetChange,
          valueRange = 2000f..5000f,
          steps = 5, // 2000, 2500, 3000, 3500, 4000, 4500, 5000
          colors = SliderDefaults.colors(
            thumbColor = DeepBluePrimary,
            activeTrackColor = DeepBluePrimary,
            inactiveTrackColor = Color(0xFFCBD5E1)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "₹2,000",
            fontSize = 11.sp,
            color = TextMuted,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "₹3,500",
            fontSize = 11.sp,
            color = TextMuted,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "₹5,000",
            fontSize = 11.sp,
            color = TextMuted,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // 4. Quick Amenities Switch (e.g. Personal Toilet Filter)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
              if (filterPersonalToiletOnly) Color(0xFFF3E8FF) else Color(0xFFF1F5F9)
            )
            .border(
              1.dp,
              if (filterPersonalToiletOnly) Color(0xFF9333EA) else BorderSubtle,
              RoundedCornerShape(16.dp)
            )
            .clickable { onToggleToiletFilter() }
            .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
          Text(
            text = if (isHindi) {
              if (filterPersonalToiletOnly) "✓ केवल पर्सनल टॉयलेट" else "+ पर्सनल टॉयलेट"
            } else {
              if (filterPersonalToiletOnly) "✓ Personal Toilet Only" else "+ Personal Toilet"
            },
            fontSize = 12.sp,
            fontWeight = if (filterPersonalToiletOnly) FontWeight.Bold else FontWeight.Medium,
            color = if (filterPersonalToiletOnly) Color(0xFF7E22CE) else TextSecondary
          )
        }

        // Preset button: Under ₹3,000
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
              if (maxBudget <= 3000f) AccentYellowLight else Color(0xFFF1F5F9)
            )
            .border(
              1.dp,
              if (maxBudget <= 3000f) AccentYellow else BorderSubtle,
              RoundedCornerShape(16.dp)
            )
            .clickable { onBudgetChange(3000f) }
            .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
          Text(
            text = if (isHindi) "₹3,000 से कम" else "Under ₹3,000",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (maxBudget <= 3000f) AccentYellowDark else TextSecondary
          )
        }

        // Preset button: Reset all
        if (selectedLocality != "All" || maxBudget < 5000f || filterPersonalToiletOnly || searchQuery.isNotEmpty()) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .clickable {
                onSelectLocality("All")
                onBudgetChange(5000f)
                onSearchQueryChange("")
                if (filterPersonalToiletOnly) onToggleToiletFilter()
              }
              .padding(horizontal = 8.dp, vertical = 5.dp)
          ) {
            Text(
              text = if (isHindi) "रीसेट (Reset)" else "Reset All",
              fontSize = 12.sp,
              color = Color(0xFFDC2626),
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
