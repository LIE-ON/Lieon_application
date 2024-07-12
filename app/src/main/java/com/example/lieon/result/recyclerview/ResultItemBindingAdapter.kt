package com.example.lieon.result.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lieon.record.db.RecordHistoryEntity
import com.example.lieon.result.model.RecordResults

object ResultItemBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapter", "submitList", requireAll = true)
    fun bindRecyclerView(view : RecyclerView, adapter: ResultRecyclerViewAdapter, submitList: List<Any>?){
        view.adapter = adapter
        adapter.submitList(submitList?.map { it as RecordHistoryEntity })
    }
}