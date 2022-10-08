package com.jaax.login.data.showusers

import com.jaax.login.data.model.User

interface ShowUsersMVP {
    interface Model {
        suspend fun getListUsers(onFinishedListener: OnFinishedListener)
        suspend fun getEmail(): String
        suspend fun deleteSession()

        interface OnFinishedListener {
            fun onFinished(users: List<User>)
        }
    }

    interface Presenter {
        suspend fun requestUsers()
        fun getUsers(users: List<User>)
        fun userSelected(position: Int)
        fun setLoadable(canLoad: Boolean)
        fun getLoadable(): Boolean
        fun increasePerPage(increment: Int)
        fun getPerPage(): Int
        fun enableSearchview()
        suspend fun logOut()
        suspend fun getEmail(): String
    }

    interface View {
        fun showUsers(list: List<User>)
        fun searchViewVisible()
        fun exit()
    }
}