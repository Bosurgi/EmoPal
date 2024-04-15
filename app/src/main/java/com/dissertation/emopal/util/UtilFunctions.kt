package com.dissertation.emopal.util

import android.graphics.Bitmap
import android.graphics.Matrix
import kotlin.math.ceil
import kotlin.math.ln
import kotlin.random.Random

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

/**
 * Function to generate a pseudo random number.
 * @param maxValue The maximum value of the random number.
 * @return the random number.
 */
fun generateRandomNumber(maxValue: Int): Int {
    return (0..maxValue).random()
}
