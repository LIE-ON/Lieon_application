package com.example.lieon.result.recyclerview

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lieon.R
import com.example.lieon.databinding.ItemResultBinding
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.record.view.RecordViewModel

class ResultViewHolder(
    private val binding: ItemResultBinding,
    private val clickListener: ResultItemClickListener,
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private val checkBox: CheckBox = binding.resultItemCheckbox
    private val title: TextView = binding.resultItemTitle
    private val date: TextView = binding.resultItemDate
    private val deleteImage : ImageView= binding.resultItemDeleteButton


    init {
        title.setOnClickListener(this)
        deleteImage.setOnClickListener(this)
    }


    fun bind(result: RecordHistoryEntity){
        title.text = result.title
        date.text = result.time

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                date.visibility = View.GONE
                binding.resultItemLeftImageSpace.visibility = View.VISIBLE
                deleteImage.visibility = View.VISIBLE
            } else {
                date.visibility = View.VISIBLE
                binding.resultItemLeftImageSpace.visibility = View.GONE
                deleteImage.visibility = View.GONE
            }
        }
    }

    override fun onClick(v: View?) {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            when (v?.id) {
                R.id.result_item_delete_button -> clickListener.onItemDeleteClick(position)
                R.id.result_item_title -> clickListener.onItemDetailClick(position)
            }

        }
    }
}