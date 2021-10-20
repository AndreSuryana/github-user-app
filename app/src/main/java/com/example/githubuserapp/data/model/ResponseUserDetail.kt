package com.example.githubuserapp.data.model

data class ResponseUserDetail(
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val company: String?,
    val location: String?,
    val email: String?,
    val twitter_username: String?,
    val bio: String?,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)