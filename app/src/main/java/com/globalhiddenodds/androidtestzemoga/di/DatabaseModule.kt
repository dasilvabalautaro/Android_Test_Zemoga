package com.globalhiddenodds.androidtestzemoga.di

import android.content.Context
import androidx.room.Room
import com.globalhiddenodds.androidtestzemoga.datasource.database.AppRoomDatabase
import com.globalhiddenodds.androidtestzemoga.repository.CommentDao
import com.globalhiddenodds.androidtestzemoga.repository.CompanyDao
import com.globalhiddenodds.androidtestzemoga.repository.UserCurrentDao
import com.globalhiddenodds.androidtestzemoga.repository.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Pattern design singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideUserDao(database: AppRoomDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideCommentDao(database: AppRoomDatabase): CommentDao {
        return database.commentDao()
    }

    @Provides
    fun provideUserCurrentDao(database: AppRoomDatabase): UserCurrentDao {
        return database.userCurrentDao()
    }

    @Provides
    fun provideCompanyDao(database: AppRoomDatabase): CompanyDao {
        return database.companyDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context):
            AppRoomDatabase{
        return Room.databaseBuilder(
            appContext,
            AppRoomDatabase::class.java,
            "android_test_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}