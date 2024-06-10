package com.example.lieon.record

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioManager = AudioManager(getFilePath())
    }
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
//            audioManager!!.startRecord()
            startRecordTime = System.currentTimeMillis()
            recordViewModel.setStartRecordTime(startRecordTime!!)
        }

        binding.stopButton.setOnClickListener {
//            audioManager!!.stopRecord()
            endRecordTime = System.currentTimeMillis()
            recordViewModel.setEndRecordTime(endRecordTime!!)
        }
        return binding.root
    }

    private fun getFilePath(): FileDescriptor {
        val uri = createFileUri()
        val pfd = requireContext().contentResolver.openFileDescriptor(uri, "w")
            ?: throw IOException("Cannot open file descriptor for URI: $uri")
        return pfd.fileDescriptor
    }
    private fun createFileUri(): Uri {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "test")
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/RecordExample")
        }

        return requireContext().contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IOException("파일 경로 생성 오류 발생")
    }
}
