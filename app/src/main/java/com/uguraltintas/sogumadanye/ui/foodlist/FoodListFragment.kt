package com.uguraltintas.sogumadanye.ui.foodlist

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentFoodListBinding
import com.uguraltintas.sogumadanye.model.Food
import com.uguraltintas.sogumadanye.ui.dialog.anonymous.AnonymousDialog
import com.uguraltintas.sogumadanye.ui.foodlist.adapter.FoodListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class FoodListFragment : BaseFragment<FragmentFoodListBinding>() {

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: FoodViewModel by activityViewModels()

    override fun initOnCreateView() {
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .setDuration(500)
    }

    override fun getViewBinding(container: ViewGroup?): FragmentFoodListBinding =
        FragmentFoodListBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val adapter = FoodListAdapter(
            { food, cardView -> onItemClick(food, cardView) },
            { position, isChecked -> onFavouriteClick(position, isChecked) }
        )
        binding.rvFoods.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        postponeEnterTransition()
        binding.rvFoods.doOnPreDraw {
            startPostponedEnterTransition()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.shimmerLayout.startShimmer()
                false -> binding.shimmerLayout.stopShimmer()
            }
        }

    }

    private fun onItemClick(food: Food, cardView: MaterialCardView) {
        auth.currentUser?.let {
            if (it.isAnonymous) {
                val dialog = AnonymousDialog()
                dialog.show(childFragmentManager, "Anonymous Dialog")
            } else {
                val action =
                    FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(food)
                val extras = FragmentNavigatorExtras(
                    cardView to cardView.transitionName
                )
                findNavController().navigate(action, extras)
            }
        }
    }

    private fun onFavouriteClick(position: Int, isChecked: Boolean) {
        auth.currentUser?.let {
            if (it.isAnonymous) {
                val dialog = AnonymousDialog()
                dialog.show(
                    childFragmentManager,
                    "Anonymous Dialog"
                ) // Change with You can't add fav
            } else {
                viewModel.onFavouriteClick(position, isChecked)
            }
        }
    }

}