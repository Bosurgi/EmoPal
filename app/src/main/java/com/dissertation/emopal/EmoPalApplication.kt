package com.dissertation.emopal

import android.app.Application
import android.content.res.AssetManager
import com.dissertation.emopal.data.GameImageDao
import com.dissertation.emopal.data.GameImageModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


/**
 * @Author: A. La Fauci
 * @Date: 10/03/2024
 */

@HiltAndroidApp
class EmoPalApplication : Application() {
    @Inject
    lateinit var gameImageDao: GameImageDao

    override fun onCreate() {
        super.onCreate()
        // Initialize the game image dao
        CoroutineScope(Dispatchers.IO).launch {
            populateDatabase()
        }
    }

    /**
     * Populates the database with the images for the mini game contained in the assets directory.
     * // TODO: Change logic to populate the database with other level's images
     */
    private suspend fun populateDatabase() {
        // Populating Database with images from assets directory
        try {
            val assetManager: AssetManager = this.assets
            // Getting the list of images from the assets directory
            val level1Files = assetManager.list("level1")?.toList() ?: emptyList()
            for (dirName in level1Files) {
                val subFolderFiles = assetManager.list("level1/$dirName")?.toList() ?: emptyList()
                // Listing the subfolder files ('angry', 'happy', etc.)
                for (fileName in subFolderFiles) {
                    val pictureName = "level1/$dirName/$fileName"
                    // Inserting the reference to the database
                    gameImageDao.insertImage(
                        GameImageModel(
                            pictureName = pictureName,
                            level = "1",
                            pictureEmotion = dirName
                        )
                    )
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

