package com.dissertation.emopal.ui.screens.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dissertation.emopal.ui.components.BackButton

@Composable
fun Level(level: String, onBackButtonClicked: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Level $level",
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            BackButton(onBackButtonClicked = onBackButtonClicked)
        }
    }
}

@Composable
@Preview
fun Level1Preview() {
    Level("1", onBackButtonClicked = {})
}
