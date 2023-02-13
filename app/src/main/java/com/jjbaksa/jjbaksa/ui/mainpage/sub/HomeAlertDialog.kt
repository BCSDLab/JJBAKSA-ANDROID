package com.jjbaksa.jjbaksa.ui.mainpage.sub

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.jjbaksa.jjbaksa.R

class HomeAlertDialog() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(R.string.location_service_term_text)
            setNegativeButton(R.string.cancel, null)
            setPositiveButton(R.string.move_setting) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                }
            }
        }.create()
    }
}
