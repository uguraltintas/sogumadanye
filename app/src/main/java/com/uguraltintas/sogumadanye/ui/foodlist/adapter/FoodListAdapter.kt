package com.uguraltintas.sogumadanye.ui.foodlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.card.MaterialCardView
import com.uguraltintas.sogumadanye.base.BaseAdapter
import com.uguraltintas.sogumadanye.databinding.FoodItemRowBinding
import com.uguraltintas.sogumadanye.model.Food
import com.uguraltintas.sogumadanye.ui.favourite.adapter.FavouriteListViewHolder

class FoodListAdapter(
    private val onItemClick: (food: Food, cardView: MaterialCardView) -> Unit,
    private val onFavouriteClick: (position: Int, isChecked : Boolean) -> Unit
) :
    BaseAdapter<Food, FoodItemRowBinding, FoodListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodItemRowBinding.inflate(inflater, parent, false)
        return FoodListViewHolder(binding, onItemClick, onFavouriteClick)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}