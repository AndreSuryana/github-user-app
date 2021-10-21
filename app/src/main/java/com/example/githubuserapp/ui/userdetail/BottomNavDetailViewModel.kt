package com.example.githubuserapp.ui.userdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiService
import com.example.githubuserapp.data.model.ResponseUserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomNavDetailViewModel : ViewModel() {

    private val userDetail = MutableLiveData<ResponseUserDetail>()
    val user: LiveData<ResponseUserDetail>
        get() = userDetail

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
}