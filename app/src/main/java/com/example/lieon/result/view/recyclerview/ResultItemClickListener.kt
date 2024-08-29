package com.example.lieon.result.view.recyclerview

interface ResultItemClickListener {
    fun onItemDeleteClick(selectedID: Int)
    fun onItemDetailClick(selectedID: Int)
}