package com.dissertation.emopal.ui.components.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dissertation.emopal.ImageConverter
import com.dissertation.emopal.data.DiaryPictureModel
import com.dissertation.emopal.data.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * @Author: A. La Fauci
 * @Date: 09/03/2024
 */

@HiltViewModel
class CameraViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val imageConverter: ImageConverter = ImageConverter()


    fun savePicture(bitmap: Bitmap) =
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

            repository.insertPicture(diaryPictureModel)
        }
}