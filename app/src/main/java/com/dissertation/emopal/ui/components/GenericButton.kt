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

/**
 *
 * @Author: A. La Fauci
 * @Date: 15/04/2024
 */

@Composable
fun GenericButton(
    title: String,
    onClick: () -> Unit,
) {
    // BACK BUTTON //
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .sizeIn(minWidth = 124.dp, minHeight = 62.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Text(text = title, fontSize = 24.sp)
    }
}