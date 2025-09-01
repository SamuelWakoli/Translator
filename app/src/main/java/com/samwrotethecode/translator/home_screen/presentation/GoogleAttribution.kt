package com.samwrotethecode.translator.home_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samwrotethecode.translator.R

@Composable
fun GoogleAttribution(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .heightIn(max = 24.dp)
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text("Powered by ")
        Image(
            painterResource(R.drawable.google_logo_transparent),
            contentDescription = "Google logo",
            contentScale = ContentScale.Fit,
        )
        VerticalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Image(
            painterResource(R.drawable.ml),
            contentDescription = "ML Kit logo",
            contentScale = ContentScale.Fit,
        )
        Text(" ML Kit")
    }
}