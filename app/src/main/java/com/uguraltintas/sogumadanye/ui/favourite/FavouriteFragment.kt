package com.uguraltintas.sogumadanye.ui.favourite

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentFavouriteBinding
import com.uguraltintas.sogumadanye.model.Food
import com.uguraltintas.sogumadanye.ui.favourite.adapter.FavouriteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isUserAnonymous()
    }

    override fun getViewBinding(container : ViewGroup?): FragmentFavouriteBinding =
        FragmentFavouriteBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FavouriteListAdapter{ food, cardView -> onItemClick(food, cardView) }

        binding.rvFavFoods.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        postponeEnterTransition()
        binding.rvFavFoods.doOnPreDraw {
            startPostponedEnterTransition()
        }
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.onSwipeFavouriteItem(position)
                adapter.removeItem(position)

            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavFoods)
    }

    private fun onItemClick(food: Food, cardView: MaterialCardView) {
        val action = FavouriteFragmentDirections.actionFavouriteFragmentToFoodDetailFragment(food)
        val extras = FragmentNavigatorExtras(
            cardView to cardView.transitionName
        )
        findNavController().navigate(action, extras)
    }

}