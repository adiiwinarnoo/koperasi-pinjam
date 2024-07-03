package com.pinjamankoperasi.data.repository

import com.google.gson.Gson
import com.pinjamankoperasi.data.source.remote.ResponseGetListPinjaman
import com.pinjamankoperasi.data.source.remote.ResponseLogin
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.utils.Contant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class GetListPinjamanRepository (private val apiConfig: ApiConfig) {

    var textError : String? = null

    suspend fun getListPinjaman(tanggal: String) : ResponseGetListPinjaman {
        return withContext(Dispatchers.IO){
            try {
                val response = apiConfig.server.getPinjaman(tanggal).execute()
                if (response.isSuccessful){
                    getListPinjamanSuccess(response)
                }else {
                    var errorBody = response.errorBody()?.string()
                    textError = if (errorBody != null){
                        try {
                            val errorResponse = Gson().fromJson(errorBody, ResponseGetListPinjaman::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseGetListPinjaman(message = textError)
                }
            }catch (e:Exception){
                ResponseGetListPinjaman(message = e.message)
            }
        }
    }

    private fun getListPinjamanSuccess(response: Response<ResponseGetListPinjaman>): ResponseGetListPinjaman {
        return when (response.code()) {
            200 -> response.body() ?: ResponseGetListPinjaman()
            400 -> ResponseGetListPinjaman(message = Contant.ERROR_TEXT_400)
            401 -> ResponseGetListPinjaman(message = response.body()?.message)
            404 -> ResponseGetListPinjaman(message = Contant.ERROR_TEXT_404)
            else -> ResponseGetListPinjaman()
        }
    }
}