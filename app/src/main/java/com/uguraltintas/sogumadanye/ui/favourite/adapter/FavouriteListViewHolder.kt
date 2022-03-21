package com.uguraltintas.sogumadanye.ui.favourite.adapter

import com.google.android.material.card.MaterialCardView
import com.uguraltintas.sogumadanye.base.BaseHolder
import com.uguraltintas.sogumadanye.databinding.FavouriteFoodItemBinding
import com.uguraltintas.sogumadanye.model.Food

class FavouriteListViewHolder(
    binding: FavouriteFoodItemBinding,
    private val onItemClick: (food: Food, cardView: MaterialCardView) -> Unit
) : BaseHolder<Food, FavouriteFoodItemBinding>(binding) {

    override fun bind(item: Food?) {
        item?.let { outer ->
            binding.food = outer
            binding.cvFood.setOnClickListener {
                onItemClick(outer, binding.cvFood)
            }
        }
    }
}