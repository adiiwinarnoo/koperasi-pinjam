package com.pinjamankoperasi.data.source.remote

import com.google.gson.annotations.SerializedName

data class ResponsePinjaman(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("dataPinjaman")
	val dataPinjaman: DataPinjaman? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataPinjaman(

	@field:SerializedName("jumlah_pinjam")
	val jumlahPinjam: String? = null,

	@field:SerializedName("id_product")
	val idProduct: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nama_karyawan")
	val namaKaryawan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("nama_product")
	val namaProduct: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nik_karyawan")
	val nikKaryawan: String? = null
)
