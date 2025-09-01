package com.samwrotethecode.translator.home_screen.presentation

sealed class HomeScreenState {
    object Initializing : HomeScreenState()
    object Loading : HomeScreenState()
    data class Success(val data: String) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}