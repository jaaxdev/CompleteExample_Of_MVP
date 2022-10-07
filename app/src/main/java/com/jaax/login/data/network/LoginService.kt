package com.jaax.login.data.network

import com.jaax.login.data.model.LoginRequestResponse
import com.jaax.login.data.model.UserRequest
import com.jaax.login.util.Utils
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST(Utils.BASEURL.plus("login"))
    fun requestToken(@Body loginRequest: UserRequest): Call<LoginRequestResponse>
}