package com.jaax.login.data.showusers

import android.util.Log
import com.jaax.login.data.model.ResultsUser
import com.jaax.login.data.network.UserService
import com.jaax.login.data.showusers.ShowUsersMVP.Model.OnFinishedListener
import com.jaax.login.util.Utils
import com.jaax.login.util.Utils.Companion.PER_PAGE
import com.jaax.login.util.Utils.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowUsersModel(private val presenter: ShowUsersPresenter): ShowUsersMVP.Model {

    val service = Retrofit
        .Builder()
        .baseUrl(Utils.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserService::class.java)

    override suspend fun getListUsers(onFinishedListener: OnFinishedListener) {
        val call = service.getUsers(1, 12)

        call.enqueue(object : Callback<ResultsUser> {
            override fun onResponse(call: Call<ResultsUser>, response: Response<ResultsUser>) {
                presenter.setLoadable(false)
                if(response.isSuccessful){
                    val list = response.body()!!.data
                    onFinishedListener.onFinished(list)
                    presenter.enableSearchview()
                } else {
                    Log.i(TAG, "UNSUCCESS")
                }
            }

            override fun onFailure(call: Call<ResultsUser>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}