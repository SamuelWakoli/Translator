package com.samwrotethecode.translator.home_screen.presentation

import androidx.lifecycle.ViewModel
import com.samwrotethecode.translator.home_screen.domain.service.LanguageDetector
import com.samwrotethecode.translator.home_screen.domain.service.LanguageTranslator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val languageDetector: LanguageDetector,
    private val languageTranslator: LanguageTranslator
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    fun detectLanguage(text: String) {
        _uiState.value = _uiState.value.copy(
            isDetectingLanguage = true,
            sourceLanguage = null,
            error = null,
            translatedText = null,
        )
        languageDetector.detectLanguage(
            text = text,
            onSuccess = { languageCode ->
                _uiState.value = _uiState.value.copy(
                    sourceLanguage = languageCode,
                    isDetectingLanguage = false,
                )
            },
            onFailure = { error ->
                _uiState.value = _uiState.value.copy(
                    error = error,
                    sourceLanguage = null,
                    isDetectingLanguage = false,
                )
            }
        )
    }

    fun toggleAutoDetectLanguageMode(value: Boolean) {
        _uiState.value = _uiState.value.copy(
            autoDetectLanguage = value,
            targetLanguage = null,
        )
    }

    fun selectSourceLanguage(languageCode: String) {
        _uiState.value = _uiState.value.copy(
            sourceLanguage = languageCode,
            autoDetectLanguage = false,
        )
    }

    fun selectTargetLanguage(languageCode: String) {
        _uiState.value = _uiState.value.copy(
            targetLanguage = languageCode,
            autoDetectLanguage = false,
        )
    }

    fun translateText(text: String) {
        if (uiState.value.sourceLanguage == null) {
            _uiState.value = _uiState.value.copy(
                error = "Source language not detected"
            )
            return
        }

        if (uiState.value.targetLanguage == null) {
            _uiState.value = _uiState.value.copy(
                error = "Target language not selected"
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            isTranslating = true,
            translatedText = null,
            error = null,
        )

        languageTranslator.translateText(
            text = text,
            sourceLanguage = uiState.value.sourceLanguage!!,
            targetLanguage = uiState.value.targetLanguage!!,
            onDownloadModel = {
                _uiState.value = _uiState.value.copy(
                    isDownloadingModel = true,
                    isTranslating = false,
                    error = null,
                )
            },
            onCompleteModelDownload = {
                _uiState.value = _uiState.value.copy(
                    isDownloadingModel = false,
                    isTranslating = true,
                    error = null,
                )
            },
            onSuccess = { translatedText ->
                _uiState.value = _uiState.value.copy(
                    translatedText = translatedText,
                    isTranslating = false,
                    error = null,
                )
            },
            onError = { error ->
                _uiState.value = _uiState.value.copy(
                    error = error,
                    isTranslating = false,
                    isDownloadingModel = false,
                    translatedText = null,
                )
            }
        )

    }

}