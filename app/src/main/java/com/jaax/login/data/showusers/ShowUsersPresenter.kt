package com.jaax.login.data.showusers

import com.jaax.login.data.db.RepositoryDB
import com.jaax.login.data.model.User
import com.jaax.login.data.model.UserInfo
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

    override suspend fun userSelected(id: Int) {
        model!!.getUserInfo(id)
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

    override fun notifyError() {
        view.showErrorMessage()
    }

    override fun notifyUnsuccessful() {
        view.showUnsuccessfulMessage()
    }

    override fun setUserInfo(user: UserInfo) {
        view.updateInfo(user)
    }

    override suspend fun logOut() {
        model!!.deleteSession()
        view.exit()
    }

    override suspend fun setItemEmail() {
        val email = model!!.getEmail()
        view.setTitleItemEmail(email)
    }

    override fun onFinished(users: List<User>) {
        view.showUsers(users)
    }
}