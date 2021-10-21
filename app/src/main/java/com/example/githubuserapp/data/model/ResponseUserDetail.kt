package com.example.githubuserapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseUserDetail(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("twitter_username")
    val twitterUsername: String?,

    @field:SerializedName("bio")
    val bio: String?,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int
)