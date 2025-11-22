package com.samwrotethecode.translator.home_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem
import com.samwrotethecode.translator.core.domain.repository.TranslationHistoryRepository
import com.samwrotethecode.translator.home_screen.domain.service.LanguageDetector
import com.samwrotethecode.translator.home_screen.domain.service.LanguageTranslator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val languageDetector: LanguageDetector,
    private val languageTranslator: LanguageTranslator,
    private val translationHistoryRepository: TranslationHistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    fun detectLanguage() {
        _uiState.value = _uiState.value.copy(
            isDetectingLanguage = true,
            sourceLanguage = null,
            error = null,
            translatedText = null,
        )
        languageDetector.detectLanguage(
            text = _uiState.value.inputText,
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
            sourceLanguage = null,
            translatedText = null,
            error = null,
        )
        if (value) {
            detectLanguage()
        }
    }

    fun selectSourceLanguage(languageCode: String) {
        _uiState.value = _uiState.value.copy(
            sourceLanguage = languageCode,
        )
    }

    fun selectTargetLanguage(languageCode: String) {
        _uiState.value = _uiState.value.copy(
            targetLanguage = languageCode,
            translatedText = null,
        )
    }

    fun updateInputText(text: String) {
        _uiState.value = _uiState.value.copy(
            inputText = text,
        )
    }

    fun swapLanguages() {
        val currentState = _uiState.value
        if (currentState.targetLanguage != null) {
             val newSource = currentState.targetLanguage
             val newTarget = currentState.sourceLanguage

             _uiState.value = currentState.copy(
                 sourceLanguage = newSource,
                 targetLanguage = newTarget,
                 autoDetectLanguage = false,
                 inputText = currentState.translatedText ?: currentState.inputText,
                 translatedText = null
             )
        }
    }

    fun translateText() {
        val text = _uiState.value.inputText
        if (text.isBlank()) {
            _uiState.value = _uiState.value.copy(
                error = "Please enter text to translate"
            )
            return
        }

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
                saveTranslationToHistory(
                    text,
                    translatedText,
                    uiState.value.sourceLanguage!!,
                    uiState.value.targetLanguage!!
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

    fun clearTranslatedText() {
        _uiState.value = _uiState.value.copy(
            translatedText = null,
        )
    }

    private fun saveTranslationToHistory(
        sourceText: String,
        translatedText: String,
        sourceLanguage: String,
        targetLanguage: String
    ) {
        viewModelScope.launch {
            translationHistoryRepository.addHistoryItem(
                TranslationHistoryItem(
                    sourceText = sourceText,
                    translatedText = translatedText,
                    sourceLanguageCode = sourceLanguage,
                    targetLanguageCode = targetLanguage
                )
            )
        }
    }
}