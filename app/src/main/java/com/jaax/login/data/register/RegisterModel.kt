package com.jaax.login.data.register

import com.jaax.login.data.model.RegisterRequestResponse
import com.jaax.login.data.model.UserRequest
import com.jaax.login.data.network.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterModel @Inject constructor(
    private val presenter: RegisterPresenter, private val service: RegisterService
): RegisterMVP.Model {

    override suspend fun requestRegister() {
        val newUser = UserRequest(
            presenter.provideUsername(),
            presenter.provideUsername(),
            presenter.providePassword()
        )
        val call = service.requestRegister(newUser)

        call.enqueue(object : Callback<RegisterRequestResponse>{
            override fun onResponse(
                call: Call<RegisterRequestResponse>,
                response: Response<RegisterRequestResponse>
            ) {
                if (response.isSuccessful) {
                    presenter.notifyRegisterValid(true)
                } else {
                    presenter.notifyRegisterInvalid(false)
                }
            }

            override fun onFailure(call: Call<RegisterRequestResponse>, t: Throwable) {
                presenter.notifyError()
            }
        })
    }
}