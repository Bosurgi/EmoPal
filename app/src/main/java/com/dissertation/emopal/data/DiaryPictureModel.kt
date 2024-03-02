package com.dissertation.emopal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity which represent the picture taken by the user.
 * The picture is stored as a ByteArray and this class contains the info of the schema.
 * @Author: A. La Fauci
 * @Date: 02/03/2024
 */
@Entity(tableName = "diary_pictures")
data class DiaryPictureModel(
    @PrimaryKey(autoGenerate = true) val pictureId: Int = 0,
    val pictureData: ByteArray,
    val pictureDate: String,
    val pictureEmotion: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiaryPictureModel

        return pictureData.contentEquals(other.pictureData)
    }

    override fun hashCode(): Int {
        return pictureData.contentHashCode()
    }
}
