package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samwrotethecode.translator.R

@Composable
fun MLKitAttribution(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .heightIn(max = 24.dp)
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text("Powered by ", fontWeight = FontWeight.SemiBold)
        Image(
            painterResource(R.drawable.ml),
            contentDescription = "ML Kit logo",
            contentScale = ContentScale.Fit,
        )
        Text(" ML Kit", fontWeight = FontWeight.SemiBold)
    }
}