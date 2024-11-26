package com.example.lieon.record.domain

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import com.example.lieon.record.data.repository.LieDetectionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class InitLieDetectionUseCase @Inject constructor(
    private val lieDetectionRepository: LieDetectionRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun invoke(uri: Uri): String {
        val file = getFileFromUri(context, uri) ?: return "-1"

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val result = lieDetectionRepository.uploadAudio(body)

        return result.getOrNull()?.result.toString()
    }

    private fun convertFileToMultipart(file: File): MultipartBody.Part? {
        return try {
            val requestFile: RequestBody = RequestBody.create(
                "audio/wav".toMediaTypeOrNull(), // wav 파일의 미디어 타입
                file
            )
            MultipartBody.Part.createFormData("file", file.name, requestFile)  // 'file'은 서버에서 받을 파라미터 이름
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val fileName = getFileName(context, uri)
        val file = File(context.cacheDir, fileName)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream!!.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }

    private fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (it != null && it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result!!
    }
}