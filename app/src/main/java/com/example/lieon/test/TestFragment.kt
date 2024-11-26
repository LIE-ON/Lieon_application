package com.example.lieon.test

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lieon.R
import com.example.lieon.databinding.FragmentTestBinding
import com.example.lieon.record.view.RecordViewModel
import com.example.lieon.db.RecordHistoryEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@AndroidEntryPoint
class TestFragment : Fragment() {

    private var _binding : FragmentTestBinding? = null

    private val binding get() = _binding!!

    private val recordViewModel : RecordViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)

        binding.insertTempDataButton.setOnClickListener {
            lifecycleScope.launch {
                recordViewModel.insertRecord(
                    RecordHistoryEntity(
                        title = "test",
                        filePath = "/test",
                        testResult = "10%",
                        time = Date(System.currentTimeMillis()).toString(),
                    )
                )
            }
        }

        binding.deleteAllButton.setOnClickListener {
            lifecycleScope.launch {
                recordViewModel.deleteAllRecord()
            }
        }

        binding.insertListDataButton.setOnClickListener {
//            RecordResults.addItem(Result(1, "Item " + 1, Date(System.currentTimeMillis()), "70%","/test"))
        }

        binding.addLieDataButton1.setOnClickListener {
            val raw = R.raw.lie_source1
            val fileName = "lie_source1"
            lifecycleScope.launch {
                val uri = getUriFromRaw(requireActivity(), raw)
                val result = recordViewModel.getPredictionResult(uri)
                withContext(Dispatchers.Main){
                    recordViewModel.insertRecord(
                        RecordHistoryEntity(
                            title = fileName,
                            filePath = saveRawResourceToFile(requireActivity(),raw, fileName) ?: "null", // 변환된 .wav 파일 경로
                            testResult = result,
                            time = convertDateToFormattedDate(Date())
                        )
                    )
                }
            }
        }

        binding.addLieDataButton2.setOnClickListener {
            val raw = R.raw.lie_source2
            val fileName = "lie_source2"
            lifecycleScope.launch {
                val uri = getUriFromRaw(requireActivity(), raw)
                val result = recordViewModel.getPredictionResult(uri)
                withContext(Dispatchers.Main){
                    recordViewModel.insertRecord(
                        RecordHistoryEntity(
                            title = fileName,
                            filePath = saveRawResourceToFile(requireActivity(),raw, fileName) ?: "null", // 변환된 .wav 파일 경로
                            testResult = result,
                            time = convertDateToFormattedDate(Date())
                        )
                    )
                }
            }
        }

        binding.addLieDataButton3.setOnClickListener {
            val raw = R.raw.lie_source3
            val fileName = "lie_source3"
            lifecycleScope.launch {
                val uri = getUriFromRaw(requireActivity(), raw)
                val result = recordViewModel.getPredictionResult(uri)
                withContext(Dispatchers.Main){
                    recordViewModel.insertRecord(
                        RecordHistoryEntity(
                            title = fileName,
                            filePath = saveRawResourceToFile(requireActivity(),raw, fileName) ?: "null", // 변환된 .wav 파일 경로
                            testResult = result,
                            time = convertDateToFormattedDate(Date())
                        )
                    )
                }
            }
        }

        binding.addLieDataButton4.setOnClickListener {
            val raw = R.raw.lie_source4
            val fileName = "lie_source4"
            lifecycleScope.launch {
                val uri = getUriFromRaw(requireActivity(), raw)
                val result = recordViewModel.getPredictionResult(uri)
                withContext(Dispatchers.Main){
                    recordViewModel.insertRecord(
                        RecordHistoryEntity(
                            title = fileName,
                            filePath = saveRawResourceToFile(requireActivity(),raw, fileName) ?: "null", // 변환된 .wav 파일 경로
                            testResult = result,
                            time = convertDateToFormattedDate(Date())
                        )
                    )
                }
            }
        }

        binding.addLieDataButton5.setOnClickListener {
            val raw = R.raw.lie_source5
            val fileName = "lie_source5"
            lifecycleScope.launch {
                val uri = getUriFromRaw(requireActivity(), raw)
                val result = recordViewModel.getPredictionResult(uri)
                withContext(Dispatchers.Main){
                    recordViewModel.insertRecord(
                        RecordHistoryEntity(
                            title = fileName,
                            filePath = saveRawResourceToFile(requireActivity(),raw, fileName) ?: "null", // 변환된 .wav 파일 경로
                            testResult = result,
                            time = convertDateToFormattedDate(Date())
                        )
                    )
                }
            }
        }




        return binding.root
    }

    fun getUriFromRaw(context: Context, rawResId: Int): Uri {
        val uri = Uri.parse("android.resource://${context.packageName}/raw/$rawResId")
        return uri
    }

    private fun getFilePathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        var filePath: String? = null
        requireContext().contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                filePath = cursor.getString(columnIndex)
            }
        }
        return filePath
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                fileName = cursor.getString(columnIndex)
            }
        }
        return fileName
    }
    private fun convertDateToFormattedDate(date: Date) = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(date)

    private fun generateRandomString(): String {
        val alphabet = ('A'..'Z') + ('a'..'z')  // 대문자와 소문자 알파벳 목록
        val length = Random.nextInt(1, 7)  // 1에서 6 사이의 길이로 랜덤 설정
        return (1..length)
            .map { alphabet.random() }
            .joinToString("")
    }

    fun saveRawResourceToFile(context: Context, @RawRes resId: Int, fileName : String): String? {
        // 저장할 파일의 경로 설정
        val outputFile = File(context.filesDir, fileName)

        try {
            // inputStream: raw 리소스를 읽기 위한 스트림
            val inputStream = context.resources.openRawResource(resId)
            // outputStream: 저장할 경로로 파일을 쓰기 위한 스트림
            val outputStream = FileOutputStream(outputFile)

            // raw 파일을 outputStream으로 복사
            inputStream.copyTo(outputStream)

            // 스트림을 닫기
            inputStream.close()
            outputStream.close()

            // 파일 경로 반환
            return outputFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}