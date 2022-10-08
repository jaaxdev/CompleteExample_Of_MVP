package com.jaax.login.data.showusers

import com.jaax.login.data.db.RepositoryDB
import com.jaax.login.data.model.User
import com.jaax.login.data.network.UserService
import com.jaax.login.ui.ShowUsersActivity
import javax.inject.Inject

class ShowUsersPresenter @Inject constructor(
    private val view: ShowUsersActivity,
    repository: RepositoryDB,
    service: UserService
    ) : ShowUsersMVP.Presenter, ShowUsersMVP.Model.OnFinishedListener {

    private var model: ShowUsersMVP.Model? = null
    private var perPage = 0
    private var loadable = false

    init {
        model = ShowUsersModel(this, repository, service)
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

    override suspend fun logOut() {
        model!!.deleteSession()
        view.exit()
    }

    override suspend fun getEmail(): String {
        return model!!.getEmail()
    }

    override fun onFinished(users: List<User>) {
        view.showUsers(users)
    }
}