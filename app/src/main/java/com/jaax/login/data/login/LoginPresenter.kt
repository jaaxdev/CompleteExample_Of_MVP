package com.jaax.login.data.login

import com.jaax.login.data.db.RepositoryDB
import com.jaax.login.data.network.LoginService
import com.jaax.login.ui.LoginActivity
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val view: LoginActivity, repository: RepositoryDB, service: LoginService
) : LoginMVP.Presenter {

    private var model: LoginMVP.Model? = null

    init {
        model = LoginModel(this, repository, service)
    }

    override fun notifyLoginValid(grant: Boolean) {
        view.grantAccess(true)
    }

    override fun notifyLoginInvalid(grant: Boolean) {
        view.grantAccess(false)
    }

    override fun notifyUnsuccessful() {
        view.showUnsuccessfulMessage()
    }

    override fun notifyError() {
        view.showError()
    }

    override suspend fun loginButtonClicked() {
        view.stateButton()
        if(view.getUsername().trim() != "" && view.getPassword().trim() != "") {
            model!!.requestLogin()
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

    override suspend fun verifySession() {
        model!!.verifySession()
    }

    override fun notifySessionAlive(isAlive: Boolean) {
        view.initActivity(isAlive)
    }
}