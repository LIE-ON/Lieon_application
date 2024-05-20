package com.example.lieon.record

import android.media.MediaRecorder
import android.net.Uri
import java.io.IOException

class AudioRecorder(
    fileUri : Uri
) {
    private var recorder: MediaRecorder? = null
    private var isRecording = true;

    init {
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        recorder!!.setOutputFile(fileUri.path)
    }

    fun record(filePath: String) {
        try {
            recorder!!.prepare()
            recorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        if (recorder != null) {
            recorder!!.stop()
            recorder!!.release()
            recorder = null
        }
    }

    companion object {
    }

}