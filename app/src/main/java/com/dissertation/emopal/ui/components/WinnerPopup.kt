package com.dissertation.emopal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * WinnerPopup Composable which shows the popup when the user wins the game.
 * @Author: A. La Fauci
 * @Date: 18/04/2024
 */

@Composable
fun WinnerPopup(
    onPlayAgainClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(32.dp)
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Text(
                text = "Congratulations!",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "You've won the game!",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = { onPlayAgainClicked() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Play Again")
            }
        }
    }
}

@Composable
@Preview
fun WinnerPopupPreview() {
    WinnerPopup(onPlayAgainClicked = {})
}