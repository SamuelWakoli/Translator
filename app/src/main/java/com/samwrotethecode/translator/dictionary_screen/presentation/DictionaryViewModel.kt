package com.samwrotethecode.translator.dictionary_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwrotethecode.translator.dictionary_screen.data.remote.DictionaryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryApi: DictionaryApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(DictionaryUiState())
    val uiState: StateFlow<DictionaryUiState> = _uiState.asStateFlow()

    init {
        loadWordOfTheDay()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    private fun loadWordOfTheDay() {
        // For now, we'll pick a random word from a predefined list or just a static one
        // In a real app, this could come from a specific API endpoint or local DB
        val words = listOf("serendipity", "ephemeral", "sonder", "vellichor", "petrichor")
        val randomWord = words.random()
        
        viewModelScope.launch {
             try {
                val result = dictionaryApi.getWordDefinition(randomWord)
                if (result.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        wordOfTheDay = result[0].toDictionaryDefinition()
                    )
                }
            } catch (e: Exception) {
                // Silent failure for WOTD
            }
        }
    }

    fun searchWord() {
        val query = _uiState.value.searchQuery
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, definition = null)
            try {
                val result = dictionaryApi.getWordDefinition(query)
                if (result.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        definition = result[0].toDictionaryDefinition()
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "No definition found for '$query'"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to fetch definition: ${e.message}"
                )
            }
        }
    }
}

data class DictionaryUiState(
    val searchQuery: String = "",
    val definition: DictionaryDefinition? = null,
    val wordOfTheDay: DictionaryDefinition? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class DictionaryDefinition(
    val word: String,
    val phonetic: String?,
    val meanings: List<Meaning>
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

data class Definition(
    val definition: String,
    val example: String?
)
