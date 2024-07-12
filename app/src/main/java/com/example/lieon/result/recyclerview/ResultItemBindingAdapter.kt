package com.example.lieon.result.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lieon.result.model.RecordResults

object ResultItemBindingAdapter {
    @BindingAdapter("app:binding_adapter")
    fun setItem(recyclerView: RecyclerView, item : RecordResults){
        item?.let {
            (recyclerView.adapter as ResultRecyclerViewAdapter)
        }
    }
}