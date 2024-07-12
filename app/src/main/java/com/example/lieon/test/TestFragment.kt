package com.example.lieon.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lieon.databinding.FragmentTestBinding
import com.example.lieon.record.view.RecordViewModel
import com.example.lieon.db.RecordHistoryEntity
import kotlinx.coroutines.launch
import java.util.Date

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


        return binding.root
    }

}