package com.jaax.login.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val username: String,
    @ColumnInfo(name = "last_token")
    val lastTokenLoggedIn: String,
    val isSessionAlive: Boolean
)
