package com.dissertation.emopal.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * The view model to get the pictures from the database and it allows to populate the diary screen.
 * @Author: A. La Fauci
 * @Date: 10/03/2024
 */

@HiltViewModel
class DiaryViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val _angryPictures = MutableStateFlow<List<Bitmap>>(emptyList())
    val angryPictures = _angryPictures.asStateFlow()

    private val _sadPictures = MutableStateFlow<List<Bitmap>>(emptyList())
    val sadPictures = _sadPictures.asStateFlow()

    private val _happyPictures = MutableStateFlow<List<Bitmap>>(emptyList())
    val happyPictures = _happyPictures.asStateFlow()

    private val _surprisedPictures = MutableStateFlow<List<Bitmap>>(emptyList())
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
                val bitmap = BitmapFactory.decodeFile(picture.picturePath)
                when (picture.pictureEmotion) {
                    "angry" -> _angryPictures.value += bitmap
                    "sad" -> _sadPictures.value += bitmap
                    "happy" -> _happyPictures.value += bitmap
                    "surprised" -> _surprisedPictures.value += bitmap
                }
            }
        }
    }

    /**
     * It gets the pictures by emotion from the database and it populates the corresponding list.
     * // TODO: Not used at the moment.
     * @param emotion The emotion to search.
     */
    private fun getPicturesByEmotion(emotion: String) {
        viewModelScope.launch {
            val pictures = withContext(Dispatchers.IO) {
                repository.getPicturesByEmotion(emotion)
            }

            val bitmapList = mutableListOf<Bitmap>()

            pictures.forEach { item ->
                val bitmap = BitmapFactory.decodeFile(item.picturePath)
                bitmapList.add(bitmap)
            }
            // TODO: Use String resources for the emotions instead of hardcoded strings
            when (emotion) {
                "angry" -> _angryPictures.value += bitmapList
                "sad" -> _sadPictures.value += bitmapList
                "happy" -> _happyPictures.value += bitmapList
                "surprised" -> _surprisedPictures.value += bitmapList
            }
        }
    }

}