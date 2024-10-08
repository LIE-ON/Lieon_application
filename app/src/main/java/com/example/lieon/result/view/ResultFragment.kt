package com.example.lieon.result.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lieon.databinding.FragmentResultBinding
import com.example.lieon.result.view.recyclerview.ResultItemClickListener
import com.example.lieon.result.view.recyclerview.ResultRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultFragment () : Fragment(), ResultItemClickListener {
    private var _binding : FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var resultAdapter : ResultRecyclerViewAdapter

    private val resultViewModel : ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = resultViewModel
        resultAdapter = ResultRecyclerViewAdapter(this@ResultFragment)
        binding.adapter = resultAdapter

        binding.resultSearchButton.setOnClickListener {
            findNavController().navigate(com.example.lieon.R.id.action_resultFragment_to_resultSearchFragment)
        }

        resultViewModel.recordResults.observe(viewLifecycleOwner) {items ->
            resultAdapter.submitList(items)
        }

        resultViewModel.sortButtonText.observe(viewLifecycleOwner) { buttonText ->
            binding.sortButton.text = buttonText
        }

        binding.sortButton.setOnClickListener {
            resultViewModel.toggleSorting()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemDeleteClick(selectedID: Int) {
//        Toast.makeText(requireContext(), "Clicked Delete item at position $position", Toast.LENGTH_SHORT).show()
        resultViewModel.deleteRecordResultById(selectedID)
    }

    override fun onItemDetailClick(selectedID: Int) {
//        Toast.makeText(requireContext(), "Clicked Detail item at position $position", Toast.LENGTH_SHORT).show()
        val bundle = Bundle().apply {
            putInt("selectedId", selectedID)
        }
        findNavController().navigate(com.example.lieon.R.id.action_resultFragment_to_resultDetailFragment, bundle)
    }



}