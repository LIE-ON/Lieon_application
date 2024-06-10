package com.example.lieon.record.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordHistory")
data class RecordHistory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val startTime: String,
    val endTime: String,
    val result: String,
)
