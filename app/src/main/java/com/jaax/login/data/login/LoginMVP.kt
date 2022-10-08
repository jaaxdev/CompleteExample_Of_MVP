package com.jaax.login.data.login

interface LoginMVP {

    interface Model {
        suspend fun requestLogin()
        suspend fun verifySession()
    }

    interface Presenter {
        fun notifyLoginValid(grant: Boolean)
        fun notifyLoginInvalid(grant: Boolean)
        fun notifyUnsuccessful()
        fun notifyError()
        suspend fun loginButtonClicked()
        fun provideUsername(): String
        fun providePassword(): String
        suspend fun verifySession()
        fun notifySessionAlive(isAlive: Boolean)
    }

    interface View {
        fun getUsername(): String
        fun getPassword(): String
        fun grantAccess(granted: Boolean)
        fun showUnsuccessfulMessage()
        fun showError()
        fun stateButton()
        fun initActivity(isSessionAlive: Boolean)
    }
}