package com.pinjamankoperasi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.pinjamankoperasi.utils.Contant

class ApiConfig {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Contant.APP_URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var server : ApiService = retrofit.create(ApiService::class.java)
}