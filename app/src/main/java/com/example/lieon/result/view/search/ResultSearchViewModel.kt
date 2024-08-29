package com.example.lieon.result.view.search

import androidx.lifecycle.ViewModel
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ResultSearchViewModel(
    private val recordRepository: RecordRepository
) : ViewModel(){
    suspend fun searchResultByTitle(title : String) : List<RecordHistoryEntity>{
        return recordRepository.searchRecordHistoryByTitle(title)
    }
}