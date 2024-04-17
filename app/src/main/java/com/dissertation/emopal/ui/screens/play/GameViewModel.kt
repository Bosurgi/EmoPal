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
import com.dissertation.emopal.util.ResponseParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    // User Picture
    private val _userPicture = MutableStateFlow<Bitmap?>(null)
    val userPicture: StateFlow<Bitmap?> get() = _userPicture

    // Current Emotion to Match
    private var _currentEmotion = MutableStateFlow<String>("")
    val currentEmotion: StateFlow<String> get() = _currentEmotion

    // User Emotion
    private val _userEmotion = MutableStateFlow<String>("")
    val userEmotion: StateFlow<String> get() = _userEmotion

    // Flag if the emotions match
    private val _isEmotionMatch = MutableStateFlow<Boolean?>(null)
    val isEmotionMatch: StateFlow<Boolean?> get() = _isEmotionMatch

    // Winner flag
    private val _isWinner = MutableStateFlow(false)
    val isWinner: StateFlow<Boolean> get() = _isWinner

    private lateinit var currentLevel: String
    private lateinit var allImages: List<GameImageModel>

    // Initialising the images for Level 1
    init {
        loadImagesForLevel("1")
    }

    /**
     * This function loads the images for the level passed as parameter.
     * @param level The level to load.
     */
    private fun loadImagesForLevel(level: String) {
        viewModelScope.launch {
            allImages = repository.getImagesByLevel(level)
            // TODO: Fix bug on first startup not loading pictures
            updatePrompt()
        }
    }

    /**
     * This function updates the prompt with a random image from the database.
     */

    private fun updatePrompt() {
        // TODO: Remove image already taken
        val randomImage = allImages.random()
        val randomImageName = randomImage.pictureName
        val emotion = randomImage.pictureEmotion
        val bitmap = loadImageFromAsset(randomImageName)
        _prompt.value = bitmap
        _currentEmotion.value = emotion
    }

    /**
     * This function loads an image from the assets folder.
     * @param imageName The name of the image to load.
     * @return The image as a Bitmap.
     */
    private fun loadImageFromAsset(imageName: String): Bitmap? {
        return try {
            val inputStream = applicationContext.assets.open(imageName)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun takePicture(userBitmap: Bitmap) {
        // TODO: Implement Logic when picture is taken
        viewModelScope.launch {
            val userEmotionResult = getUserEmotion(userBitmap)
            _userEmotion.value = userEmotionResult

            val isMatch = _currentEmotion.value.equals(userEmotionResult, ignoreCase = true)
            _isEmotionMatch.value = isMatch

            // Increment Counter if match
            if (isMatch) {
                incrementCounter()
            }

        }
    }

    private suspend fun getUserEmotion(bitmap: Bitmap): String {
        // API CALL //
        // Processing the Emotion of the picture
        val featureType = "FACE_DETECTION"
        val maxResults = 1
        val response = withContext(Dispatchers.IO) {
            // Calling the Vision API from different Thread
            visionClient.annotateImage(bitmap, featureType, maxResults)
        }
        return ResponseParser.parseResponse(response)
    }

    /**
     * This function increments the counter and checks if the player has won.
     */
    private fun incrementCounter() {
        _counter.value += 1
        if (_counter.value == 10) {
            _isWinner.value = true
        }
    }


}