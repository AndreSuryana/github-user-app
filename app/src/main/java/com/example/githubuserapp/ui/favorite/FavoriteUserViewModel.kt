package com.example.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.data.local.FavoriteUserDao
import com.example.githubuserapp.data.local.FavoriteUserDatabase

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteUserDatabase: FavoriteUserDatabase? = null
    private var favoriteUserDao: FavoriteUserDao? = null

    init {
        favoriteUserDatabase = FavoriteUserDatabase.getDatabase(application)
        favoriteUserDao = favoriteUserDatabase?.favoriteUserDao()
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>? {
        return favoriteUserDao?.getFavoriteUsers()
    }
}