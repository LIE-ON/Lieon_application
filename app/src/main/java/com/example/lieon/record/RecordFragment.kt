package com.example.lieon.record

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.lieon.R
import com.example.lieon.databinding.FragmentRecordBinding
import java.io.FileDescriptor
import java.io.IOException

class RecordFragment : Fragment() {

    private var _binding : FragmentRecordBinding? = null
    private val binding : FragmentRecordBinding get() = _binding!!

    private var audioRecorder : AudioRecorder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater,container,false)
        ActivityCompat.requestPermissions(
            this.requireActivity(), arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            , 100
        )

        binding.recordButton.setOnClickListener {
            audioRecorder = AudioRecorder(getFilePath())
            createFileUri().path?.let { it -> audioRecorder!!.record() }
        }

        binding.stopButton.setOnClickListener {
            audioRecorder!!.stop()
            audioRecorder = null
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun getFilePath(): FileDescriptor {
        val uri = createFileUri();

        val pfd = requireContext().contentResolver.openFileDescriptor(uri,"w")
            ?: throw IOException("Cannot open file descriptor for URI : " + uri)
        return pfd.fileDescriptor
    }

    private fun createFileUri() : Uri{
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "test")
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/RecordExample")

        val uri = requireContext().contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IOException("파일 경로 생성 오류 발생")
        return uri
    }


}