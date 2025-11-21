package com.samwrotethecode.translator.history_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem
import com.samwrotethecode.translator.core.domain.repository.TranslationHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TranslationHistoryRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isFavoriteFilter = MutableStateFlow(false)
    val isFavoriteFilter = _isFavoriteFilter.asStateFlow()

    private val _history = MutableStateFlow<List<TranslationHistoryItem>>(emptyList())
    
    val history = combine(_history, _searchQuery, _isFavoriteFilter) { history, query, isFavorite ->
        history.filter { item ->
            val matchesQuery = item.sourceText.contains(query, ignoreCase = true) ||
                    item.translatedText.contains(query, ignoreCase = true)
            val matchesFavorite = if (isFavorite) item.isFavorite else true
            matchesQuery && matchesFavorite
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.getHistory().collect {
                _history.value = it
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavoriteFilter() {
        _isFavoriteFilter.value = !_isFavoriteFilter.value
    }

    fun toggleFavorite(item: TranslationHistoryItem) {
        viewModelScope.launch {
            repository.updateHistoryItem(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun deleteHistoryItem(item: TranslationHistoryItem) {
        viewModelScope.launch {
            repository.deleteHistoryItem(item)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
