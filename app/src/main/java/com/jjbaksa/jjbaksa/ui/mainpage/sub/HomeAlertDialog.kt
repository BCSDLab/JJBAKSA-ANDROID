package com.jjbaksa.jjbaksa.ui.mainpage.sub

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel

class HomeAlertDialog() : DialogFragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(R.string.location_service_term_text)
            setNegativeButton(R.string.cancel, null)
            setPositiveButton(R.string.move_setting) { _, _ ->
                val intent = Intent(Settings.ACTION_SETTINGS)
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                }
            }
        }.create()
    }
}
