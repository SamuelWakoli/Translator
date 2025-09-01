package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.samwrotethecode.translator.R
import com.samwrotethecode.translator.core.presentation.MultiScreenPreview
import com.samwrotethecode.translator.core.theme.TranslatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { HomeScreenAppBar(navController) }
    ) { innerPadding ->
        HomeScreenBody(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar(navController: NavHostController) {
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
        )
    )
}

@Composable
fun HomeScreenBody(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it }
            )
            Text("")
        }
    }
}


@MultiScreenPreview
@Composable
fun HomeScreenPreview() {
    TranslatorTheme { HomeScreen(rememberNavController()) }
}