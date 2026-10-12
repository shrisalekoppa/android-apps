package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.PriceGainGreen
import com.example.ui.viewmodel.MandiViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val profile by viewModel.userProfile.collectAsState()
    val syncMeta by viewModel.syncMeta.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    val otpUiState by viewModel.otpUiState.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    var name by remember(profile) { mutableStateOf(profile?.name ?: "Ramesh Patil") }
    var role by remember(profile) { mutableStateOf(profile?.role ?: "Farmer") }
    var state by remember(profile) { mutableStateOf(profile?.state ?: "Karnataka") }
    var district by remember(profile) { mutableStateOf(profile?.district ?: "Shivamogga") }
    var farmSizeText by remember(profile) { mutableStateOf((profile?.farmSizeAcres ?: 6.5).toString()) }

    var selectedCrops by remember(profile) {
        mutableStateOf(profile?.selectedCrops?.toSet() ?: setOf("Areca Nut", "Coconut", "Cocoa", "Banana", "Pineapple", "Coffee", "Soybean", "Onion"))
    }

    val allCropsAvailable = listOf(
        "Areca Nut", "Coconut", "Cocoa", "Banana", "Pineapple", "Coffee",
        "Black Pepper", "Cardamom", "Cashew Nut", "Ginger", "Sugarcane & Jaggery", "Rubber",
        "Wheat", "Paddy (Basmati)", "Soybean", "Onion", "Tomato", "Cotton",
        "Mustard", "Chana", "Potato", "Red Chilli", "Turmeric", "Cumin (Jeera)"
    )

    val lastSyncFormatted = syncMeta?.lastSyncedTimestamp?.let {
        SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(it))
    } ?: "Today"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Card with Avatar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = name.ifBlank { "Farmer Profile" },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = "Verified",
                                tint = PriceGainGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "$role • $district, $state",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Authenticated: ${profile?.identifier?.ifBlank { "+91 98765 43210" }}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // 0. Language Settings Card (English / Kannada / Hindi)
        item {
            val currentLanguage by viewModel.currentLanguage.collectAsState()

            Card(
                modifier = Modifier.fillMaxWidth().testTag("language_settings_card"),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🌐", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "App Language / ಭಾಷೆ / भाषा",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = currentLanguage.nativeName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Choose your preferred language for Mandi prices, AI advisor and analytics:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        com.example.data.localization.AppLanguage.values().forEach { lang ->
                            val isSelected = lang == currentLanguage
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    viewModel.setLanguage(lang)
                                    Toast.makeText(context, com.example.data.localization.LocalizationStrings.languageChangeNotice(lang), Toast.LENGTH_SHORT).show()
                                },
                                label = {
                                    Text(
                                        text = "${lang.nativeName} (${lang.shortLabel})",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.weight(1f).testTag("select_lang_${lang.code}")
                            )
                        }
                    }
                }
            }
        }

        // 1. Mobile Number / Email OTP Subscription & Authentication Card
        item {
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "OTP Subscription & Account Access"
                                    com.example.data.localization.AppLanguage.KANNADA -> "OTP ಚಂದಾದಾರಿಕೆ ಮತ್ತು ಖಾತೆ ಪ್ರವೇಶ"
                                    com.example.data.localization.AppLanguage.HINDI -> "ओटीपी सदस्यता और खाता पहुंच"
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AgriGreenContainer
                        ) {
                            Text(
                                text = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "SECURED"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಸುರಕ್ಷಿತ"
                                    com.example.data.localization.AppLanguage.HINDI -> "सुरक्षित"
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PriceGainGreen,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = when (currentLanguage) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Subscribe using mobile number or email ID to sync personalized watchlist and receive high-priority market price SMS/notifications."
                            com.example.data.localization.AppLanguage.KANNADA -> "ವೈಯಕ್ತಿಕಗೊಳಿಸಿದ ಬೆಳೆ ಪಟ್ಟಿಯನ್ನು ಸಿಂಕ್ ಮಾಡಲು ಮತ್ತು ಹೆಚ್ಚಿನ ಆದ್ಯತೆಯ ಮಾರುಕಟ್ಟೆ ದರ ಅಧಿಸೂಚನೆಗಳನ್ನು ಪಡೆಯಲು ಮೊಬೈಲ್ ಸಂಖ್ಯೆ ಅಥವಾ ಇಮೇಲ್ ಬಳಸಿ ಚಂದಾದಾರರಾಗಿ."
                            com.example.data.localization.AppLanguage.HINDI -> "व्यक्तिगत वॉचलिस्ट सिंक करने और उच्च प्राथमिकता वाले बाजार भाव अलर्ट प्राप्त करने के लिए मोबाइल नंबर या ईमेल से सदस्यता लें।"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = otpUiState.identifierInput,
                            onValueChange = { viewModel.onIdentifierInputChanged(it) },
                            placeholder = {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Enter Mobile No. or Email"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಮೊಬೈಲ್ ಸಂಖ್ಯೆ ಅಥವಾ ಇಮೇಲ್ ನಮೂದಿಸಿ"
                                        com.example.data.localization.AppLanguage.HINDI -> "मोबाइल नंबर या ईमेल दर्ज करें"
                                    },
                                    fontSize = 12.sp
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("otp_identifier_input")
                        )

                        Button(
                            onClick = { viewModel.requestOtp() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("send_otp_btn")
                        ) {
                            Text(
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Send OTP"
                                    com.example.data.localization.AppLanguage.KANNADA -> "OTP ಕಳುಹಿಸಿ"
                                    com.example.data.localization.AppLanguage.HINDI -> "ओटीपी भेजें"
                                },
                                fontSize = 12.sp
                            )
                        }
                    }

                    if (otpUiState.isOtpSent) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = otpUiState.enteredOtp,
                                onValueChange = { viewModel.onEnteredOtpChanged(it) },
                                placeholder = {
                                    Text(
                                        when (currentLanguage) {
                                            com.example.data.localization.AppLanguage.ENGLISH -> "Enter 6-digit OTP"
                                            com.example.data.localization.AppLanguage.KANNADA -> "6-ಅಂಕಿಯ OTP ನಮೂದಿಸಿ"
                                            com.example.data.localization.AppLanguage.HINDI -> "6-अंकों का ओटीपी दर्ज करें"
                                        },
                                        fontSize = 12.sp
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("otp_code_input")
                            )

                            Button(
                                onClick = {
                                    viewModel.verifyOtp()
                                    Toast.makeText(
                                        context,
                                        when (currentLanguage) {
                                            com.example.data.localization.AppLanguage.ENGLISH -> "OTP Verified successfully!"
                                            com.example.data.localization.AppLanguage.KANNADA -> "OTP ಯಶಸ್ವಿಯಾಗಿ ಪರಿಶೀಲಿಸಲಾಗಿದೆ!"
                                            com.example.data.localization.AppLanguage.HINDI -> "ओटीपी सफलतापूर्वक सत्यापित हुआ!"
                                        },
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("verify_otp_btn")
                            ) {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Verify"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಪರಿಶೀಲಿಸಿ"
                                        com.example.data.localization.AppLanguage.HINDI -> "सत्यापित करें"
                                    },
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    if (otpUiState.infoMessage != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = otpUiState.infoMessage!!,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PriceGainGreen
                        )
                    }

                    if (otpUiState.errorMessage != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = otpUiState.errorMessage!!,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        // 2. Personalized Farmer & Farm Details
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
                            com.example.data.localization.AppLanguage.ENGLISH -> "Farmer & Farm Preferences"
                            com.example.data.localization.AppLanguage.KANNADA -> "ರೈತ ಮತ್ತು ಜಮೀನಿನ ವಿವರಗಳು"
                            com.example.data.localization.AppLanguage.HINDI -> "किसान और खेत प्राथमिकताएं"
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = when (currentLanguage) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Customize your regional mandis and farm holdings"
                            com.example.data.localization.AppLanguage.KANNADA -> "ನಿಮ್ಮ ಪ್ರಾದೇಶಿಕ ಮಂಡಿಗಳು ಮತ್ತು ಕೃಷಿ ವಿವರಗಳನ್ನು ಹೊಂದಿಸಿ"
                            com.example.data.localization.AppLanguage.HINDI -> "अपनी क्षेत्रीय मंडियों और खेत विवरण को अनुकूलित करें"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Your Name"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ನಿಮ್ಮ ಹೆಸರು"
                                        com.example.data.localization.AppLanguage.HINDI -> "आपका नाम"
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("profile_name_input")
                        )

                        OutlinedTextField(
                            value = role,
                            onValueChange = { role = it },
                            label = {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Role / ಉದ್ಯೋಗ"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಉದ್ಯೋಗ"
                                        com.example.data.localization.AppLanguage.HINDI -> "भूमिका"
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("profile_role_input")
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = state,
                            onValueChange = { state = it },
                            label = {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "State"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ರಾಜ್ಯ"
                                        com.example.data.localization.AppLanguage.HINDI -> "राज्य"
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("profile_state_input")
                        )

                        OutlinedTextField(
                            value = district,
                            onValueChange = { district = it },
                            label = {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "District"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಜಿಲ್ಲೆ"
                                        com.example.data.localization.AppLanguage.HINDI -> "जिला"
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("profile_district_input")
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = farmSizeText,
                        onValueChange = { farmSizeText = it },
                        label = {
                            Text(
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Total Landholding (Acres)"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಒಟ್ಟು ಜಮೀನು (ಎಕರೆ)"
                                    com.example.data.localization.AppLanguage.HINDI -> "कुल भूमि (एकड़)"
                                }
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("profile_farm_size_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Crop Selection for Custom Dashboard
                    Text(
                        text = when (currentLanguage) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Select Primary Crops for Custom Dashboard:"
                            com.example.data.localization.AppLanguage.KANNADA -> "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್‌ಗಾಗಿ ಮುಖ್ಯ ಬೆಳೆಗಳನ್ನು ಆಯ್ಕೆ ಮಾಡಿ:"
                            com.example.data.localization.AppLanguage.HINDI -> "डैशबोर्ड के लिए प्राथमिक फसलें चुनें:"
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        allCropsAvailable.forEach { crop ->
                            val isSelected = selectedCrops.contains(crop)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedCrops = if (isSelected) {
                                        selectedCrops - crop
                                    } else {
                                        selectedCrops + crop
                                    }
                                },
                                label = {
                                    val isCommercial = com.example.data.model.isCommercialCrop(crop)
                                    Text(
                                        text = "${if (isCommercial) "🌴 " else ""}${com.example.data.localization.LocalizationStrings.localizedCropName(currentLanguage, crop)}",
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.testTag("crop_chip_$crop")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val farmSize = farmSizeText.toDoubleOrNull() ?: 5.0
                            viewModel.updateProfile(
                                name = name,
                                role = role,
                                state = state,
                                district = district,
                                farmSize = farmSize,
                                crops = selectedCrops.toList(),
                                mandis = listOf("$district APMC", "State Central Mandi")
                            )
                            Toast.makeText(
                                context,
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Preferences saved successfully!"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಆಯ್ಕೆಗಳನ್ನು ಯಶಸ್ವಿಯಾಗಿ ಉಳಿಸಲಾಗಿದೆ!"
                                    com.example.data.localization.AppLanguage.HINDI -> "प्राथमिकताएं सफलतापूर्वक सहेजी गईं!"
                                },
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_profile_btn")
                    ) {
                        Text(
                            when (currentLanguage) {
                                com.example.data.localization.AppLanguage.ENGLISH -> "Save & Update Preferences"
                                com.example.data.localization.AppLanguage.KANNADA -> "ಆಯ್ಕೆಗಳನ್ನು ಉಳಿಸಿ ಮತ್ತು ನವೀಕರಿಸಿ"
                                com.example.data.localization.AppLanguage.HINDI -> "प्राथमिकताएं सहेजें और अपडेट करें"
                            }
                        )
                    }
                }
            }
        }

        // 3. Database Synchronization & Offline Field Usage Card
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
                            com.example.data.localization.AppLanguage.ENGLISH -> "Backend Sync & Offline Field Storage"
                            com.example.data.localization.AppLanguage.KANNADA -> "ಡೇಟಾಬೇಸ್ ಸಿಂಕ್ ಮತ್ತು ಆಫ್‌ಲೈನ್ ಶೇಖರಣೆ"
                            com.example.data.localization.AppLanguage.HINDI -> "डेटाबेस सिंक और ऑफ़लाइन स्टोरेज"
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = when (currentLanguage) {
                            com.example.data.localization.AppLanguage.ENGLISH -> "Database synchronization engine with local Room SQLite storage for reliable usage in rural fields with weak connectivity."
                            com.example.data.localization.AppLanguage.KANNADA -> "ದುರ್ಬಲ ಸಂಪರ್ಕವಿರುವ ಗ್ರಾಮೀಣ ಪ್ರದೇಶಗಳಲ್ಲಿ ವಿಶ್ವಾಸಾರ್ಹ ಬಳಕೆಗಾಗಿ ಸ್ಥಳೀಯ Room SQLite ಶೇಖರಣೆ ಹೊಂದಿರುವ ಸಿಂಕ್ ಎಂಜಿನ್."
                            com.example.data.localization.AppLanguage.HINDI -> "कमजोर कनेक्टिविटी वाले ग्रामीण क्षेत्रों में विश्वसनीय उपयोग के लिए स्थानीय Room SQLite स्टोरेज के साथ डेटाबेस सिंक।"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    val isOffline = syncMeta?.isOfflineMode ?: false

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Offline Field Mode"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಆಫ್‌ಲೈನ್ ಫೀಲ್ಡ್ ಮೋಡ್"
                                    com.example.data.localization.AppLanguage.HINDI -> "ऑफ़लाइन फ़ील्ड मोड"
                                },
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isOffline) {
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Using Local SQLite Cache"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಸ್ಥಳೀಯ SQLite ಸಂಗ್ರಹಣೆ ಬಳಸಲಾಗುತ್ತಿದೆ"
                                        com.example.data.localization.AppLanguage.HINDI -> "स्थानीय SQLite कैश का उपयोग"
                                    }
                                } else {
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Syncing with Cloud Mandi API"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಕ್ಲೌಡ್ ಮಂಡಿ API ನೊಂದಿಗೆ ಸಿಂಕ್ ಆಗುತ್ತಿದೆ"
                                        com.example.data.localization.AppLanguage.HINDI -> "क्लाउड मंडी एपीआई के साथ सिंक"
                                    }
                                },
                                fontSize = 11.sp,
                                color = if (isOffline) MaterialTheme.colorScheme.error else PriceGainGreen
                            )
                        }

                        Switch(
                            checked = isOffline,
                            onCheckedChange = { viewModel.toggleOfflineMode(it) },
                            modifier = Modifier.testTag("offline_mode_toggle_profile")
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Last Synced:"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಕೊನೆಯ ಸಿಂಕ್:"
                                        com.example.data.localization.AppLanguage.HINDI -> "अंतिम सिंक:"
                                    },
                                    fontSize = 11.sp
                                )
                                Text(lastSyncFormatted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    when (currentLanguage) {
                                        com.example.data.localization.AppLanguage.ENGLISH -> "Local Mandi Records:"
                                        com.example.data.localization.AppLanguage.KANNADA -> "ಸ್ಥಳೀಯ ಮಂಡಿ ದಾಖಲೆಗಳು:"
                                        com.example.data.localization.AppLanguage.HINDI -> "स्थानीय मंडी रिकॉर्ड:"
                                    },
                                    fontSize = 11.sp
                                )
                                Text(
                                    "${syncMeta?.totalRecordsCount ?: 12} ${
                                        when (currentLanguage) {
                                            com.example.data.localization.AppLanguage.ENGLISH -> "mandis cached"
                                            com.example.data.localization.AppLanguage.KANNADA -> "ಮಂಡಿ ಸಂಗ್ರಹವಾಗಿದೆ"
                                            com.example.data.localization.AppLanguage.HINDI -> "मंडियां कैश्ड"
                                        }
                                    }",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.syncNow()
                            Toast.makeText(
                                context,
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Synchronizing database..."
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಡೇಟಾಬೇಸ್ ಸಿಂಕ್ ಮಾಡಲಾಗುತ್ತಿದೆ..."
                                    com.example.data.localization.AppLanguage.HINDI -> "डेटाबेस सिंक हो रहा है..."
                                },
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sync_now_profile_btn")
                    ) {
                        if (isSyncing) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Syncing..."
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಸಿಂಕ್ ಆಗುತ್ತಿದೆ..."
                                    com.example.data.localization.AppLanguage.HINDI -> "सिंक हो रहा है..."
                                }
                            )
                        } else {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                when (currentLanguage) {
                                    com.example.data.localization.AppLanguage.ENGLISH -> "Re-sync All-India Mandi Database"
                                    com.example.data.localization.AppLanguage.KANNADA -> "ಅಖಿಲ ಭಾರತ ಮಂಡಿ ಡೇಟಾಬೇಸ್ ಮರು-ಸಿಂಕ್ ಮಾಡಿ"
                                    com.example.data.localization.AppLanguage.HINDI -> "अखिल भारतीय मंडी डेटाबेस को पुनः सिंक करें"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
