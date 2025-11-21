package com.samwrotethecode.translator.home_screen.domain.service

interface LanguageDetector {
    fun detectLanguage(
        text: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
    ): Unit
}