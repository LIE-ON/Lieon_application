package com.example.lieon.record.audio

import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.FileDescriptor

class AudioManager(
    private val outputFilePath : String,
    private val fileDescriptor: FileDescriptor
) {
    //private val outputFilePath : String = Environment.DIRECTORY_MUSIC + "/RecordExample"
    private var audioRecorder : AudioRecorder = AudioRecorder(fileDescriptor)
    private var audioConverter: AudioConverter = AudioConverter(outputFilePath)

    fun startRecord(){
        audioRecorder.record()
        Log.d("record", "녹음 시작")
    }
    fun stopRecord(){
        audioRecorder.stop()
        audioConverter.convertToWav()
        Log.d("record", "녹음 중지")
    }

    fun saveRecord(){

    }
}