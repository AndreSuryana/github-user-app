package com.example.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class FavoriteUserDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        private var INSTANCE: FavoriteUserDatabase? = null

        fun getDatabase(context: Context): FavoriteUserDatabase? {
            if (INSTANCE == null) {
                // If there is no database, then we create database
                synchronized(FavoriteUserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteUserDatabase::class.java, "favorite_database").build()
                }
            }
            return INSTANCE
        }
    }
}