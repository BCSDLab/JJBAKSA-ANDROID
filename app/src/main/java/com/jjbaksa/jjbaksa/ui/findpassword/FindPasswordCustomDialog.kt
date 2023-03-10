package com.jjbaksa.jjbaksa.ui.findpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.AlertDialogFindPasswordBinding

class FindPasswordCustomDialog() : DialogFragment() {
    private lateinit var binding: AlertDialogFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FindIdCustomDialogStyle)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.alert_dialog_find_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPositive.setOnClickListener {
            dismiss()
            activity?.finish()
        }
    }
}
