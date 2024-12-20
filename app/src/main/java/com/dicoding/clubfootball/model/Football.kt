package com.dicoding.clubfootball.model

data class Football(
    val id: Int,
    val image: Int,
    val name: String,
    val description: String,

    var isBookmark: Boolean = false
)
