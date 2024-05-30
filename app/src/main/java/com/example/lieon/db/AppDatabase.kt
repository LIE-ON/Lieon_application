package com.example.lieon.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecordHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordHistoryDao(): RecordHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "record_history_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
