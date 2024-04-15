package com.dissertation.emopal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity which represent the picture available for the mini game.
 * The picture stores its absolute path and this class contains the info of the schema.
 * It also stores the level which it belongs to.
 * @Author: A. La Fauci
 * @Date: 10/04/2024
 */
@Entity(tableName = "game_images")
data class GameImageModel(
    @PrimaryKey(autoGenerate = true) val pictureId: Int = 0,
    val picturePath: String,
    val level: String,
    val pictureEmotion: String,
)