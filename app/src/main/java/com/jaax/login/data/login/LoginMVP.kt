package com.jaax.login.data.login

interface LoginMVP {

    interface Model {
        suspend fun verifyData()
    }

    interface Presenter {
        fun notifyLoginValid(grant: Boolean)
        fun notifyLoginInvalid(grant: Boolean)
        suspend fun loginButtonClicked()
        fun provideUsername(): String
        fun providePassword(): String
    }

    interface View {
        fun getUsername(): String
        fun getPassword(): String
        fun grantAccess(granted: Boolean)
    }
}