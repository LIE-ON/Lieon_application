package com.example.lieon.result.view

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.lieon.R
import com.example.lieon.databinding.FragmentResultDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ResultDetailFragment : Fragment() {

    private var _binding : FragmentResultDetailBinding? = null

    private val binding get() = _binding!!
    private val resultDetailViewModel : ResultDetailViewModel by viewModels()

    private var mediaPlayer : MediaPlayer? = null
    private var isPlaying = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultDetailBinding.inflate(layoutInflater)

        arguments?.let {
            val selectedId = it.getInt("selectedId")
            Toast.makeText(requireContext(), "$selectedId", Toast.LENGTH_SHORT).show()
            resultDetailViewModel.setSelectedId(selectedId)

            CoroutineScope(Dispatchers.IO).launch {
                val recordHistoryEntity = resultDetailViewModel.searchResult(selectedId)
                recordHistoryEntity?.let {
                    withContext(Dispatchers.Main) {
                        val filePath = it.filePath
                        Log.d("ResultDetailFragment", "$filePath")
                        initializeMediaPlayer(filePath)
                    }
                }
            }

        }

        binding.playButton.setOnClickListener {
            if (mediaPlayer != null && !isPlaying) {
                mediaPlayer?.start()
                isPlaying = true
                binding.playButton.text = "일시정지"
            } else {
                mediaPlayer?.pause()
                isPlaying = false
                binding.playButton.text = "재생"
            }
        }

//        binding.stopButton.setOnClickListener {
//            stopAndResetMediaPlayer()
//        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return binding.root
    }
    private fun initializeMediaPlayer(filePath: String) {
        Log.d("FilePath", "Initializing MediaPlayer with path: $filePath")
        mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            binding.seekBar.max = duration
            setOnCompletionListener {
                stopAndResetMediaPlayer()
            }
        }
    }

    private fun stopAndResetMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
            binding.playButton.text = "재생"
            binding.seekBar.progress = 0
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        stopAndResetMediaPlayer()
        _binding = null
    }

}