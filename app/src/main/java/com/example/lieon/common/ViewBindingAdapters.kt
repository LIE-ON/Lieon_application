package com.example.lieon.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lieon.record.db.RecordHistoryEntity
import com.example.lieon.result.recyclerview.ResultRecyclerViewAdapter

object ViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("adapter", "submitList", requireAll = true)
    fun bindRecyclerView(view : RecyclerView, adapter: ResultRecyclerViewAdapter, submitList: List<Any>?){

        view.adapter = adapter
        adapter.submitList(submitList?.map { it as RecordHistoryEntity })

//        view.adapter = adapter.apply {
//            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
//            (this as ListAdapter<Any, *>).submitList(submitList?.toMutableList())
//        }
    }

}