package com.dissertation.emopal.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The database for the diary pictures.
 * It contains the reference to the DAO and the schema.
 * @Author: A. La Fauci
 * @Date: 02/03/2024
 */

//@TypeConverters(ImageConverter::class)
@Database(
    entities = [DiaryPictureModel::class],
    version = 1,
    exportSchema = false
)
abstract class DiaryPictureDatabase : RoomDatabase() {

    abstract val diaryPictureDao: DiaryPictureDao

    // NOTE: The code below is commented out because of using Dependency Injection with Dagger Hilt
//    companion object {
//        /**
//         * The instance of the database, it keeps the reference of the database maintaining only one.
//         */
//        @Volatile
//        private var INSTANCE: DiaryPictureDatabase? = null
//
//        /**
//         * Gets the database instance.
//         * @param context The context.
//         * @return The database instance.
//         */
//        fun getDatabase(context: Context): DiaryPictureDatabase {
//            // If the instance is not null, returns it, otherwise it creates the database.
//            return INSTANCE ?: synchronized(this) {
//                Room.databaseBuilder(
//                    context,
//                    DiaryPictureDatabase::class.java,
//                    "diary_picture_database"
//                )
//                    // If the schema changes we can add migration logic here.
//                    .fallbackToDestructiveMigration()
//
//                    // If we have an asset to create the database from uncomment line below
//                    // .createFromAsset("YOUR_DATABASE_NAME.db")
//
//                    // Building the database
//                    .build()
//                    // Keep the reference to the created database.
//                    .also { INSTANCE = it }
//
//            } // End of Synchronised
//
//        } // End of Method
//
//    } // End of Companion Object

} // End of Class