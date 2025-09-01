package com.samwrotethecode.translator.home_screen.data.service

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.samwrotethecode.translator.home_screen.domain.service.LanguageTranslator

class LanguageTranslatorImpl : LanguageTranslator {
    private val tag = "LanguageTranslator"

    override fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        onSuccess: (String?) -> Unit,
        onError: (String) -> Unit
    ) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        val translator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            translator.translate(text).addOnSuccessListener { result: String? ->
                onSuccess(result)
            }.addOnFailureListener { exception ->
                Log.e(tag, "Error translating text", exception)
                onError("An error occurred during translation: ${exception.message}")
            }
        }.addOnFailureListener { exception ->
            Log.e(tag, "Error downloading model", exception)
            onError("An error occurred during translation: ${exception.message}")
        }
    }
}