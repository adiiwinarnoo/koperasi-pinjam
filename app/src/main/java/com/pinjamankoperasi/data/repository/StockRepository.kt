package com.pinjamankoperasi.data.repository

import com.google.gson.Gson
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.data.source.remote.ResponseStockKeluarMonth
import com.pinjamankoperasi.data.source.remote.ResponseStockKeluarYear
import com.pinjamankoperasi.data.source.remote.ResponseStockMasukMonth
import com.pinjamankoperasi.data.source.remote.ResponseStockMasukYear
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.utils.Contant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class StockRepository (private val apiConfig: ApiConfig) {

    var textError : String? = null

    suspend fun getStockMonth(bulan: String, tahun: Int) : ResponseStockMasukMonth{
        return withContext(Dispatchers.IO){
            try {
                val response = apiConfig.server.getStockMasukMonth(bulan, tahun).execute()
                if (response.isSuccessful){
                    stockMonthSuccess(response)
                }else{
                    var errorBuddy = response.errorBody()?.string()
                    textError = if (errorBuddy != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBuddy, ResponseStockMasukMonth::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseStockMasukMonth(message = textError ?: "Unknown error")
                }
            }catch (e: Exception){
                ResponseStockMasukMonth(message = textError ?: "Unknown error")
            }
        }
    }
    suspend fun getStockYear(tahun: Int) : ResponseStockMasukYear{
        return withContext(Dispatchers.IO){
            try {
                val response = apiConfig.server.getStockMasukYear(tahun).execute()
                if (response.isSuccessful){
                    stockYearSuccess(response)
                }else{
                    var errorBuddy = response.errorBody()?.string()
                    textError = if (errorBuddy != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBuddy, ResponseStockMasukYear::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseStockMasukYear(message = textError ?: "Unknown error")
                }
            }catch (e: Exception){
                ResponseStockMasukYear(message = textError ?: "Unknown error")
            }
        }
    }

    suspend fun outStockMonth(bulan: String, tahun: Int) : ResponseStockKeluarMonth{
        return withContext(Dispatchers.IO){
            try {
                val response = apiConfig.server.getOutStockMonth(bulan, tahun).execute()
                if (response.isSuccessful){
                    outSttockMonthSuccess(response)
                }else{
                    var errorBuddy = response.errorBody()?.string()
                    textError = if (errorBuddy != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBuddy, ResponseStockKeluarMonth::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseStockKeluarMonth(message = textError ?: "Unknown error")
                }
            }catch (e: Exception){
                ResponseStockKeluarMonth(message = textError ?: "Unknown error")
            }
        }
    }

    suspend fun outStockYear(tahun: Int) : ResponseStockKeluarYear{
        return withContext(Dispatchers.IO){
            try {
                val response = apiConfig.server.getOutStockYear(tahun).execute()
                if (response.isSuccessful){
                    outSttockYearSuccess(response)
                }else{
                    var errorBuddy = response.errorBody()?.string()
                    textError = if (errorBuddy != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBuddy, ResponseStockKeluarYear::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseStockKeluarYear(message = textError ?: "Unknown error")
                }
            }catch (e: Exception){
                ResponseStockKeluarYear(message = textError ?: "Unknown error")
            }
        }
    }



    fun stockMonthSuccess(response: Response<ResponseStockMasukMonth>) : ResponseStockMasukMonth {
        return when(response.code()) {
            200 -> response.body() ?: ResponseStockMasukMonth()
            400 -> ResponseStockMasukMonth(message = Contant.ERROR_TEXT_400)
            401 -> ResponseStockMasukMonth(message = response.body()?.message)
            404 -> ResponseStockMasukMonth(message = Contant.ERROR_TEXT_404)
            else -> ResponseStockMasukMonth()
        }
    }
    fun stockYearSuccess(response: Response<ResponseStockMasukYear>) : ResponseStockMasukYear {
        return when(response.code()) {
            200 -> response.body() ?: ResponseStockMasukYear()
            400 -> ResponseStockMasukYear(message = Contant.ERROR_TEXT_400)
            401 -> ResponseStockMasukYear(message = response.body()?.message)
            404 -> ResponseStockMasukYear(message = Contant.ERROR_TEXT_404)
            else -> ResponseStockMasukYear()
        }
    }
    fun outSttockMonthSuccess(response: Response<ResponseStockKeluarMonth>) : ResponseStockKeluarMonth {
        return when(response.code()) {
            200 -> response.body() ?: ResponseStockKeluarMonth()
            400 -> ResponseStockKeluarMonth(message = Contant.ERROR_TEXT_400)
            401 -> ResponseStockKeluarMonth(message = response.body()?.message)
            404 -> ResponseStockKeluarMonth(message = Contant.ERROR_TEXT_404)
            else -> ResponseStockKeluarMonth()
        }
    }
    fun outSttockYearSuccess(response: Response<ResponseStockKeluarYear>) : ResponseStockKeluarYear {
        return when(response.code()) {
            200 -> response.body() ?: ResponseStockKeluarYear()
            400 -> ResponseStockKeluarYear(message = Contant.ERROR_TEXT_400)
            401 -> ResponseStockKeluarYear(message = response.body()?.message)
            404 -> ResponseStockKeluarYear(message = Contant.ERROR_TEXT_404)
            else -> ResponseStockKeluarYear()
        }
    }

}