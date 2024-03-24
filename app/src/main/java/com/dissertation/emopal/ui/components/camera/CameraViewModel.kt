package com.dissertation.emopal.ui.components.camera

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dissertation.emopal.BuildConfig
import com.dissertation.emopal.data.DiaryPictureModel
import com.dissertation.emopal.data.ImageRepository
import com.dissertation.emopal.util.FaceEmotionClient
import com.dissertation.emopal.util.ResponseParser.Companion.parseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel for the CameraView which contains the logic to save the picture taken by the user.
 * @Author: A. La Fauci
 * @Date: 09/03/2024
 */

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val applicationContext: Context
) : ViewModel() {

    private val apiKey = BuildConfig.API_KEY
    private val visionClient = FaceEmotionClient(apiKey)

    /**
     * Function to save the picture taken by the user in the internal storage of the app.
     * @param bitmap: the picture taken by the user.
     */
    fun savePicture(bitmap: Bitmap) =
        viewModelScope.launch {

            // Getting the current date
            val currentDate =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                    Date()
                )
            val directoryName = "diary_images"

            var fileName = "IMG_$currentDate.png"

            val directory = File(applicationContext.filesDir, directoryName)

            // If the directory does not exist, it is created here
            if (!directory.exists()) {
                directory.mkdirs()
            }

            var file = File(directory, fileName)

            // If the file exists appending a new number to the file name
            var index = 1
            while (file.exists()) {
                fileName = "IMG_${currentDate}(${index++}).png"
                file = File(directory, fileName)
            }

            // Saving the bitmap as PNG
            FileOutputStream(file).use { output ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            }

            // Processing the Emotion of the picture
            val featureType = "FACE_DETECTION"
            val maxResults = 1
            val response = visionClient.annotateImage(bitmap, featureType, maxResults)
            val emotion = parseResponse(response)

            // Updating the database with the picture storing its path and the date of the picture
            val diaryPictureModel = DiaryPictureModel(
                picturePath = file.absolutePath,
                pictureDate = currentDate,
                pictureEmotion = emotion
            )

            repository.insertPicture(diaryPictureModel)
        }
}