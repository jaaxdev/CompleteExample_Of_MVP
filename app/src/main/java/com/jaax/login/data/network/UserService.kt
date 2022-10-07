package com.jaax.login.data.network

import com.jaax.login.data.model.ResultsUser
import com.jaax.login.data.model.User
import com.jaax.login.util.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    fun getUsers(
        @Query(value = "page")
        page: Int,
        @Query(value = "per_page")
        per_page: Int
    ): Call<ResultsUser>
}