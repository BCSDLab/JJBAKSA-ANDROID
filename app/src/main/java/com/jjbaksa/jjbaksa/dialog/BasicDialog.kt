package com.jjbaksa.jjbaksa.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BasicDialog(
    private val message: String,
    private val negativeMsg: String,
    private val positiveMsg: String,
    val positiveEvent: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setNegativeButton(negativeMsg, null)
            .setPositiveButton(positiveMsg) { _, _ ->
                positiveEvent()
            }
            .create()
    }
}
