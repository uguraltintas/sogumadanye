package com.uguraltintas.sogumadanye.ui.dialog.order

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.uguraltintas.sogumadanye.databinding.FragmentOrderPrepareDialogBinding

class OrderPrepareDialog : DialogFragment() {
    private lateinit var binding: FragmentOrderPrepareDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderPrepareDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }
}