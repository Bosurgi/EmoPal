package com.dissertation.emopal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * The Diary Picture Dao is the interface that contains the methods to interact with the database.
 * @Author: A.La Fauci
 * @Date: 02/03/2024
 */
@Dao
interface DiaryPictureDao {

    /**
     * Inserts a picture in the database.
     * @param diaryPictureModel The picture to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(diaryPictureModel: DiaryPictureModel)

    /**
     * Updates a picture in the database.
     * @param diaryPictureModel The picture to update.
     */
    @Update
    suspend fun updatePicture(diaryPictureModel: DiaryPictureModel)

    /**
     * Deletes a picture from the database.
     * @param diaryPictureModel The picture to delete.
     */
    @Delete
    suspend fun deletePicture(diaryPictureModel: DiaryPictureModel)

    /**
     * Gets all the pictures from the database.
     */
    @Query("SELECT * FROM diary_pictures")
    fun getAllPictures(): List<DiaryPictureModel>

}