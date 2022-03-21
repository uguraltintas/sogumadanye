package com.uguraltintas.sogumadanye.ui.cart.adapter

import com.uguraltintas.sogumadanye.base.BaseHolder
import com.uguraltintas.sogumadanye.databinding.CartItemRowBinding
import com.uguraltintas.sogumadanye.model.FoodFromCart

class CartViewHolder(
    binding: CartItemRowBinding
) : BaseHolder<FoodFromCart, CartItemRowBinding>(binding) {
    override fun bind(item: FoodFromCart?) {
        item?.let {
            binding.foodCart = it
            binding.cost = it.count * it.price
        }
    }
}