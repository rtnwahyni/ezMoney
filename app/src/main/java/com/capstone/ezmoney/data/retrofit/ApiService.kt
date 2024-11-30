package com.capstone.ezmoney.data.retrofit

import com.capstone.ezmoney.data.request.LoginRequest
import com.capstone.ezmoney.data.request.RegisterRequest
import com.capstone.ezmoney.data.response.LoginResponse
import com.capstone.ezmoney.data.response.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}
