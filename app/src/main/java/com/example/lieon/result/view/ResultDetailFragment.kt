package com.example.lieon.result.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.lieon.R
import com.example.lieon.databinding.FragmentResultDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultDetailFragment : Fragment() {

    private var _binding : FragmentResultDetailBinding? = null

    private val binding get() = _binding!!
    private val resultDetailViewModel : ResultDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultDetailBinding.inflate(layoutInflater)

        arguments?.let {
            val selectedId = it.getInt("selectedId")
            resultDetailViewModel.setSelectedId(selectedId)
            resultDetailViewModel.searchResult().let {
                Toast.makeText(requireContext(), "selected ID : $selectedId", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}