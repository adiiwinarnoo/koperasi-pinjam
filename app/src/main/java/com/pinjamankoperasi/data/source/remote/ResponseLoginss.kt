package com.pinjamankoperasi.data.source.remote

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("dataLogin")
	val dataLogin: DataLogin? = null,

)

data class DataLogin(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nomor_induk")
	val nomorInduk: Int? = null,

	@field:SerializedName("jabatan")
	val jabatan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nama_admin")
	val namaAdmin: String? = null
)
