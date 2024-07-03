package com.pinjamankoperasi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.LoginRepository
import com.pinjamankoperasi.data.source.remote.ResponseLogin
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel() : ViewModel() {
    private val apiConfig = ApiConfig()
    private val loginRepository = LoginRepository(apiConfig)

    private val _loginResult = MutableLiveData<ResponseLogin>()
    val loginResult : LiveData<ResponseLogin> = _loginResult

    private val _errorResult = MutableLiveData<String>()
    val errorResult : LiveData<String> = _errorResult

    fun login(nomorInduk: String, password: String) {
        viewModelScope.launch {
            try {
                val response = loginRepository.login(nomorInduk, password)
                _loginResult.postValue(response)
            }catch (e: Exception){
                _errorResult.postValue(e.message)
            }
        }
    }
}