package com.dissertation.emopal.ui.screens.play

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dissertation.emopal.BuildConfig
import com.dissertation.emopal.data.GameImageModel
import com.dissertation.emopal.data.GameImageRepository
import com.dissertation.emopal.util.FaceEmotionClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 *
 * @Author: A. La Fauci
 * @Date: 15/04/2024
 */
@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameImageRepository,
    private val applicationContext: Context
) : ViewModel() {

    // Setting up the API key and the client
    private val apiKey = BuildConfig.API_KEY
    private val visionClient = FaceEmotionClient(apiKey)

    // Counter for correct matches
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> get() = _counter

    // Image prompt
    private val _prompt = MutableStateFlow<Bitmap?>(null)
    val prompt: StateFlow<Bitmap?> get() = _prompt

    // Winner flag
    private val _isWinner = MutableStateFlow(false)
    val isWinner: StateFlow<Boolean> get() = _isWinner

    private lateinit var currentLevel: String
    private lateinit var allImages: List<GameImageModel>

    init {
        loadImagesForLevel("level1")
    }

    private fun loadImagesForLevel(level: String) {
        viewModelScope.launch {
            allImages = repository.getImagesByLevel(level)
            updatePrompt()
        }
    }

    private fun updatePrompt() {
        val randomImageName = allImages.random().pictureName
        val bitmap = loadImageFromAsset(randomImageName)
        _prompt.value = bitmap
    }

    private fun loadImageFromAsset(imageName: String): Bitmap? {
        return try {
            val inputStream = applicationContext.assets.open(imageName)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun incrementCounter() {
        _counter.value += 1
        if (_counter.value == 5) {
            _isWinner.value = true
        }
    }


}