package com.example.githubuserapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String?,
    val username: String?,
    val avatar: Int,
    val company: String?,
    val location: String?,
    val repository: Int,
    val followers: Int,
    val following: Int
) : Parcelable