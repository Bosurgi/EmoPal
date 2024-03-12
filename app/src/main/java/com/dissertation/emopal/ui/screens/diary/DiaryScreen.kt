package com.dissertation.emopal.ui.screens.diary

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
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
@Composable
fun DiaryBody(
    emotions: List<String>,
    onBackButtonClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
    pictures: List<Bitmap>
) {

// Wrapping it in a Box to have the footer buttons at the bottom of the screen.
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {

            items(emotions.size) { index ->
                CategoryList(emotions[index], pictures)
                Divider()
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
                PictureBox(picture)
            }
        }
    }
} // End of CategoryList Composable

@Composable
fun PictureBox(picture: Bitmap) {
    Image(
        bitmap = picture.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp),
    )
}

@Composable
@Preview
fun DiaryScreenPreview() {
    DiaryScreen(onBackButtonClicked = {}, onTakePictureClicked = {})
}