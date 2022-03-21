package com.uguraltintas.sogumadanye.ui.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uguraltintas.sogumadanye.base.BaseAdapter
import com.uguraltintas.sogumadanye.databinding.OrderStatusRowBinding
import com.uguraltintas.sogumadanye.model.Order

class OrdersAdapter(private val onOrderClick: (order: Order) -> Unit) :
    BaseAdapter<Order, OrderStatusRowBinding, OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderStatusRowBinding.inflate(inflater, parent, false)
        return OrdersViewHolder(binding,onOrderClick)
    }
}