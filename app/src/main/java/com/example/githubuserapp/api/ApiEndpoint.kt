package com.example.githubuserapp.api

import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.data.model.ResponseUser
import com.example.githubuserapp.data.model.ResponseUserDetail
import com.example.githubuserapp.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUsersResult(@Query("q") q: String?): Call<ResponseUser>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUser(@Path("username") username: String): Call<ResponseUserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListFollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListFollowing(@Path("username") username: String): Call<ArrayList<User>>
}