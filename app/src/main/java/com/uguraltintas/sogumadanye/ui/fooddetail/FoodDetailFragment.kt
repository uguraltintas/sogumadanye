package com.uguraltintas.sogumadanye.ui.fooddetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentFoodDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FoodDetailFragment : BaseFragment<FragmentFoodDetailBinding>() {

    private val args: FoodDetailFragmentArgs by navArgs()
    private val viewModel: FoodDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFoodFromCart()
    }
    override fun initOnCreateView() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .setDuration(500)
        postponeEnterTransition(50, TimeUnit.MILLISECONDS)
    }

    override fun getViewBinding(container: ViewGroup?): FragmentFoodDetailBinding =
        FragmentFoodDetailBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val food = args.food
        binding.food = food
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearCount()
    }
}
