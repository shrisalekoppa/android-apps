package com.example.ui.screens

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
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.MandiRecord
import com.example.ui.components.ChartTimeRange
import com.example.ui.components.MandiFilterBar
import com.example.ui.components.MandiPriceCard
import com.example.ui.components.MandiTrendChart
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.MSPBadgeGold
import com.example.ui.theme.PriceGainGreen
import com.example.ui.theme.PriceLossRed
import com.example.ui.viewmodel.MandiViewModel

enum class SortOption {
    DEFAULT,
    HIGHEST_PRICE,
    LOWEST_PRICE,
    TOP_GAINERS,
    HIGHEST_ARRIVALS;

    fun getLocalizedLabel(lang: com.example.data.localization.AppLanguage): String = when (this) {
        DEFAULT -> com.example.data.localization.LocalizationStrings.sortDefault(lang)
        HIGHEST_PRICE -> com.example.data.localization.LocalizationStrings.sortHighestPrice(lang)
        LOWEST_PRICE -> com.example.data.localization.LocalizationStrings.sortLowestPrice(lang)
        TOP_GAINERS -> com.example.data.localization.LocalizationStrings.sortTopGainers(lang)
        HIGHEST_ARRIVALS -> com.example.data.localization.LocalizationStrings.sortHighestArrivals(lang)
    }
}

@Composable
fun MarketPricesScreen(
    viewModel: MandiViewModel,
    onNavigateToAlerts: () -> Unit,
    onNavigateToReports: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredRecords by viewModel.filteredRecords.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val selectedState by viewModel.selectedState.collectAsState()
    val selectedCrop by viewModel.selectedCrop.collectAsState()
    val selectedVariety by viewModel.selectedVariety.collectAsState()
    val availableStates by viewModel.availableStates.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val selectedRecordForDetail by viewModel.selectedRecordForDetail.collectAsState()
    val selectedChartRange by viewModel.selectedChartRange.collectAsState()
    val allRecords by viewModel.allRecords.collectAsState()

    val availableCrops = remember(allRecords) {
        allRecords.map { it.commodity }.distinct().sortedByDescending { com.example.data.model.isCommercialCrop(it) }
    }

    var currentSort by remember { mutableStateOf(SortOption.DEFAULT) }
    var sortMenuExpanded by remember { mutableStateOf(false) }

    val sortedRecords = remember(filteredRecords, currentSort) {
        when (currentSort) {
            SortOption.DEFAULT -> filteredRecords
            SortOption.HIGHEST_PRICE -> filteredRecords.sortedByDescending { it.modalPrice }
            SortOption.LOWEST_PRICE -> filteredRecords.sortedBy { it.modalPrice }
            SortOption.TOP_GAINERS -> filteredRecords.sortedByDescending { it.priceChangePercent }
            SortOption.HIGHEST_ARRIVALS -> filteredRecords.sortedByDescending { it.arrivalsQuintals }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("market_prices_screen")
    ) {
        // Search & Filter Header Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            MandiFilterBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.onCategoryChange(it) },
                allStates = availableStates,
                selectedState = selectedState,
                onStateSelected = { viewModel.onStateChange(it) },
                selectedCrop = selectedCrop,
                onCropSelected = { viewModel.onCropChange(it) },
                selectedVariety = selectedVariety,
                onVarietySelected = { viewModel.onVarietyChange(it) },
                availableCrops = availableCrops,
                language = currentLanguage
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Commercial Crops Spotlight Bar
            Surface(
                onClick = {
                    if (selectedCategory == com.example.data.model.CommodityCategory.COMMERCIAL_PLANTATION) {
                        viewModel.onCategoryChange(com.example.data.model.CommodityCategory.ALL)
                    } else {
                        viewModel.onCategoryChange(com.example.data.model.CommodityCategory.COMMERCIAL_PLANTATION)
                    }
                },
                shape = RoundedCornerShape(10.dp),
                color = if (selectedCategory == com.example.data.model.CommodityCategory.COMMERCIAL_PLANTATION) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("commercial_crops_spotlight_btn")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🌴", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (currentLanguage) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "Commercial Crops: Areca Nut, Coconut, Cocoa, Banana, Pineapple"
                                com.example.data.localization.AppLanguage.KANNADA -> "ವಾಣಿಜ್ಯ ಬೆಳೆಗಳು: ಅಡಿಕೆ, ತೆಂಗಿನಕಾಯಿ, ಕೋಕೋ, ಬಾಳೆಹಣ್ಣು, ಅನಾನಸ್"
                                com.example.data.localization.AppLanguage.HINDI -> "वाणिज्यिक फसलें: सुपारी, नारियल, कोको, केला, अनानास"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = if (selectedCategory == com.example.data.model.CommodityCategory.COMMERCIAL_PLANTATION) "Active ✓" else "Filter →",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Result count & Sort dropdown button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (currentLanguage) {
                        com.example.data.localization.AppLanguage.ENGLISH -> "${sortedRecords.size} Mandi Markets Listed"
                        com.example.data.localization.AppLanguage.KANNADA -> "${sortedRecords.size} ಮಂಡಿ ಮಾರುಕಟ್ಟೆಗಳು ಪಟ್ಟಿಮಾಡಲಾಗಿದೆ"
                        com.example.data.localization.AppLanguage.HINDI -> "${sortedRecords.size} मंडी बाजार उपलब्ध हैं"
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Box {
                    OutlinedButton(
                        onClick = { sortMenuExpanded = true },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("sort_filter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(currentSort.getLocalizedLabel(currentLanguage), fontSize = 11.sp)
                    }

                    DropdownMenu(
                        expanded = sortMenuExpanded,
                        onDismissRequest = { sortMenuExpanded = false }
                    ) {
                        SortOption.values().forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.getLocalizedLabel(currentLanguage)) },
                                onClick = {
                                    currentSort = option
                                    sortMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // List of Mandi Price Cards
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (sortedRecords.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "No Mandis Match Your Filter"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಯಾವುದೇ ಮಂಡಿ ಹೊಂದಿಕೆಯಾಗುತ್ತಿಲ್ಲ"
                                    com.example.data.localization.AppLanguage.HINDI -> "कोई मंडी उपलब्ध नहीं है"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Try clearing search query or reset crop/variety filters"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಹುಡುಕಾಟ ಪದವನ್ನು ತೆರವುಗೊಳಿಸಿ ಅಥವಾ ಬೆಳೆ/ತಳಿ ಫಿಲ್ಟರ್ ಮರುಹೊಂದಿಸಿ"
                                    com.example.data.localization.AppLanguage.HINDI -> "खोज शब्द साफ़ करें या फसल/किस्म फ़िल्टर बदलें"
                                },
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = {
                                    viewModel.onSearchQueryChange("")
                                    viewModel.onCategoryChange(com.example.data.model.CommodityCategory.ALL)
                                    viewModel.onStateChange(null)
                                    viewModel.onCropChange(null)
                                    viewModel.onVarietyChange(null)
                                }
                            ) {
                                Text(
                                    text = when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Reset Filters"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಫಿಲ್ಟರ್ ಮರುಹೊಂದಿಸಿ"
                                        com.example.data.localization.AppLanguage.HINDI -> "फ़िल्टर रीसेट करें"
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                items(sortedRecords) { record ->
                    MandiPriceCard(
                        record = record,
                        onCardClick = { viewModel.selectRecordForDetail(record) },
                        onFavoriteToggle = { viewModel.toggleFavorite(record) },
                        onSetAlertClick = { onNavigateToAlerts() },
                        language = currentLanguage
                    )
                }
            }
        }
    }

    // Detailed Mandi Dialog / Bottom Sheet
    if (selectedRecordForDetail != null) {
        val record = selectedRecordForDetail!!
        Dialog(onDismissRequest = { viewModel.selectRecordForDetail(null) }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mandi_detail_dialog"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, record.commodity),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${record.variety} • ${com.example.data.localization.LocalizationStrings.localizedCategoryName(currentLanguage, record.category)}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "📍 ${record.market}, ${record.district} (${record.state})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        IconButton(
                            onClick = { viewModel.selectRecordForDetail(null) },
                            modifier = Modifier.testTag("close_detail_dialog_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Price Headline
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = com.example.data.localization.LocalizationStrings.modalPrice(currentLanguage),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "₹${String.format("%,.0f", record.modalPrice)} / Qtl",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        val isPositive = record.priceChangePercent >= 0
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = (if (isPositive) PriceGainGreen else PriceLossRed).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${if (isPositive) "+" else ""}${String.format("%.1f", record.priceChangePercent)}% 24h",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPositive) PriceGainGreen else PriceLossRed,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Chart
                    val chartData = if (selectedChartRange == ChartTimeRange.SEVEN_DAYS) {
                        record.historical7Days
                    } else {
                        record.historical30Days
                    }

                    MandiTrendChart(
                        dataPoints = chartData,
                        minBand = record.minPrice,
                        maxBand = record.maxPrice,
                        arrivalsVolume = record.arrivalsQuintals,
                        selectedRange = selectedChartRange,
                        onRangeSelected = { viewModel.onSelectChartRange(it) }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Details breakdown
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            DetailRow(
                                label = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Minimum Price"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಕನಿಷ್ಠ ದರ"
                                    com.example.data.localization.AppLanguage.HINDI -> "न्यूनतम भाव"
                                },
                                value = "₹${String.format("%,.0f", record.minPrice)} / Qtl"
                            )
                            DetailRow(
                                label = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Maximum Price"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಗರಿಷ್ಠ ದರ"
                                    com.example.data.localization.AppLanguage.HINDI -> "अधिकतम भाव"
                                },
                                value = "₹${String.format("%,.0f", record.maxPrice)} / Qtl"
                            )
                            DetailRow(
                                label = com.example.data.localization.LocalizationStrings.arrivals(currentLanguage),
                                value = "${String.format("%,.0f", record.arrivalsQuintals)} Quintals"
                            )
                            if (record.mspPrice > 0.0) {
                                DetailRow(
                                    label = when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Minimum Support Price (MSP)"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಕನಿಷ್ಠ ಬೆಂಬಲ ಬೆಲೆ (MSP)"
                                        com.example.data.localization.AppLanguage.HINDI -> "न्यूनतम समर्थन मूल्य (MSP)"
                                    },
                                    value = "₹${String.format("%,.0f", record.mspPrice)} / Qtl"
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.selectRecordForDetail(null)
                                onNavigateToAlerts()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.AddAlert, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(com.example.data.localization.LocalizationStrings.priceAlerts(currentLanguage), fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                viewModel.selectRecordForDetail(null)
                                onNavigateToReports()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Share Bulletin"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ವರದಿ ಹಂಚಿಕೊಳ್ಳಿ"
                                    com.example.data.localization.AppLanguage.HINDI -> "बुलेटिन साझा करें"
                                },
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}
