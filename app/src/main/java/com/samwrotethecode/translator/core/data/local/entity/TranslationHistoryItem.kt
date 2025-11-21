package com.samwrotethecode.translator.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translation_history")
data class TranslationHistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceText: String,
    val translatedText: String,
    val sourceLanguageCode: String,
    val targetLanguageCode: String,
    val timestamp: Long = System.currentTimeMillis()
)
