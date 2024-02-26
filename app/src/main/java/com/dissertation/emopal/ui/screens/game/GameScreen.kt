package com.dissertation.emopal.ui.screens.game

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
fun GameScreen() {
    val orientation = LocalConfiguration.current.orientation
    val GAMES = 3

    Box(modifier = Modifier.fillMaxSize()) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until GAMES) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.small,
//                    modifier = Modifier
//                        .size(150.dp)
                    ) {
                        Text(
                            text = "Level ${i + 1}",
                            style = MaterialTheme.typography.headlineLarge
                        );
                    }
                }
            } // End of Row

        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in 0 until GAMES) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.small,
//                    modifier = Modifier
//                        .size(150.dp)
                    ) {
                        Text(
                            text = "Level ${i + 1}",
                            style = MaterialTheme.typography.headlineLarge
                        );
                    }
                }
            } // End of Column
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // BACK BUTTON //
            BackButton(onBackButtonClicked = { /*TODO*/ })

        } // End of Row
    } // End of Box
} // End of Composable

@Composable
@Preview
fun GameScreenPreview() {
    GameScreen()
}