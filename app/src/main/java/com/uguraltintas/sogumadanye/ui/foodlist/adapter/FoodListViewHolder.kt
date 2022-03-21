package com.uguraltintas.sogumadanye.ui.foodlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.uguraltintas.sogumadanye.base.BaseHolder
import com.uguraltintas.sogumadanye.databinding.FoodItemRowBinding
import com.uguraltintas.sogumadanye.model.Food

class FoodListViewHolder(
    binding: FoodItemRowBinding,
    private val onItemClick: (food: Food, cardView: MaterialCardView) -> Unit,
    private val onFavouriteClick: (position: Int, isChecked : Boolean) -> Unit
) : BaseHolder<Food, FoodItemRowBinding>(binding) {

    override fun bind(item: Food?) {
        item?.let { outer ->
            binding.food = outer
            binding.cvFood.setOnClickListener {
                onItemClick(outer, binding.cvFood)
            }
            binding.cbFav.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFavouriteClick(position, binding.cbFav.isChecked)
                }
            }
        }
    }
}