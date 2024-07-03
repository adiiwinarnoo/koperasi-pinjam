package com.pinjamankoperasi.data.repository

import com.google.gson.Gson
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.data.source.remote.ResponseRegister
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.utils.Contant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RegisterRepository (private val apiConfig: ApiConfig) {

    var textError : String? = null

    suspend fun register(nomorInduk : String,nama : String, jabatan: String,
                               password: String) : ResponseRegister {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiConfig.server.register(nomorInduk,nama,jabatan,password).execute()
                if (response.isSuccessful){
                    RegisterSuccess(response)
                }else{
                    var errorBuddy = response.errorBody()?.string()
                    textError = if (errorBuddy != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBuddy, ResponsePinjaman::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseRegister(message = textError ?: "Unknown error")
                }
            }catch (e: Exception){
                ResponseRegister(message = textError ?: "Unknown error")
            }
        }
    }

    private fun RegisterSuccess(response: Response<ResponseRegister>) : ResponseRegister {
        return when(response.code()) {
            200 -> response.body() ?: ResponseRegister()
            400 -> ResponseRegister(message = Contant.ERROR_TEXT_400)
            401 -> ResponseRegister(message = response.body()?.message)
            404 -> ResponseRegister(message = Contant.ERROR_TEXT_404)
            else -> ResponseRegister()
        }
    }
}