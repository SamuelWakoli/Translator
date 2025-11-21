package com.samwrotethecode.translator.history_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwrotethecode.translator.core.data.local.entity.TranslationHistoryItem
import com.samwrotethecode.translator.core.domain.repository.TranslationHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TranslationHistoryRepository
) : ViewModel() {

    private val _history = MutableStateFlow<List<TranslationHistoryItem>>(emptyList())
    val history = _history.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getHistory().collect {
                _history.value = it
            }
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
