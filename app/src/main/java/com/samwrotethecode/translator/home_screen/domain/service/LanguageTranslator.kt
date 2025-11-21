package com.samwrotethecode.translator.home_screen.domain.service

interface LanguageTranslator {
    fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        onDownloadModel: () -> Unit,
        onCompleteModelDownload: () -> Unit,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ): Unit
}