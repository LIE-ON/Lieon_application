package com.example.lieon.result.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.lieon.result.view.recyclerview.ResultItemClickListener
import com.example.lieon.result.view.recyclerview.ResultRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultSearchFragment : Fragment(), ResultItemClickListener {
    private var _binding : FragmentResultSearchBinding? = null

    private val binding get() = _binding!!

    private val resultSearchViewModel : ResultSearchViewModel by viewModels()

    private lateinit var resultAdapter : ResultRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultSearchBinding.inflate(layoutInflater)

        binding.apply {
            viewmodel = resultSearchViewModel
            resultAdapter = ResultRecyclerViewAdapter(this@ResultSearchFragment)
            adapter = resultAdapter
            lifecycleOwner = viewLifecycleOwner

            resultSearchViewModel.searchResults.observe(viewLifecycleOwner){items ->
                resultAdapter.submitList(items)
            }

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 텍스트가 변경되기 전에 수행할 작업이 있으면 작성
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrEmpty()){
                        resultSearchViewModel.setSearchResultEmpty()
                        return
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        resultSearchViewModel.searchResultByTitle(s.toString())
                    }
                }
                override fun afterTextChanged(s: Editable?) {

                }
            })

            searchBackwardButton.setOnClickListener {
                findNavController().navigate(com.example.lieon.R.id.action_resultSearchFragment_to_resultFragment)
            }

        }
        return binding.root
    }

    override fun onItemDeleteClick(position: Int) {

    }

    override fun onItemDetailClick(position: Int) {

    }

}