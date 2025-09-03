package com.samwrotethecode.translator.home_screen.data.service

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.samwrotethecode.translator.home_screen.domain.service.LanguageTranslator

class LanguageTranslatorImpl : LanguageTranslator {
    private val tag = "LanguageTranslator"

    override fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        onDownloadModel: () -> Unit,
        onCompleteModelDownload: () -> Unit,
        onSuccess: (String?) -> Unit,
        onError: (String) -> Unit
    ) {
        val options = TranslatorOptions.Builder().setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage).build()

        val conditions = DownloadConditions.Builder().requireWifi().build()
        val translator = Translation.getClient(options)
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            translator.translate(text).addOnSuccessListener {
                onSuccess(it)
            }.addOnFailureListener { exception ->
                Log.e(tag, "Error translating text", exception)
                onError("An error occurred during translation: ${exception.message}")
            }
        }.addOnFailureListener {
            Log.e(tag, "Error downloading model", it)
            onError("An error occurred on ML model download: ${it.message}")
        }
    }
}