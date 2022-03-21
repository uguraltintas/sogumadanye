package com.uguraltintas.sogumadanye.ui.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.uguraltintas.sogumadanye.R
import com.uguraltintas.sogumadanye.base.BaseFragment
import com.uguraltintas.sogumadanye.databinding.FragmentRegisterBinding
import com.uguraltintas.sogumadanye.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel : RegisterViewModel by viewModels()

    override fun getViewBinding(container : ViewGroup?): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.register.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val name = binding.name.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            val user = User(email, name)
            if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                viewModel.createUserWithEmailAndPassword(user, password)
            }
            viewModel.registerStatus.observe(viewLifecycleOwner){
                when(it){
                    RegisterStatus.SUCCESS -> findNavController().navigate(R.id.action_registerFragment_to_foodListFragment)
                    else -> Snackbar.make(binding.register, "Please try again", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}