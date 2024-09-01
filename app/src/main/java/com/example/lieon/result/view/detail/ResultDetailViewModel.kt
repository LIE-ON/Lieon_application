package com.example.lieon.result.view.detail

import androidx.lifecycle.ViewModel
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResultDetailViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    private var _selectedId : Int = 0

    fun setSelectedId(id: Int){
        _selectedId = id
    }

    suspend fun searchRecordHistoryById(id: Int): RecordHistoryEntity? {
        return recordRepository.searchRecordHistoryById(id)
    }

    suspend fun updateRecordHistory(recordHistoryEntity: RecordHistoryEntity){
        recordRepository.updateRecordHistory(recordHistoryEntity)
    }

    fun getSelectedId(): Int {
        return _selectedId
    }
}