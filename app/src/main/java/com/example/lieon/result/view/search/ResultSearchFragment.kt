package com.example.lieon.result.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lieon.R
import com.example.lieon.databinding.FragmentResultDetailBinding
import com.example.lieon.databinding.FragmentResultSearchBinding


class ResultSearchFragment : Fragment() {
    private var _binding : FragmentResultSearchBinding? = null

    private val binding get() = _binding!!

    private val resultSearchViewModel : ResultSearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultSearchBinding.inflate(layoutInflater)

        binding.apply {
            searchBackwardButton.setOnClickListener {
                findNavController().navigate(com.example.lieon.R.id.action_resultSearchFragment_to_resultFragment)
            }
        }
        return binding.root
    }

}