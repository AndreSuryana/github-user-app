package com.example.githubuserapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("items")
    val items: ArrayList<User>
)
