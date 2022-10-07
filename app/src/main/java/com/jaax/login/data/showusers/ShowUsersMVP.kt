package com.jaax.login.data.showusers

import com.jaax.login.data.model.User

interface ShowUsersMVP {
    interface Model {
        suspend fun getListUsers(onFinishedListener: OnFinishedListener)

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
    }

    interface View {
        fun showUsers(list: List<User>)
        fun searchViewVisible()
    }
}