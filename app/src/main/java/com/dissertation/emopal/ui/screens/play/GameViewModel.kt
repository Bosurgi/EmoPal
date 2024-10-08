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

    // Current Level
    private val _currentLevel = MutableStateFlow("")
    val currentLevel: StateFlow<String> get() = _currentLevel

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

    private lateinit var allImages: List<GameImageModel>

    /**
     * This function loads the images for the level passed as parameter.
     * @param level The level to load.
     */
    fun loadImagesForLevel(level: String) {
        viewModelScope.launch {
            allImages = repository.getImagesByLevel(level)
            updatePrompt()
        }
    }

    /**
     * This function sets the current level.
     * @param level The level to set.
     */
    fun setCurrentLevel(level: String) {
        _currentLevel.value = level
    }

    /**
     * This function updates the prompt with a random image from the database.
     */

    fun updatePrompt() {
        if (!isWinner.value && allImages.isNotEmpty()) {
            // TODO: Remove image already taken
            val randomImage = allImages.random()
            val randomImageName = randomImage.pictureName
            val emotion = randomImage.pictureEmotion
            val bitmap = loadImageFromAsset(randomImageName)
            _prompt.value = bitmap
            _currentEmotion.value = emotion
        }
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

    /**
     * This function is called when the user takes a picture.
     * It processes the picture and checks if the emotion matches the user's emotion.
     * It also increments the counter if the emotions match.
     * @param userBitmap The picture taken by the user.
     */
    fun takePicture(userBitmap: Bitmap) {
        viewModelScope.launch {
            val userEmotionResult = getUserEmotion(userBitmap)
            _userEmotion.value = userEmotionResult

            val isMatch = _currentEmotion.value.equals(userEmotionResult, ignoreCase = true)
            _isEmotionMatch.value = isMatch

            // Increment Counter if match and counter is less than 10
            if (isMatch && counter.value < 10) {
                incrementCounter()
            }

        }
    }

    /**
     * This function gets the emotion of the user from the picture taken.
     * @param bitmap The picture taken by the user.
     * @return The emotion of the user as a String
     */
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

    /**
     * This function resets the game and re-loads the images for Level 1.
     */
    fun resetGame() {
        _counter.value = 0
        _isWinner.value = false
        loadImagesForLevel(currentLevel.value)
    }


}