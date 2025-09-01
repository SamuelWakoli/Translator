package com.samwrotethecode.translator.home_screen.domain.service

interface LanguageTranslator {
    fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        onSuccess: (String?) -> Unit,
        onError: (String) -> Unit,
    )
}