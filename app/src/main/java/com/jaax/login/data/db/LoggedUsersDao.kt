package com.jaax.login.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.jaax.login.data.model.LoggedUser
import com.jaax.login.data.model.Session

@Dao
interface LoggedUsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveToken(newUserLogin: LoggedUser)

    @Query("SELECT * FROM logged_users WHERE username = :username")
    suspend fun getLoggedUser(username: String): LoggedUser?

    @Insert(onConflict = REPLACE)
    suspend fun saveSessionToken(session: Session)

    @Update(onConflict = REPLACE)
    suspend fun updateSessionTolen(session: Session)

    @Query("SELECT * FROM sessions")
    suspend fun getLastTokenSaved(): Session?
}