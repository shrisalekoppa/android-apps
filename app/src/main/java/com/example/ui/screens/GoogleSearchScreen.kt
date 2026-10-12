package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppLanguage
import com.example.data.localization.LocalizationStrings
import com.example.ui.viewmodel.MandiViewModel
import java.net.URLEncoder

fun launchGoogleSearch(context: Context, query: String) {
    if (query.isBlank()) return
    try {
        val encoded = URLEncoder.encode(query.trim(), "UTF-8")
        val uri = Uri.parse("https://www.google.com/search?q=$encoded")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open browser: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun GoogleSearchScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    var searchInput by remember { mutableStateOf("") }

    val recentSearches = remember {
        mutableStateListOf(
            "Areca nut Shivamogga APMC price today",
            "Tiptur copra coconut rate today",
            "CAMPCO cocoa bean price today",
            "Govt MSP 2026 notification Agmarknet"
        )
    }

    // Query Builder states
    var selectedBuilderCrop by remember { mutableStateOf("Areca Nut") }
    var selectedBuilderVariety by remember(selectedBuilderCrop) { mutableStateOf<String?>(null) }
    var selectedBuilderMandi by remember { mutableStateOf("Shivamogga APMC") }
    var selectedBuilderIntent by remember { mutableStateOf("Mandi Price Today") }

    val varietiesForBuilderCrop = remember(selectedBuilderCrop) {
        com.example.data.model.CropVarietyCatalog.getVarietiesForCrop(selectedBuilderCrop)
    }

    val builderCrops = listOf(
        "Areca Nut", "Coconut", "Cocoa", "Banana", "Pineapple", "Coffee",
        "Black Pepper", "Cardamom", "Cashew Nut", "Ginger", "Sugarcane", "Rubber",
        "Onion", "Soybean", "Wheat", "Paddy (Basmati)", "Tomato", "Cotton", "Red Chilli"
    )

    val builderMandis = listOf(
        "Shivamogga APMC", "Sirsi TSS", "Tiptur APMC", "CAMPCO Puttur", "Bengaluru APMC",
        "Vazhakulam Market", "Chikmagalur APMC", "Kumily APMC", "Kottayam Rubber Board",
        "Pune APMC", "Lasalgaon Mandi", "Indore Mandi", "Guntur Mirchi Yard", "All-India APMC"
    )

    val builderIntents = listOf(
        "Mandi Price Today",
        "Price Trends & Arrival Forecast",
        "Govt MSP & APMC Notice",
        "Export & Import Policies"
    )

    val builtQuery = remember(selectedBuilderCrop, selectedBuilderVariety, selectedBuilderMandi, selectedBuilderIntent, currentLanguage) {
        val cropName = when (currentLanguage) {
            AppLanguage.ENGLISH -> selectedBuilderCrop
            AppLanguage.KANNADA -> LocalizationStrings.localizedCropName(AppLanguage.KANNADA, selectedBuilderCrop)
            AppLanguage.HINDI -> LocalizationStrings.localizedCropName(AppLanguage.HINDI, selectedBuilderCrop)
        }
        val varietyPart = if (selectedBuilderVariety != null) " $selectedBuilderVariety" else ""
        val intentPart = when (currentLanguage) {
            AppLanguage.ENGLISH -> selectedBuilderIntent
            AppLanguage.KANNADA -> when (selectedBuilderIntent) {
                "Mandi Price Today" -> "ಇಂದಿನ ಮಾರುಕಟ್ಟೆ ದರ"
                "Price Trends & Arrival Forecast" -> "ದರ ಪ್ರವೃತ್ತಿ ಮತ್ತು ಆವಕ"
                "Govt MSP & APMC Notice" -> "ಸರ್ಕಾರಿ ಎಂಎಸ್‌ಪಿ ಆದೇಶ"
                else -> "ರಫ್ತು ಮತ್ತು ಮಾರುಕಟ್ಟೆ ನೀತಿ"
            }
            AppLanguage.HINDI -> when (selectedBuilderIntent) {
                "Mandi Price Today" -> "आज का मंडी भाव"
                "Price Trends & Arrival Forecast" -> "भाव रुझान और आवक"
                "Govt MSP & APMC Notice" -> "सरकारी एमएसपी सूचना"
                else -> "निर्यात व बाजार नीति"
            }
        }
        "$cropName$varietyPart $selectedBuilderMandi $intentPart"
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("google_search_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🔍", fontSize = 22.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.ENGLISH -> "Google Mandi Live Search"
                                    AppLanguage.KANNADA -> "ಗೂಗಲ್ ಮಂಡಿ ಲೈವ್ ಹುಡುಕಾಟ"
                                    AppLanguage.HINDI -> "गूगल मंडी लाइव खोज"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.ENGLISH -> "Fetch live commodity prices, news & govt circulars on Google"
                                    AppLanguage.KANNADA -> "ಲೈವ್ ಬೆಳೆ ದರಗಳು, ಮಾರುಕಟ್ಟೆ ಸುದ್ದಿ ಮತ್ತು ಸರ್ಕಾರಿ ಆದೇಶಗಳನ್ನು ಗೂಗಲ್‌ನಲ್ಲಿ ಪಡೆಯಿರಿ"
                                    AppLanguage.HINDI -> "ताजा मंडी भाव, बाजार समाचार और सरकारी आदेश गूगल पर प्राप्त करें"
                                },
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Search Input Box
                    OutlinedTextField(
                        value = searchInput,
                        onValueChange = { searchInput = it },
                        placeholder = {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.ENGLISH -> "Type crop or mandi (e.g., Areca nut Sirsi rate)..."
                                    AppLanguage.KANNADA -> "ಬೆಳೆ ಅಥವಾ ಮಂಡಿ ನಮೂದಿಸಿ (ಉದಾ: ಅಡಿಕೆ ಶಿರಸಿ ದರ)..."
                                    AppLanguage.HINDI -> "फसल या मंडी लिखें (उदा: सुपारी सिरसी भाव)..."
                                },
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            if (searchInput.isNotEmpty()) {
                                IconButton(onClick = { searchInput = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (searchInput.isNotBlank()) {
                                    if (!recentSearches.contains(searchInput.trim())) {
                                        recentSearches.add(0, searchInput.trim())
                                    }
                                    launchGoogleSearch(context, searchInput)
                                }
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("google_search_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            val q = searchInput.ifBlank { "Indian agricultural crop mandi prices today Agmarknet" }
                            if (!recentSearches.contains(q.trim())) {
                                recentSearches.add(0, q.trim())
                            }
                            launchGoogleSearch(context, q)
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("google_search_submit_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.ENGLISH -> "Search on Google ↗"
                                AppLanguage.KANNADA -> "ಗೂಗಲ್‌ನಲ್ಲಿ ಹುಡುಕಿ ↗"
                                AppLanguage.HINDI -> "गूगल पर खोजें ↗"
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Custom Agri Search Builder Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⚡", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.ENGLISH -> "Custom Agricultural Query Builder"
                                AppLanguage.KANNADA -> "ಕೃಷಿ ಕಸ್ಟಮ್ ಪ್ರಶ್ನೆ ನಿರ್ಮಾಪಕ"
                                AppLanguage.HINDI -> "कृषि कस्टम सर्च बिल्डर"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "Build precision search queries with 1 tap without typing:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Step 1: Crop
                    Text(
                        text = "1. ${LocalizationStrings.selectCrop(currentLanguage)}:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        builderCrops.forEach { crop ->
                            val isCommercial = com.example.data.model.isCommercialCrop(crop)
                            FilterChip(
                                selected = crop == selectedBuilderCrop,
                                onClick = {
                                    selectedBuilderCrop = crop
                                    selectedBuilderVariety = null
                                },
                                label = {
                                    Text(
                                        text = "${if (isCommercial) "🌴 " else ""}${LocalizationStrings.localizedCropName(currentLanguage, crop)}",
                                        fontSize = 11.sp,
                                        fontWeight = if (crop == selectedBuilderCrop) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    // Step 1.5: Variety Sub-list
                    if (varietiesForBuilderCrop.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "🌱 1.5 ${LocalizationStrings.selectVariety(currentLanguage)} (${LocalizationStrings.localizedCropName(currentLanguage, selectedBuilderCrop)}):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = selectedBuilderVariety == null,
                                onClick = { selectedBuilderVariety = null },
                                label = {
                                    Text(
                                        text = LocalizationStrings.allVarieties(currentLanguage),
                                        fontSize = 10.sp,
                                        fontWeight = if (selectedBuilderVariety == null) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onTertiary
                                )
                            )
                            varietiesForBuilderCrop.forEach { variety ->
                                val isVarSelected = selectedBuilderVariety == variety.nameEn
                                FilterChip(
                                    selected = isVarSelected,
                                    onClick = {
                                        selectedBuilderVariety = if (isVarSelected) null else variety.nameEn
                                    },
                                    label = {
                                        Text(
                                            text = variety.localizedName(currentLanguage),
                                            fontSize = 10.sp,
                                            fontWeight = if (isVarSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Step 2: Mandi
                    Text(
                        text = "2. ${LocalizationStrings.selectMandi(currentLanguage)}:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        builderMandis.forEach { mandi ->
                            FilterChip(
                                selected = mandi == selectedBuilderMandi,
                                onClick = { selectedBuilderMandi = mandi },
                                label = { Text(mandi, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Step 3: Intent
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.ENGLISH -> "3. Search Focus / Information Type:"
                            AppLanguage.KANNADA -> "3. ಹುಡುಕಾಟದ ಗುರಿ / ಮಾಹಿತಿ ಪ್ರಕಾರ:"
                            AppLanguage.HINDI -> "3. खोज का उद्देश्य / सूचना प्रकार:"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        builderIntents.forEach { focus ->
                            val localizedFocus = when (currentLanguage) {
                                AppLanguage.ENGLISH -> focus
                                AppLanguage.KANNADA -> when (focus) {
                                    "Mandi Price Today" -> "ಇಂದಿನ ಮಾರುಕಟ್ಟೆ ದರ"
                                    "Price Trends & Arrival Forecast" -> "ದರ ಪ್ರವೃತ್ತಿ & ಆವಕ"
                                    "Govt MSP & APMC Notice" -> "ಸರ್ಕಾರಿ ಎಂಎಸ್‌ಪಿ ಆದೇಶ"
                                    else -> "ರಫ್ತು ಮತ್ತು ಮಾರುಕಟ್ಟೆ ನೀತಿ"
                                }
                                AppLanguage.HINDI -> when (focus) {
                                    "Mandi Price Today" -> "आज का मंडी भाव"
                                    "Price Trends & Arrival Forecast" -> "भाव रुझान व आवक"
                                    "Govt MSP & APMC Notice" -> "सरकारी एमएसपी सूचना"
                                    else -> "निर्यात व बाजार नीति"
                                }
                            }
                            FilterChip(
                                selected = focus == selectedBuilderIntent,
                                onClick = { selectedBuilderIntent = focus },
                                label = { Text(localizedFocus, fontSize = 11.sp) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Query Preview Box
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.ENGLISH -> "Generated Google Query:"
                                        AppLanguage.KANNADA -> "ರಚಿಸಲಾದ ಗೂಗಲ್ ಪ್ರಶ್ನೆ:"
                                        AppLanguage.HINDI -> "तैयार की गई गूगल खोज क्वेरी:"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text("\"$builtQuery\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Button(
                                onClick = {
                                    if (!recentSearches.contains(builtQuery)) {
                                        recentSearches.add(0, builtQuery)
                                    }
                                    launchGoogleSearch(context, builtQuery)
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("launch_builder_query_btn")
                            ) {
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.ENGLISH -> "Search ↗"
                                        AppLanguage.KANNADA -> "ಹುಡುಕಿ ↗"
                                        AppLanguage.HINDI -> "खोजें ↗"
                                    },
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Popular 1-Tap Commercial Crops Search Topics
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🌴", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.ENGLISH -> "Popular Commercial Crop Google Searches"
                            AppLanguage.KANNADA -> "ಜನಪ್ರಿಯ ವಾಣಿಜ್ಯ ಬೆಳೆ ಗೂಗಲ್ ಹುಡುಕಾಟಗಳು"
                            AppLanguage.HINDI -> "लोकप्रिय वाणिज्यिक फसल गूगल सर्च"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "Instant 1-tap live market rate searches on Google:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        val popularCommercialSearches = listOf(
            "Areca nut Shivamogga APMC mandi rate today" to "🌰 Shivamogga Arecanut Rates",
            "Sirsi TSS chali arecanut price today" to "🌰 Sirsi White Chali Rates",
            "Tiptur copra coconut rate today APMC" to "🥥 Tiptur Ball Copra Rates",
            "CAMPCO cocoa beans purchase price Karnataka" to "🍫 CAMPCO Cocoa Price Today",
            "Yelakki banana rate Bengaluru Yeshwanthpur APMC" to "🍌 Bengaluru Yelakki Banana",
            "Vazhakulam pineapple price Kerala market today" to "🍍 Vazhakulam GI Pineapple",
            "Chikmagalur coffee curing APMC Arabica price" to "☕ Chikmagalur Arabica Coffee",
            "Natural rubber RSS4 Kottayam market price today" to "🌲 Kottayam Rubber RSS-4",
            "Bodinayakanur green cardamom auction price today" to "🌱 Cardamom Auction Rate",
            "Guntur mirchi yard red chilli price today" to "🌶️ Guntur Red Chilli Rates",
            "IMD Kisan agricultural weather rain forecast this week" to "🌦️ Agricultural Rain Forecast",
            "Government MSP rates 2026 notification Agmarknet" to "📜 Govt MSP Rates Notification"
        )

        items(popularCommercialSearches) { (query, title) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!recentSearches.contains(query)) {
                            recentSearches.add(0, query)
                        }
                        launchGoogleSearch(context, query)
                    }
                    .testTag("quick_search_${title.hashCode()}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = query, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                    }
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Recent Searches
        if (recentSearches.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Recent Google Searches",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(recentSearches.take(5)) { pastQuery ->
                Surface(
                    onClick = { launchGoogleSearch(context, pastQuery) },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = pastQuery, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
