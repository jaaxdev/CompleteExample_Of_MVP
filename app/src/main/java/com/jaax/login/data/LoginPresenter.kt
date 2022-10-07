package com.jaax.login.data

import android.util.Log
import com.jaax.login.data.network.LoginService
import com.jaax.login.ui.LoginActivity
import com.jaax.login.util.Utils
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val view: LoginActivity, service: LoginService
): LoginMVP.Presenter {

    private var model: LoginModel? = null

    init {
        model = LoginModel(this, service)
    }

    override fun notifyLoginValid(grant: Boolean) {
        view.grantAccess(true)
    }

    override fun notifyLoginInvalid(grant: Boolean) {
        view.grantAccess(false)
    }

    override fun loginButtonClicked() {
        if( view.getUsername().trim() != "" && view.getPassword().trim() != "") {
            runBlocking { model!!.verifyData() }
        } else {
            view.grantAccess(false)
        }
    }

    override fun provideUsername(): String {
        return view.getUsername()
    }

    override fun providePassword(): String {
        return view.getPassword()
    }
}