package com.jaax.login.data.model

data class LoginRequest(
    val username: String,
    val email: String,
    val password: String,
)