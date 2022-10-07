package com.jaax.login.data.network

import com.jaax.login.data.model.RegisterRequestResponse
import com.jaax.login.data.model.UserRequest
import com.jaax.login.util.Utils
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST(Utils.BASEURL.plus("register"))
    fun requestRegister(@Body registerRequest: UserRequest): Call<RegisterRequestResponse>
}