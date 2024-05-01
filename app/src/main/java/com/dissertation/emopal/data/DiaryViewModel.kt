package com.dissertation.emopal.data

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

/**
 * The view model to get the pictures from the database and it allows to populate the diary screen.
 * @Author: A. La Fauci
 * @Date: 10/03/2024
 */

@HiltViewModel
class DiaryViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val _angryPictures = MutableStateFlow<List<BitmapMetadata>>(emptyList())
    val angryPictures = _angryPictures.asStateFlow()

    private val _sadPictures = MutableStateFlow<List<BitmapMetadata>>(emptyList())
    val sadPictures = _sadPictures.asStateFlow()

    private val _happyPictures = MutableStateFlow<List<BitmapMetadata>>(emptyList())
    val happyPictures = _happyPictures.asStateFlow()

    private val _surprisedPictures = MutableStateFlow<List<BitmapMetadata>>(emptyList())
    val surprisedPictures = _surprisedPictures.asStateFlow()

    init {
        getPictures()
    }

    // Getter for all pictures in the database
    private fun getPictures() {
        viewModelScope.launch {
            // It calls the suspended function without interrupting the thread.
            val pictures = withContext(Dispatchers.IO) {
                repository.getAllPictures()
            }
            // Categorising the pictures and populating the lists
            pictures.forEach { picture ->
                // Transforming the file into Bitmap and adding it to the list
                val bitmap = decodeBitmapFromFilePath(picture.picturePath)
                val bitmapMetadata = BitmapMetadata(bitmap, picture.picturePath)

                when (picture.pictureEmotion) {
                    "angry" -> _angryPictures.value += bitmapMetadata
                    "sad" -> _sadPictures.value += bitmapMetadata
                    "happy" -> _happyPictures.value += bitmapMetadata
                    "surprised" -> _surprisedPictures.value += bitmapMetadata
                }
            }
        }
    }

    /**
     * It deletes a picture from the database and from the corresponding list.
     * @param category The category of the picture.
     * @param picture The picture to delete.
     */
    fun deletePicture(category: String, picture: Bitmap) {
        viewModelScope.launch {
            val pictureToDelete: BitmapMetadata? = when (category) {
                "Angry" -> _angryPictures.value.find { it.bitmap == picture }
                "Sad" -> _sadPictures.value.find { it.bitmap == picture }
                "Happy" -> _happyPictures.value.find { it.bitmap == picture }
                "Surprised" -> _surprisedPictures.value.find { it.bitmap == picture }
                else -> null
            }

            when (category) {
                "Angry" -> _angryPictures.value =
                    _angryPictures.value.filterNot { it.bitmap == picture }

                "Sad" -> _sadPictures.value = _sadPictures.value.filterNot { it.bitmap == picture }
                "Happy" -> _happyPictures.value =
                    _happyPictures.value.filterNot { it.bitmap == picture }

                "Surprised" -> _surprisedPictures.value =
                    _surprisedPictures.value.filterNot { it.bitmap == picture }
            }
            // Delete the picture from the database using the metadata represented by its path
            pictureToDelete?.let { bitmapMetadata ->
                val pictureModel = withContext(Dispatchers.IO) {
                    repository.getPictureByPictureMetadata(bitmapMetadata)
                }
                repository.deletePicture(pictureModel)
                // Delete the picture from the file system
                try {
                    val pictureFile = File(bitmapMetadata.filePath)
                    pictureFile.delete()
                } catch (exception: Exception) {
                    Log.e("DiaryViewModel", "Error deleting the picture: $exception")
                }
            }
        }
    }

    /**
     * It gets the pictures by emotion from the database and it populates the corresponding list.
     * // TODO: Not used at the moment.
     * @param emotion The emotion to search.
     */
//    private fun getPicturesByEmotion(emotion: String) {
//        viewModelScope.launch {
//            val pictures = withContext(Dispatchers.IO) {
//                repository.getPicturesByEmotion(emotion)
//            }
//
//            val bitmapList = mutableListOf<Bitmap>()
//
//            pictures.forEach { item ->
//                val bitmap = BitmapFactory.decodeFile(item.picturePath)
//                bitmapList.add(bitmap)
//            }
//            // TODO: Use String resources for the emotions instead of hardcoded strings
//            when (emotion) {
//                "angry" -> _angryPictures.value += bitmapList
//                "sad" -> _sadPictures.value += bitmapList
//                "happy" -> _happyPictures.value += bitmapList
//                "surprised" -> _surprisedPictures.value += bitmapList
//            }
//        }
//    }

}