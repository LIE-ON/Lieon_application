package com.example.lieon.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordHistory")
data class RecordHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title : String,
    val time : String,
    val testResult: String,
    var filePath : String
)
