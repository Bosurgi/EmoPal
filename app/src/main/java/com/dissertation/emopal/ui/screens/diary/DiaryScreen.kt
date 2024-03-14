package com.dissertation.emopal.ui.screens.diary

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dissertation.emopal.data.DiaryViewModel
import com.dissertation.emopal.ui.components.BackButton

/**
 * Diary Screen Composable which contains the list of emotions and the images.
 */
@Composable
fun DiaryScreen(
    onBackButtonClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
) {
    // TODO: Implement the below
    val emotions = listOf("Happy", "Sad", "Angry", "Surprise")

    val viewModel: DiaryViewModel = hiltViewModel()
    val pictures by viewModel.pictures.collectAsState()

    DiaryBody(emotions, onBackButtonClicked, onTakePictureClicked, pictures)
}

/**
 * Diary Body Composable which contains the composable for the list of emotions and the images.
 * This contains a [LazyColumn] which allows to have a scrollable list of categories,
 * followed by the images related to such category.
 * @param emotions List of emotions to be displayed.
 */
// TODO: To be changed using the Database and ViewModel with real pictures.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryBody(
    emotions: List<String>,
    onBackButtonClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
    pictures: List<Bitmap>
) {

// Wrapping it in a Box to have the footer buttons at the bottom of the screen.
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight(1f)) {
            stickyHeader {

            }
            // TODO: Adjusted with different components divided by emotions
            items(emotions.size) { index ->
                CategoryList(emotions[index], pictures)
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
            // TODO: This is going to be used in the game as well. To Make a reusable composable.
            Button(
                onClick = { onTakePictureClicked() },
                modifier = Modifier
                    .sizeIn(minWidth = 124.dp, minHeight = 62.dp)
                    .padding(8.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Take Picture", fontSize = 24.sp)
            }
        } // End of Row
    } // End of Box
} // End of Composable

/**
 * Category List Composable which contains the category name and the images related to such category.
 * @param category Category name to be displayed.
 */
@Composable
fun CategoryList(category: String, pictures: List<Bitmap>) {

    var activePicture by remember { mutableStateOf<Bitmap?>(null) }

    if (pictures.isEmpty()) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Feels Empty Here...Take a Picture")
        }
    } else {
        Text(
            text = category,
            modifier = Modifier
                .padding(16.dp),
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily
        )
        Divider()
        LazyRow {
            items(pictures) { picture ->
                PictureBox(picture, onClick = { activePicture = picture })
            }
        }
    }
    // If the active picture is selected then it will enlarge it for the size of the parent component.
    if (activePicture != null) {
        FullScreenImage(photo = pictures.first { it == activePicture!! },
            onDismiss = { activePicture = null })
    }
} // End of CategoryList Composable

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
@Composable
fun PictureBox(picture: Bitmap, onClick: () -> Unit) {
    Image(
        bitmap = picture.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
            .clickable { onClick() },
    )
}

@Composable
@Preview
fun DiaryScreenPreview() {
    DiaryScreen(onBackButtonClicked = {}, onTakePictureClicked = {})
}