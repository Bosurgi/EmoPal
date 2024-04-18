package com.dissertation.emopal.ui.screens.play

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dissertation.emopal.ui.components.BackButton
import com.dissertation.emopal.ui.components.GenericButton
import com.dissertation.emopal.ui.components.camera.CameraView

@Composable
fun Level(
    level: String,
    onBackButtonClicked: () -> Unit,
    onTakePicture: (Bitmap) -> Unit,
    viewModel: GameViewModel
) {
    val orientation = LocalConfiguration.current.orientation
    val boxPadding = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 8.dp else 32.dp

    val counter by viewModel.counter.collectAsState(0)
    val leftImageResource by viewModel.prompt.collectAsState()
    val currentEmotion by viewModel.currentEmotion.collectAsState()
    val userEmotion by viewModel.userEmotion.collectAsState()
    val isEmotionMatch by viewModel.isEmotionMatch.collectAsState()
    val isWinner by viewModel.isWinner.collectAsState(false)
    // Flag to trigger the snap of the picture
    var shouldTakePicture by remember { mutableStateOf(false) }
    // Flag to show the camera or the user picture
    var isCameraVisible by remember { mutableStateOf(true) }
    // The current user picture taken by the camera
    var currentUserPicture by remember { mutableStateOf<Bitmap?>(null) }


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
            Text(
                text = "Match the emotion",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            // Counter //
            Text(
                text = "Correct Matches: $counter / 10",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
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
                        .background(Color.LightGray)
                        .size(200.dp)
                ) {
                    // Load and display image here using leftImageResource
                    leftImageResource?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Prompt Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                            alignment = Alignment.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.width(boxPadding))

                // USER PICTURE //
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(start = boxPadding)
                ) {

                    // Display Camera or User Picture if a picture is taken
                    if (isCameraVisible) {
                        CameraView(
                            onBackButtonClicked = onBackButtonClicked,
                            onTakePhoto = { bitmap ->
                                onTakePicture(bitmap)
                                shouldTakePicture = true
                                isCameraVisible = false
                                currentUserPicture = bitmap
                            },
                            isButtonVisible = false,
                            shouldTakePicture = shouldTakePicture
                        )
                    } else {
                        currentUserPicture?.asImageBitmap()?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "User Picture",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.FillBounds,
                                alignment = Alignment.Center
                            )
                        }
                    }
                }
            }

            // Response Text

            when (isEmotionMatch) {
                true -> {
                    Text(
                        text = "Correct! This is a $currentEmotion face!",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                false -> {
                    Text(
                        text = "Incorrect! Your face is showing a $userEmotion emotion!",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                null -> {
                    // No Text is Shown
                }
            }
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
                GenericButton("Take Picture", onClick = {
                    shouldTakePicture = true
                })

                // Showing Next or Retry button based on the result state
                // TODO: Implement the onClick logic
                when (isEmotionMatch) {
                    true -> {
                        GenericButton("Next", onClick = {
                            viewModel.updatePrompt()
                            isCameraVisible = true
                            currentUserPicture = null
                            shouldTakePicture = false
                        })
                    }

                    false -> {
                        GenericButton("Retry", onClick = {
                            isCameraVisible = true
                            currentUserPicture = null
                            shouldTakePicture = false

                        })
                    }

                    null -> {
                        // No Button is Shown
                    }
                }
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
        viewModel = hiltViewModel()
    )
}