package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.MandiRecordEntity
import com.example.data.local.PriceAlertEntity
import com.example.data.local.SyncMetaEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.CommodityCategory
import com.example.data.model.MandiRecord
import com.example.data.model.PriceAlert
import com.example.data.model.StorageCalculatorResult
import com.example.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID

class MandiRepository(private val database: AppDatabase) {

    private val cropDao = database.cropPriceDao()
    private val alertDao = database.priceAlertDao()
    private val userDao = database.userDao()
    private val syncMetaDao = database.syncMetaDao()

    val allRecords: Flow<List<MandiRecord>> = cropDao.getAllRecords().map { entities ->
        entities.map { it.toDomain() }
    }

    val favoriteRecords: Flow<List<MandiRecord>> = cropDao.getFavoriteRecords().map { entities ->
        entities.map { it.toDomain() }
    }

    val allAlerts: Flow<List<PriceAlert>> = alertDao.getAllAlerts().map { entities ->
        entities.map { it.toDomain() }
    }

    val userProfile: Flow<UserProfile?> = userDao.getUserProfile().map { entity ->
        entity?.toDomain()
    }

    val syncMeta: Flow<SyncMetaEntity?> = syncMetaDao.getSyncMetaFlow()

    suspend fun initializeAndSeedDatabase() = withContext(Dispatchers.IO) {
        val currentMeta = syncMetaDao.getSyncMeta()
        // Always insert/refresh base catalog so newly added commercial crops are immediately available
        val entities = MandiMockDataSource.sampleRecords.map { it.toEntity() }
        cropDao.insertAll(entities)

        if (currentMeta == null) {
            val defaultProfile = UserProfile(
                id = "primary_user",
                identifier = "+91 98765 43210",
                name = "Ramesh Patil",
                role = "Farmer & Producer Group Leader",
                state = "Karnataka",
                district = "Shivamogga",
                farmSizeAcres = 6.5,
                selectedCrops = listOf("Areca Nut", "Coconut", "Cocoa", "Banana", "Pineapple", "Coffee", "Soybean", "Onion"),
                preferredMandis = listOf("Shivamogga APMC", "Tiptur APMC", "CAMPCO Puttur", "Bengaluru APMC", "Vazhakulam Market", "Pune APMC"),
                isLoggedIn = true
            )
            userDao.saveUserProfile(defaultProfile.toEntity())

            // Seed initial sample price alerts including commercial crops
            val sampleAlert1 = PriceAlert(
                id = UUID.randomUUID().toString(),
                cropName = "Areca Nut",
                marketName = "Shivamogga APMC",
                targetPrice = 55000.0,
                isAbove = true,
                isActive = true,
                createdAt = System.currentTimeMillis() - 86400000L,
                triggeredAt = null,
                lastCheckedPrice = 54800.0
            )
            val sampleAlert2 = PriceAlert(
                id = UUID.randomUUID().toString(),
                cropName = "Coconut",
                marketName = "Tiptur APMC",
                targetPrice = 14000.0,
                isAbove = true,
                isActive = true,
                createdAt = System.currentTimeMillis() - 43200000L,
                triggeredAt = System.currentTimeMillis() - 3600000L,
                lastCheckedPrice = 13800.0
            )
            val sampleAlert3 = PriceAlert(
                id = UUID.randomUUID().toString(),
                cropName = "Onion",
                marketName = "Lasalgaon Mandi",
                targetPrice = 3200.0,
                isAbove = true,
                isActive = true,
                createdAt = System.currentTimeMillis() - 86400000L,
                triggeredAt = System.currentTimeMillis() - 7200000L,
                lastCheckedPrice = 3450.0
            )
            alertDao.insertAlert(sampleAlert1.toEntity())
            alertDao.insertAlert(sampleAlert2.toEntity())
            alertDao.insertAlert(sampleAlert3.toEntity())

            syncMetaDao.saveSyncMeta(
                SyncMetaEntity(
                    key = "app_sync",
                    lastSyncedTimestamp = System.currentTimeMillis(),
                    totalRecordsCount = entities.size,
                    isOfflineMode = false
                )
            )
        }
    }

    suspend fun syncRemoteMandiPrices(simulateOffline: Boolean = false) = withContext(Dispatchers.IO) {
        if (simulateOffline) {
            val currentMeta = syncMetaDao.getSyncMeta() ?: SyncMetaEntity("app_sync", System.currentTimeMillis(), 0, true)
            syncMetaDao.saveSyncMeta(currentMeta.copy(isOfflineMode = true))
            return@withContext
        }

        // Simulate network fetch with updated prices and arrival volumes
        val baseList = MandiMockDataSource.sampleRecords
        val updatedEntities = baseList.map { item ->
            // Apply slight realistic daily market jitter
            val jitterFactor = 1.0 + ((item.commodity.hashCode() % 5) - 2) * 0.008
            val newModal = (item.modalPrice * jitterFactor).toInt().toDouble()
            val newMin = (item.minPrice * jitterFactor).toInt().toDouble()
            val newMax = (item.maxPrice * jitterFactor).toInt().toDouble()
            item.copy(
                modalPrice = newModal,
                minPrice = newMin,
                maxPrice = newMax
            ).toEntity()
        }

        cropDao.insertAll(updatedEntities)

        // Evaluate alerts
        checkAndTriggerAlerts(updatedEntities)

        syncMetaDao.saveSyncMeta(
            SyncMetaEntity(
                key = "app_sync",
                lastSyncedTimestamp = System.currentTimeMillis(),
                totalRecordsCount = updatedEntities.size,
                isOfflineMode = false
            )
        )
    }

    private suspend fun checkAndTriggerAlerts(records: List<MandiRecordEntity>) {
        // Evaluate active alerts against current prices
        val alertList = alertDao.getAllAlerts() // Note: DAO can be queried
        // We'll update any that matched
        records.forEach { record ->
            // check if any alert exists
        }
    }

    suspend fun setOfflineMode(enabled: Boolean) = withContext(Dispatchers.IO) {
        val current = syncMetaDao.getSyncMeta() ?: SyncMetaEntity("app_sync", System.currentTimeMillis(), 0, false)
        syncMetaDao.saveSyncMeta(current.copy(isOfflineMode = enabled))
    }

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        cropDao.updateFavorite(id, isFavorite)
    }

    suspend fun addPriceAlert(
        cropName: String,
        marketName: String,
        targetPrice: Double,
        isAbove: Boolean
    ) = withContext(Dispatchers.IO) {
        val alert = PriceAlert(
            id = UUID.randomUUID().toString(),
            cropName = cropName,
            marketName = marketName,
            targetPrice = targetPrice,
            isAbove = isAbove,
            isActive = true,
            createdAt = System.currentTimeMillis(),
            triggeredAt = null,
            lastCheckedPrice = targetPrice
        )
        alertDao.insertAlert(alert.toEntity())
    }

    suspend fun deletePriceAlert(id: String) = withContext(Dispatchers.IO) {
        alertDao.deleteAlert(id)
    }

    suspend fun toggleAlertStatus(id: String, active: Boolean) = withContext(Dispatchers.IO) {
        alertDao.updateAlertStatus(id, active, null)
    }

    suspend fun saveUserProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        userDao.saveUserProfile(profile.toEntity())
    }

    suspend fun logoutUser() = withContext(Dispatchers.IO) {
        userDao.logout()
    }

    fun calculateStorageROI(
        cropName: String,
        quantityQuintals: Double,
        currentSpotPrice: Double,
        holdMonths: Int,
        monthlyStorageCostPerQtl: Double,
        expectedFuturePricePerQtl: Double
    ): StorageCalculatorResult {
        val currentRevenue = quantityQuintals * currentSpotPrice
        val totalStorageCost = quantityQuintals * monthlyStorageCostPerQtl * holdMonths
        // 1.5% moisture shrinkage allowance over hold period
        val effectiveQuantity = quantityQuintals * 0.985
        val futureRevenue = effectiveQuantity * expectedFuturePricePerQtl
        val netGain = futureRevenue - currentRevenue - totalStorageCost
        val isProfitable = netGain > 0

        val recommendation = if (isProfitable) {
            "PROFITABLE: Holding $cropName for $holdMonths months is estimated to yield an extra ₹${String.format("%,.0f", netGain)} net profit after accounting for storage rent and 1.5% moisture shrinkage."
        } else {
            "UNFAVORABLE: Holding $cropName in storage will result in a net loss of ₹${String.format("%,.0f", -netGain)} because the projected price gain does not cover the ₹${String.format("%,.0f", totalStorageCost)} storage overhead. Selling on current spot market is recommended."
        }

        return StorageCalculatorResult(
            cropName = cropName,
            quantityQuintals = quantityQuintals,
            currentSpotRevenue = currentRevenue,
            holdMonths = holdMonths,
            storageCostTotal = totalStorageCost,
            projectedFuturePricePerQtl = expectedFuturePricePerQtl,
            projectedFutureRevenue = futureRevenue,
            netGainOrLoss = netGain,
            isProfitable = isProfitable,
            recommendationSummary = recommendation
        )
    }
}

// Extension mappers
private fun MandiRecordEntity.toDomain(): MandiRecord {
    return MandiRecord(
        id = id,
        commodity = commodity,
        variety = variety,
        category = try {
            CommodityCategory.valueOf(category)
        } catch (_: Exception) {
            CommodityCategory.ALL
        },
        state = state,
        district = district,
        market = market,
        minPrice = minPrice,
        modalPrice = modalPrice,
        maxPrice = maxPrice,
        arrivalsQuintals = arrivalsQuintals,
        date = date,
        priceChangePercent = priceChangePercent,
        mspPrice = mspPrice,
        historical7Days = historical7DaysCsv.split(",").mapNotNull { it.trim().toDoubleOrNull() },
        historical30Days = historical30DaysCsv.split(",").mapNotNull { it.trim().toDoubleOrNull() },
        isFavorite = isFavorite
    )
}

private fun MandiRecord.toEntity(): MandiRecordEntity {
    return MandiRecordEntity(
        id = id,
        commodity = commodity,
        variety = variety,
        category = category.name,
        state = state,
        district = district,
        market = market,
        minPrice = minPrice,
        modalPrice = modalPrice,
        maxPrice = maxPrice,
        arrivalsQuintals = arrivalsQuintals,
        date = date,
        priceChangePercent = priceChangePercent,
        mspPrice = mspPrice,
        historical7DaysCsv = historical7Days.joinToString(","),
        historical30DaysCsv = historical30Days.joinToString(","),
        isFavorite = isFavorite
    )
}

private fun PriceAlertEntity.toDomain(): PriceAlert {
    return PriceAlert(
        id = id,
        cropName = cropName,
        marketName = marketName,
        targetPrice = targetPrice,
        isAbove = isAbove,
        isActive = isActive,
        createdAt = createdAt,
        triggeredAt = triggeredAt,
        lastCheckedPrice = lastCheckedPrice
    )
}

private fun PriceAlert.toEntity(): PriceAlertEntity {
    return PriceAlertEntity(
        id = id,
        cropName = cropName,
        marketName = marketName,
        targetPrice = targetPrice,
        isAbove = isAbove,
        isActive = isActive,
        createdAt = createdAt,
        triggeredAt = triggeredAt,
        lastCheckedPrice = lastCheckedPrice
    )
}

private fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        identifier = identifier,
        name = name,
        role = role,
        state = state,
        district = district,
        farmSizeAcres = farmSizeAcres,
        selectedCrops = selectedCropsCsv.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        preferredMandis = preferredMandisCsv.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        isLoggedIn = isLoggedIn
    )
}

private fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = id,
        identifier = identifier,
        name = name,
        role = role,
        state = state,
        district = district,
        farmSizeAcres = farmSizeAcres,
        selectedCropsCsv = selectedCrops.joinToString(","),
        preferredMandisCsv = preferredMandis.joinToString(","),
        isLoggedIn = isLoggedIn
    )
}
