package com.example.githubuserapp.ui.userdetail.fragment.information

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.api.ApiService
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.data.local.FavoriteUserDao
import com.example.githubuserapp.data.local.FavoriteUserDatabase
import com.example.githubuserapp.data.model.ResponseUserDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteUserDatabase: FavoriteUserDatabase? = null
    private var favoriteUserDao: FavoriteUserDao? = null

    private val userDetail = MutableLiveData<ResponseUserDetail>()
    val user: LiveData<ResponseUserDetail>
        get() = userDetail

    init {
        favoriteUserDatabase = FavoriteUserDatabase.getDatabase(application)
        favoriteUserDao = favoriteUserDatabase?.favoriteUserDao()
    }

    fun setUserDetailData(username: String?) {
        ApiService.endPoint
            .getDetailUser(username.toString())
            .enqueue(object : Callback<ResponseUserDetail> {
                override fun onResponse(
                    call: Call<ResponseUserDetail>,
                    response: Response<ResponseUserDetail>
                ) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetailData(): LiveData<ResponseUserDetail> {
        return user
    }

    suspend fun addFavoriteUser(id: Int?, username: String?, avatarUrl: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val favUser = FavoriteUser(id, username, avatarUrl)
            favoriteUserDao?.addFavoriteUser(favUser)
        }
    }

    suspend fun checkFavoriteUsers(id: Int?) = favoriteUserDao?.checkFavoriteUsers(id)

    fun deleteFavoriteUser(id: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.deleteFavoriteUser(id)
        }
    }
}