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

    private val _pictures = MutableStateFlow<List<Bitmap>>(emptyList())
    val pictures = _pictures.asStateFlow()

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

            val bitmapList = mutableListOf<Bitmap>()

            pictures.forEach { item ->
                // Transforming the file into Bitmap and adding it to the list
                val bitmap = BitmapFactory.decodeFile(item.picturePath)
                bitmapList.add(bitmap)
            }
            _pictures.value += bitmapList
        }
    }

}