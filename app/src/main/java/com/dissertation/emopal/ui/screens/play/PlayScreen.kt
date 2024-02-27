package com.dissertation.emopal.ui.screens.play

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dissertation.emopal.ui.components.BackButton

@Composable
fun PlayScreen(
    onBackButtonClicked: () -> Unit,
    onLevelClicked: (String) -> Unit,
) {
    val orientation = LocalConfiguration.current.orientation
    val GAMES = 3

    Box(modifier = Modifier.fillMaxSize()) {
        // Checking the orientation to change button disposition accordingly
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            PlayScreenLandscape(games = GAMES, onLevelClicked = onLevelClicked)

        } else {
            PlayScreenPortrait(games = GAMES, onLevelClicked = onLevelClicked)
        }
        // FOOTER BUTTONS //
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // BACK BUTTON //
            BackButton(onBackButtonClicked = onBackButtonClicked)

        } // End of Row
    } // End of Box
} // End of Composable

@Composable
fun PlayScreenLandscape(games: Int, onLevelClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1 until games + 1) {
            Button(
                onClick = {
                    onLevelClicked("LEVEL${i.toString()}")
                },
                shape = MaterialTheme.shapes.small,
//                    modifier = Modifier
//                        .size(150.dp)
            ) {
                Text(
                    text = "Level $i",
                    style = MaterialTheme.typography.headlineLarge
                );
            }
        }
    } // End of Row
}

@Composable
fun PlayScreenPortrait(games: Int, onLevelClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 1 until games + 1) {
            Button(
                onClick = { onLevelClicked("LEVEL${i.toString()}") },
                shape = MaterialTheme.shapes.small,
//                    modifier = Modifier
//                        .size(150.dp)
            ) {
                Text(
                    text = "Level $i",
                    style = MaterialTheme.typography.headlineLarge
                );
            }
        }
    } // End of Column
}

@Composable
@Preview
fun GameScreenPreview() {
    PlayScreen(onBackButtonClicked = {}, onLevelClicked = {})
}