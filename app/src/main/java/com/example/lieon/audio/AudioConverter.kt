package com.example.lieon.audio

import android.util.Log
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AudioConverter() {

    interface ConvertCallback {
        fun onConversionSuccess(outputFilePath: String)  // 변환 성공 시 호출
        fun onConversionFailure()  // 변환 실패 시 호출
    }

    fun convertToWav(recordFilePath: String, callback: ConvertCallback) {
        val outputPath = recordFilePath.replace(".m4a", ".wav")

        val command = arrayOf(
            "-i", recordFilePath,
            "-acodec", "pcm_s16le", // WAV 형식의 표준 코덱
            outputPath
        )

        FFmpeg.executeAsync(command) { executionId, returnCode ->
            if (returnCode == Config.RETURN_CODE_SUCCESS) {
                Log.d("FFmpeg", "Conversion successful!")
                callback.onConversionSuccess(outputPath)  // 변환 성공 시 콜백 호출
            } else if (returnCode == Config.RETURN_CODE_CANCEL) {
                Log.d("FFmpeg", "Conversion canceled.")
                callback.onConversionFailure()  // 변환 취소 시 콜백 호출
            } else {
                Log.d("FFmpeg", "Conversion failed with return code $returnCode.")
                callback.onConversionFailure()  // 변환 실패 시 콜백 호출
            }
        }
    }


}