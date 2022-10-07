package com.jaax.login.data.network

import com.jaax.login.data.model.LoginRequestResponse
import com.jaax.login.data.model.LoginRequest
import com.jaax.login.util.Utils
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST(Utils.BASEURL_LOGIN)
    fun requestToken(@Body loginRequest: LoginRequest): Call<LoginRequestResponse>
}