package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samwrotethecode.translator.R
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(

) {
    Scaffold(
        topBar = { HomeScreenAppBar() }) { innerPadding ->
        HomeScreenBody(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar(
) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(56.dp)
            )
        },
        title = {
            Text("Translator", fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
fun HomeScreenBody(
    modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val clipboardManager = LocalClipboardManager.current

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .padding(16.dp)
        ) {
//            MLKitAttribution(
//                modifier = Modifier
//                    .padding(bottom = 16.dp)
//                    .padding(horizontal = 16.dp)
//            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputLanguageSelector(modifier = Modifier.weight(1f))
                Spacer(Modifier.size(8.dp))
                Card(
                    onClick = {
                        clipboardManager.getText()?.let {
                            viewModel.updateInputText(it.text)
                        }
                    },
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = "Paste",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(8.dp))
                        Text("Paste")
                    }
                }
            }

            OutlinedTextField(
                value = uiState.inputText,
                onValueChange = { viewModel.updateInputText(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = { Text("Enter text to translate") },
                singleLine = false,
                minLines = 4,
                maxLines = 8,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = MaterialTheme.shapes.large,
            )

            LaunchedEffect(uiState.inputText) {
                if (uiState.inputText.isNotBlank() && uiState.autoDetectLanguage) {
                    // Debounce for 1 second before detecting language
                    delay(1000L)
                    if (uiState.inputText.isNotBlank()) { // Re-check after delay
                        viewModel.detectLanguage()
                    }
                }
            }

            Spacer(Modifier.size(8.0.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutputLanguageSelector(modifier = Modifier.weight(1f))

                Spacer(Modifier.size(8.dp))

                Card(
                    onClick = { viewModel.swapLanguages() },
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Swap Languages",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.size(8.dp))
                        Text("Swap")
                    }
                }
            }

            Spacer(Modifier.size(8.dp))

            uiState.translatedText?.let {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    ) {
                        Column {
                            Text(
                                it,
                                Modifier.padding(16.dp),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(it))
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy",
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                    Spacer(Modifier.size(8.dp))
                    FilledIconButton(onClick = { viewModel.clearTranslatedText() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                if (uiState.isDetectingLanguage) {
                    Text("Detecting language...")
                }
                if (uiState.isDownloadingModel) {
                    Text("Downloading ML model...")
                }
                if (uiState.error != null) {
                    Text(
                        uiState.error,
                        Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }

            if (uiState.sourceLanguage != null && uiState.targetLanguage != null && uiState.inputText.isNotBlank()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(Modifier.size(16.dp))
                    ElevatedButton(
                        onClick = {
                            viewModel.translateText()
                        }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Translate",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            if (uiState.isTranslating) CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(24.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp,
                            )
                        }
                    }
                }
            }
        }
    }
}