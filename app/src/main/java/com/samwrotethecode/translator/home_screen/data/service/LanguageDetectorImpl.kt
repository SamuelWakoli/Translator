package com.samwrotethecode.translator.home_screen.data.service

import android.util.Log
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.samwrotethecode.translator.home_screen.domain.service.LanguageDetector

class LanguageDetectorImpl : LanguageDetector {
    val languageIdentifier = LanguageIdentification.getClient()
    private val tag = "LanguageDetector"

    override fun detectLanguage(
        text: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        languageIdentifier.identifyLanguage(text)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    onFailure("Language not detected")
                } else {
                    onSuccess(languageCode)
                }
            }.addOnFailureListener { exception ->
                Log.e(tag, "Error detecting language", exception)

                onFailure("Error detecting language: ${exception.message}")
            }
    }
}