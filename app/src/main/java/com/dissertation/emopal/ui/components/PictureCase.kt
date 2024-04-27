package com.dissertation.emopal.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dissertation.emopal.ui.screens.diary.FullScreenImage
import com.dissertation.emopal.ui.screens.diary.PictureBox

/**
 * @Author: A. La Fauci
 * @Date: 16/03/2024
 */

@Composable
fun PictureCase(pictures: List<Bitmap>, category: String) {

    var activePicture by remember { mutableStateOf<Bitmap?>(null) }
    var haptics = LocalHapticFeedback.current
    var contextMenuPhoto by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    if (pictures.isEmpty()) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CategoryHeader(category = category)
        }
        Divider()

        Text("Feels Empty Here...Take a Picture")

    } else {
        CategoryHeader(category = category)
        Divider()
        LazyRow {
            items(pictures) { picture ->
                PictureBox(
                    picture,
                    onClick = { activePicture = picture },
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        contextMenuPhoto = picture
                    }
                )
            }
        }
    }
    // If the active picture is selected then it will enlarge it for the size of the parent component.
    if (activePicture != null) {
        FullScreenImage(photo = pictures.first { it == activePicture!! },
            onDismiss = { activePicture = null })
    }

    if (contextMenuPhoto != null) {
        PhotoActionsSheet(
            photo = contextMenuPhoto!!,
            onDismissSheet = { contextMenuPhoto = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoActionsSheet(
    @Suppress("UNUSED_PARAMETER") photo: Bitmap,
    onDismissSheet: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissSheet
    ) {
        ListItem(
            headlineContent = { Text("Delete") },
            leadingContent = { Icon(Icons.Default.DeleteOutline, null) }
        )
    }
}

@Composable
@Preview
fun PictureCasePreview() {
    PictureCase(pictures = emptyList(), category = "Happy")
}

/**
 * Category Header Composable which displays the category in a header style.
 * @param category the category to be displayed.
 */
@Composable
fun CategoryHeader(category: String) {
    Text(
        text = category,
        modifier = Modifier
            .padding(16.dp),
        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        fontFamily = MaterialTheme.typography.headlineLarge.fontFamily
    )
}