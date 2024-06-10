package com.example.lieon.record

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lieon.databinding.FragmentRecordBinding
import com.example.lieon.record.audio.AudioManager
import java.io.FileDescriptor
import java.io.IOException

class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding: FragmentRecordBinding get() = _binding!!

    private var audioManager : AudioManager? = null

    private val recordViewModel : RecordViewModel by viewModels{
        RecordViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)

        requestPermissions()

        var startRecordTime : Long? = null
        var endRecordTime : Long? = null

        binding.recordButton.setOnClickListener {
            audioManager?.startRecord()
            startRecordTime = System.currentTimeMillis()
            recordViewModel.setStartRecordTime(startRecordTime!!)
        }

        binding.stopButton.setOnClickListener {
            audioManager?.stopRecord()
            endRecordTime = System.currentTimeMillis()
            recordViewModel.setEndRecordTime(endRecordTime!!)
        }
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                initAudioManager()
            } else {
                // Handle permission denial
            }
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permissions.all {
                ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
            }) {
            initAudioManager()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 100)
        }
    }

    private fun initAudioManager() {
        try {
            val uri = createFileUri()
            audioManager = AudioManager(getFilePathFromUri(uri)!!,getFileDescriptor(uri))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getFileDescriptor(uri: Uri) = requireContext().contentResolver.openFileDescriptor(uri, "w")?.fileDescriptor
        ?: throw IOException("Cannot open file descriptor for URI: $uri")
    private fun createFileUri(): Uri {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "abc")
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/RecordExample")
        }

        return requireContext().contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IOException("파일 경로 생성 오류 발생")
    }
    fun getFilePathFromUri(uri: Uri): String? {
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
}
