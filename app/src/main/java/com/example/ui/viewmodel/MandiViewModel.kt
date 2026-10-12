package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SyncMetaEntity
import com.example.data.model.CommodityCategory
import com.example.data.model.MandiRecord
import com.example.data.model.PriceAlert
import com.example.data.model.SellTimingInsight
import com.example.data.model.StorageCalculatorResult
import com.example.data.model.UserProfile
import com.example.data.model.WeatherAgriRisk
import com.example.data.repository.MandiMockDataSource
import com.example.data.repository.MandiRepository
import com.example.ui.components.ChartTimeRange
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class OtpAuthUiState(
    val identifierInput: String = "",
    val isOtpSent: Boolean = false,
    val generatedOtp: String = "",
    val enteredOtp: String = "",
    val isVerifying: Boolean = false,
    val isAuthenticated: Boolean = true, // Initial user is authenticated
    val errorMessage: String? = null,
    val infoMessage: String? = null
)

class MandiViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val repository = MandiRepository(database)

    val allRecords: StateFlow<List<MandiRecord>> = repository.allRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteRecords: StateFlow<List<MandiRecord>> = repository.favoriteRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAlerts: StateFlow<List<PriceAlert>> = repository.allAlerts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfile?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val syncMeta: StateFlow<SyncMetaEntity?> = repository.syncMeta
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow(CommodityCategory.ALL)
    val selectedState = MutableStateFlow<String?>(null)
    val selectedCrop = MutableStateFlow<String?>(null)
    val selectedVariety = MutableStateFlow<String?>(null)
    val selectedChartRange = MutableStateFlow(ChartTimeRange.SEVEN_DAYS)
    val selectedRecordForDetail = MutableStateFlow<MandiRecord?>(null)

    val isSyncing = MutableStateFlow(false)
    val otpUiState = MutableStateFlow(OtpAuthUiState())

    val sellInsights = MutableStateFlow(MandiMockDataSource.sampleSellAdvisorInsights)
    val weatherRisks = MutableStateFlow(MandiMockDataSource.sampleWeatherRisks)

    val storageCalculationResult = MutableStateFlow<StorageCalculatorResult?>(null)

    // Language State (English, Kannada, Hindi)
    val currentLanguage = MutableStateFlow(com.example.data.localization.AppLanguage.ENGLISH)

    fun setLanguage(lang: com.example.data.localization.AppLanguage) {
        currentLanguage.value = lang
    }

    fun onCropChange(crop: String?) {
        selectedCrop.value = crop
        selectedVariety.value = null
    }

    fun onVarietyChange(variety: String?) {
        selectedVariety.value = variety
    }

    // Filtered Records stream
    val filteredRecords: StateFlow<List<MandiRecord>> = combine(
        combine(allRecords, searchQuery, selectedCategory) { records, query, category ->
            Triple(records, query, category)
        },
        combine(selectedState, selectedCrop, selectedVariety) { state, crop, variety ->
            Triple(state, crop, variety)
        }
    ) { (records, query, category), (state, crop, variety) ->
        val q = query.trim().lowercase()
        records.filter { record ->
            val matchesQuery = q.isBlank() ||
                record.commodity.contains(q, ignoreCase = true) ||
                record.market.contains(q, ignoreCase = true) ||
                record.district.contains(q, ignoreCase = true) ||
                record.variety.contains(q, ignoreCase = true) ||
                record.state.contains(q, ignoreCase = true) ||
                com.example.data.localization.LocalizationStrings.localizedCropName(com.example.data.localization.AppLanguage.KANNADA, record.commodity).contains(q, ignoreCase = true) ||
                com.example.data.localization.LocalizationStrings.localizedCropName(com.example.data.localization.AppLanguage.HINDI, record.commodity).contains(q, ignoreCase = true) ||
                (q.contains("areca") && record.commodity.equals("Areca Nut", ignoreCase = true)) ||
                (q.contains("adike") && record.commodity.equals("Areca Nut", ignoreCase = true)) ||
                (q.contains("supari") && record.commodity.equals("Areca Nut", ignoreCase = true)) ||
                ((q.contains("coco nut") || q.contains("coconut") || q.contains("copra") || q.contains("tengu") || q.contains("nariyal")) && record.commodity.equals("Coconut", ignoreCase = true)) ||
                ((q.contains("cocova") || q.contains("cocoa") || q.contains("cacao") || q.contains("campco")) && record.commodity.equals("Cocoa", ignoreCase = true)) ||
                ((q.contains("banana") || q.contains("bale") || q.contains("kela") || q.contains("yelakki")) && record.commodity.equals("Banana", ignoreCase = true)) ||
                ((q.contains("pinapple") || q.contains("pineapple") || q.contains("ananas")) && record.commodity.equals("Pineapple", ignoreCase = true)) ||
                ((q.contains("coffee") || q.contains("kafi") || q.contains("kaapi")) && record.commodity.equals("Coffee", ignoreCase = true)) ||
                ((q.contains("pepper") || q.contains("menasu")) && record.commodity.equals("Black Pepper", ignoreCase = true)) ||
                ((q.contains("cardamom") || q.contains("elakki") || q.contains("elaichi")) && record.commodity.equals("Cardamom", ignoreCase = true)) ||
                ((q.contains("cashew") || q.contains("kaju") || q.contains("geru")) && record.commodity.equals("Cashew Nut", ignoreCase = true)) ||
                ((q.contains("ginger") || q.contains("shunti") || q.contains("adrak")) && record.commodity.equals("Ginger", ignoreCase = true)) ||
                ((q.contains("jaggery") || q.contains("sugarcane") || q.contains("bella") || q.contains("kabbu") || q.contains("ganna")) && record.commodity.equals("Sugarcane & Jaggery", ignoreCase = true)) ||
                (q.contains("rubber") && record.commodity.equals("Rubber", ignoreCase = true)) ||
                ((q.contains("cotton") || q.contains("kapas") || q.contains("hathi")) && record.commodity.equals("Cotton", ignoreCase = true)) ||
                ((q.contains("commercial") || q.contains("plantation") || q.contains("ವಾಣಿಜ್ಯ") || q.contains("वाणिज्यिक")) && com.example.data.model.isCommercialCrop(record.commodity))

            val matchesCategory = when (category) {
                CommodityCategory.ALL -> true
                CommodityCategory.COMMERCIAL_PLANTATION ->
                    record.category == CommodityCategory.COMMERCIAL_PLANTATION ||
                    com.example.data.model.isCommercialCrop(record.commodity)
                CommodityCategory.FRUITS ->
                    record.category == CommodityCategory.FRUITS ||
                    record.commodity.equals("Banana", ignoreCase = true) ||
                    record.commodity.equals("Pineapple", ignoreCase = true)
                CommodityCategory.SPICES ->
                    record.category == CommodityCategory.SPICES ||
                    record.commodity.contains("Pepper", ignoreCase = true) ||
                    record.commodity.contains("Cardamom", ignoreCase = true) ||
                    record.commodity.contains("Ginger", ignoreCase = true)
                else -> record.category == category
            }

            val matchesState = state == null || record.state.equals(state, ignoreCase = true)

            val matchesCrop = crop == null ||
                record.commodity.equals(crop, ignoreCase = true) ||
                (crop.equals("Areca Nut", ignoreCase = true) && record.commodity.contains("Areca", ignoreCase = true)) ||
                (crop.equals("Coconut", ignoreCase = true) && (record.commodity.contains("Coconut", ignoreCase = true) || record.commodity.contains("Copra", ignoreCase = true)))

            val matchesVariety = variety == null ||
                record.variety.contains(variety, ignoreCase = true) ||
                variety.contains(record.variety, ignoreCase = true)

            matchesQuery && matchesCategory && matchesState && matchesCrop && matchesVariety
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableStates: StateFlow<List<String>> = allRecords.combine(MutableStateFlow(Unit)) { records, _ ->
        records.map { it.state }.distinct().sorted()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.initializeAndSeedDatabase()
            // Run default ROI calculation for demo
            calculateStorageRoi("Onion", 100.0, 3450.0, 2, 70.0, 4200.0)
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun onCategoryChange(category: CommodityCategory) {
        selectedCategory.value = category
    }

    fun onStateChange(state: String?) {
        selectedState.value = state
    }

    fun onSelectChartRange(range: ChartTimeRange) {
        selectedChartRange.value = range
    }

    fun selectRecordForDetail(record: MandiRecord?) {
        selectedRecordForDetail.value = record
    }

    fun toggleFavorite(record: MandiRecord) {
        viewModelScope.launch {
            repository.toggleFavorite(record.id, !record.isFavorite)
        }
    }

    fun syncNow() {
        viewModelScope.launch {
            isSyncing.value = true
            delay(1200) // Realistic sync animation
            repository.syncRemoteMandiPrices(simulateOffline = false)
            isSyncing.value = false
        }
    }

    fun toggleOfflineMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setOfflineMode(enabled)
        }
    }

    fun addPriceAlert(cropName: String, marketName: String, targetPrice: Double, isAbove: Boolean) {
        viewModelScope.launch {
            repository.addPriceAlert(cropName, marketName, targetPrice, isAbove)
        }
    }

    fun deletePriceAlert(alertId: String) {
        viewModelScope.launch {
            repository.deletePriceAlert(alertId)
        }
    }

    fun toggleAlertStatus(alertId: String, active: Boolean) {
        viewModelScope.launch {
            repository.toggleAlertStatus(alertId, active)
        }
    }

    // OTP Auth Flow
    fun onIdentifierInputChanged(input: String) {
        otpUiState.value = otpUiState.value.copy(identifierInput = input, errorMessage = null)
    }

    fun onEnteredOtpChanged(otp: String) {
        otpUiState.value = otpUiState.value.copy(enteredOtp = otp, errorMessage = null)
    }

    fun requestOtp() {
        val input = otpUiState.value.identifierInput.trim()
        if (input.length < 5) {
            otpUiState.value = otpUiState.value.copy(errorMessage = "Please enter a valid mobile number or email ID")
            return
        }

        // Generate 6-digit OTP
        val randomOtp = (100000 + Random.nextInt(900000)).toString()
        otpUiState.value = otpUiState.value.copy(
            isOtpSent = true,
            generatedOtp = randomOtp,
            enteredOtp = randomOtp, // Auto-populate for frictionless trial
            infoMessage = "OTP sent to $input! Verification code: $randomOtp"
        )
    }

    fun verifyOtp() {
        val current = otpUiState.value
        if (current.enteredOtp.trim() == current.generatedOtp) {
            otpUiState.value = current.copy(
                isAuthenticated = true,
                isOtpSent = false,
                infoMessage = "Authenticated successfully!",
                errorMessage = null
            )
            // Save profile
            viewModelScope.launch {
                val existing = userProfile.value ?: UserProfile()
                val updated = existing.copy(
                    identifier = current.identifierInput,
                    isLoggedIn = true,
                    name = if (existing.name.isBlank()) "Farmer User" else existing.name
                )
                repository.saveUserProfile(updated)
            }
        } else {
            otpUiState.value = current.copy(errorMessage = "Invalid OTP code. Please check and retry.")
        }
    }

    fun updateProfile(
        name: String,
        role: String,
        state: String,
        district: String,
        farmSize: Double,
        crops: List<String>,
        mandis: List<String>
    ) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfile()
            val updated = current.copy(
                name = name,
                role = role,
                state = state,
                district = district,
                farmSizeAcres = farmSize,
                selectedCrops = crops,
                preferredMandis = mandis,
                isLoggedIn = true
            )
            repository.saveUserProfile(updated)
        }
    }

    fun calculateStorageRoi(
        cropName: String,
        quantityQuintals: Double,
        currentSpotPrice: Double,
        holdMonths: Int,
        monthlyStorageCostPerQtl: Double,
        expectedFuturePrice: Double
    ) {
        val result = repository.calculateStorageROI(
            cropName = cropName,
            quantityQuintals = quantityQuintals,
            currentSpotPrice = currentSpotPrice,
            holdMonths = holdMonths,
            monthlyStorageCostPerQtl = monthlyStorageCostPerQtl,
            expectedFuturePricePerQtl = expectedFuturePrice
        )
        storageCalculationResult.value = result
    }

    // Export Reports Generation
    fun generateBulletinText(records: List<MandiRecord>, title: String = "All-India Mandi Price Bulletin"): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val dateStr = sdf.format(Date())
        val builder = StringBuilder()
        builder.append("🌾 KRISHIMANDI DAILY PRICE BULLETIN 🌾\n")
        builder.append("Generated: $dateStr\n")
        builder.append("Scope: $title (${records.size} Mandi Markets)\n")
        builder.append("=========================================\n\n")

        records.forEachIndexed { index, r ->
            val sign = if (r.priceChangePercent >= 0) "+" else ""
            builder.append("${index + 1}. ${r.commodity.uppercase()} (${r.variety})\n")
            builder.append("   Mandi: ${r.market}, ${r.state}\n")
            builder.append("   Modal Price: ₹${String.format("%,.0f", r.modalPrice)} / Qtl ($sign${String.format("%.1f", r.priceChangePercent)}%)\n")
            builder.append("   Range: ₹${String.format("%,.0f", r.minPrice)} - ₹${String.format("%,.0f", r.maxPrice)} / Qtl\n")
            builder.append("   Arrivals: ${String.format("%,.0f", r.arrivalsQuintals)} Quintals\n")
            if (r.mspPrice > 0.0) {
                builder.append("   Govt MSP: ₹${String.format("%,.0f", r.mspPrice)} / Qtl\n")
            }
            builder.append("-----------------------------------------\n")
        }

        builder.append("\nShared via KrishiMandi: Smart Mandi Analytics & Decision Support\n")
        return builder.toString()
    }

    fun generateCsvData(records: List<MandiRecord>): String {
        val builder = StringBuilder()
        builder.append("Commodity,Variety,Category,State,District,Market,Modal_Price_INR,Min_Price_INR,Max_Price_INR,Arrivals_Quintals,Price_Change_Pct,MSP_INR,Date\n")
        records.forEach { r ->
            builder.append("\"${r.commodity}\",\"${r.variety}\",\"${r.category.displayName}\",\"${r.state}\",\"${r.district}\",\"${r.market}\",${r.modalPrice},${r.minPrice},${r.maxPrice},${r.arrivalsQuintals},${r.priceChangePercent},${r.mspPrice},\"${r.date}\"\n")
        }
        return builder.toString()
    }
}
