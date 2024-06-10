package com.example.lieon.record.audio

import android.os.Environment
import java.io.FileDescriptor

class AudioManager(
    private val fileDescriptor: FileDescriptor
) {
    private val outputFilePath : String = Environment.getExternalStorageDirectory().absolutePath + "/RecordExample"
    private var audioRecorder : AudioRecorder = AudioRecorder(fileDescriptor)
    private var audioConverter: AudioConverter = AudioConverter(outputFilePath)

    fun startRecord(){
        audioRecorder.record()
    }
    fun stopRecord(){
        audioRecorder.stop()
        audioConverter.convertToWav()
    }

    fun saveRecord(){

    }
}