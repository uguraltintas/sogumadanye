package com.uguraltintas.sogumadanye.ui.viewpager

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentViewPagerBinding


class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>() {
    override fun getViewBinding(container : ViewGroup?): FragmentViewPagerBinding =
        FragmentViewPagerBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}