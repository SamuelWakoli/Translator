package com.samwrotethecode.translator.core.domain.repository

import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem
import kotlinx.coroutines.flow.Flow

interface TranslationHistoryRepository {
    fun getHistory(): Flow<List<TranslationHistoryItem>>
    suspend fun addHistoryItem(item: TranslationHistoryItem)
    suspend fun deleteHistoryItem(item: TranslationHistoryItem)
    suspend fun clearHistory()
}
