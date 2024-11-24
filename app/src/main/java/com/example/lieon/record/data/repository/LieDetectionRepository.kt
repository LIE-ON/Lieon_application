package com.example.lieon.record.data.repository

import com.example.lieon.record.data.payload.LieDetectionResponse
import com.example.lieon.record.data.source.LieDetectionSource
import okhttp3.MultipartBody
import javax.inject.Inject

class LieDetectionRepository @Inject constructor(
    private val lieDetectionSource: LieDetectionSource
) {
    suspend fun uploadAudio(audio: String): Result<LieDetectionResponse> {
        return kotlin.runCatching {
            val response = lieDetectionSource.postRecordUpload(audio)
            if (response.isSuccessful) {
                response.body() ?: throw RuntimeException("바디 없음")
            } else {
                throw RuntimeException("통신 실패: ${response.errorBody()?.string()}")
            }
        }
    }
}