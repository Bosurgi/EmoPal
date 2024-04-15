package com.dissertation.emopal.data

import androidx.room.Dao
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
    @Query("DELETE FROM game_images")
    suspend fun deleteAllImages()
}
