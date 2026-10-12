package com.example.data.model

enum class CommodityCategory(val displayName: String, val icon: String = "🌾") {
    ALL("All Categories", "🌾"),
    COMMERCIAL_PLANTATION("Commercial Crops", "🌴"),
    FRUITS("Fruits & Horticulture", "🍌"),
    SPICES("Spices & Condiments", "🌶️"),
    CEREALS("Cereals & Grains", "🌾"),
    PULSES("Pulses (Dal)", "🫘"),
    OILSEEDS("Oilseeds", "🌻"),
    VEGETABLES("Vegetables", "🥦"),
    CASH_CROPS("Cash Crops", "💰")
}

fun isCommercialCrop(commodity: String): Boolean {
    val lower = commodity.lowercase()
    return lower.contains("areca") || lower.contains("adike") || lower.contains("supari") ||
           lower.contains("coconut") || lower.contains("copra") || lower.contains("tengu") || lower.contains("kobbari") ||
           lower.contains("cocoa") || lower.contains("cocova") || lower.contains("cacao") || lower.contains("campco") ||
           lower.contains("banana") || lower.contains("bale") || lower.contains("kela") ||
           lower.contains("pineapple") || lower.contains("pinapple") || lower.contains("ananas") ||
           lower.contains("coffee") || lower.contains("kafi") ||
           lower.contains("pepper") || lower.contains("menasu") ||
           lower.contains("cardamom") || lower.contains("elakki") || lower.contains("elaichi") ||
           lower.contains("cashew") || lower.contains("kaju") || lower.contains("geru") ||
           lower.contains("ginger") || lower.contains("shunti") || lower.contains("adrak") ||
           lower.contains("sugar") || lower.contains("jaggery") || lower.contains("gur") || lower.contains("bella") || lower.contains("kabbu") ||
           lower.contains("cotton") || lower.contains("kapas") ||
           lower.contains("rubber")
}

data class MandiRecord(
    val id: String,
    val commodity: String,
    val variety: String,
    val category: CommodityCategory,
    val state: String,
    val district: String,
    val market: String,
    val minPrice: Double,
    val modalPrice: Double,
    val maxPrice: Double,
    val arrivalsQuintals: Double,
    val date: String,
    val priceChangePercent: Double,
    val mspPrice: Double = 0.0,
    val historical7Days: List<Double> = emptyList(),
    val historical30Days: List<Double> = emptyList(),
    val isFavorite: Boolean = false
) {
    val mspDifferencePercent: Double?
        get() = if (mspPrice > 0.0) {
            ((modalPrice - mspPrice) / mspPrice) * 100.0
        } else null
}

data class HistoricalDataPoint(
    val date: String,
    val modalPrice: Double,
    val minPrice: Double,
    val maxPrice: Double,
    val arrivals: Double
)

data class PriceAlert(
    val id: String,
    val cropName: String,
    val marketName: String,
    val targetPrice: Double,
    val isAbove: Boolean, // true: alert when price >= target, false: alert when price <= target
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val triggeredAt: Long? = null,
    val lastCheckedPrice: Double = 0.0
)

data class UserProfile(
    val id: String = "default_user",
    val identifier: String = "", // email or phone
    val name: String = "",
    val role: String = "Farmer",
    val state: String = "Maharashtra",
    val district: String = "Pune",
    val farmSizeAcres: Double = 5.0,
    val selectedCrops: List<String> = listOf("Soybean", "Onion", "Wheat"),
    val preferredMandis: List<String> = listOf("Pune APMC", "Lasalgaon", "Indore Mandi"),
    val isLoggedIn: Boolean = false
)

enum class SellAction(val label: String, val badgeColorHex: Long) {
    HOLD_STRONG("RECOMMENDED TO HOLD", 0xFF15803D), // Green
    FAVORABLE_SELL("GOOD TIME TO SELL", 0xFFD97706), // Amber
    SELL_URGENT("SELL NOW (RISK AHEAD)", 0xFFDC2626) // Red
}

data class SellTimingInsight(
    val cropName: String,
    val marketName: String,
    val action: SellAction,
    val title: String,
    val summary: String,
    val arrivalForecast: String,
    val weatherWarning: String,
    val projectedPriceChange: String,
    val confidencePercent: Int
)

data class WeatherAgriRisk(
    val district: String,
    val state: String,
    val temperatureCelsius: Int,
    val humidityPercent: Int,
    val precipitationRiskPercent: Int,
    val condition: String,
    val rainAlertText: String,
    val harvestDryingIndex: String, // "Optimal", "High Moisture Risk"
    val godownStorageAdvisory: String
)

data class StorageCalculatorResult(
    val cropName: String,
    val quantityQuintals: Double,
    val currentSpotRevenue: Double,
    val holdMonths: Int,
    val storageCostTotal: Double,
    val projectedFuturePricePerQtl: Double,
    val projectedFutureRevenue: Double,
    val netGainOrLoss: Double,
    val isProfitable: Boolean,
    val recommendationSummary: String
)
