package com.uguraltintas.sogumadanye.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uguraltintas.sogumadanye.R
import com.uguraltintas.sogumadanye.model.Food
import com.uguraltintas.sogumadanye.model.FoodFromCart
import com.uguraltintas.sogumadanye.model.Order
import com.uguraltintas.sogumadanye.ui.cart.adapter.CartAdapter
import com.uguraltintas.sogumadanye.ui.favourite.adapter.FavouriteListAdapter
import com.uguraltintas.sogumadanye.ui.foodlist.adapter.FoodListAdapter
import com.uguraltintas.sogumadanye.ui.order.adapter.OrdersAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setImageWithUrl")
fun ImageView.setImageWithUrl(imageName: String?) {
    Glide
        .with(context)
        .load("http://kasimadalan.pe.hu/yemekler/resimler/$imageName")
        .centerCrop()
        .circleCrop()
        .into(this)
}

@BindingAdapter("setVisibility")
fun View.setVisibility(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("replaceFoodList")
fun RecyclerView.replaceFoodList(list: MutableList<Food>?) {
    val adapter = this.adapter as FoodListAdapter
    adapter.replaceData(list)
}

@BindingAdapter("replaceFavFoodList")
fun RecyclerView.replaceFavFoodList(list: MutableList<Food>?) {
    val adapter = this.adapter as FavouriteListAdapter
    adapter.replaceData(list)
}

@BindingAdapter("replaceCartList")
fun RecyclerView.replaceCartList(list: MutableList<FoodFromCart>?) {
    val adapter = this.adapter as CartAdapter
    adapter.replaceData(list)
}

@BindingAdapter("replaceOrderList")
fun RecyclerView.replaceOrderList(list: MutableList<Order>?) {
    val adapter = this.adapter as OrdersAdapter
    adapter.replaceData(list)
}

@BindingAdapter("setOrderStatus")
fun TextView.setOrderStatus(status: Int) {
    text = when (status) {
        0 -> {
            resources.getString(R.string.prepare)
            //setTextColor(R.color.) set orange
        }
        1 -> {
            resources.getString(R.string.on_way)
        }
        2 -> {
            resources.getString(R.string.delivered)
        }
        else -> {
            resources.getString(R.string.canceled)
        }
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(timestamp : Long){
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    text = formatter.format(timestamp)
}