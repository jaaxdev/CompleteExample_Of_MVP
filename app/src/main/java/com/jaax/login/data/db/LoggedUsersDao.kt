package com.jaax.login.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.jaax.login.data.model.LoggedUser
import com.jaax.login.data.model.Session

@Dao
interface LoggedUsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveToken(newUserLogin: LoggedUser)

    @Insert(onConflict = REPLACE)
    suspend fun saveSessionToken(session: Session)

    @Query("DELETE FROM sessions")
    suspend fun deleteSession()

    @Query("SELECT * FROM sessions")
    suspend fun getLastTokenSaved(): Session?
}