package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samwrotethecode.translator.core.utils.getLanguageNameFromBcp47
import com.samwrotethecode.translator.core.utils.languagesBcp47

@Composable
fun InputLanguageSelector(
    modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    var showModeSelector by remember { mutableStateOf(false) }
    var showLanguageSelector by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        onClick = {
            showModeSelector = true
        },
        shape = MaterialTheme.shapes.medium,
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
            overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ), overlineContent = {
            Text("Source Language", fontWeight = FontWeight.Bold)
        }, headlineContent = {
            if (uiState.autoDetectLanguage) {
                Column {
                    if (uiState.sourceLanguage == null) Text("Auto Detect")
                    else Text("${getLanguageNameFromBcp47(uiState.sourceLanguage)} (Detected)")
                }
            } else {
                Column {
                    if (uiState.sourceLanguage == null) Text("Select Language")
                    else Text(getLanguageNameFromBcp47(uiState.sourceLanguage))
                }
            }
        }, trailingContent = {
            IconButton(onClick = {
                showModeSelector = true
            }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                offset = DpOffset(8.dp, 4.dp),
                expanded = showModeSelector,
                onDismissRequest = { showModeSelector = false }) {
                DropdownMenuItem(text = {
                    Text(
                        "Auto Detect",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }, onClick = {
                    viewModel.toggleAutoDetectLanguageMode(true)
                    showModeSelector = false
                })
                DropdownMenuItem(text = {
                    Text(
                        "Select Language",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }, onClick = {
                    viewModel.toggleAutoDetectLanguageMode(false)
                    showLanguageSelector = true
                    showModeSelector = false
                })
            }
        })
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
    modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    var showLanguageSelector by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = {
            showLanguageSelector = true
        },
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            overlineContent = if (uiState.targetLanguage == null) null else {
                {
                    Text("Target Language", fontWeight = FontWeight.Bold)
                }
            },
            headlineContent = {
                if (uiState.targetLanguage == null) Text("Select Target Language")
                else Text(getLanguageNameFromBcp47(uiState.targetLanguage))
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
    isSourceLanguage: Boolean = false,
    onDismissRequest: () -> Unit,
    onSelectLanguage: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                "Select ${if (isSourceLanguage) "Source" else "Target"} Language",
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column {
                Text(
                    "${languagesBcp47.size} languages",
                    color = MaterialTheme.colorScheme.secondary,
                )
                Spacer(Modifier.size(8.dp))
                LazyColumn {
                    items(languagesBcp47) {
                        ListItem(
                            headlineContent = {
                                Text(getLanguageNameFromBcp47(it))
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable {
                                    onSelectLanguage(it)
                                    onDismissRequest()
                                },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                        )
                    }
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