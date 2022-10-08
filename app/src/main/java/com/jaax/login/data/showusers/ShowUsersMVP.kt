package com.jaax.login.data.showusers

import com.jaax.login.data.model.User
import com.jaax.login.data.model.UserInfo

interface ShowUsersMVP {
    interface Model {
        suspend fun getListUsers(onFinishedListener: OnFinishedListener)
        suspend fun getEmail(): String
        suspend fun deleteSession()
        suspend fun getUserInfo(id: Int)

        interface OnFinishedListener {
            fun onFinished(users: List<User>)
        }
    }

    interface Presenter {
        suspend fun requestUsers()
        fun getUsers(users: List<User>)
        suspend fun userSelected(id: Int)
        fun setLoadable(canLoad: Boolean)
        fun getLoadable(): Boolean
        fun increasePerPage(increment: Int)
        fun getPerPage(): Int
        fun enableSearchview()
        fun notifyError()
        fun notifyUnsuccessful()
        fun setUserInfo(user: UserInfo)
        suspend fun logOut()
        suspend fun setItemEmail()
    }

    interface View {
        fun showUsers(list: List<User>)
        fun searchViewVisible()
        fun setTitleItemEmail(email: String)
        fun exit()
        fun showUnsuccessfulMessage()
        fun showErrorMessage()
        fun updateInfo(user: UserInfo)
    }
}