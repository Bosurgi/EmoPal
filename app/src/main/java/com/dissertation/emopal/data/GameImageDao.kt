package com.dissertation.emopal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Game Image Dao is the interface that contains the methods to interact with the database.
 * @author: A. La Fauci
 * @date: 15/04/2024
 */

@Dao
interface GameImageDao {

    /**
     * Selects all the images from the database.
     * @return The list of images.
     */
    @Query("SELECT * FROM game_images")
    fun getAllImages(): List<GameImageModel>

    /**
     * Inserts an image in the database.
     * @param gameImageModel The image to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(gameImageModel: GameImageModel)

    /**
     * Deletes all the images from the database.
     */
    @Delete
    suspend fun deletePicture(gameImageModel: GameImageModel)

    /**
     * Gets the images by level from the database.
     * @param level The level to search.
     */
    @Query("SELECT * FROM game_images WHERE level = :level")
    suspend fun getImagesByLevel(level: String): List<GameImageModel>
}
