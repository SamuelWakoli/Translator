package com.samwrotethecode.translator.home_screen.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Initializing)
    val state = _state.asStateFlow()


}