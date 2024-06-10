package com.example.lieon.record.audio

import android.media.MediaRecorder
import android.util.Log
import java.io.FileDescriptor
import java.io.IOException

class AudioRecorder(
     fileDescriptor: FileDescriptor
) {
    private var recorder: MediaRecorder? = null
    init {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(fileDescriptor)
        }
    }
    fun record() {
        try {
            recorder!!.prepare()
            recorder!!.start()
            Log.d("recorder", "record start")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        if (recorder != null) {
            Log.d("recorder", "record stop")
            recorder!!.stop()
            recorder!!.reset()
        }
    }

}