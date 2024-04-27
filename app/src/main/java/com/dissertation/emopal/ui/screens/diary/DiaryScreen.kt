package com.dissertation.emopal.ui.screens.diary

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dissertation.emopal.data.DiaryViewModel
import com.dissertation.emopal.ui.components.BackButton
import com.dissertation.emopal.ui.components.GenericButton
import com.dissertation.emopal.ui.components.PictureCase

/**
 * Diary Screen Composable which contains the list of emotions and the images.
 */
@Composable
fun DiaryScreen(
    onBackButtonClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
) {
    // TODO: To use R.String for the emotions for internationalisation and less errors
    val emotions = listOf("Happy", "Sad", "Angry", "Surprised")

    val viewModel: DiaryViewModel = hiltViewModel()
    // Collecting the pictures from the ViewModel
    val happyPictures by viewModel.happyPictures.collectAsState(emptyList())
    val sadPictures by viewModel.sadPictures.collectAsState(emptyList())
    val angryPictures by viewModel.angryPictures.collectAsState(emptyList())
    val surprisedPictures by viewModel.surprisedPictures.collectAsState(emptyList())

    DiaryBody(
        emotions,
        onBackButtonClicked,
        onTakePictureClicked,
        happyPictures,
        sadPictures,
        angryPictures,
        surprisedPictures
    )
}

/**
 * Diary Body Composable which contains the composable for the list of emotions and the images.
 * This contains a [LazyColumn] which allows to have a scrollable list of categories,
 * followed by the images related to such category.
 * @param emotions List of emotions to be displayed.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryBody(
    emotions: List<String>,
    onBackButtonClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
    happyPictures: List<Bitmap>,
    sadPictures: List<Bitmap>,
    angryPictures: List<Bitmap>,
    surprisedPictures: List<Bitmap>

) {

// Wrapping it in a Box to have the footer buttons at the bottom of the screen.
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight(1f)) {
            stickyHeader {

            }
            // TODO: Using String Resources (R.string) for the emotions
            items(emotions.size) { index ->
                val picturesForCategory = when (emotions[index]) {
                    "Happy" -> happyPictures
                    "Sad" -> sadPictures
                    "Angry" -> angryPictures
                    "Surprised" -> surprisedPictures
                    else -> emptyList()
                }
                PictureCase(picturesForCategory, emotions[index])
                Divider()
            }
            item {
                Spacer(modifier = Modifier.height(
                    with(LocalDensity.current) {
                        90.dp
                    }
                ))
            }
        } // End of LazyColumn

        // FOOTER BUTTONS //
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceEvenly,
        )
        {
            // BACK BUTTON //
            BackButton(onBackButtonClicked = onBackButtonClicked)

            // TAKE PICTURE BUTTON //
            GenericButton(title = "Take Picture", onClick = onTakePictureClicked)
        } // End of Row
    } // End of Box
} // End of Composable

/**
 * Full Screen Image Composable which displays the image in full screen based on the parent component.
 * @param photo The photo to be displayed.
 * @param onDismiss The lambda to dismiss the full screen image.
 */
@Composable
fun FullScreenImage(photo: Bitmap, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = photo.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onDismiss() }
        )
    }
}

/**
 * Picture Box Composable which displays the picture in a box with a click listener.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PictureBox(picture: Bitmap, onClick: () -> Unit, onLongClick: () -> Unit) {
    Image(
        bitmap = picture.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
            // Detecting the tap gestures for the image
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongClick() },
                    onTap = { onClick() }
                )
            },
    )
}

@Composable
@Preview
fun DiaryScreenPreview() {
    DiaryScreen(onBackButtonClicked = {}, onTakePictureClicked = {})
}