package com.jaax.login.data.login

import com.jaax.login.data.db.RepositoryDB
import com.jaax.login.data.model.LoggedUser
import com.jaax.login.data.model.LoginRequestResponse
import com.jaax.login.data.model.Session
import com.jaax.login.data.model.UserRequest
import com.jaax.login.data.network.LoginService
import com.jaax.login.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginModel @Inject constructor(
    private val presenter: LoginPresenter,
    private val repository: RepositoryDB,
    private val service: LoginService
) : LoginMVP.Model {

    override suspend fun requestLogin() {
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
                    val loggedUser = LoggedUser(0, loginUser.username, response.body()!!.token)
                    val session = Session(0, loginUser.username, response.body()!!.token, true)

                    runBlocking(Dispatchers.IO) {
                        val lastSession = repository.getLastTokenSaved()

                        if(lastSession != null ) {
                            repository.saveToken(loggedUser)
                        } else {
                            repository.saveToken(loggedUser)
                            repository.saveSessionToken(session)
                        }
                    }
                    presenter.notifyLoginValid(true)
                } else {
                    presenter.notifyLoginInvalid(false)
                }
            }

            override fun onFailure(call: Call<LoginRequestResponse>, t: Throwable) {
                presenter.notifyError()
            }
        })
    }

    override suspend fun verifySession() {
        val session = repository.getLastTokenSaved()
        if(session != null) {
            presenter.notifySessionAlive(true)
        } else {
            presenter.notifySessionAlive(false)
        }
    }
}