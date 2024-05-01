package com.dissertation.emopal.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * The data class to store the Bitmap and the file path of the image.
 * This is useful to delete picture and for future reference to the image.
 * @Author: A. La Fauci
 * @Date: 27/04/2024
 */
data class BitmapMetadata(
    val bitmap: Bitmap,
    val filePath: String
)

/**
 * Helper method that decodes a Bitmap from a file path.
 */
fun decodeBitmapFromFilePath(filePath: String): Bitmap {
    return BitmapFactory.decodeFile(filePath)
}
