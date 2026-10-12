package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import com.example.data.model.MandiRecord
import com.example.ui.components.ChartTimeRange
import com.example.ui.components.MandiTrendChart
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.MSPBadgeGold
import com.example.ui.theme.PriceGainGreen
import com.example.ui.theme.PriceLossRed
import com.example.ui.viewmodel.MandiViewModel

@Composable
fun TrendsAnalyticsScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    val allRecords by viewModel.allRecords.collectAsState()
    val selectedChartRange by viewModel.selectedChartRange.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    val distinctCommodities = remember(allRecords) {
        val all = allRecords.map { it.commodity }.distinct()
        // Put requested commercial crops first
        all.sortedByDescending { com.example.data.model.isCommercialCrop(it) }
    }

    var selectedCommodityName by remember(distinctCommodities) {
        mutableStateOf(distinctCommodities.firstOrNull() ?: "Areca Nut")
    }

    var selectedVarietyName by remember(selectedCommodityName) {
        mutableStateOf<String?>(null)
    }

    val varietiesForCommodity = remember(selectedCommodityName) {
        com.example.data.model.CropVarietyCatalog.getVarietiesForCrop(selectedCommodityName)
    }

    // Records for the selected commodity across different mandis
    val commodityRecords = remember(allRecords, selectedCommodityName, selectedVarietyName) {
        val base = allRecords.filter { it.commodity.equals(selectedCommodityName, ignoreCase = true) }
        if (selectedVarietyName != null) {
            val matching = base.filter { it.variety.contains(selectedVarietyName!!, ignoreCase = true) }
            if (matching.isNotEmpty()) matching else base
        } else base
    }

    var selectedRecord by remember(commodityRecords) {
        mutableStateOf(commodityRecords.firstOrNull() ?: allRecords.firstOrNull())
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("trends_analytics_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title & Description
        item {
            Column {
                Text(
                    text = when (currentLanguage) {
                        com.example.data.localization.AppLanguage.ENGLISH -> "Market Trends & Price Volatility"
                        com.example.data.localization.AppLanguage.KANNADA -> "ಮಾರುಕಟ್ಟೆ ಪ್ರವೃತ್ತಿ ಮತ್ತು ದರ ಏರಿಳಿತಗಳು"
                        com.example.data.localization.AppLanguage.HINDI -> "बाजार रुझान और मूल्य अस्थिरता"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = when (currentLanguage) {
                        com.example.data.localization.AppLanguage.ENGLISH -> "Historical trends, volume elasticity & cross-mandi price arbitrage (Commercial & Field crops)"
                        com.example.data.localization.AppLanguage.KANNADA -> "ಐತಿಹಾಸಿಕ ಪ್ರವೃತ್ತಿಗಳು, ಆವಕ ಪರಿಮಾಣ ಮತ್ತು ವಿವಿಧ ಮಂಡಿಗಳ ದರ ಹೋಲಿಕೆ (ಅಡಿಕೆ, ತೆಂಗು, ಕೋಕೋ, ಬಾಳೆ...)"
                        com.example.data.localization.AppLanguage.HINDI -> "ऐतिहासिक रुझान, आवक प्रभाव और मंडी भाव तुलना (सुपारी, नारियल, कोको, केला...)"
                    },
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Horizontal Commodity Selector Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                distinctCommodities.forEach { name ->
                    val isCommercial = com.example.data.model.isCommercialCrop(name)
                    FilterChip(
                        selected = name == selectedCommodityName,
                        onClick = {
                            selectedCommodityName = name
                            selectedRecord = allRecords.firstOrNull { it.commodity == name }
                        },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isCommercial) {
                                    Text("🌴 ", fontSize = 11.sp)
                                }
                                Text(
                                    text = com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, name),
                                    fontWeight = if (name == selectedCommodityName) FontWeight.Bold else FontWeight.SemiBold
                                )
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.testTag("trend_crop_chip_$name")
                    )
                }
            }
        }

        // Sub-list for Varieties of selected commodity
        if (varietiesForCommodity.isNotEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🌱 ${com.example.data.localization.LocalizationStrings.selectVariety(currentLanguage)}: ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, selectedCommodityName),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = selectedVarietyName == null,
                                onClick = {
                                    selectedVarietyName = null
                                    selectedRecord = commodityRecords.firstOrNull()
                                },
                                label = {
                                    Text(
                                        text = com.example.data.localization.LocalizationStrings.allVarieties(currentLanguage),
                                        fontSize = 10.sp,
                                        fontWeight = if (selectedVarietyName == null) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onTertiary
                                )
                            )

                            varietiesForCommodity.forEach { variety ->
                                val isSelected = selectedVarietyName == variety.nameEn
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedVarietyName = if (isSelected) null else variety.nameEn
                                    },
                                    label = {
                                        Text(
                                            text = variety.localizedName(currentLanguage),
                                            fontSize = 10.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
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
                }
            }
        }

        // Active Mandi Selector Dropdown & Market Badge
        item {
            if (selectedRecord != null) {
                val record = selectedRecord!!
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, record.commodity)} - ${record.variety}",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "📍 ${record.market}, ${record.state}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Switch Market Dropdown
                            var marketDropdownExpanded by remember { mutableStateOf(false) }
                            Box {
                                OutlinedButton(
                                    onClick = { marketDropdownExpanded = true },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = com.example.data.localization.LocalizationStrings.switchMandi(currentLanguage),
                                        fontSize = 11.sp
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                DropdownMenu(
                                    expanded = marketDropdownExpanded,
                                    onDismissRequest = { marketDropdownExpanded = false }
                                ) {
                                    commodityRecords.forEach { r ->
                                        DropdownMenuItem(
                                            text = { Text("${r.market} (₹${r.modalPrice.toInt()})") },
                                            onClick = {
                                                selectedRecord = r
                                                marketDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Large Interactive Chart
                        val chartPrices = if (selectedChartRange == ChartTimeRange.SEVEN_DAYS) {
                            record.historical7Days
                        } else {
                            record.historical30Days
                        }

                        MandiTrendChart(
                            dataPoints = chartPrices,
                            minBand = record.minPrice,
                            maxBand = record.maxPrice,
                            arrivalsVolume = record.arrivalsQuintals,
                            selectedRange = selectedChartRange,
                            onRangeSelected = { viewModel.onSelectChartRange(it) }
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Analytics Summary Tiles
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AnalyticsTile(
                                label = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Modal Price"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಮಾದರಿ ದರ"
                                    com.example.data.localization.AppLanguage.HINDI -> "मॉडल भाव"
                                },
                                value = "₹${String.format("%,.0f", record.modalPrice)}",
                                subValue = "${if (record.priceChangePercent >= 0) "+" else ""}${String.format("%.1f", record.priceChangePercent)}%",
                                subColor = if (record.priceChangePercent >= 0) PriceGainGreen else PriceLossRed,
                                modifier = Modifier.weight(1f)
                            )
                            AnalyticsTile(
                                label = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Govt MSP"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಸರ್ಕಾರಿ ಬೆಂಬಲ ಬೆಲೆ"
                                    com.example.data.localization.AppLanguage.HINDI -> "सरकारी एमएसपी"
                                },
                                value = if (record.mspPrice > 0.0) "₹${String.format("%,.0f", record.mspPrice)}" else "N/A",
                                subValue = record.mspDifferencePercent?.let { "${if (it >= 0) "+" else ""}${String.format("%.1f", it)}% spread" } ?: when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Free market"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಮುಕ್ತ ಮಾರುಕಟ್ಟೆ"
                                    com.example.data.localization.AppLanguage.HINDI -> "मुक्त बाजार"
                                },
                                subColor = MSPBadgeGold,
                                modifier = Modifier.weight(1f)
                            )
                            AnalyticsTile(
                                label = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Arrival Volume"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಆವಕ ಪರಿಮಾಣ"
                                    com.example.data.localization.AppLanguage.HINDI -> "आवक मात्रा"
                                },
                                value = "${String.format("%,.0f", record.arrivalsQuintals)} Qtl",
                                subValue = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Daily Supply"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ದೈನಂದಿನ ಪೂರೈಕೆ"
                                    com.example.data.localization.AppLanguage.HINDI -> "दैनिक आपूर्ति"
                                },
                                subColor = HarvestGoldDark,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Cross-Mandi Price Arbitrage Table for this Crop
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CompareArrows,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (currentLanguage) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "All-India Mandi Price Arbitrage (${com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, selectedCommodityName)})"
                                com.example.data.localization.AppLanguage.KANNADA -> "ಅಖಿಲ ಭಾರತ ಮಂಡಿ ದರ ಹೋಲಿಕೆ (${com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, selectedCommodityName)})"
                                com.example.data.localization.AppLanguage.HINDI -> "अखिल भारतीय मंडी भाव तुलना (${com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, selectedCommodityName)})"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = when (currentLanguage) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Compare spot prices across states to choose the best market to dispatch crops"
                            com.example.data.localization.AppLanguage.KANNADA -> "ಬೆಳೆಯನ್ನು ಸಾಗಿಸಲು ಉತ್ತಮ ಮಾರುಕಟ್ಟೆಯನ್ನು ಆಯ್ಕೆ ಮಾಡಲು ರಾಜ್ಯಗಳ ನಡುವಿನ ದರಗಳನ್ನು ಹೋಲಿಕೆ ಮಾಡಿ"
                            com.example.data.localization.AppLanguage.HINDI -> "फसल भेजने के लिए सर्वोत्तम बाजार चुनने हेतु विभिन्न राज्यों के भावों की तुलना करें"
                        },
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (commodityRecords.isEmpty()) {
                        Text(
                            text = when (currentLanguage) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "Only 1 mandi reporting for this crop today"
                                com.example.data.localization.AppLanguage.KANNADA -> "ಈ ಬೆಳೆಗೆ ಇಂದು 1 ಮಂಡಿ ಮಾತ್ರ ವರದಿ ಮಾಡಿದೆ"
                                com.example.data.localization.AppLanguage.HINDI -> "आज इस फसल के लिए केवल 1 मंडी रिपोर्ट कर रही है"
                            },
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        commodityRecords.forEachIndexed { index, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedRecord = item }
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.market,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedRecord?.id == item.id) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${item.district}, ${item.state} • ${item.variety}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "₹${String.format("%,.0f", item.modalPrice)} / Qtl",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${if (item.priceChangePercent >= 0) "+" else ""}${String.format("%.1f", item.priceChangePercent)}%",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.priceChangePercent >= 0) PriceGainGreen else PriceLossRed
                                    )
                                }
                            }
                            if (index < commodityRecords.lastIndex) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp),
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                ) {}
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnalyticsTile(
    label: String,
    value: String,
    subValue: String,
    subColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
            Text(text = subValue, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = subColor)
        }
    }
}
