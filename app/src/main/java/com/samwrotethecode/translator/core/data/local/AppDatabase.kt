package com.samwrotethecode.translator.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samwrotethecode.translator.core.data.local.dao.TranslationHistoryDao
import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem

@Database(entities = [TranslationHistoryItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationHistoryDao(): TranslationHistoryDao
}
