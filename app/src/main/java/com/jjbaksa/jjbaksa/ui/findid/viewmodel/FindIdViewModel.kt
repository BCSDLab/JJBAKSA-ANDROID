package com.jjbaksa.jjbaksa.ui.findid.viewmodel

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.jjbaksa.domain.resp.findid.FindIdResp
import com.jjbaksa.jjbaksa.di.NetworkModule
import com.jjbaksa.jjbaksa.ui.findid.FindIdCustomDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(

) : ViewModel() {
    val numberBoxUiState: MutableLiveData<MutableList<Boolean>> by lazy {
        MutableLiveData<MutableList<Boolean>>()
    }
    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setUserEmail(_userEmail: String) {
        userEmail.value = _userEmail
    }

    fun stateButton(emailLength: Int): Boolean{
        return emailLength > 0
    }


    fun checkEmailFormat(userEmail: String): Boolean{
        val emailValidation =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        var email = userEmail.trim()
        return Pattern.matches(emailValidation, email)
    }

    fun checkNumberInCodeBox(numberLength:Int, boxState:MutableList<Boolean>, pos:Int, boxNumber: EditText?){
        if (numberLength == 1){
            if (pos != 3){
                boxNumber?.requestFocus()
            }
            boxState[pos] = true
        } else boxState[pos] = false

        numberBoxUiState.value = boxState
    }

    fun getFindIdNumberCode(email: String, nav: NavController?, layout: Int?) {
        NetworkModule.provideAuthApi(NetworkModule.provideNoAuthRetrofit(NetworkModule.provideNoAuthOkHttpClient()))
            .getFindIdCodeNumber(email)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.code() == 200) {
                        setUserEmail(email)
                        if (nav != null) {
                            nav?.navigate(layout!!)
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                }
            })
    }

    fun getFindId(
        number1: String,
        number2: String,
        number3: String,
        number4: String,
        customDialog: FindIdCustomDialog,
        emailFormatIsNot: TextView,
        isOkButton: Button
    ) {
        NetworkModule.provideAuthApi(NetworkModule.provideNoAuthRetrofit(NetworkModule.provideNoAuthOkHttpClient()))
            .findAccount(
                userEmail.value.toString(),
                number1 + number2 + number3 + number4
            )
            .enqueue(object : Callback<FindIdResp> {
                override fun onResponse(
                    call: Call<FindIdResp>,
                    response: Response<FindIdResp>
                ) {
                    if (response.code() == 200) {
                        // ok
                        customDialog.showDialog(
                            userEmail.value.toString(),
                            response.body()?.account.toString(),

                        )
                        emailFormatIsNot.isVisible = false

                    } else {
                        // fail
                        isOkButton.isEnabled = false
                        emailFormatIsNot.isVisible = true

                    }
                }

                override fun onFailure(call: Call<FindIdResp>, t: Throwable) {
                    Log.d("레트로핏", "onFailure - ${t.message}")
                }
            })
    }


}