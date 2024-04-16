package com.dissertation.emopal.ui.screens.play

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dissertation.emopal.ui.components.BackButton
import com.dissertation.emopal.ui.components.GenericButton

@Composable
fun Level(
    level: String,
    onBackButtonClicked: () -> Unit,
    onTakePicture: () -> Unit,
    leftImageResource: String,
    rightImageResource: String
) {
    val orientation = LocalConfiguration.current.orientation
    val boxPadding = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 8.dp else 32.dp
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // HEADER SECTION //
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            // Level Description //
            Text(
                text = "Level $level",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        // MAIN BODY //
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Middle Row for Images
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                // PROMPT //
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.LightGray)
                        .padding(end = boxPadding)
                ) {
                    // Load and display image here using leftImageResource
                }

                Spacer(modifier = Modifier.width(boxPadding))

                // USER PICTURE //
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.LightGray)
                        .padding(start = boxPadding)
                ) {
                    // Load and display image here using rightImageResource
                }
            }

            // Response Text //
            Text(
                text = "Response text will appear here",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        // FOOTER SECTION //
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                BackButton(onBackButtonClicked = onBackButtonClicked)

                // Take Picture Button
                GenericButton("Take Picture", onClick = onTakePicture)
            }

        }
    }
}

@Composable
@Preview
fun LevelScreenPreview() {
    Level(
        level = "1",
        onBackButtonClicked = {},
        onTakePicture = {},
        leftImageResource = "level1/anger/anger1.jpg",
        rightImageResource = "level1/happy/happy1.jpg"
    )
}