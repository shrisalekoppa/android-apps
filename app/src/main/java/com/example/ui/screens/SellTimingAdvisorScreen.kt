package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SellAction
import com.example.data.model.SellTimingInsight
import com.example.data.model.WeatherAgriRisk
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriOnGreenContainer
import com.example.ui.theme.AgriSaffron
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.PriceGainGreen
import com.example.ui.theme.PriceLossRed
import com.example.ui.theme.WeatherRainBlue
import com.example.ui.viewmodel.MandiViewModel

@Composable
fun SellTimingAdvisorScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    val sellInsights by viewModel.sellInsights.collectAsState()
    val weatherRisks by viewModel.weatherRisks.collectAsState()
    val storageCalcResult by viewModel.storageCalculationResult.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = when (currentLanguage) {
        com.example.data.localization.AppLanguage.ENGLISH -> listOf("Sell Timing Insights", "Weather Analytics", "Storage ROI Calculator")
        com.example.data.localization.AppLanguage.KANNADA -> listOf("ಮಾರಾಟದ ಸಮಯ ಒಳನೋಟ", "ಹವಾಮಾನ ವಿಶ್ಲೇಷಣೆ", "ದಾಸ್ತಾನು ಲಾಭ ಲೆಕ್ಕಾಚಾರ")
        com.example.data.localization.AppLanguage.HINDI -> listOf("बिक्री समय अंतर्दृष्टि", "मौसम विश्लेषण", "भंडारण लाभ कैलकुलेटर")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("sell_timing_advisor_screen")
    ) {
        // Tab Header
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
                    modifier = Modifier.testTag("advisor_tab_$index")
                )
            }
        }

        when (selectedTabIndex) {
            0 -> SellTimingInsightsTab(insights = sellInsights, language = currentLanguage)
            1 -> WeatherAnalyticsTab(weatherList = weatherRisks, language = currentLanguage)
            2 -> StorageCalculatorTab(viewModel = viewModel, calcResult = storageCalcResult, language = currentLanguage)
        }
    }
}

@Composable
private fun SellTimingInsightsTab(
    insights: List<SellTimingInsight>,
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AgriGreenContainer)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = when (language) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Real-time agronomist intelligence factoring seasonal cycles, incoming mandi arrivals, national festive demand, and weather patterns to maximize harvest revenue."
                            com.example.data.localization.AppLanguage.KANNADA -> "ಗರಿಷ್ಠ ಆದಾಯವನ್ನು ಗಳಿಸಲು ಋತುಮಾನದ ಚಕ್ರಗಳು, ಮಂಡಿ ಆವಕ, ಹಬ್ಬದ ಬೇಡಿಕೆ ಮತ್ತು ಹವಾಮಾನವನ್ನು ಪರಿಗಣಿಸುವ ಕೃಷಿ ಸಲಹೆಗಾರರ ಬುದ್ಧಿಮತ್ತೆ."
                            com.example.data.localization.AppLanguage.HINDI -> "अधिकतम मुनाफा प्राप्त करने हेतु मौसमी चक्र, आवक, त्योहारी मांग और मौसम को ध्यान में रखकर तैयार कृषि परामर्श।"
                        },
                        fontSize = 12.sp,
                        color = AgriOnGreenContainer,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        items(insights) { insight ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("insight_card_${insight.cropName.lowercase()}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header badge & Confidence
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(insight.action.badgeColorHex)
                        ) {
                            val localizedBadgeText = when (insight.action) {
                                SellAction.SELL_URGENT -> when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "SELL URGENT"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ತಕ್ಷಣ ಮಾರಿ"
                                    com.example.data.localization.AppLanguage.HINDI -> "तुरंत बेचें"
                                }
                                SellAction.HOLD_STRONG -> when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "HOLD (BULLISH)"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಹಿಡಿದಿಟ್ಟುಕೊಳ್ಳಿ"
                                    com.example.data.localization.AppLanguage.HINDI -> "रोक कर रखें"
                                }
                                SellAction.FAVORABLE_SELL -> when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "GOOD TIME TO SELL"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಮಾರಾಟಕ್ಕೆ ಸೂಕ್ತ ಸಮಯ"
                                    com.example.data.localization.AppLanguage.HINDI -> "बिक्री का उपयुक्त समय"
                                }
                            }

                            Text(
                                text = localizedBadgeText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Text(
                            text = "${insight.confidencePercent}% ${
                                when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Confidence"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ವಿಶ್ವಾಸಾರ್ಹತೆ"
                                    com.example.data.localization.AppLanguage.HINDI -> "विश्वास"
                                }
                            }",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${com.example.data.localization.LocalizationStrings.localizedCropName(language, insight.cropName)} (${insight.marketName})",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = insight.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = insight.summary,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "📦 Supply Arrival Forecast: ${insight.arrivalForecast}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "🌦️ Weather Hazard: ${insight.weatherWarning}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Estimated Price Shift:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = insight.projectedPriceChange,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (insight.action == SellAction.SELL_URGENT) PriceLossRed else PriceGainGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherAnalyticsTab(
    weatherList: List<WeatherAgriRisk>,
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = when (language) {
                    com.example.data.localization.AppLanguage.ENGLISH -> "Agrometeorology & Mandi Dispatch Hazards"
                    com.example.data.localization.AppLanguage.KANNADA -> "ಕೃಷಿ ಹವಾಮಾನ ಮತ್ತು ಮಂಡಿ ಸಾಗಾಣಿಕೆ ಅಪಾಯಗಳು"
                    com.example.data.localization.AppLanguage.HINDI -> "कृषि मौसम विज्ञान और मंडी प्रेषण जोखिम"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = when (language) {
                    com.example.data.localization.AppLanguage.ENGLISH -> "Precipitation risk, moisture penalty index & transit road conditions"
                    com.example.data.localization.AppLanguage.KANNADA -> "ಮಳೆಯ ಅಪಾಯ, ತೇವಾಂಶ ದಂಡ ಸೂಚ್ಯಂಕ ಮತ್ತು ಸಾಗಾಟ ಪರಿಸ್ಥಿತಿಗಳು"
                    com.example.data.localization.AppLanguage.HINDI -> "वर्षा जोखिम, नमी कटौती सूचकांक और परिवहन स्थिति"
                },
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(weatherList) { weather ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("weather_risk_${weather.district.lowercase()}"),
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
                                text = "${weather.district}, ${weather.state}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = weather.condition,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (weather.precipitationRiskPercent > 30) Icons.Default.Thunderstorm else Icons.Default.WbSunny,
                                contentDescription = null,
                                tint = if (weather.precipitationRiskPercent > 30) WeatherRainBlue else HarvestGoldDark,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${weather.temperatureCelsius}°C",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Rain Hazard"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಮಳೆಯ ಅಪಾಯ"
                                        com.example.data.localization.AppLanguage.HINDI -> "बारिश जोखिम"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${weather.precipitationRiskPercent}% Risk",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (weather.precipitationRiskPercent > 30) PriceLossRed else PriceGainGreen
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Humidity"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಆರ್ದ್ರತೆ"
                                        com.example.data.localization.AppLanguage.HINDI -> "आर्द्रता"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${weather.humidityPercent}% RH",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Drying Index"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಒಣಗಿಸುವಿಕೆ"
                                        com.example.data.localization.AppLanguage.HINDI -> "सुखाने की दर"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = weather.harvestDryingIndex.split(" ").first(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "⚠️ Mandi Advisory: ${weather.rainAlertText}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🌾 Storage Advice: ${weather.godownStorageAdvisory}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun StorageCalculatorTab(
    viewModel: MandiViewModel,
    calcResult: com.example.data.model.StorageCalculatorResult?,
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH
) {
    val catalogCrops = remember { com.example.data.model.CropVarietyCatalog.getAllCrops() }
    var selectedCropName by remember { mutableStateOf("Areca Nut") }
    var selectedVarietyName by remember(selectedCropName) { mutableStateOf<String?>(null) }

    val cropVarieties = remember(selectedCropName) {
        com.example.data.model.CropVarietyCatalog.getVarietiesForCrop(selectedCropName)
    }

    var cropMenuExpanded by remember { mutableStateOf(false) }
    var varietyMenuExpanded by remember { mutableStateOf(false) }

    var quantityText by remember { mutableStateOf("20") }
    var spotPriceText by remember { mutableStateOf("54800") }
    var holdMonthsText by remember { mutableStateOf("4") }
    var storageCostText by remember { mutableStateOf("50") }
    var futurePriceText by remember { mutableStateOf("59500") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = when (language) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Godown & Cold Storage ROI Calculator"
                            com.example.data.localization.AppLanguage.KANNADA -> "ಗೋದಾಮು ಮತ್ತು ಕೋಲ್ಡ್ ಸ್ಟೋರೇಜ್ ಲಾಭದಾಯಕತೆ ಲೆಕ್ಕಾಚಾರ"
                            com.example.data.localization.AppLanguage.HINDI -> "गोदाम और कोल्ड स्टोरेज आरओआई कैलकुलेटर"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = when (language) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Evaluate whether storing your harvest pays off after factoring in rent, interest, and natural shrinkage."
                            com.example.data.localization.AppLanguage.KANNADA -> "ಬಾಡಿಗೆ, ಬಡ್ಡಿ ಮತ್ತು ನೈಸರ್ಗಿಕ ತೂಕ ನಷ್ಟವನ್ನು ಲೆಕ್ಕಹಾಕಿ ಬೆಳೆಯನ್ನು ಸಂಗ್ರಹಿಸುವುದು ಲಾಭದಾಯಕವೇ ಎಂದು ನಿರ್ಧರಿಸಿ."
                            com.example.data.localization.AppLanguage.HINDI -> "किराया, ब्याज और प्राकृतिक घटौती के बाद फसल का भंडारण लाभदायक है या नहीं इसका मूल्यांकन करें।"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Crop Selection Dropdown
                    Text(
                        text = "1. ${com.example.data.localization.LocalizationStrings.selectCrop(language)}:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Box(modifier = Modifier.padding(top = 4.dp)) {
                        OutlinedButton(
                            onClick = { cropMenuExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = com.example.data.localization.LocalizationStrings.localizedCropName(language, selectedCropName),
                                    maxLines = 1
                                )
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                        DropdownMenu(
                            expanded = cropMenuExpanded,
                            onDismissRequest = { cropMenuExpanded = false }
                        ) {
                            catalogCrops.forEach { crop ->
                                val isCommercial = com.example.data.model.isCommercialCrop(crop)
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "${if (isCommercial) "🌴 " else ""}${com.example.data.localization.LocalizationStrings.localizedCropName(language, crop)}",
                                            fontWeight = if (isCommercial) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        selectedCropName = crop
                                        selectedVarietyName = null
                                        cropMenuExpanded = false
                                        val firstVar = com.example.data.model.CropVarietyCatalog.getVarietiesForCrop(crop).firstOrNull()
                                        if (firstVar != null) {
                                            selectedVarietyName = firstVar.nameEn
                                            spotPriceText = firstVar.typicalPricePerQtl.toInt().toString()
                                            futurePriceText = (firstVar.typicalPricePerQtl * 1.12).toInt().toString()
                                        }
                                    }
                                )
                            }
                        }
                    }

                    // Variety Sub-List Dropdown
                    if (cropVarieties.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "2. 🌱 ${com.example.data.localization.LocalizationStrings.selectVariety(language)}:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Box(modifier = Modifier.padding(top = 4.dp)) {
                            OutlinedButton(
                                onClick = { varietyMenuExpanded = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val label = selectedVarietyName ?: com.example.data.localization.LocalizationStrings.allVarieties(language)
                                    Text(text = label, maxLines = 1)
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                                }
                            }
                            DropdownMenu(
                                expanded = varietyMenuExpanded,
                                onDismissRequest = { varietyMenuExpanded = false }
                            ) {
                                cropVarieties.forEach { v ->
                                    DropdownMenuItem(
                                        text = { Text(v.localizedName(language)) },
                                        onClick = {
                                            selectedVarietyName = v.nameEn
                                            varietyMenuExpanded = false
                                            spotPriceText = v.typicalPricePerQtl.toInt().toString()
                                            futurePriceText = (v.typicalPricePerQtl * 1.12).toInt().toString()
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = quantityText,
                            onValueChange = { quantityText = it },
                            label = {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Quantity (Qtl)"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಪ್ರಮಾಣ (ಕ್ವಿಂಟಾಲ್)"
                                        com.example.data.localization.AppLanguage.HINDI -> "मात्रा (क्विंटल)"
                                    },
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("storage_calc_quantity")
                        )

                        OutlinedTextField(
                            value = holdMonthsText,
                            onValueChange = { holdMonthsText = it },
                            label = {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Hold Period (Months)"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಸಂಗ್ರಹ ಅವಧಿ (ತಿಂಗಳು)"
                                        com.example.data.localization.AppLanguage.HINDI -> "भंडारण अवधि (महीने)"
                                    },
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("storage_calc_months")
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = spotPriceText,
                            onValueChange = { spotPriceText = it },
                            label = {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Spot Price (₹/Qtl)"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಹಾಲಿ ದರ (₹/ಕ್ವಿಂ)"
                                        com.example.data.localization.AppLanguage.HINDI -> "वर्तमान भाव (₹/क्विಂ)"
                                    },
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("storage_calc_spot_price")
                        )

                        OutlinedTextField(
                            value = futurePriceText,
                            onValueChange = { futurePriceText = it },
                            label = {
                                Text(
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Exp. Price (₹/Qtl)"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ನಿರೀಕ್ಷಿತ ದರ (₹/ಕ್ವಿಂ)"
                                        com.example.data.localization.AppLanguage.HINDI -> "अपेक्षित भाव (₹/क्विಂ)"
                                    },
                                    fontSize = 11.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("storage_calc_future_price")
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = storageCostText,
                        onValueChange = { storageCostText = it },
                        label = {
                            Text(
                                when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Storage Rent (₹/Qtl/Month)"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ದಾಸ್ತಾನು ಬಾಡಿಗೆ (₹/ಕ್ವಿಂ/ತಿಂಗಳು)"
                                    com.example.data.localization.AppLanguage.HINDI -> "भंडारण किराया (₹/क्विಂ/माह)"
                                },
                                fontSize = 11.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("storage_calc_rent")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val qty = quantityText.toDoubleOrNull() ?: 20.0
                            val spot = spotPriceText.toDoubleOrNull() ?: 54800.0
                            val months = holdMonthsText.toIntOrNull() ?: 4
                            val rent = storageCostText.toDoubleOrNull() ?: 50.0
                            val future = futurePriceText.toDoubleOrNull() ?: 59500.0
                            val finalName = if (selectedVarietyName != null) "$selectedCropName ($selectedVarietyName)" else selectedCropName
                            viewModel.calculateStorageRoi(finalName, qty, spot, months, rent, future)
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("storage_calc_calculate_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            when (language) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "Calculate Storage Profitability"
                                com.example.data.localization.AppLanguage.KANNADA -> "ದಾಸ್ತಾನು ಲಾಭದಾಯಕತೆ ಲೆಕ್ಕ ಹಾಕಿ"
                                com.example.data.localization.AppLanguage.HINDI -> "भंडारण लाभप्रदता की गणना करें"
                            }
                        )
                    }
                }
            }
        }

        // Calculation Output Card
        if (calcResult != null) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (calcResult.isProfitable) AgriGreenContainer else MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.testTag("storage_calc_result_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (calcResult.isProfitable) {
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "✅ PROFITABLE TO STORE"
                                        com.example.data.localization.AppLanguage.KANNADA -> "✅ ದಾಸ್ತಾನು ಮಾಡುವುದು ಲಾಭದಾಯಕ"
                                        com.example.data.localization.AppLanguage.HINDI -> "✅ भंडारण करना लाभदायक है"
                                    }
                                } else {
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "⚠️ UNFAVORABLE: SELL ON SPOT"
                                        com.example.data.localization.AppLanguage.KANNADA -> "⚠️ ನಷ್ಟದಾಯಕ: ತಕ್ಷಣ ಮಾರಿ"
                                        com.example.data.localization.AppLanguage.HINDI -> "⚠️ अनुपयुक्त: तुरंत बेचें"
                                    }
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (calcResult.isProfitable) PriceGainGreen else PriceLossRed
                            )
                            Text(
                                text = "${calcResult.holdMonths} ${
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Months Hold"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ತಿಂಗಳ ದಾಸ್ತಾನು"
                                        com.example.data.localization.AppLanguage.HINDI -> "महीने भंडारण"
                                    }
                                }",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Current Spot Revenue:"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಹಾಲಿ ಮಾರಾಟದ ಆದಾಯ:"
                                    com.example.data.localization.AppLanguage.HINDI -> "वर्तमान बिक्री आय:"
                                },
                                fontSize = 12.sp
                            )
                            Text("₹${String.format("%,.0f", calcResult.currentSpotRevenue)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Total Storage Rent Cost:"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಒಟ್ಟು ಗೋದಾಮು ಬಾಡಿಗೆ ವೆಚ್ಚ:"
                                    com.example.data.localization.AppLanguage.HINDI -> "कुल गोदाम किराया खर्च:"
                                },
                                fontSize = 12.sp
                            )
                            Text("-₹${String.format("%,.0f", calcResult.storageCostTotal)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PriceLossRed)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = when (language) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Projected Future Revenue:"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ನಿರೀಕ್ಷಿತ ಭವಿಷ್ಯದ ಆದಾಯ:"
                                    com.example.data.localization.AppLanguage.HINDI -> "अनुमानित भविष्य की आय:"
                                },
                                fontSize = 12.sp
                            )
                            Text("₹${String.format("%,.0f", calcResult.projectedFutureRevenue)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        ) {}
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (calcResult.isProfitable) {
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Estimated Net Gain:"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಅಂದಾಜು ನಿವ್ವಳ ಲಾಭ:"
                                        com.example.data.localization.AppLanguage.HINDI -> "अनुमानित शुद्ध लाभ:"
                                    }
                                } else {
                                    when (language) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Estimated Net Loss:"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಅಂದಾಜು ನಿವ್ವಳ ನಷ್ಟ:"
                                        com.example.data.localization.AppLanguage.HINDI -> "अनुमानित शुद्ध हानि:"
                                    }
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "₹${String.format("%,.0f", if (calcResult.isProfitable) calcResult.netGainOrLoss else -calcResult.netGainOrLoss)}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = if (calcResult.isProfitable) PriceGainGreen else PriceLossRed
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = calcResult.recommendationSummary,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
