package com.jaax.login.data.showusers

import com.jaax.login.data.model.User
import com.jaax.login.ui.ShowUsersActivity
import com.jaax.login.util.Utils

class ShowUsersPresenter(private val view: ShowUsersActivity) : ShowUsersMVP.Presenter,
    ShowUsersMVP.Model.OnFinishedListener {

    private var model: ShowUsersMVP.Model? = null
    private var perPage = 0
    private var loadable = false

    init {
        model = ShowUsersModel(this)
    }

    override suspend fun requestUsers() {
        model!!.getListUsers(this)
    }

    override fun getUsers(users: List<User>) {
        view.showUsers(users)
    }

    override fun userSelected(position: Int) {
        TODO("Not yet implemented")
    }

    override fun setLoadable(canLoad: Boolean) {
        loadable = canLoad
    }

    override fun getLoadable(): Boolean {
        return loadable
    }

    override fun increasePerPage(increment: Int) {
        perPage += increment
    }

    override fun getPerPage(): Int {
        return perPage
    }

    override fun enableSearchview() {
        view.searchViewVisible()
    }

    override fun onFinished(users: List<User>) {
        view.showUsers(users)
    }
}