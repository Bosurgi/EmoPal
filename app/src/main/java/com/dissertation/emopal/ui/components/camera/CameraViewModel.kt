package com.dissertation.emopal.ui.components.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dissertation.emopal.ImageConverter
import com.dissertation.emopal.data.DiaryPictureDao
import com.dissertation.emopal.data.DiaryPictureModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @Author: A. La Fauci
 * @Date: 09/03/2024
 */
class CameraViewModel : ViewModel() {

    // TODO: Create Repository to implement the DAO
    private val diaryPictureDao: DiaryPictureDao = TODO()
    private val imageConverter: ImageConverter = ImageConverter()

    fun savePicture(bitmap: Bitmap) {
        viewModelScope.launch {
            val convertedImage = imageConverter.bitmapToBase64(bitmap)
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()
            )
            val diaryPictureModel = DiaryPictureModel(
                pictureData = convertedImage,
                pictureDate = currentDate,
                pictureEmotion = ""
            )

            diaryPictureDao.insertPicture(diaryPictureModel)
        }
    }
}