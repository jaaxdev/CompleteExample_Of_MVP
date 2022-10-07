package com.jaax.login.data.model

data class ResultsUser(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val `data`: List<User>
)