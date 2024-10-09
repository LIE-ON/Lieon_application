package com.example.lieon.result.view.detail

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lieon.databinding.FragmentResultDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

@AndroidEntryPoint
class ResultDetailFragment : Fragment() {

    private var _binding: FragmentResultDetailBinding? = null
    private val binding get() = _binding!!
    private val resultDetailViewModel: ResultDetailViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null
    private var mediaPlayerState = MediaPlayerState.IDLE
    private var filePath: String? = null

    private enum class MediaPlayerState {
        IDLE, INITIALIZED, PREPARED, STARTED, PAUSED, STOPPED
    }

    private val updateSeekBarHandler = android.os.Handler()
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                binding.seekBar.progress = it.currentPosition
                binding.recordingLength.text = formatDuration(it.currentPosition)
                updateSeekBarHandler.postDelayed(this, 100) // Update every 100ms
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultDetailBinding.inflate(inflater, container, false)

        arguments?.let {
            val selectedId = it.getInt("selectedId")
            Toast.makeText(requireContext(), "$selectedId", Toast.LENGTH_SHORT).show()
            resultDetailViewModel.setSelectedId(selectedId)

            resultDetailViewModel.recordHistory.observe(viewLifecycleOwner, Observer { record ->
                record?.let { recordData ->
                    binding.recordName.text = recordData.title
                    filePath = recordData.filePath // Assigning file path to a variable
                    Log.d("ResultDetailFragment", "FilePath: $filePath")
                    initializeMediaPlayer(filePath!!)
                }
            })
        }

        setupButtons()
        setupSeekBar()
        return binding.root
    }

    private fun setupButtons() {
        binding.resultDetailBackwardButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.resultDetailNameChangeButton.setOnClickListener {
            val dialog = ResultDetailNameChangeDialog { newName ->
                updateRecordName(newName)
            }
            dialog.show(parentFragmentManager, "ResultDetailNameChangeDialog")
        }

        binding.playButton.setOnClickListener {
            handlePlayButton()
        }
    }

    private fun setupSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    binding.recordingLength.text = formatDuration(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (mediaPlayerState == MediaPlayerState.STARTED) {
                    mediaPlayer?.pause()
                }
                updateSeekBarHandler.removeCallbacks(updateSeekBarRunnable)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer?.let {
                    it.start()
                    mediaPlayerState = MediaPlayerState.STARTED
                    binding.playButton.text = "일시정지"
                    updateSeekBarHandler.post(updateSeekBarRunnable)
                }
            }
        })
    }

    private fun handlePlayButton() {
        when (mediaPlayerState) {
            MediaPlayerState.PREPARED, MediaPlayerState.PAUSED -> {
                mediaPlayer?.start()
                mediaPlayerState = MediaPlayerState.STARTED
                binding.playButton.text = "일시정지"
                updateSeekBarHandler.post(updateSeekBarRunnable)
            }
            MediaPlayerState.STARTED -> {
                mediaPlayer?.pause()
                mediaPlayerState = MediaPlayerState.PAUSED
                binding.playButton.text = "재생"
                updateSeekBarHandler.removeCallbacks(updateSeekBarRunnable)
            }
            MediaPlayerState.STOPPED -> {
                filePath?.let { path ->
                    initializeMediaPlayer(path)
                    mediaPlayer?.start()
                    mediaPlayerState = MediaPlayerState.STARTED
                    binding.playButton.text = "일시정지"
                    updateSeekBarHandler.post(updateSeekBarRunnable)
                }
            }
            MediaPlayerState.IDLE, MediaPlayerState.INITIALIZED -> {
                Log.d("ResultDetailFragment", "MediaPlayer is in an idle or initialized state.")
            }
        }
    }

    private fun updateRecordName(newName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val recordHistoryEntity = resultDetailViewModel.recordHistory.value

            recordHistoryEntity?.let {
                it.title = newName
                resultDetailViewModel.updateRecordHistory(it)

                withContext(Dispatchers.Main) {
                    binding.recordName.text = it.title
                    stopAndResetMediaPlayer()
                }
            }
        }
    }

    private fun initializeMediaPlayer(filePath: String) {
        if (filePath.isNotEmpty()) {
            Log.d("FilePath", "Initializing MediaPlayer with path: $filePath")
            stopAndResetMediaPlayer()

            try {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(filePath)
                    prepare()
                    mediaPlayerState = MediaPlayerState.PREPARED
                    val durationMs = duration
                    binding.seekBar.max = durationMs
                    binding.recordingLength.text = formatDuration(0)
                    binding.endRecoridngFile.text = formatDuration(durationMs)
                    Log.d("MediaPlayer", "MediaPlayer prepared with path: $filePath")

                    setOnCompletionListener {
                        mediaPlayerState = MediaPlayerState.STOPPED
                        binding.playButton.text = "재생"
                        binding.seekBar.progress = 0
                        binding.recordingLength.text = formatDuration(0)
                        updateSeekBarHandler.removeCallbacks(updateSeekBarRunnable)
                    }
                    updateSeekBarHandler.post(updateSeekBarRunnable)
                }
            } catch (e: IOException) {
                Log.e("MediaPlayer", "Error initializing MediaPlayer: ${e.message}")
            }
        } else {
            Log.e("MediaPlayer", "File path is empty or invalid.")
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
            mediaPlayerState = MediaPlayerState.IDLE
            binding.playButton.text = "재생"
            binding.seekBar.progress = 0
            binding.recordingLength.text = formatDuration(0)
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
