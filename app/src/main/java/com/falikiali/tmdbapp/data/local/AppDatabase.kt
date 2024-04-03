package com.falikiali.tmdbapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.falikiali.tmdbapp.data.local.dao.MediaDao
import com.falikiali.tmdbapp.data.local.entity.MediaEntity

@Database(entities = [MediaEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun mediaDao(): MediaDao

}