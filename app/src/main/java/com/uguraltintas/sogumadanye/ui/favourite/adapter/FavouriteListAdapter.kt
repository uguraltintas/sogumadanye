package com.uguraltintas.sogumadanye.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.card.MaterialCardView
import com.uguraltintas.sogumadanye.base.BaseAdapter
import com.uguraltintas.sogumadanye.databinding.FavouriteFoodItemBinding
import com.uguraltintas.sogumadanye.model.Food

class FavouriteListAdapter(
    private val onItemClick: (food: Food, cardView: MaterialCardView) -> Unit
) :
    BaseAdapter<Food, FavouriteFoodItemBinding, FavouriteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavouriteFoodItemBinding.inflate(inflater, parent, false)
        return FavouriteListViewHolder(binding, onItemClick)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}