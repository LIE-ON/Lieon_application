package com.example.lieon.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RecordHistoryDataModule {

    @Provides
    fun provideRecordHistoryDao(db : RecordHistoryDatabase)
        = db.recordHistoryDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : RecordHistoryDatabase
        = Room.databaseBuilder(context, RecordHistoryDatabase::class.java, "record_history.db").build()
}