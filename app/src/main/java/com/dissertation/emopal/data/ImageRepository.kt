package com.dissertation.emopal.data

import javax.inject.Inject

/**
 *  Repository to manage the pictures in the database.
 *  Injecting the DAO constructor using Dependency Injection managed by Dagger Hilt.
 *  @property pictureDao The DAO for the images.
 */
class ImageRepository @Inject constructor(private val pictureDao: DiaryPictureDao) {

    /**
     * Gets all the pictures from the database.
     */
    fun getAllPictures(): List<DiaryPictureModel> = pictureDao.getAllPictures()

    /**
     * Inserts a picture in the database.
     */
    suspend fun insertPicture(picture: DiaryPictureModel) = pictureDao.insertPicture(picture)

    /**
     * Deletes a picture from the database.
     */
    suspend fun deletePicture(picture: DiaryPictureModel) = pictureDao.deletePicture(picture)

    /**
     * Updates a picture in the database.
     */
    suspend fun updatePicture(picture: DiaryPictureModel) = pictureDao.updatePicture(picture)

    fun getPicturesByEmotion(emotion: String): List<DiaryPictureModel> =
        pictureDao.getPicturesByEmotion(emotion)

    fun getPictureByPictureMetadata(pictureMetadata: BitmapMetadata): DiaryPictureModel =
        pictureDao.getPictureByPath(pictureMetadata.filePath)

}