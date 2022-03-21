package com.uguraltintas.sogumadanye.ui.orderdetail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentOrderDetailBinding
import com.uguraltintas.sogumadanye.ui.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>() {
    private val viewModel : OrderDetailViewModel by viewModels()
    private val args : OrderDetailFragmentArgs by navArgs()

    override fun getViewBinding(container : ViewGroup?): FragmentOrderDetailBinding =
        FragmentOrderDetailBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val order = args.order
        val adapter = CartAdapter()
        binding.rvOrderDetail.adapter = adapter
        binding.order = order
    }
}