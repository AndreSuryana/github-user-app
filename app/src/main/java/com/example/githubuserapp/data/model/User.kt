package com.example.githubuserapp.data.model

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null
)