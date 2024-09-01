package com.example.lieon.result.view.detail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.lieon.databinding.DialogResultNameChangeBinding

class ResultDetailNameChangeDialog(
    private val onNameChanged: (newName:String)-> Unit
): DialogFragment() {

    private var _binding : DialogResultNameChangeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogResultNameChangeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resultNameChangeSubmitButton.setOnClickListener{
            val newName = binding.resultNameChangeTitle.text.toString()
            if(newName.isNotEmpty()){
                onNameChanged(newName)
                dismiss()
            }else {
                Toast.makeText(requireContext(),"이름 변경",Toast.LENGTH_SHORT).show()

            }
        }
    }
}