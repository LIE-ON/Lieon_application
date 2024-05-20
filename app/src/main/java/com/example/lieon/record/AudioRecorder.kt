package com.example.lieon.record

import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import java.io.FileDescriptor
import java.io.IOException

class AudioRecorder(
     fileDescriptor: FileDescriptor
) {
    private var recorder: MediaRecorder? = null

    init {
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        recorder!!.setOutputFile(fileDescriptor)
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
            recorder!!.release()
            recorder = null
        }
    }

}