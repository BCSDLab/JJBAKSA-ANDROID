package com.jjbaksa.jjbaksa.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.jjbaksa.jjbaksa.R

class PermissionDialog(): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.location_service_term_text)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.move_setting) {_, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                }
            }
            .create()
    }
}