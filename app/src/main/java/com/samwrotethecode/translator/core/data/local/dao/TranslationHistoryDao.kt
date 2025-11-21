package com.samwrotethecode.translator.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationHistoryDao {
    @Query("SELECT * FROM translation_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<TranslationHistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranslationHistoryItem)

    @Delete
    suspend fun delete(item: TranslationHistoryItem)

    @Query("DELETE FROM translation_history")
    suspend fun clearAll()
}
