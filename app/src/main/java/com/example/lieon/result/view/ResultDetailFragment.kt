package com.example.lieon.result.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lieon.R
import com.example.lieon.databinding.FragmentResultDetailBinding

class ResultDetailFragment : Fragment() {

    private var _binding : FragmentResultDetailBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultDetailBinding.inflate(layoutInflater)

        arguments?.let {
            val selectedPosition = it.getInt("selectedPosition")
        }

        return binding.root
    }

}