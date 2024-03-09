package com.dissertation.emopal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.nio.ByteBuffer
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * @Author: A. La Fauci
 * @Date: 09/03/2024
 */
class ImageConverter {
    @OptIn(ExperimentalEncodingApi::class)
//    @TypeConverter
    fun bitmapToBase64(bitmap: Bitmap): ByteArray {
        // Byte Buffer to store the bitmap
        val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        // Need to copy the pixels from the bitmap to the byte buffer
        bitmap.copyPixelsFromBuffer(byteBuffer)
        // Converting the byte buffer to a byte array
//        val byteArray = byteBuffer.array()
        // Returning the byte array as a string
//        return Base64.encode(byteArray, DEFAULT_BUFFER_SIZE)
        return byteBuffer.array()
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String: String): Bitmap {
        // Decoding the base64 string to a byte array
        val byteArray = Base64.decode(base64String, DEFAULT_BUFFER_SIZE)
        // Creating a bitmap from the byte buffer
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}