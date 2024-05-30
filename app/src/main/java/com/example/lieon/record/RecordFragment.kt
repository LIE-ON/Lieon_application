package com.example.lieon.record

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.lieon.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding: FragmentRecordBinding get() = _binding!!

    private var audioRecorder : AudioRecorder? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        ActivityCompat.requestPermissions(
            this.requireActivity(), arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ), 100
        )

        var startRecordTime : Long? = null
        var endRecordTime : Long? = null


        binding.recordButton.setOnClickListener {
//            recorder.record()
            startRecordTime = System.currentTimeMillis()
        }

        binding.stopButton.setOnClickListener {
//            recorder.stop()
            endRecordTime = System.currentTimeMillis()
        }

        binding.stopButton.setOnClickListener {
        }


        return binding.root
    }
}
//
//    private fun startRecording() {
//        audioRecorder = AudioRecorder(getFilePath())
//        createFileUri().path?.let { it -> audioRecorder!!.record() }
//        binding.recordButton.isEnabled = false
//        binding.stopButton.isEnabled = true
//    }

//    private fun stopRecording() {
//        audioRecorder?.stop()
//        audioRecorder = null
//        binding.recordButton.isEnabled = true
//        binding.stopButton.isEnabled = false
//        saveRecordHistory()
//    }
//
//    private fun getFilePath(): FileDescriptor {
//        val uri = createFileUri()
//        val pfd = requireContext().contentResolver.openFileDescriptor(uri, "w")
//            ?: throw IOException("Cannot open file descriptor for URI: $uri")
//        return pfd.fileDescriptor
//    }
//
//    private fun createFileUri(): Uri {
//        val values = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, "test")
//            put(MediaStore.MediaColumns.MIME_TYPE, "audio/3gpp")
//            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/RecordExample")
//        }
//
//        return requireContext().contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
//            ?: throw IOException("파일 경로 생성 오류 발생")
//    }
//
//    private fun createAudioRecorder(){
//    }
//
//    private fun file() : File {
//        return File(Environment.getExternalStorageDirectory(), "demo.wav");
//    }
//
//    private fun observeRecordHistory() {
//        lifecycleScope.launch {
//            database.recordHistoryDao().getAllRecordHistories().collect { recordHistories ->
//                // Update UI with record histories
//                // For example: binding.historyTextView.text = recordHistories.joinToString("\n")
//            }
//        }
//    }

