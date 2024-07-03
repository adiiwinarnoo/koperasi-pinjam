package com.pinjamankoperasi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.RegisterRepository
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.data.source.remote.ResponseRegister
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val apiConfig = ApiConfig()
    private val registerRepo = RegisterRepository(apiConfig)

    private val _registerResult = MutableLiveData<ResponseRegister>()
    val registerResult : LiveData<ResponseRegister> = _registerResult


    fun register(nomorInduk : String,nama : String, jabatan: String,
                 password: String) {
        viewModelScope.launch {
            try {
                val response = registerRepo.register(nomorInduk, nama, jabatan, password)
                _registerResult.postValue(response)
            }catch (e:Exception){
                _registerResult.postValue(ResponseRegister(message = e.message))
            }
        }
    }

}