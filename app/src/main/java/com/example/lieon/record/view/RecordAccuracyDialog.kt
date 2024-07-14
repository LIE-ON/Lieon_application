package com.example.lieon.record.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.lieon.databinding.DialogRecordAccuracyBinding

class RecordAccuracyDialog(context: Context) : Dialog(context){

    private var _binding : DialogRecordAccuracyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogRecordAccuracyBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding?.accuracySubmitButton?.setOnClickListener {
            val spf = context.getSharedPreferences("LieonPrefs",Context.MODE_PRIVATE)
            val editor = spf.edit()

            editor.putInt("goalAccuracy",binding!!.accuracyInputEdittext.text.toString().toInt())

            Toast.makeText(context, spf.getInt("goalAccuracy", 90).toString(), Toast.LENGTH_SHORT).show()

        }


    }
}