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
import java.io.IOException

class RecordFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this.requireActivity(), arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            , 100
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    private fun createFileUri() : Uri{
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "test")
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/RecordExample")

        val uri = requireContext().contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
        if (uri == null){
            throw IOException("파일 경로 생성 오류 발생")
        }
        return uri
    }

}