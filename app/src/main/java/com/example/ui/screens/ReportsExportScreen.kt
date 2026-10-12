package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.PriceGainGreen
import com.example.ui.viewmodel.MandiViewModel

@Composable
fun ReportsExportScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val filteredRecords by viewModel.filteredRecords.collectAsState()
    val allRecords by viewModel.allRecords.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    var commercialOnlyFilter by remember { mutableStateOf(false) }

    val baseList = if (filteredRecords.isNotEmpty()) filteredRecords else allRecords
    val recordsToExport = remember(baseList, commercialOnlyFilter) {
        if (commercialOnlyFilter) {
            baseList.filter { com.example.data.model.isCommercialCrop(it.commodity) }
        } else {
            baseList
        }
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles: List<String> = listOf(
        com.example.data.localization.LocalizationStrings.tabMandiBulletin(currentLanguage),
        com.example.data.localization.LocalizationStrings.tabCsvSpreadsheet(currentLanguage)
    )

    val bulletinText = remember(recordsToExport, currentLanguage) {
        viewModel.generateBulletinText(
            recordsToExport,
            if (commercialOnlyFilter) "Commercial & Plantation Crop Bulletin" else "All-India Mandi Price Bulletin"
        )
    }

    val csvText = remember(recordsToExport) {
        viewModel.generateCsvData(recordsToExport)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("reports_export_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = when (currentLanguage) {
                        com.example.data.localization.AppLanguage.ENGLISH -> "Exportable Market Reports"
                        com.example.data.localization.AppLanguage.KANNADA -> "ಮಾರುಕಟ್ಟೆ ವರದಿಗಳ ರಫ್ತು"
                        com.example.data.localization.AppLanguage.HINDI -> "निर्यात योग्य बाजार रिपोर्ट"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = when (currentLanguage) {
                        com.example.data.localization.AppLanguage.ENGLISH -> "Generate and export formatted reports for farmers, FPOs & APMC traders (WhatsApp & CSV)"
                        com.example.data.localization.AppLanguage.KANNADA -> "ರೈತರು, ಎಫ್‌ಪಿಒಗಳು ಮತ್ತು ಮಂಡಿ ವ್ಯಾಪಾರಿಗಳಿಗಾಗಿ ವರದಿ ರಚಿಸಿ (ವಾಟ್ಸಾಪ್ ಮತ್ತು ಸಿಎಸ್‌ವಿ)"
                        com.example.data.localization.AppLanguage.HINDI -> "किसानों, एफपीओ और व्यापारियों के लिए रिपोर्ट बनाएं (व्हाट्सएप और सीएसवी)"
                    },
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Quick Filter Chips for Report Scope
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = !commercialOnlyFilter,
                    onClick = { commercialOnlyFilter = false },
                    label = {
                        Text(
                            when (currentLanguage) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "All Crops (${baseList.size})"
                                com.example.data.localization.AppLanguage.KANNADA -> "ಎಲ್ಲ ಬೆಳೆಗಳು (${baseList.size})"
                                com.example.data.localization.AppLanguage.HINDI -> "सभी फसलें (${baseList.size})"
                            },
                            fontSize = 11.sp
                        )
                    }
                )
                FilterChip(
                    selected = commercialOnlyFilter,
                    onClick = { commercialOnlyFilter = true },
                    label = {
                        Text(
                            when (currentLanguage) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "🌴 Commercial Crops Only"
                                com.example.data.localization.AppLanguage.KANNADA -> "🌴 ವಾಣಿಜ್ಯ ಬೆಳೆಗಳು ಮಾತ್ರ"
                                com.example.data.localization.AppLanguage.HINDI -> "🌴 केवल वाणिज्यिक फसलें"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        // Summary Statistics Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = when (currentLanguage) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Report Overview (${recordsToExport.size} Markets)"
                            com.example.data.localization.AppLanguage.KANNADA -> "ವರದಿ ಅವಲೋಕನ (${recordsToExport.size} ಮಂಡಿಗಳು)"
                            com.example.data.localization.AppLanguage.HINDI -> "रिपोर्ट अवलोकन (${recordsToExport.size} मंडियां)"
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val avgPrice = if (recordsToExport.isNotEmpty()) {
                        recordsToExport.map { it.modalPrice }.average()
                    } else 0.0

                    val totalArrivals = recordsToExport.sumOf { it.arrivalsQuintals }
                    val maxPrice = recordsToExport.maxOfOrNull { it.modalPrice } ?: 0.0

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Avg Modal Price"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಸರಾಸರಿ ದರ"
                                        com.example.data.localization.AppLanguage.HINDI -> "औसत मॉडल भाव"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text("₹${String.format("%,.0f", avgPrice)}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Total Arrivals"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಒಟ್ಟು ಆವಕ"
                                        com.example.data.localization.AppLanguage.HINDI -> "कुल आवक"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text("${String.format("%,.0f", totalArrivals)} Qtl", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = HarvestGoldDark)
                            }
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Peak Mandi Price"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಗರಿಷ್ಠ ದರ"
                                        com.example.data.localization.AppLanguage.HINDI -> "उच्चतम भाव"
                                    },
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text("₹${String.format("%,.0f", maxPrice)}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = PriceGainGreen)
                            }
                        }
                    }
                }
            }
        }

        // Tab Selector for Formats
        item {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("report_tab_$index")
                    )
                }
            }
        }

        // Preview & Actions Card
        item {
            val contentToShare = if (selectedTabIndex == 0) bulletinText else csvText

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
                        Text(
                            text = if (selectedTabIndex == 0) {
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Bulletin Preview"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ವರದಿ ಮುನ್ನೋಟ"
                                    com.example.data.localization.AppLanguage.HINDI -> "बुलेटिन पूर्वावलोकन"
                                }
                            } else {
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "CSV Data Preview"
                                    com.example.data.localization.AppLanguage.KANNADA -> "CSV ಡೇಟಾ ಮುನ್ನೋಟ"
                                    com.example.data.localization.AppLanguage.HINDI -> "सीएसवी डेटा पूर्वावलोकन"
                                }
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Mandi Report", contentToShare)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(
                                        context,
                                        when (currentLanguage) {
                                            com.example.data.localization.AppLanguage.ENGLISH -> "Report copied to clipboard!"
                                            com.example.data.localization.AppLanguage.KANNADA -> "ವರದಿಯನ್ನು ಕ್ಲಿಪ್‌ಬೋರ್ಡ್‌ಗೆ ನಕಲಿಸಲಾಗಿದೆ!"
                                            com.example.data.localization.AppLanguage.HINDI -> "रिपोर्ट क्लिपबोर्ड पर कॉपी हो गई!"
                                        },
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("copy_report_btn")
                            ) {
                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Copy"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ನಕಲಿಸಿ"
                                        com.example.data.localization.AppLanguage.HINDI -> "कॉपी करें"
                                    },
                                    fontSize = 11.sp
                                )
                            }

                            Button(
                                onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, contentToShare)
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, "Share Mandi Report")
                                    context.startActivity(shareIntent)
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("share_report_btn")
                            ) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Share"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಹಂಚಿಕೊಳ್ಳಿ"
                                        com.example.data.localization.AppLanguage.HINDI -> "साझा करें"
                                    },
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Text display box
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        LazyColumn(modifier = Modifier.padding(12.dp)) {
                            item {
                                Text(
                                    text = contentToShare,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
