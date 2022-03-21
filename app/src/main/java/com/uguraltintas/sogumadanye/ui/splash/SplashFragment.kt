package com.uguraltintas.sogumadanye.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.uguraltintas.sogumadanye.R
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    @Inject
    lateinit var auth: FirebaseAuth

    override fun getViewBinding(container : ViewGroup?): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentUser = auth.currentUser
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (currentUser != null)
                    findNavController().navigate(R.id.action_splashFragment_to_foodListFragment)
                else
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
        timer.start()
    }
}