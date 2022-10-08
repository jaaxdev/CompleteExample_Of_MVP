package com.jaax.login.data.register

import com.jaax.login.data.network.RegisterService
import com.jaax.login.ui.RegisterActivity
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val view: RegisterActivity, service: RegisterService
): RegisterMVP.Presenter {
    private var model: RegisterMVP.Model? = null

    init {
        model = RegisterModel(this, service)
    }

    override fun notifyRegisterValid(registered: Boolean) {
        view.userRegistered(true)
    }

    override fun notifyRegisterInvalid(registered: Boolean) {
        view.userRegistered(false)
    }

    override suspend fun registerButtonClicked() {
        if(view.getUsername().trim() != "" && view.getPassword().trim() != "") {
            model!!.requestRegister()
        } else {
            view.userRegistered(false)
        }
    }

    override fun notifyUnsuccessful() {
        view.showUnsuccessfulMessage()
    }

    override fun notifyError() {
        view.showError()
    }

    override fun provideUsername(): String {
        return view.getUsername()
    }

    override fun providePassword(): String {
        return view.getPassword()
    }
}