package com.pinjamankoperasi.network

import com.pinjamankoperasi.data.source.remote.ResponseGetListPinjaman
import com.pinjamankoperasi.data.source.remote.ResponseGetProduct
import com.pinjamankoperasi.data.source.remote.ResponseLogin
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.data.source.remote.ResponseRegister
import com.pinjamankoperasi.data.source.remote.ResponseStockKeluarMonth
import com.pinjamankoperasi.data.source.remote.ResponseStockKeluarYear
import com.pinjamankoperasi.data.source.remote.ResponseStockMasukMonth
import com.pinjamankoperasi.data.source.remote.ResponseStockMasukYear
import com.pinjamankoperasi.data.source.remote.ResponseUploadProduct
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("nomor_induk") nomorInduk : String,
        @Field("password") password : String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nomor_induk") nomorInduk : String,
        @Field("nama_admin") namaAdmin : String,
        @Field("jabatan") jabatan : String,
        @Field("password") password : String
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("pengajuan")
    fun pengajuanPinjaman(
        @Field("id_product") idProduct : Int,
        @Field("nik_karyawan") nikKaryawan : Int,
        @Field("nama_karyawan") namaKaryawan : String,
        @Field("jumlah_pinjam") jumlahPinjam : Int,
        @Field("harga") harga : Int,
    ) : Call<ResponsePinjaman>

    @FormUrlEncoded
    @POST("upload-product")
    fun uploadProduct(
        @Field("code_barang") codeBarang : String,
        @Field("nama_product") namaProduct : String,
        @Field("harga_jual") hargaJual : Int,
        @Field("stock_masuk") stockMasuk : Int,
    ) : Call<ResponseUploadProduct>

    @GET("product")
    fun getAllProduct() : Call<ResponseGetProduct>

    @GET("pinjaman/{tanggal}")
    fun getPinjaman(
        @Path("tanggal") tanggal: String
    ) : Call<ResponseGetListPinjaman>

    @GET("out-stock-year/{tahun}")
    fun getOutStockYear(
        @Path("tahun") tahun: Int) : Call<ResponseStockKeluarYear>

    @GET("out-stock-month/{bulan}/{tahun}")
    fun getOutStockMonth(
        @Path("bulan") bulan: String,
        @Path("tahun") tahun: Int
    ) : Call<ResponseStockKeluarMonth>

    @GET("stock-masuk/{bulan}/{tahun}")
    fun getStockMasukMonth(
        @Path("bulan") bulan: String,
        @Path("tahun") tahun: Int
    ) : Call<ResponseStockMasukMonth>

    @GET("stock-tahun/{tahun}")
    fun getStockMasukYear(
        @Path("tahun") tahun: Int
    ) : Call<ResponseStockMasukYear>


}