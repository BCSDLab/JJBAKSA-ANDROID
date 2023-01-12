package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.AlertDialogFindIdBinding
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel

class FindIdCustomDialog() : DialogFragment() {
    private lateinit var binding: AlertDialogFindIdBinding
    private val findIdViewModel: FindIdViewModel by activityViewModels()

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
            DataBindingUtil.inflate(layoutInflater, R.layout.alert_dialog_find_id, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewGetId.text =
            "${findIdViewModel.userEmail.value}으로 가입된 아이디는 ${findIdViewModel.userAccount.value}입니다."

        binding.buttonGetIdOk.setOnClickListener {
            dismiss()
            activity?.finish()
        }
    }
}
