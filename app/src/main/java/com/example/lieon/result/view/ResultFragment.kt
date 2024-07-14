package com.example.lieon.result.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.lieon.databinding.FragmentResultBinding
import com.example.lieon.result.recyclerview.ResultItemClickListener
import com.example.lieon.result.recyclerview.ResultRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
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


//        binding.resultRecyclerview.adapter = resultAdapter
//
        resultViewModel.recordResults.observe(viewLifecycleOwner) {items ->
            resultAdapter.submitList(items)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Clicked item at position $position", Toast.LENGTH_SHORT).show()
    }



}