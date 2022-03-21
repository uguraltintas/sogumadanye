package com.uguraltintas.sogumadanye.ui.order

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentOrderBinding
import com.uguraltintas.sogumadanye.model.Order
import com.uguraltintas.sogumadanye.ui.order.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {
    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isUserAnonymous()
    }
    override fun getViewBinding(container : ViewGroup?): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = OrdersAdapter { order -> onOrderClick(order) }
        binding.rvOrders.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    private fun onOrderClick(order: Order) {
        val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(order)
        findNavController().navigate(action)
    }
}