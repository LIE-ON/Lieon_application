package com.example.lieon.result.recyclerview

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lieon.databinding.ItemResultBinding
import com.example.lieon.record.db.RecordHistoryEntity

class ResultViewHolder(val binding: ItemResultBinding, private val clickListener: ResultItemClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.root.setOnClickListener(this)
    }

    private val checkBox: CheckBox = binding.resultItemCheckbox
    private val title: TextView = binding.resultItemTitle
    private val date: TextView = binding.resultItemDate
    private val deleteImage : ImageView= binding.resultItemDeleteButton

    fun bind(result: RecordHistoryEntity){
        title.text = result.title
        date.text = result.time.toString()

        checkBox.setOnClickListener {
            if (checkBox.isChecked){
                date.apply {
                    visibility = View.GONE
                }

                title.apply {
                    val params = layoutParams as LinearLayout.LayoutParams
                    params.weight = 3f
                    layoutParams = params
                }

                binding.resultItemLeftImageSpace.apply {
                    visibility = View.VISIBLE
                    val params = layoutParams as LinearLayout.LayoutParams
                    params.weight = 2f
                    layoutParams = params
                }

                deleteImage.apply {
                    visibility = View.VISIBLE
                    val params = layoutParams as LinearLayout.LayoutParams
                    params.weight = 3f
                    layoutParams = params
                }

            } else {
                title.apply {
                    val params = layoutParams as LinearLayout.LayoutParams
                    params.weight = 2f
                    layoutParams = params
                }
                date.visibility = View.VISIBLE
                deleteImage.visibility = View.GONE
                binding.resultItemLeftImageSpace.visibility = View.GONE
            }
        }


    }


    override fun onClick(v: View?) {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            clickListener.onItemClick(position)
        }
    }
}