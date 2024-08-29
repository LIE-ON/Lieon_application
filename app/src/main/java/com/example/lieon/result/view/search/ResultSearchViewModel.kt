package com.example.lieon.result.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.db.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultSearchViewModel @Inject constructor(
     private val recordRepository: RecordRepository
) : ViewModel(){
    private val _searchResults = MutableLiveData<List<RecordHistoryEntity>>()
    val searchResults: LiveData<List<RecordHistoryEntity>> get() = _searchResults

    fun searchResultByTitle(title: String) {
        viewModelScope.launch {
            val results = recordRepository.searchRecordHistoryByTitle(title)
            _searchResults.value = results
        }
    }
    fun setSearchResultEmpty(){
        _searchResults.value = emptyList()
    }
}