package com.example.lieon.record.data.source

import com.example.lieon.record.data.response.LieDetectionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LieDetectionSource {
    @Multipart
    @POST("test")
    suspend fun postRecordUpload(@Part audioFile : MultipartBody.Part ) : Response<LieDetectionResponse>
}