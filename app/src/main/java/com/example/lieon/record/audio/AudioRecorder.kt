package com.example.lieon.record.audio

import android.media.MediaRecorder
import android.util.Log
import java.io.FileDescriptor
import java.io.IOException
import java.lang.IllegalStateException

class AudioRecorder(
     private val fileDescriptor: FileDescriptor
) {
    private var recorder: MediaRecorder? = null

    fun record() {
        try {
            setRecorder()
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

    private fun setRecorder(){
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(fileDescriptor)
        }
    }

}