package com.example.lieon.result.view.recyclerview

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lieon.R
import com.example.lieon.databinding.ItemResultBinding
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.record.view.RecordViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

class ResultViewHolder(
    private val binding: ItemResultBinding,
    private val clickListener: ResultItemClickListener,
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private val title: TextView = binding.resultItemTitle
    private val deleteImage : ImageView= binding.resultItemDeleteButton

    private lateinit var result : RecordHistoryEntity


    init {
        title.setOnClickListener(this)
        deleteImage.setOnClickListener(this)
    }

    fun bind(result: RecordHistoryEntity){
        this.result = result

        binding.apply {
            title.text = result.title
            resultItemDate.text = formatDate(result.time)

            root.setBackgroundColor(UNCHECKED_COLOR)

            resultItemCheckbox.isChecked = false

            resultItemCheckbox.setOnClickListener {
                if (resultItemCheckbox.isChecked) {
                    binding.root.setBackgroundColor(CHECKED_COLOR)
                    resultItemDate.visibility = View.GONE
                    binding.resultItemLeftImageSpace.visibility = View.VISIBLE
                    deleteImage.visibility = View.VISIBLE
                } else {
                    binding.root.setBackgroundColor(UNCHECKED_COLOR)
                    resultItemDate.visibility = View.VISIBLE
                    binding.resultItemLeftImageSpace.visibility = View.GONE
                    deleteImage.visibility = View.GONE
                }
            }
        }


    }

    override fun onClick(v: View?) {
        val position = bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            when (v?.id) {
                R.id.result_item_delete_button -> clickListener.onItemDeleteClick(result.id)
                R.id.result_item_title -> clickListener.onItemDetailClick(result.id)
            }

        }
    }

    private fun formatDate(input: String): String {
        val inputFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
        val outputFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault())

        return try {
            val date = inputFormat.parse(input)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    companion object {
        private const val CHECKED_COLOR = android.graphics.Color.LTGRAY
        private const val UNCHECKED_COLOR = android.graphics.Color.WHITE
        private const val INPUT_DATE_FORMAT = "yyyyMMdd_HHmmss"
        private const val OUTPUT_DATE_FORMAT = "yyyy.MM.dd HH:mm:ss"
    }
}