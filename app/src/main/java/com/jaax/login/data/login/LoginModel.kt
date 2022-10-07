package com.jaax.login.data.login

import android.util.Log
import com.jaax.login.data.model.LoginRequestResponse
import com.jaax.login.data.model.UserRequest
import com.jaax.login.data.network.LoginService
import com.jaax.login.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginModel @Inject constructor(
    private val presenter: LoginPresenter, private val service: LoginService
): LoginMVP.Model {

    override suspend fun verifyData() {
        val loginUser = UserRequest(
            presenter.provideUsername(),
            presenter.provideUsername(),
            presenter.providePassword()
        )
        val call = service.requestToken(loginUser)

        call.enqueue(object : Callback<LoginRequestResponse> {
            override fun onResponse(
                call: Call<LoginRequestResponse>,
                response: Response<LoginRequestResponse>
            ) {
                if (response.isSuccessful) {
                    presenter.notifyLoginValid(true)
                } else {
                    presenter.notifyLoginInvalid(false)
                }
            }

            override fun onFailure(call: Call<LoginRequestResponse>, t: Throwable) {
                Log.e(Utils.TAG, t.message.toString())
            }
        })
    }
}