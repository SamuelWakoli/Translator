package com.samwrotethecode.translator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.samwrotethecode.translator.core.presentation.navigation.NavigationHost
import com.samwrotethecode.translator.home_screen.presentation.HomeScreen
import com.samwrotethecode.translator.core.theme.TranslatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                NavigationHost()
            }
        }
    }
}