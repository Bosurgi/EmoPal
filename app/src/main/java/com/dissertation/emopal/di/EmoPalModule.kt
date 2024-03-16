package com.dissertation.emopal.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dissertation.emopal.data.DiaryPictureDao
import com.dissertation.emopal.data.DiaryPictureDatabase
import com.dissertation.emopal.data.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: A. La Fauci
 * @Date: 10/03/2024
 */

@Module
@InstallIn(SingletonComponent::class)
object EmoPalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DiaryPictureDatabase {
        return Room.databaseBuilder(
            appContext,
            DiaryPictureDatabase::class.java,
            "diary_picture_database"
        )
            // If the schema changes we can add migration logic here.
            .fallbackToDestructiveMigration()

            // If we have an asset to create the database from uncomment line below
            // .createFromAsset("YOUR_DATABASE_NAME.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: DiaryPictureDatabase): DiaryPictureDao {
        return database.diaryPictureDao
    }

    @Provides
    @Singleton
    fun provideRepository(pictureDao: DiaryPictureDao): ImageRepository {
        return ImageRepository(pictureDao)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

}