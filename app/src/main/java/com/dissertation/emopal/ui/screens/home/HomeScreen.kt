package com.dissertation.emopal.ui.screens.home

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dissertation.emopal.BuildConfig
import com.dissertation.emopal.R
import com.dissertation.emopal.ui.theme.EmoPalTheme
import com.dissertation.emopal.util.FaceEmotionClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HomeScreen(
    onDiaryButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val apiKey = BuildConfig.API_KEY
    val visionApiRequest = FaceEmotionClient(apiKey)

    // TODO: Temporary function to test the Vision API
    fun onHelpClickPressed() {
        GlobalScope.launch(Dispatchers.IO) {
            val bitmap =
                BitmapFactory.decodeFile("/data/user/0/com.dissertation.emopal/files/diary_images/IMG_2024-03-16 12:45:11.png")
            val featureType = "FACE_DETECTION" // Change this according to your requirements
            val maxResults = 1 // Change this according to your requirements
            val response = visionApiRequest.annotateImage(bitmap, featureType, maxResults)

            Log.d("VisionAPIResponse", response) // Log the response
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                // TODO: Add to string Resource
                text = "Welcome to EmoPal",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // HOME PAGE IMAGE //
            Image(
                modifier = Modifier
                    .width(300.dp)
                    .weight(1F, fill = true),
                painter = painterResource(id = R.drawable.smiley_bee),
                // TODO: Add to string Resource
                contentDescription = "Smiley Bee",
            )

            // PLAY BUTTON //
            Button(
                onClick = { onPlayButtonClicked() },
                modifier = Modifier.sizeIn(minWidth = 144.dp, minHeight = 62.dp),
                shape = MaterialTheme.shapes.small,
            )
            {
                Text(
                    text = "Play",
                    fontSize = 24.sp
                )
            }
            // Bottom Button Column
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp, 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    // DIARY BUTTON //
                    Button(
                        onClick = { onDiaryButtonClicked() },
                        modifier = Modifier.sizeIn(minWidth = 124.dp, minHeight = 62.dp),
                        shape = MaterialTheme.shapes.small,
                    ) {

                        Text(
                            text = "Diary",
                            fontSize = 24.sp
                        )
                    }

                    // HELP BUTTON //
                    Button(
                        // TODO: Temporary function to test the Vision API
                        onClick = { onHelpClickPressed() },
                        modifier = Modifier.sizeIn(minWidth = 124.dp, minHeight = 62.dp),
                        shape = MaterialTheme.shapes.small,
                    ) {

                        Text(
                            text = "Help",
                            fontSize = 24.sp
                        )
                    }

                }
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    EmoPalTheme {
        HomeScreen(
            onDiaryButtonClicked = {},
            onPlayButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        )
    }

}