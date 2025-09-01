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
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Initial)
    val state = _state.asStateFlow()


}