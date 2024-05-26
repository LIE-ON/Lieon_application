package com.example.lieon.record

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.lieon.db.AppDatabase
import com.example.lieon.db.OfflineRecordRepository
import com.example.lieon.db.RecordRepository

class RecordViewModel(context: Context) : ViewModel() {
    private val recordRepository : RecordRepository by lazy {
        OfflineRecordRepository(AppDatabase.getDatabase(context).recordHistoryDao())
    }

    init {
        recordRepository
    }
}