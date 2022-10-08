package com.jaax.login.data.db

import com.jaax.login.data.model.LoggedUser
import com.jaax.login.data.model.Session
import javax.inject.Inject

class RepositoryDB @Inject constructor(private val db: DatabaseLogin) {

    suspend fun saveToken(newUserLogin: LoggedUser) = db.loggedUsersDao().saveToken(newUserLogin)

    suspend fun saveSessionToken(session: Session) = db.loggedUsersDao().saveSessionToken(session)

    suspend fun deleteSession() = db.loggedUsersDao().deleteSession()

    suspend fun getLastTokenSaved() = db.loggedUsersDao().getLastTokenSaved()
}