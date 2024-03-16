package com.dissertation.emopal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity which represent the picture taken by the user.
 * The picture stores its absolute path and this class contains the info of the schema.
 * @Author: A. La Fauci
 * @Date: 02/03/2024
 */
@Entity(tableName = "diary_pictures")
data class DiaryPictureModel(
    @PrimaryKey(autoGenerate = true) val pictureId: Int = 0,
    val picturePath: String,
    val pictureDate: String,
    val pictureEmotion: String,
)

