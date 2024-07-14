package com.example.lieon.record.view

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lieon.databinding.FragmentRecordBinding
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.record.audio.AudioManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding: FragmentRecordBinding get() = _binding!!

    private var audioManager : AudioManager? = null

    private val recordViewModel : RecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)

        requestPermissions()

        var endRecordTime : Long? = null

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = recordViewModel

        binding.recordButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime()
            val uri = createFileUri()
            recordViewModel.setCurrentUri(uri)
            val fileDescriptor = getFileDescriptor(uri)
            audioManager?.startRecord(fileDescriptor)
            recordViewModel.setRecording(true)
            binding.chronometer.start()
        }

        binding.stopButton.setOnClickListener {
            val filePath = getFilePathFromUri(recordViewModel.getCurrentUri())!!
            audioManager?.stopRecord(filePath)
            endRecordTime = System.currentTimeMillis()
            recordViewModel.setEndRecordTime(endRecordTime!!)
            recordViewModel.setRecording(false)
            lifecycleScope.launch(Dispatchers.IO){
                recordViewModel.insertRecord(
                    RecordHistoryEntity(title = "test",
                        filePath = filePath,
                        testResult = "80%",
                        time = convertDateToFormattedDate(Date()))
                )
            }

            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
        }

        val spf = requireActivity().getSharedPreferences("LieonPrefs", Context.MODE_PRIVATE)

        recordViewModel.setGoalAccuracy(spf.getInt("goalAccuracy", 80))

        binding.accuracyGoalButton.setOnClickListener {
            val dialog = RecordAccuracyDialog(requireActivity())
            dialog.show()

            dialog.setOnDismissListener {
                recordViewModel.setGoalAccuracy(spf.getInt("goalAccuracy", 80))
                it.dismiss()
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chronometer = binding.chronometer

        chronometer.setOnChronometerTickListener {
            val elapsedMillis = SystemClock.elapsedRealtime() - it.base
            val minutes = (elapsedMillis / 1000) / 60
            val seconds = (elapsedMillis / 1000) % 60
            it.text = String.format("%02d:%02d", minutes, seconds)
        }
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
            audioManager = AudioManager()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getFileDescriptor(uri: Uri) = requireContext().contentResolver.openFileDescriptor(uri, "w")?.fileDescriptor
        ?: throw IOException("Cannot open file descriptor for URI: $uri")
    private fun createFileUri(): Uri {
        val timeStamp = convertDateToFormattedDate(Date())
        val fileName = "[Lieon] 녹음 파일_$timeStamp"

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/RecordExample")
        }

        return requireContext().contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IOException("파일 경로 생성 오류 발생")
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

    private fun convertDateToFormattedDate(date: Date) = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(date)

}
