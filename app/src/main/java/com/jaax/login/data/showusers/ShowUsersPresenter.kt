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
    private var currentPage = 1
    private var totalPages = 0
    private var perPage = 8
    private var isLoading = false

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

    override fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    override fun getIsLoading(): Boolean {
        return this.isLoading
    }

    override fun increaseCurrentPage() {
        currentPage++
    }

    override fun getCurrentPage(): Int {
        return currentPage
    }

    override fun setTotalPages(total: Int) {
        totalPages = total
    }

    override fun getTotalPages(): Int {
        return totalPages
    }

    override fun itemsPerPage(): Int {
        return perPage
    }

    override fun visibleProgressBar() {
        view.visibleProgressbar()
    }

    override fun notifyError() {
        view.showErrorMessage()
    }

    override fun notifyUnsuccessful() {
        view.showUnsuccessfulMessage()
    }

    override fun notifyNoMoreData() {
        view.showNoDataFoundMessage()
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

    override fun onSetTotalItems(total: Int) {
        totalPages = total
    }
}