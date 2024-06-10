package com.example.lieon.record.audio

import android.util.Log
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg

class AudioConverter(private val recordFilePath : String) {

    fun convertToWav(){
        val outputPath = recordFilePath.replace(".m4a", ".wav")

        val command = arrayOf(
            "-i", recordFilePath,
            outputPath
        )

        FFmpeg.executeAsync(command) { executionId, returnCode ->
            if (returnCode == Config.RETURN_CODE_SUCCESS) {
                Log.d("FFmpeg", "Conversion successful!")
            } else if (returnCode == Config.RETURN_CODE_CANCEL) {
                Log.d("FFmpeg", "Conversion canceled.")
            } else {
                Log.d("FFmpeg", "Conversion failed with return code $returnCode.")
            }
        }
    }
}