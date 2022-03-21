package com.uguraltintas.sogumadanye.ui.cart

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentCartBinding
import com.uguraltintas.sogumadanye.ui.cart.adapter.CartAdapter
import com.uguraltintas.sogumadanye.ui.dialog.order.OrderPrepareDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val viewModel: CartViewModel by viewModels()

    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isUserAnonymous()
    }

    override fun getViewBinding(container: ViewGroup?): FragmentCartBinding =
        FragmentCartBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CartAdapter()
        binding.rvCart.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isAnonymous.value?.let {
            if (!it) {
                viewModel.getFoodFromCart()
            }
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteFood(adapter.getItemByIndex(position))
                adapter.removeItem(position)

            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvCart)

        viewModel.loading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.shimmerLayout.startShimmer()
                false -> binding.shimmerLayout.stopShimmer()
            }
        }
        binding.btnOrder.setOnClickListener {
            viewModel.onOrderClick()
            val dialog = OrderPrepareDialog()
            dialog.show(childFragmentManager, "Order Prepare Dialog")
        }

    }
}