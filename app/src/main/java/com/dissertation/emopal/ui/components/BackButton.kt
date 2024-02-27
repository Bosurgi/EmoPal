package com.dissertation.emopal.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BackButton(onBackButtonClicked: () -> Unit) {

    // BACK BUTTON //
    Button(
        onClick = { onBackButtonClicked() },
        modifier = Modifier
            .sizeIn(minWidth = 124.dp, minHeight = 62.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Text(text = "Back", fontSize = 24.sp)
    }
}