package com.jaax.login.data.register

interface RegisterMVP {

    interface Model {
        suspend fun requestRegister()
    }

    interface Presenter {
        fun notifyRegisterValid(registered: Boolean)
        fun notifyRegisterInvalid(registered: Boolean)
        suspend fun registerButtonClicked()
        fun notifyUnsuccessful()
        fun notifyError()
        fun provideUsername(): String
        fun providePassword(): String
    }

    interface View {
        fun getUsername(): String
        fun getPassword(): String
        fun showUnsuccessfulMessage()
        fun showError()
        fun userRegistered(registered: Boolean)
    }
}