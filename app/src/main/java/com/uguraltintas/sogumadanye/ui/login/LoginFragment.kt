package com.uguraltintas.sogumadanye.ui.login

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.uguraltintas.sogumadanye.R
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var auth : FirebaseAuth

    override fun getViewBinding(container : ViewGroup?): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.login.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmailAndPassword(email, password)
            }
        }

        binding.tvAnonymous.setOnClickListener {
            auth.signInAnonymously().addOnSuccessListener {
                findNavController().navigate(R.id.action_loginFragment_to_foodListFragment)
              /*  when (onBoardingFinished()) {
                    true -> {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    false -> findNavController().navigate(R.id.action_loginFragment_to_viewPagerFragment)
                }*/
            }
        }
    }
    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_loginFragment_to_foodListFragment)
                /*when (onBoardingFinished()) {
                    true -> {

                    }
                    false -> findNavController().navigate(R.id.action_loginFragment_to_viewPagerFragment)
                }*/
            } else {
                Snackbar.make(binding.email, "Giriş yapılamadı", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}