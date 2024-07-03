package com.pinjamankoperasi

import com.pinjamankoperasi.data.repository.LoginRepository
import com.pinjamankoperasi.data.source.remote.ResponseLogin
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.network.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class LoginRepoUnitTest {

//    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiConfig : ApiConfig
    private lateinit var apiService : ApiService
    private lateinit var loginRepo : LoginRepository

    @Before
    fun setup() {
//        mockWebServer = MockWebServer()
//        mockWebServer.start()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("/")) // URL MockWebServer
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

        apiService = mock(ApiService::class.java)
        apiConfig = ApiConfig()
        apiConfig.server = apiService
        loginRepo = LoginRepository(apiConfig)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


//    @After
//    fun tearDown() {
//        mockWebServer.shutdown()
//    }

    @Test
    fun `login success`() = runTest {
        val responseLoginSuccess = Response.success(ResponseLogin(message = "Successkk", status = 200,dataLogin = null))

        val call = mock(Call::class.java) as Call<ResponseLogin>
        `when`(call.execute()).thenAnswer {
            responseLoginSuccess
        }

        // Setup dynamic behavior for apiService.login()
        `when`(apiService.login(anyString(), anyString())).thenAnswer {
            call
        }

        val result = loginRepo.login("111", "1234").dataLogin
        assertNotNull(result)
        assertEquals("Success", result?.jabatan)
    }

//    @Test
//    fun `login failed with password tidak sesuai`() = runTest {
//        val mockResponse = MockResponse()
//            .setResponseCode(401)
//            .setBody("""{"message":"Password Tidak Sesuai","status":0}""")
//        mockWebServer.enqueue(mockResponse)
//
//        val result = loginRepo.login("111", "12345")
//        assertNotNull(result)
//        assertEquals("Password Tidak Sesuai", result.message)
//    }
//
//    @Test
//    fun `login failed with nik tidak terdaftar`() = runTest {
//        val mockResponse = MockResponse()
//            .setResponseCode(401)
//            .setBody("""{"message":"Nomor Induk Tidak Terdaftar","status":0}""")
//        mockWebServer.enqueue(mockResponse)
//
//        val result = loginRepo.login("111", "12345")
//        assertNotNull(result)
//        assertEquals("Password Tidak Sesuai", result.message)
//    }


}