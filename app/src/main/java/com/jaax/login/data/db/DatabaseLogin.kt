package com.jaax.login.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jaax.login.data.model.LoggedUser
import com.jaax.login.data.model.Session

@Database(entities = [LoggedUser::class, Session::class], version = 1)
abstract class DatabaseLogin: RoomDatabase() {

    abstract fun loggedUsersDao(): LoggedUsersDao

    companion object {
        @Volatile
        private var instance: DatabaseLogin? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDB(context).also { instance = it }
        }

        private fun createDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseLogin::class.java,
                "edsa_db"
            ).build()
    }
}