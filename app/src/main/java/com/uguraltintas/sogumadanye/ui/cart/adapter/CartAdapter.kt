package com.uguraltintas.sogumadanye.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uguraltintas.sogumadanye.base.BaseAdapter
import com.uguraltintas.sogumadanye.databinding.CartItemRowBinding
import com.uguraltintas.sogumadanye.model.FoodFromCart

class CartAdapter :
    BaseAdapter<FoodFromCart, CartItemRowBinding, CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CartItemRowBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }
}