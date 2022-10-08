package com.jaax.login.data.showusers

import android.util.Log
import com.jaax.login.data.db.RepositoryDB
import com.jaax.login.data.model.ResultsUser
import com.jaax.login.data.model.UserInfo
import com.jaax.login.data.network.UserService
import com.jaax.login.data.showusers.ShowUsersMVP.Model.OnFinishedListener
import com.jaax.login.util.Utils.Companion.PER_PAGE
import com.jaax.login.util.Utils.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ShowUsersModel @Inject constructor(
    private val presenter: ShowUsersPresenter,
    private val repository: RepositoryDB,
    private val service: UserService
): ShowUsersMVP.Model {

    override suspend fun getListUsers(onFinishedListener: OnFinishedListener) {
            val call = service.getUsers(presenter.getCurrentPage(), PER_PAGE)
            presenter.setLoading(true)

            call.enqueue(object : Callback<ResultsUser> {
                override fun onResponse(call: Call<ResultsUser>, response: Response<ResultsUser>) {

                    if(response.isSuccessful){
                        val data = response.body()!!
                        presenter.setTotalPages(data.total_pages)
                        onFinishedListener.onFinished(data.data)
                        presenter.visibleProgressBar()
                        presenter.setLoading(false)
                    } else {
                        presenter.setLoading(false)
                        presenter.notifyUnsuccessful()
                    }
                }

                override fun onFailure(call: Call<ResultsUser>, t: Throwable) {
                    presenter.setLoading(false)
                    presenter.notifyError()
                }
            })
    }

    override suspend fun getEmail(): String {
        return repository.getLastTokenSaved()!!.username
    }

    override suspend fun deleteSession() {
        repository.deleteSession()
    }

    override suspend fun getUserInfo(id: Int) {
        val call = service.getUserInfo(id)

        call.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful) {
                    presenter.setUserInfo(response.body()!!)
                } else {
                    presenter.notifyUnsuccessful()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                presenter.notifyError()
            }

        })
    }
}