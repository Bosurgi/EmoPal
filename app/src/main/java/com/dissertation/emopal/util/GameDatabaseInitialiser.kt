package com.dissertation.emopal.util


import android.content.Context
import android.content.res.AssetManager
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dissertation.emopal.data.GameImageDao
import com.dissertation.emopal.data.GameImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Provider

/**
 *
 * The Game Database Initialiser is the class that initialises the database.
 * It is used to populate the database with the images for the mini game.
 * @Author: A. La Fauci
 * @Date: 15/04/2024
 */

class GameDatabaseInitialiser(
    private val context: Context,
    private val gameImageProvider: Provider<GameImageDao>
) : RoomDatabase.Callback() {

    // The application scope to be used.
    private val appScope = CoroutineScope(SupervisorJob())

    // Overriding the onCreate method to populate the database on creation.
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        appScope.launch(Dispatchers.IO) {
            // Populating the database
            populateDatabase()
        }
    }

    /**
     * Populates the database with the images for the mini game contained in the assets directory.
     */
    private suspend fun populateDatabase() {
        val gameImageDao = gameImageProvider.get()
        // Populating Database with images from assets directory
        try {
            val assetManager: AssetManager = context.assets
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