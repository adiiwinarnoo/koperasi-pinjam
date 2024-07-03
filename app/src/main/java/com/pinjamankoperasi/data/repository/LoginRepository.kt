package com.pinjamankoperasi.data.repository

import android.provider.SyncStateContract.Constants
import android.util.Log
import com.google.gson.Gson
import com.pinjamankoperasi.data.source.remote.ResponseLogin
import com.pinjamankoperasi.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pinjamankoperasi.utils.Contant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val apiConfig : ApiConfig) {

    var textError : String? = null

    suspend fun login(nomorInduk: String, password: String): ResponseLogin {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiConfig.server.login(nomorInduk, password).execute()
                if (response.isSuccessful) {
                    loginSuccess(response)
                } else {
                    var errorBuddy = response.errorBody()?.string()
                    textError = if (errorBuddy != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBuddy, ResponseLogin::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseLogin(message = textError ?: "Unknown error")
                }
            } catch (e: Exception) {
                ResponseLogin(message = e.message ?: "Unknown error")
            }
        }
    }

    private fun loginSuccess(response: Response<ResponseLogin>): ResponseLogin {
        return when (response.code()) {
            200 -> response.body() ?: ResponseLogin()
            400 -> ResponseLogin(message = Contant.ERROR_TEXT_400)
            401 -> ResponseLogin(message = response.body()?.message)
            404 -> ResponseLogin(message = Contant.ERROR_TEXT_404)
            else -> ResponseLogin()
        }
    }

}