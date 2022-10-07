package com.jaax.login.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logged_users")
data class LoggedUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val username: String,
    val token: String
)
