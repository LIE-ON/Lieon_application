package com.example.lieon.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {

    private val _startRecordTime = MutableLiveData<Long>()

    private val startRecordTime: LiveData<Long> get() = startRecordTime

    private val endRecordTime = MutableLiveData<Long>()

    private val _endRecordTime: LiveData<Long> get() = endRecordTime


    init {

    }
}
