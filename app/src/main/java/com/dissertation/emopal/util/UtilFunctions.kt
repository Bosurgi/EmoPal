package com.dissertation.emopal.util

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * Kotlin Class with eventual Helper Functions
 * @Author: A. La Fauci
 * @Date: 14/03/2024
 */

/**
 * Function to rotate a bitmap once the picture is taken.
 * As by default the camera takes the picture in landscape mode, the bitmap needs to be rotated.
 * @param degree The degree to rotate the bitmap.
 * @return the rotated bitmap.
 */
fun Bitmap.rotateBitmap(degree: Int): Bitmap {
    val matrix = Matrix().apply {
        postRotate(-degree.toFloat())
        postScale(-1f, -1f)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}