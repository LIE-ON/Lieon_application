package com.example.lieon.result.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lieon.R
import com.example.lieon.databinding.FragmentResultDetailBinding
import com.example.lieon.databinding.FragmentResultSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            searchEditText.addTextChangedListener {object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    CoroutineScope(Dispatchers.IO).launch {
                        resultSearchViewModel.searchResultByTitle(s.toString())
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

             }

            }


        }
        return binding.root
    }

}