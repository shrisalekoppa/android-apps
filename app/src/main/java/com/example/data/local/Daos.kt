package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CropPriceDao {
    @Query("SELECT * FROM mandi_records ORDER BY commodity ASC")
    fun getAllRecords(): Flow<List<MandiRecordEntity>>

    @Query("SELECT * FROM mandi_records WHERE isFavorite = 1")
    fun getFavoriteRecords(): Flow<List<MandiRecordEntity>>

    @Query("SELECT * FROM mandi_records WHERE id = :id LIMIT 1")
    suspend fun getRecordById(id: String): MandiRecordEntity?

    @Query("SELECT DISTINCT state FROM mandi_records ORDER BY state ASC")
    fun getAllStates(): Flow<List<String>>

    @Query("SELECT DISTINCT commodity FROM mandi_records ORDER BY commodity ASC")
    fun getAllCommodities(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<MandiRecordEntity>)

    @Query("UPDATE mandi_records SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("DELETE FROM mandi_records")
    suspend fun clearAll()
}

@Dao
interface PriceAlertDao {
    @Query("SELECT * FROM price_alerts ORDER BY createdAt DESC")
    fun getAllAlerts(): Flow<List<PriceAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: PriceAlertEntity)

    @Query("DELETE FROM price_alerts WHERE id = :id")
    suspend fun deleteAlert(id: String)

    @Query("UPDATE price_alerts SET isActive = :isActive, triggeredAt = :triggeredAt WHERE id = :id")
    suspend fun updateAlertStatus(id: String, isActive: Boolean, triggeredAt: Long?)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    fun getUserProfile(id: String = "primary_user"): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET isLoggedIn = 0 WHERE id = :id")
    suspend fun logout(id: String = "primary_user")
}

@Dao
interface SyncMetaDao {
    @Query("SELECT * FROM sync_meta WHERE `key` = :key LIMIT 1")
    suspend fun getSyncMeta(key: String = "app_sync"): SyncMetaEntity?

    @Query("SELECT * FROM sync_meta WHERE `key` = :key LIMIT 1")
    fun getSyncMetaFlow(key: String = "app_sync"): Flow<SyncMetaEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSyncMeta(meta: SyncMetaEntity)
}
