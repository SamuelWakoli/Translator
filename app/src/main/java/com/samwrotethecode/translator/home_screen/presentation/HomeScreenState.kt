package com.samwrotethecode.translator.home_screen.presentation

data class HomeScreenState(
    val autoDetectLanguage: Boolean = true,
    val isDetectingLanguage: Boolean = false,
    val sourceLanguage: String? = null,
    val targetLanguage: String? = null,
    val isDownloadingModel: Boolean = false,
    val isTranslating: Boolean = false,
    val translatedText: String? = null,
    val error: String? = null
)