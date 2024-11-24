package com.example.lieon.record.data.source

import com.example.lieon.record.data.payload.LieDetectionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Part

interface LieDetectionSource {
    @Headers("Content-Type: application/json")
    @POST("test")
    suspend fun postRecordUpload(@Body audioFile: String): Response<LieDetectionResponse>
}