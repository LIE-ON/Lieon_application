package com.example.lieon.result.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import com.example.lieon.databinding.ItemResultBinding
import com.example.lieon.db.RecordHistoryEntity
import com.example.lieon.result.model.RecordResults
import com.example.lieon.result.view.ResultViewModel

class ResultRecyclerViewAdapter(
    private val clickListener: ResultItemClickListener,
) : ListAdapter<RecordHistoryEntity, ResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )

    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

//    override fun getItemCount(): Int = data.size


    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecordHistoryEntity>() {
            override fun areItemsTheSame(oldItem: RecordHistoryEntity, newItem: RecordHistoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecordHistoryEntity, newItem: RecordHistoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}