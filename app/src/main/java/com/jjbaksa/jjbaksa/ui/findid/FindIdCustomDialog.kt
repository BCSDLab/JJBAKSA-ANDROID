package com.jjbaksa.jjbaksa.ui.findid

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.jjbaksa.jjbaksa.R

class FindIdCustomDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener : OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }

    fun showDialog(email:String, id: String){
        dialog.setContentView(R.layout.alert_dialog_find_id)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val getIdText = dialog.findViewById<TextView>(R.id.text_view_get_id)
        val getIdOkButton = dialog.findViewById<Button>(R.id.button_get_id_ok)

        getIdText.text = "${email}으로 가입된 아이디는 ${id}입니다."

        getIdOkButton.setOnClickListener {
            dialog.dismiss()
        }
    }


    interface OnDialogClickListener {
        fun onClicked(name: String)
    }
}