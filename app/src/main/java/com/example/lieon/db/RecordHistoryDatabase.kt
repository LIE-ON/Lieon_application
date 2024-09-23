package com.example.lieon.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecordHistoryEntity::class], version = 1)
abstract class RecordHistoryDatabase : RoomDatabase() {
    abstract fun recordHistoryDao(): RecordHistoryDao

//    companion object {
//        @Volatile
//        private var INSTANCE: RecordHistoryDatabase? = null
//
//        fun getInstance(context: Context): RecordHistoryDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    RecordHistoryDatabase::class.java,
//                    "record_history_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}
