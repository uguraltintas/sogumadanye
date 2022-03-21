package com.uguraltintas.sogumadanye.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseHolder<DataType, VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: DataType?)
}