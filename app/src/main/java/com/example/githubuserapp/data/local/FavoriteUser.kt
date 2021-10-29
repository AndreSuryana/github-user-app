package com.example.githubuserapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(

    @PrimaryKey
    val id: Int?,

    val login: String?,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?
) : Serializable
