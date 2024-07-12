package com.example.lieon.record.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordHistory")
data class RecordHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title : String,
    val time : String,
    val testResult: String,
    val filePath : String
)
