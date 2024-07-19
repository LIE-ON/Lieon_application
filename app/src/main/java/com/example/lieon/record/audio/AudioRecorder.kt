package com.example.lieon.record.audio

import android.media.MediaRecorder
import android.util.Log
import java.io.FileDescriptor
import java.io.IOException
import java.lang.IllegalStateException

class AudioRecorder() {
    private var recorder: MediaRecorder? = null
    fun record(fileDescriptor: FileDescriptor) {
        try {
            setRecorder(fileDescriptor)
            recorder?.apply {
                prepare()
                start()
            }
            Log.d("recorder", "record start")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e : IllegalStateException){
            e.printStackTrace()
        }
    }

    fun stop() {
        recorder?.apply {
            try{
                stop()
            } catch (e : IllegalStateException){
                e.printStackTrace()
            } finally {
                reset()
                release()
                recorder = null
            }
        }
    }

    private fun setRecorder(fileDescriptor: FileDescriptor){
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(fileDescriptor)
        }
    }

}