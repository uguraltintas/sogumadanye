package com.uguraltintas.sogumadanye.ui.order.adapter

import com.uguraltintas.sogumadanye.base.BaseHolder
import com.uguraltintas.sogumadanye.databinding.OrderStatusRowBinding
import com.uguraltintas.sogumadanye.model.Order

class OrdersViewHolder(
    binding: OrderStatusRowBinding,
    private val onOrderClick: (order: Order) -> Unit
) : BaseHolder<Order, OrderStatusRowBinding>(binding) {
    override fun bind(item: Order?) {
        item?.let { outer ->
            binding.order = outer
            binding.cvFood.setOnClickListener {
                onOrderClick(outer)
            }
        }
    }
}