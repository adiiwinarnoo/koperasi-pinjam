package com.pinjamankoperasi.data.repository

import com.google.gson.Gson
import com.pinjamankoperasi.data.source.remote.ResponseLogin
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.utils.Contant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PinjamanRepository (private val apiConfig: ApiConfig) {

    var textError : String? = null

    suspend fun uploadPinjaman(idProduct : Int,nikKaryawan : Int, namaKaryawan: String,
                               jumlahPinjam: Int,harga: Int) : ResponsePinjaman {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiConfig.server.pengajuanPinjaman(idProduct, nikKaryawan,namaKaryawan ,jumlahPinjam, harga).execute()
                if (response.isSuccessful){
                    uploadPinjamanSuccess(response)
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
                    ResponsePinjaman(message = textError ?: "Unknown error")
                }
            }catch (e: Exception){
                ResponsePinjaman(message = textError ?: "Unknown error")
            }
        }
    }

    private fun uploadPinjamanSuccess(response: Response<ResponsePinjaman>) : ResponsePinjaman {
        return when(response.code()) {
            200 -> response.body() ?: ResponsePinjaman()
            400 -> ResponsePinjaman(message = Contant.ERROR_TEXT_400)
            401 -> ResponsePinjaman(message = response.body()?.message)
            404 -> ResponsePinjaman(message = Contant.ERROR_TEXT_404)
            else -> ResponsePinjaman()
        }
    }

}