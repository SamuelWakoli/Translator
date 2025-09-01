package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samwrotethecode.translator.R
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { HomeScreenAppBar() }) { innerPadding ->
        HomeScreenBody(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar() {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
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
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    var inputText by remember { mutableStateOf("") }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .padding(8.dp)
        ) {
            GoogleAttribution(modifier = Modifier.padding(16.dp))
            InputLanguageSelector(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Enter text to translate") },
                singleLine = false,
                minLines = 5,
                maxLines = 8,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = MaterialTheme.shapes.large,
            )
            LaunchedEffect(inputText) {
                if (inputText.isNotBlank() && uiState.autoDetectLanguage) {
                    // Debounce for 1 second before detecting language
                    delay(1000L)
                    if (inputText.isNotBlank()) { // Re-check after delay
                        viewModel.detectLanguage(inputText)
                    }
                }
            }
            Spacer(Modifier.size(8.0.dp))
            OutputLanguageSelector(modifier = Modifier.padding(8.dp))
            Spacer(Modifier.size(8.dp))
            uiState.translatedText?.let {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                ) {
                    Text(
                        it,
                        Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                    )
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

            if (uiState.sourceLanguage != null && uiState.targetLanguage != null && inputText.isNotBlank()) {
                ElevatedButton(
                    onClick = {
                        viewModel.translateText(inputText)
                    }, modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "Translate",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (uiState.isTranslating)
                            CircularProgressIndicator(
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