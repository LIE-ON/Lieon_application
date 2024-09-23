package com.example.lieon.record.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.lieon.R
import com.example.lieon.databinding.DialogRecordAccuracyBinding

class RecordAccuracyDialog(context: Context) : Dialog(context){

    private var _binding : DialogRecordAccuracyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogRecordAccuracyBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

//        window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )

        window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        val window = window ?: return
        val layoutParams = window.attributes
        val marginHorizontal = (context.resources.displayMetrics.density * 25).toInt()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.width = context.resources.displayMetrics.widthPixels - 2 * marginHorizontal
        window.attributes = layoutParams


        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        val spf = context.getSharedPreferences("LieonPrefs",Context.MODE_PRIVATE)
        val beforeAccuracyGoal = spf.getInt("goalAccuracy",90)

        binding.accuracySeekbar.apply {
            progress = beforeAccuracyGoal
            setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    binding.accuracySeekbarTextview.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }

        binding.accuracySeekbarTextview.text = beforeAccuracyGoal.toString()

        binding.accuracySubmitButton.setOnClickListener {

            spf.edit()
                .putInt("goalAccuracy", binding.accuracySeekbar.progress)
                .apply()

            Toast.makeText(context, spf.getInt("goalAccuracy", 90).toString(), Toast.LENGTH_SHORT).show()
            dismiss()
        }


    }
}