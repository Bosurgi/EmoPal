package com.dissertation.emopal.data

import javax.inject.Inject

/**
 *
 * @Author: A. La Fauci
 * @Date: 15/04/2024
 */
class GameImageRepository @Inject constructor(private val gameImageDao: GameImageDao) {

    /**
     * Selects all the images from the database.
     * @return The list of images.
     */
    fun getAllImages(): List<GameImageModel> {
        return gameImageDao.getAllImages()
    }

    /**
     * Inserts an image in the database.
     * @param gameImageModel The image to insert.
     */
    suspend fun insertImage(gameImageModel: GameImageModel) {
        gameImageDao.insertImage(gameImageModel)
    }

    /**
     * Deletes an image from the database.
     */
    suspend fun deletePicture(picture: GameImageModel) {
        gameImageDao.deletePicture(picture)
    }

    /**
     * Gets the images by level from the database.
     */
    suspend fun getImagesByLevel(level: String): List<GameImageModel> {
        return gameImageDao.getImagesByLevel(level)
    }
}