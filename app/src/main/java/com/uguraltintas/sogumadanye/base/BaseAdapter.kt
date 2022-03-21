package com.uguraltintas.sogumadanye.base

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<ItemType, VB : ViewBinding, ViewHolderType : BaseHolder<ItemType, VB>> :
    RecyclerView.Adapter<ViewHolderType>() {

    private val itemList : MutableList<ItemType> = mutableListOf()

    override fun onBindViewHolder(@NonNull holder: ViewHolderType, position: Int) {
        holder.bind(itemList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun replaceData(newList: List<ItemType>?) {
        itemList.clear()
        itemList.addAll(newList ?: emptyList())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size

    fun getData(): MutableList<ItemType> {
        return itemList
    }

    fun removeItem(position : Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemByIndex(position: Int) = itemList[position]
}