package com.samwrotethecode.translator.core.data.repository

import com.samwrotethecode.translator.core.data.local.dao.TranslationHistoryDao
import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem
import com.samwrotethecode.translator.core.domain.repository.TranslationHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TranslationHistoryRepositoryImpl @Inject constructor(
    private val dao: TranslationHistoryDao
) : TranslationHistoryRepository {
    override fun getHistory(): Flow<List<TranslationHistoryItem>> = dao.getAllHistory()

    override suspend fun addHistoryItem(item: TranslationHistoryItem) = dao.insert(item)

    override suspend fun deleteHistoryItem(item: TranslationHistoryItem) = dao.delete(item)

    override suspend fun clearHistory() = dao.clearAll()
}
