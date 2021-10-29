package com.example.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiService
import com.example.githubuserapp.data.model.ResponseUser
import com.example.githubuserapp.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val users = MutableLiveData<ArrayList<User>>()
    private val listUsers: LiveData<ArrayList<User>>
        get() = users

    var status = MutableLiveData<Boolean>()

    fun setSearchUsersResult(searchKeyword: String?) {
        ApiService.endPoint
            .getSearchUsersResult(searchKeyword)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    if (response.isSuccessful) {
                        users.postValue(response.body()?.items)
                        status.postValue(true)
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    status.postValue(false)
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getSearchUserResult(): LiveData<ArrayList<User>> {
        return listUsers
    }
}