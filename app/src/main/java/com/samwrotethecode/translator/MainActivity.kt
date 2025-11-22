package com.samwrotethecode.translator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.samwrotethecode.translator.core.theme.TranslatorTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.samwrotethecode.translator.core.presentation.MainScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                MainScreen(windowSizeClass = windowSizeClass.widthSizeClass)
            }
        }
    }
}