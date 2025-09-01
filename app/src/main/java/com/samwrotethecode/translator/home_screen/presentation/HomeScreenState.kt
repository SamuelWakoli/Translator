package com.samwrotethecode.translator.home_screen.presentation

data class HomeScreenState(
    val isDetectingLanguage: Boolean = false,
    val sourceLanguage: String? = null,
    val targetLanguage: String? = null,
    val isDownloadingModel: Boolean = false,
    val modelDownloadProgress: Float = 0f,
    val isTranslating: Boolean = false,
    val translatedText: String? = null,
    val error: String? = null
)