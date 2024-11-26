package com.example.lieon.audio

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.lieon.db.RecordRepository
import java.io.FileDescriptor
import java.io.InputStream
import javax.inject.Inject

class AudioManager (
) {
    private var audioRecorder : AudioRecorder = AudioRecorder()
    private var audioConverter: AudioConverter = AudioConverter()

    fun startRecord(fileDescriptor: FileDescriptor){
        audioRecorder.record(fileDescriptor)
        Log.d("record", "녹음 시작")
    }
    fun stopRecord(outputFilePath: String, callback: AudioConverter.ConvertCallback) {
        audioRecorder.stop()
        audioConverter.convertToWav(outputFilePath, callback)  // 변환 완료 후 콜백 호출
        Log.d("record", "녹음 중지")
    }


    fun saveRecord(){

    }
}