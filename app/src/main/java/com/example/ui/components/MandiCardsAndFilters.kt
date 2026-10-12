package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CommodityCategory
import com.example.data.model.MandiRecord
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.MSPBadgeGold
import com.example.ui.theme.PriceGainGreen
import com.example.ui.theme.PriceLossRed

@Composable
fun MandiPriceCard(
    record: MandiRecord,
    onCardClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onSetAlertClick: () -> Unit,
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick)
            .testTag("mandi_card_${record.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: Commodity & Variety + Favorite Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = com.example.data.localization.LocalizationStrings.localizedCropName(language, record.commodity),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        val isCommercial = com.example.data.model.isCommercialCrop(record.commodity)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isCommercial) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        ) {
                            Text(
                                text = if (isCommercial) "🌴 Commercial" else record.category.displayName.split(" ").first(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCommercial) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = record.variety,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onSetAlertClick,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("set_alert_btn_${record.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "Set Price Alert",
                            tint = HarvestGoldDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("favorite_btn_${record.id}")
                    ) {
                        Icon(
                            imageVector = if (record.isFavorite) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (record.isFavorite) "Remove from Watchlist" else "Add to Watchlist",
                            tint = if (record.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Market & State Location
            Text(
                text = "📍 ${record.market}, ${record.district} (${record.state})",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Price Row: Modal Price, 24h change %, and Sparkline
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Modal Price",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${String.format("%,.0f", record.modalPrice)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = " / Qtl",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 3.dp, start = 2.dp)
                        )
                    }
                }

                // 24h Trend Pill
                val isPositive = record.priceChangePercent >= 0
                val trendColor = if (isPositive) PriceGainGreen else PriceLossRed
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = trendColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isPositive) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                            contentDescription = null,
                            tint = trendColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${if (isPositive) "+" else ""}${String.format("%.1f", record.priceChangePercent)}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = trendColor
                        )
                    }
                }

                // Mini 7D Sparkline Canvas
                if (record.historical7Days.size >= 2) {
                    MiniSparkline(
                        prices = record.historical7Days,
                        lineColor = trendColor,
                        modifier = Modifier
                            .width(64.dp)
                            .height(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer info: Min-Max Range & MSP Comparison Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Range: ₹${String.format("%,.0f", record.minPrice)} - ₹${String.format("%,.0f", record.maxPrice)}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (record.mspPrice > 0.0) {
                    val diff = record.mspDifferencePercent ?: 0.0
                    val mspText = if (diff >= 0) "+${String.format("%.1f", diff)}% vs MSP" else "${String.format("%.1f", diff)}% vs MSP"
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MSPBadgeGold.copy(alpha = 0.14f)
                    ) {
                        Text(
                            text = "MSP ₹${String.format("%,.0f", record.mspPrice)} ($mspText)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MSPBadgeGold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Text(
                        text = "Arrivals: ${String.format("%,.0f", record.arrivalsQuintals)} Qtl",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun MiniSparkline(
    prices: List<Double>,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    val min = prices.minOrNull() ?: 0.0
    val max = prices.maxOrNull() ?: 1.0
    val range = (max - min).coerceAtLeast(1.0)

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val stepX = width / (prices.size - 1)
        val path = Path()

        prices.forEachIndexed { i, p ->
            val normY = 1f - ((p - min) / range).toFloat()
            val x = i * stepX
            val y = (normY * (height - 4.dp.toPx())) + 2.dp.toPx()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun SyncStatusHeader(
    isOfflineMode: Boolean,
    lastSyncedTimeText: String,
    isSyncing: Boolean,
    onToggleOffline: (Boolean) -> Unit,
    onSyncNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = if (isOfflineMode) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (isOfflineMode) Icons.Default.CloudOff else Icons.Default.CloudDone,
                    contentDescription = null,
                    tint = if (isOfflineMode) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (isOfflineMode) "Offline Mode (Local Cache Active)" else "Connected (All-India Mandi Sync)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Last synced: $lastSyncedTimeText",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isSyncing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    IconButton(
                        onClick = onSyncNowClick,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("sync_refresh_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sync Now",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))
                Switch(
                    checked = !isOfflineMode,
                    onCheckedChange = { isOnline -> onToggleOffline(!isOnline) },
                    modifier = Modifier.testTag("offline_mode_switch")
                )
            }
        }
    }
}

@Composable
fun MandiFilterBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: CommodityCategory,
    onCategorySelected: (CommodityCategory) -> Unit,
    allStates: List<String>,
    selectedState: String?,
    onStateSelected: (String?) -> Unit,
    selectedCrop: String? = null,
    onCropSelected: (String?) -> Unit = {},
    selectedVariety: String? = null,
    onVarietySelected: (String?) -> Unit = {},
    availableCrops: List<String> = emptyList(),
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    var stateMenuExpanded by remember { mutableStateOf(false) }

    val cropsToShow = remember(availableCrops, selectedCategory) {
        val base = if (availableCrops.isNotEmpty()) availableCrops else listOf(
            "Areca Nut", "Coconut", "Cocoa", "Banana", "Pineapple", "Coffee",
            "Black Pepper", "Cardamom", "Cashew Nut", "Ginger", "Sugarcane & Jaggery", "Rubber",
            "Wheat", "Paddy (Basmati)", "Soybean", "Onion", "Tomato", "Cotton",
            "Mustard", "Chana", "Potato", "Red Chilli", "Turmeric", "Cumin (Jeera)"
        )
        when (selectedCategory) {
            CommodityCategory.ALL -> base
            CommodityCategory.COMMERCIAL_PLANTATION -> base.filter { com.example.data.model.isCommercialCrop(it) }
            CommodityCategory.FRUITS -> base.filter { it.contains("Banana", true) || it.contains("Pineapple", true) }
            CommodityCategory.SPICES -> base.filter { it.contains("Pepper", true) || it.contains("Cardamom", true) || it.contains("Ginger", true) || it.contains("Chilli", true) || it.contains("Turmeric", true) || it.contains("Cumin", true) }
            CommodityCategory.CEREALS -> base.filter { it.contains("Wheat", true) || it.contains("Paddy", true) }
            CommodityCategory.PULSES -> base.filter { it.contains("Chana", true) }
            CommodityCategory.OILSEEDS -> base.filter { it.contains("Soybean", true) || it.contains("Mustard", true) }
            CommodityCategory.VEGETABLES -> base.filter { it.contains("Onion", true) || it.contains("Tomato", true) || it.contains("Potato", true) }
            CommodityCategory.CASH_CROPS -> base.filter { it.contains("Cotton", true) || it.contains("Sugar", true) || it.contains("Rubber", true) }
        }
    }

    val activeVarieties = remember(selectedCrop) {
        if (selectedCrop != null) {
            com.example.data.model.CropVarietyCatalog.getVarietiesForCrop(selectedCrop)
        } else emptyList()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Search Input & State Filter Dropdown
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text(com.example.data.localization.LocalizationStrings.searchPlaceholder(language), fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("mandi_search_input")
            )

            Box {
                OutlinedButton(
                    onClick = { stateMenuExpanded = true },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("state_filter_button")
                ) {
                    Text(
                        text = selectedState ?: com.example.data.localization.LocalizationStrings.allStates(language),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }

                DropdownMenu(
                    expanded = stateMenuExpanded,
                    onDismissRequest = { stateMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(com.example.data.localization.LocalizationStrings.allStates(language)) },
                        onClick = {
                            onStateSelected(null)
                            stateMenuExpanded = false
                        }
                    )
                    allStates.forEach { state ->
                        DropdownMenuItem(
                            text = { Text(state) },
                            onClick = {
                                onStateSelected(state)
                                stateMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal Category Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            CommodityCategory.values().forEach { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { 
                        Text(
                            text = com.example.data.localization.LocalizationStrings.localizedCategoryName(language, category),
                            fontSize = 12.sp,
                            fontWeight = if (category == selectedCategory) FontWeight.Bold else FontWeight.Normal
                        ) 
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("filter_chip_${category.name}")
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Crop Selection Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "All Crops" Chip
            FilterChip(
                selected = selectedCrop == null,
                onClick = { onCropSelected(null) },
                label = {
                    Text(
                        text = com.example.data.localization.LocalizationStrings.allCrops(language),
                        fontSize = 11.sp,
                        fontWeight = if (selectedCrop == null) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.testTag("crop_filter_all")
            )

            cropsToShow.forEach { crop ->
                val isSelected = selectedCrop == crop
                val isCommercial = com.example.data.model.isCommercialCrop(crop)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) onCropSelected(null) else onCropSelected(crop)
                    },
                    label = {
                        Text(
                            text = "${if (isCommercial) "🌴 " else ""}${com.example.data.localization.LocalizationStrings.localizedCropName(language, crop)}",
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("crop_filter_$crop")
                )
            }
        }

        // Sub-list for Varieties when a crop is selected!
        if (selectedCrop != null && activeVarieties.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🌱 ${com.example.data.localization.LocalizationStrings.selectVariety(language)}: ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = com.example.data.localization.LocalizationStrings.localizedCropName(language, selectedCrop),
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
                        // "All Varieties" Sub-chip
                        FilterChip(
                            selected = selectedVariety == null,
                            onClick = { onVarietySelected(null) },
                            label = {
                                Text(
                                    text = com.example.data.localization.LocalizationStrings.allVarieties(language),
                                    fontSize = 10.sp,
                                    fontWeight = if (selectedVariety == null) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedLabelColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            modifier = Modifier.testTag("variety_filter_all")
                        )

                        activeVarieties.forEach { variety ->
                            val isVarSelected = selectedVariety == variety.nameEn
                            FilterChip(
                                selected = isVarSelected,
                                onClick = {
                                    if (isVarSelected) onVarietySelected(null) else onVarietySelected(variety.nameEn)
                                },
                                label = {
                                    Text(
                                        text = variety.localizedName(language),
                                        fontSize = 10.sp,
                                        fontWeight = if (isVarSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.testTag("variety_chip_${variety.id}")
                            )
                        }
                    }
                }
            }
        }
    }
}
