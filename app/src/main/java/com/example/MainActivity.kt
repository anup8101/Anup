package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.HousingListing
import com.example.model.ToiletType
import com.example.ui.components.AddListingDialog
import com.example.ui.components.HeaderSection
import com.example.ui.components.ListingCard
import com.example.ui.components.RoomDetailDialog
import com.example.ui.components.SearchAndFilterSection
import com.example.ui.components.WhatsAppStickyBottomBar
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.AccentYellowDark
import com.example.ui.theme.AccentYellowLight
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CallGreen
import com.example.ui.theme.DeepBlueDark
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        JaipurHousingApp()
      }
    }
  }
}

@Composable
fun JaipurHousingApp(modifier: Modifier = Modifier) {
  var isHindi by remember { mutableStateOf(true) }
  var searchQuery by remember { mutableStateOf("") }
  var selectedLocality by remember { mutableStateOf("All") }
  var maxBudget by remember { mutableFloatStateOf(5000f) }
  var filterPersonalToiletOnly by remember { mutableStateOf(false) }

  var listings by remember { mutableStateOf(SampleData.initialListings) }
  var showAddListingDialog by remember { mutableStateOf(false) }
  var selectedListingForDetail by remember { mutableStateOf<HousingListing?>(null) }

  // Filter listings based on locality, search query, budget, and toilet type
  val filteredListings = remember(listings, searchQuery, selectedLocality, maxBudget, filterPersonalToiletOnly) {
    listings.filter { listing ->
      val matchesLocality = selectedLocality == "All" || listing.locality.equals(selectedLocality, ignoreCase = true)
      val matchesSearch = searchQuery.isBlank() ||
        listing.locality.contains(searchQuery, ignoreCase = true) ||
        listing.addressEn.contains(searchQuery, ignoreCase = true) ||
        listing.addressHi.contains(searchQuery, ignoreCase = true) ||
        listing.landmarkEn.contains(searchQuery, ignoreCase = true) ||
        listing.landmarkHi.contains(searchQuery, ignoreCase = true) ||
        listing.titleEn.contains(searchQuery, ignoreCase = true) ||
        listing.titleHi.contains(searchQuery, ignoreCase = true)
      val matchesBudget = listing.rentAmount <= maxBudget.toInt()
      val matchesToilet = !filterPersonalToiletOnly || listing.toiletType == ToiletType.PERSONAL

      matchesLocality && matchesSearch && matchesBudget && matchesToilet
    }
  }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = BackgroundLight,
    topBar = {
      HeaderSection(
        isHindi = isHindi,
        onToggleLanguage = { isHindi = !isHindi },
        onOpenAddListing = { showAddListingDialog = true }
      )
    },
    bottomBar = {
      // 4. WhatsApp Integration: A sticky floating button at the bottom
      WhatsAppStickyBottomBar(isHindi = isHindi)
    }
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      contentPadding = PaddingValues(bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // 1. Search & Filter Header (Locality search & ₹2,000 to ₹5,000 budget slider)
      item {
        SearchAndFilterSection(
          isHindi = isHindi,
          searchQuery = searchQuery,
          onSearchQueryChange = { searchQuery = it },
          selectedLocality = selectedLocality,
          onSelectLocality = { selectedLocality = it },
          maxBudget = maxBudget,
          onBudgetChange = { maxBudget = it },
          filterPersonalToiletOnly = filterPersonalToiletOnly,
          onToggleToiletFilter = { filterPersonalToiletOnly = !filterPersonalToiletOnly }
        )
      }

      // Listings count and active filter summary banner
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Apartment,
              contentDescription = null,
              tint = DeepBluePrimary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isHindi)
                "उपलब्ध सस्ते कमरे (${filteredListings.size})"
              else
                "Available Affordable Rooms (${filteredListings.size})",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = DeepBluePrimary
            )
          }

          if (selectedLocality != "All" || maxBudget < 5000f || searchQuery.isNotEmpty()) {
            Text(
              text = if (isHindi) "फिल्टर सक्रिय" else "Filtered",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = AccentYellowDark
            )
          }
        }
      }

      // 2. Room Feed / Listings
      if (filteredListings.isEmpty()) {
        item {
          EmptyListingsState(
            isHindi = isHindi,
            onResetFilters = {
              selectedLocality = "All"
              searchQuery = ""
              maxBudget = 5000f
              filterPersonalToiletOnly = false
            }
          )
        }
      } else {
        items(filteredListings, key = { it.id }) { listing ->
          Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            ListingCard(
              listing = listing,
              isHindi = isHindi,
              onClickDetails = { selectedListingForDetail = it }
            )
          }
        }
      }

      // Worker Housing Trust & Direct Owner Note
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(14.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = DeepBluePrimary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (isHindi)
                  "मजदूर व कामगार भाइयों हेतु सूचना:"
                else
                  "Notice for Low-Income Workers & Families:",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBluePrimary
              )
              Text(
                text = if (isHindi)
                  "सभी कमरे सीधे मकान मालिकों द्वारा बिना किसी दलाली के पोस्ट किए गए हैं। कोई भी व्यक्ति कमरे दिखाने का पैसा मांगे तो न दें।"
                else
                  "All rooms are listed directly by verified property owners with zero broker fees. Never pay anyone fees to view rooms.",
                fontSize = 11.5.sp,
                color = TextSecondary,
                lineHeight = 15.sp,
                modifier = Modifier.padding(top = 2.dp)
              )
            }
          }
        }
      }
    }
  }

  // 3. Owner Add-Listing Form (Multi-step modal with OTP & 2 photos)
  if (showAddListingDialog) {
    AddListingDialog(
      isHindi = isHindi,
      onDismiss = { showAddListingDialog = false },
      onListingAdded = { newListing ->
        listings = listOf(newListing) + listings
      }
    )
  }

  // Room Detailed View
  selectedListingForDetail?.let { listing ->
    RoomDetailDialog(
      listing = listing,
      isHindi = isHindi,
      onDismiss = { selectedListingForDetail = null }
    )
  }
}

@Composable
fun EmptyListingsState(
  isHindi: Boolean,
  onResetFilters: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 20.dp),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(56.dp)
          .clip(RoundedCornerShape(28.dp))
          .background(Color(0xFFFEF3C7)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.SearchOff,
          contentDescription = null,
          tint = AccentYellowDark,
          modifier = Modifier.size(30.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = if (isHindi) "इस बजट या इलाके में कमरा नहीं मिला" else "No Rooms Found Matching Filters",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = DeepBluePrimary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = if (isHindi)
          "कृपया बजट स्लाइडर बढ़ाएं (उदा. ₹3,500 तक) या क्षेत्र 'सभी' चुनें।"
        else
          "Please try increasing the budget slider or clearing the locality filter.",
        fontSize = 12.5.sp,
        color = TextSecondary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onResetFilters,
        colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
        shape = RoundedCornerShape(8.dp)
      ) {
        Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(if (isHindi) "सभी फिल्टर हटाएं (Reset)" else "Reset All Filters")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun JaipurHousingAppPreview() {
  MyApplicationTheme {
    JaipurHousingApp()
  }
}
