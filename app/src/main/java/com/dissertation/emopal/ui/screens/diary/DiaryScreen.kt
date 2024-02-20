package com.dissertation.emopal.ui.screens.diary

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Diary Screen Composable which contains the list of emotions and the images.
 */
@Composable
fun DiaryScreen() {
    val emotions = listOf("Happy", "Sad", "Angry", "Surprise")
    DiaryBody(emotions)
}

/**
 * Diary Body Composable which contains the composable for the list of emotions and the images.
 * This contains a [LazyColumn] which allows to have a scrollable list of categories,
 * followed by the images related to such category.
 * @param emotions List of emotions to be displayed.
 */
// TODO: To be changed using the Database and ViewModel with real pictures.
@Composable
fun DiaryBody(emotions: List<String>) {
    LazyColumn {
        items(emotions.size) { index ->
            CategoryList(emotions[index])
            Divider()
        }
    }
}

/**
 * Category List Composable which contains the category name and the images related to such category.
 * @param category Category name to be displayed.
 */
@Composable
fun CategoryList(category: String) {
    Text(
        text = category,
        modifier = Modifier
            .padding(16.dp),
        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        fontFamily = MaterialTheme.typography.headlineLarge.fontFamily
    )
    Divider()
    LazyRow {
        item {
            for (i in 1..10) {
                Text(
                    text = "Image $i ",
                    modifier =
                    Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
            } // End of for loop
        } // End of LazyRow item
    }

}

@Composable
@Preview
fun DiaryScreenPreview() {
    DiaryScreen()
}