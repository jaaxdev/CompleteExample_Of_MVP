package com.jaax.login.data.db

import com.jaax.login.data.model.LoggedUser
import com.jaax.login.data.model.Session
import javax.inject.Inject

class RepositoryDB @Inject constructor(private val db: DatabaseLogin) {

    suspend fun saveToken(newUserLogin: LoggedUser) = db.loggedUsersDao().saveToken(newUserLogin)

    suspend fun getLoggedUser(username: String) = db.loggedUsersDao().getLoggedUser(username)

    suspend fun saveSessionToken(session: Session) = db.loggedUsersDao().saveSessionToken(session)

    suspend fun updateSessionTolen(session: Session) = db.loggedUsersDao().updateSessionTolen(session)

    suspend fun getLastTokenSaved() = db.loggedUsersDao().getLastTokenSaved()
}