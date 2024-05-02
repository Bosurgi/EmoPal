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

// Constant to store the prefix string of the level
private const val LEVEL_PREFIX = "level"

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
     */
    private suspend fun populateDatabase() {
        populateDatabasePerLevel(1)
        populateDatabasePerLevel(2)
        populateDatabasePerLevel(3)
    }

    /**
     * Populates the database with the images for the mini game contained in the assets directory.
     *
     */
    private suspend fun populateDatabasePerLevel(level: Int) {
        // Populating Database with images from assets directory
        val levelName = "$LEVEL_PREFIX$level"
        try {
            val assetManager: AssetManager = this.assets
            // Getting the list of images from the assets directory
            val levelFiles = assetManager.list(levelName)?.toList() ?: emptyList()
            // Listing the subfolders ('angry', 'happy', etc.)
            for (dirName in levelFiles) {
                val subFolderFiles =
                    assetManager.list("$levelName/$dirName")?.toList() ?: emptyList()
                // Listing the subfolder files ('angry1', 'happy2', etc.)
                for (fileName in subFolderFiles) {
                    val pictureName = "$levelName/$dirName/$fileName"
                    // Inserting the reference to the database
                    gameImageDao.insertImage(
                        GameImageModel(
                            pictureName = pictureName,
                            level = level.toString(),
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

