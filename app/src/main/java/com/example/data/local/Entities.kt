package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mandi_records")
data class MandiRecordEntity(
    @PrimaryKey val id: String,
    val commodity: String,
    val variety: String,
    val category: String,
    val state: String,
    val district: String,
    val market: String,
    val minPrice: Double,
    val modalPrice: Double,
    val maxPrice: Double,
    val arrivalsQuintals: Double,
    val date: String,
    val priceChangePercent: Double,
    val mspPrice: Double,
    val historical7DaysCsv: String,
    val historical30DaysCsv: String,
    val isFavorite: Boolean = false
)

@Entity(tableName = "price_alerts")
data class PriceAlertEntity(
    @PrimaryKey val id: String,
    val cropName: String,
    val marketName: String,
    val targetPrice: Double,
    val isAbove: Boolean,
    val isActive: Boolean,
    val createdAt: Long,
    val triggeredAt: Long?,
    val lastCheckedPrice: Double
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String = "primary_user",
    val identifier: String,
    val name: String,
    val role: String,
    val state: String,
    val district: String,
    val farmSizeAcres: Double,
    val selectedCropsCsv: String,
    val preferredMandisCsv: String,
    val isLoggedIn: Boolean
)

@Entity(tableName = "sync_meta")
data class SyncMetaEntity(
    @PrimaryKey val key: String,
    val lastSyncedTimestamp: Long,
    val totalRecordsCount: Int,
    val isOfflineMode: Boolean
)
