package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import com.samwrotethecode.translator.core.utils.getLanguageNameFromBcp47

// Use this to show all supported languages
val languagesBcp47: List<String?> = TranslateLanguage.getAllLanguages()

@Composable
fun InputLanguageSelector(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    var showModeSelector by remember { mutableStateOf(false) }
    var showLanguageSelector by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        onClick = {
            showModeSelector = true
        },
    ) {
        ListItem(
            overlineContent = {
                Text("Source Language")
            },
            headlineContent = {
                if (uiState.autoDetectLanguage) {
                    Column {
                        Text("Auto Detect")
                        if (uiState.sourceLanguage != null) Text(
                            "${
                                getLanguageNameFromBcp47(
                                    uiState.sourceLanguage
                                )
                            } (Detected)"
                        )
                    }
                } else {
                    Column {
                        Text("Select Language")
                        if (uiState.sourceLanguage != null) Text(
                            getLanguageNameFromBcp47(
                                uiState.sourceLanguage
                            )
                        )
                    }
                }
            },
        )

        if (showModeSelector) {
            DropdownMenu(
                expanded = true, onDismissRequest = { showModeSelector = false }) {
                DropdownMenuItem(text = { Text("Auto Detect") }, onClick = {
                    viewModel.toggleAutoDetectLanguageMode(true)
                    showModeSelector = false
                })
                DropdownMenuItem(text = { Text("Select Language") }, onClick = {
                    viewModel.toggleAutoDetectLanguageMode(false)
                    showLanguageSelector = true
                    showModeSelector = false
                })
            }
        }
    }



    if (showLanguageSelector) {
        LanguageSelectorDialog(
            onDismissRequest = {
                showLanguageSelector = false
            },
            onSelectLanguage = {
                viewModel.selectSourceLanguage(it)
            },
        )
    }
}

@Composable
fun OutputLanguageSelector(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    var showLanguageSelector by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        onClick = {
            showLanguageSelector = true
        },
    ) {
        ListItem(
            overlineContent = {
                Text("Target Language")
            },
            headlineContent = {
                if (uiState.targetLanguage != null) Text(
                    getLanguageNameFromBcp47(
                        uiState.targetLanguage
                    )
                )
            },
        )
    }

    if (showLanguageSelector) {
        LanguageSelectorDialog(
            onDismissRequest = {
                showLanguageSelector = false
            },
            onSelectLanguage = {
                viewModel.selectTargetLanguage(it)
            },
        )
    }
}

@Composable
fun LanguageSelectorDialog(
    onDismissRequest: () -> Unit, onSelectLanguage: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Language") },
        text = {
            LazyColumn {
                items(languagesBcp47) {
                    ListItem(
                        headlineContent = {
                            Text(getLanguageNameFromBcp47(it!!).toString())
                        },
                        Modifier
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                onSelectLanguage(it!!)
                                onDismissRequest()
                            },
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) { Text("Close") }

        },
    )
}