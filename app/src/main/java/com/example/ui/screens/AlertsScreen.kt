package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.PriceAlert
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.PriceGainGreen
import com.example.ui.theme.PriceLossRed
import com.example.ui.viewmodel.MandiViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AlertsScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    val alerts by viewModel.allAlerts.collectAsState()
    val allRecords by viewModel.allRecords.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }

    val distinctCrops = remember(allRecords) {
        val crops = allRecords.map { it.commodity }.distinct()
        crops.sortedByDescending { com.example.data.model.isCommercialCrop(it) }
    }
    val distinctMandis = remember(allRecords) { allRecords.map { it.market }.distinct() }

    Scaffold(
        modifier = modifier.testTag("alerts_screen"),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.testTag("fab_create_alert")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create Alert")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column {
                    Text(
                        text = com.example.data.localization.LocalizationStrings.priceAlertsTitle(currentLanguage),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = com.example.data.localization.LocalizationStrings.priceAlertsSubtitle(currentLanguage),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (alerts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = com.example.data.localization.LocalizationStrings.noAlertsTitle(currentLanguage),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = com.example.data.localization.LocalizationStrings.noAlertsSubtitle(currentLanguage),
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { showCreateDialog = true },
                                modifier = Modifier.testTag("create_first_alert_btn")
                            ) {
                                Text(com.example.data.localization.LocalizationStrings.createAlert(currentLanguage))
                            }
                        }
                    }
                }
            } else {
                items(alerts) { alert ->
                    AlertItemCard(
                        alert = alert,
                        onToggleActive = { viewModel.toggleAlertStatus(alert.id, it) },
                        onDelete = { viewModel.deletePriceAlert(alert.id) },
                        language = currentLanguage
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateAlertDialog(
            availableCrops = distinctCrops,
            availableMandis = distinctMandis,
            language = currentLanguage,
            onDismiss = { showCreateDialog = false },
            onCreate = { crop, mandi, price, isAbove ->
                viewModel.addPriceAlert(crop, mandi, price, isAbove)
                showCreateDialog = false
            }
        )
    }
}

@Composable
private fun AlertItemCard(
    alert: PriceAlert,
    onToggleActive: (Boolean) -> Unit,
    onDelete: () -> Unit,
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH
) {
    val isTriggered = alert.triggeredAt != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("alert_card_${alert.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isTriggered) MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (alert.isAbove) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = if (alert.isAbove) PriceGainGreen else PriceLossRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = com.example.data.localization.LocalizationStrings.localizedCropName(language, alert.cropName),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = alert.isActive,
                        onCheckedChange = onToggleActive,
                        modifier = Modifier.testTag("toggle_alert_${alert.id}")
                    )
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.testTag("delete_alert_${alert.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Alert",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = "📍 ${alert.marketName}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (alert.isAbove) com.example.data.localization.LocalizationStrings.targetRisesAbove(language) else com.example.data.localization.LocalizationStrings.targetDropsBelow(language),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "₹${String.format("%,.0f", alert.targetPrice)} / Qtl",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (isTriggered) {
                    val triggerTimeText = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(alert.triggeredAt!!))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PriceGainGreen.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = PriceGainGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${com.example.data.localization.LocalizationStrings.triggered(language)} ($triggerTimeText)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = PriceGainGreen
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = if (alert.isActive) com.example.data.localization.LocalizationStrings.monitoring247(language) else com.example.data.localization.LocalizationStrings.paused(language),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateAlertDialog(
    availableCrops: List<String>,
    availableMandis: List<String>,
    language: com.example.data.localization.AppLanguage = com.example.data.localization.AppLanguage.ENGLISH,
    onDismiss: () -> Unit,
    onCreate: (crop: String, mandi: String, price: Double, isAbove: Boolean) -> Unit
) {
    var selectedCrop by remember { mutableStateOf(availableCrops.firstOrNull() ?: "Areca Nut") }
    var selectedVariety by remember(selectedCrop) { mutableStateOf<String?>(null) }
    var selectedMandi by remember { mutableStateOf(availableMandis.firstOrNull() ?: "Shivamogga APMC") }
    var targetPriceText by remember { mutableStateOf("55000") }
    var isAbove by remember { mutableStateOf(true) }

    var cropMenuExpanded by remember { mutableStateOf(false) }
    var varietyMenuExpanded by remember { mutableStateOf(false) }
    var mandiMenuExpanded by remember { mutableStateOf(false) }

    val cropVarieties = remember(selectedCrop) {
        com.example.data.model.CropVarietyCatalog.getVarietiesForCrop(selectedCrop)
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("create_alert_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = com.example.data.localization.LocalizationStrings.createAlert(language),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = com.example.data.localization.LocalizationStrings.priceAlertsSubtitle(language),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Crop Selector
                Text(
                    text = com.example.data.localization.LocalizationStrings.selectCrop(language),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Box {
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
                                text = com.example.data.localization.LocalizationStrings.localizedCropName(language, selectedCrop),
                                maxLines = 1
                            )
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                    DropdownMenu(
                        expanded = cropMenuExpanded,
                        onDismissRequest = { cropMenuExpanded = false }
                    ) {
                        availableCrops.forEach { crop ->
                            val isCommercial = com.example.data.model.isCommercialCrop(crop)
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${if (isCommercial) "🌴 " else ""}${com.example.data.localization.LocalizationStrings.localizedCropName(language, crop)}",
                                        fontWeight = if (isCommercial) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    selectedCrop = crop
                                    selectedVariety = null
                                    cropMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Variety Sub-list Selector
                if (cropVarieties.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "🌱 ${com.example.data.localization.LocalizationStrings.selectVariety(language)}:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Box {
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
                                Text(
                                    text = selectedVariety ?: com.example.data.localization.LocalizationStrings.allVarieties(language),
                                    maxLines = 1
                                )
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                        DropdownMenu(
                            expanded = varietyMenuExpanded,
                            onDismissRequest = { varietyMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(com.example.data.localization.LocalizationStrings.allVarieties(language)) },
                                onClick = {
                                    selectedVariety = null
                                    varietyMenuExpanded = false
                                }
                            )
                            cropVarieties.forEach { v ->
                                DropdownMenuItem(
                                    text = { Text(v.localizedName(language)) },
                                    onClick = {
                                        selectedVariety = v.nameEn
                                        varietyMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Mandi Selector
                Text(
                    text = com.example.data.localization.LocalizationStrings.selectMandi(language),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Box {
                    OutlinedButton(
                        onClick = { mandiMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(selectedMandi, maxLines = 1)
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                    DropdownMenu(
                        expanded = mandiMenuExpanded,
                        onDismissRequest = { mandiMenuExpanded = false }
                    ) {
                        availableMandis.forEach { mandi ->
                            DropdownMenuItem(
                                text = { Text(mandi) },
                                onClick = {
                                    selectedMandi = mandi
                                    mandiMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Condition Toggle
                Text(
                    text = when (language) {
                        com.example.data.localization.AppLanguage.ENGLISH -> "Condition"
                        com.example.data.localization.AppLanguage.KANNADA -> "ಷರತ್ತು"
                        com.example.data.localization.AppLanguage.HINDI -> "शर्त"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { isAbove = true },
                        modifier = Modifier.weight(1f),
                        colors = if (isAbove) androidx.compose.material3.ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer) else androidx.compose.material3.ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text(
                            text = com.example.data.localization.LocalizationStrings.targetRisesAbove(language),
                            fontSize = 11.sp
                        )
                    }
                    OutlinedButton(
                        onClick = { isAbove = false },
                        modifier = Modifier.weight(1f),
                        colors = if (!isAbove) androidx.compose.material3.ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer) else androidx.compose.material3.ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text(
                            text = com.example.data.localization.LocalizationStrings.targetDropsBelow(language),
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Target Price Input
                OutlinedTextField(
                    value = targetPriceText,
                    onValueChange = { targetPriceText = it },
                    label = { Text(com.example.data.localization.LocalizationStrings.targetPrice(language)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("target_price_input")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(com.example.data.localization.LocalizationStrings.cancel(language))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val price = targetPriceText.toDoubleOrNull() ?: 3000.0
                            val finalCropName = if (selectedVariety != null) "$selectedCrop ($selectedVariety)" else selectedCrop
                            onCreate(finalCropName, selectedMandi, price, isAbove)
                        },
                        modifier = Modifier.testTag("submit_create_alert_btn")
                    ) {
                        Text(com.example.data.localization.LocalizationStrings.save(language))
                    }
                }
            }
        }
    }
}

