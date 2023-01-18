package com.jjbaksa.jjbaksa.ui.findid.viewmodel

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.FindIdRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.ui.findid.FindIdCustomDialog
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val repository: FindIdRepository
) : BaseViewModel() {
    //    private val _numberBoxUiState: SingleLiveEvent<FindIdResult>()
//    val numberBoxUiState: SingleLiveEvent<FindIdResult> get() = _numberBoxUiState
    private val _authEmailState = SingleLiveEvent<RespResult<Boolean>>()
    val authEmailState: SingleLiveEvent<RespResult<Boolean>> get() = _authEmailState

    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userAccount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val findIdCustomDialog by lazy {
        FindIdCustomDialog()
    }

    fun setUserEmail(_userEmail: String) {
        userEmail.value = _userEmail
    }

    fun setUserAccount(account: String) {
        userAccount.value = account
    }

    fun stateButton(emailLength: Int): Boolean {
        return emailLength > 0
    }

    fun checkNumberInCodeBox(
        numberLength: Int,
        boxState: MutableList<Boolean>,
        pos: Int,
        boxNumber: EditText?
    ) {
        if (numberLength == 1) {
            if (pos != 3) {
                boxNumber?.requestFocus()
            }
            boxState[pos] = true
        } else boxState[pos] = false

//        numberBoxUiState.value = boxState
    }

    fun getAuthEmail(email: String) {
        viewModelScope.launch(ceh) {
            repository.checkAuthEmail(email).let {
                authEmailState.value = it
                Log.d("Jo", "checkAuthEmail $it")
            }
        }
    }
}

//    fun getFindId(
//        number1: String,
//        number2: String,
//        number3: String,
//        number4: String,
//        fragmentManager: FragmentManager,
//        emailFormatIsNot: TextView,
//        isOkButton: Button
//    ) {
//        NetworkModule.provideAuthApi(NetworkModule.provideNoAuthRetrofit(NetworkModule.provideNoAuthOkHttpClient()))
//            .findAccount(
//                userEmail.value.toString(),
//                number1 + number2 + number3 + number4
//            )
//            .enqueue(object : Callback<FindIdResp> {
//                override fun onResponse(
//                    call: Call<FindIdResp>,
//                    response: Response<FindIdResp>
//                ) {
//                    if (response.code() == 200) {
//                        // ok
//                        setUserAccount(response.body()?.account.toString())
//                        val ft = fragmentManager.beginTransaction()
//                        val prev = fragmentManager.findFragmentByTag("find_id_custom_dialog")
//                        if (prev != null) ft.remove(prev)
//                        ft.addToBackStack(null)
//
//                        findIdCustomDialog.show(fragmentManager, "find_id_custom_dialog")
//                        emailFormatIsNot.isVisible = false
//                    } else {
//                        // fail
//                        isOkButton.isEnabled = false
//                        emailFormatIsNot.isVisible = true
//                    }
//                }
//
//                override fun onFailure(call: Call<FindIdResp>, t: Throwable) {
//                    Log.d("레트로핏", "onFailure - ${t.message}")
//                }
//            })
//    }
// }
