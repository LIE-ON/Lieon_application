package com.example.lieon.result.view.detail

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
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

    private val updateSeekBarHandler = android.os.Handler()
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                val currentPosition = it.currentPosition
                binding.seekBar.progress = currentPosition
                binding.recordingLength.text = formatDuration(currentPosition)
                updateSeekBarHandler.postDelayed(this, 100) // Update every second
            }
        }
    }

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
                val recordHistoryEntity = resultDetailViewModel.searchRecordHistoryById(selectedId)
                recordHistoryEntity?.let {
                    withContext(Dispatchers.Main) {
                        binding.recordName.text = it.title
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
                updateSeekBarHandler.post(updateSeekBarRunnable)
            } else {
                mediaPlayer?.pause()
                isPlaying = false
                binding.playButton.text = "재생"
                updateSeekBarHandler.removeCallbacks(updateSeekBarRunnable)
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
        mediaPlayer?.let {
            it.stop()
            it.reset()
            it.release()
        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            val durationMs = duration
            binding.seekBar.max = durationMs
            binding.recordingLength.text = formatDuration(0)
            binding.endRecoridngFile.text = formatDuration(durationMs)
            setOnCompletionListener {
                stopAndResetMediaPlayer()
            }
            updateSeekBarHandler.post(updateSeekBarRunnable)
        }
    }

    private fun stopAndResetMediaPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
            mediaPlayer = null
            isPlaying = false
            binding.playButton.text = "재생"
            updateSeekBarHandler.removeCallbacks(updateSeekBarRunnable)
        }
    }
    private fun formatDuration(durationMs: Int): String {
        val minutes = durationMs / 1000 / 60
        val seconds = (durationMs / 1000 % 60)
        return String.format("%02d:%02d", minutes, seconds)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        stopAndResetMediaPlayer()
        updateSeekBarHandler.removeCallbacks(updateSeekBarRunnable)
        _binding = null
    }

}