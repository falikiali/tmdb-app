package com.falikiali.tmdbapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.falikiali.tmdbapp.data.local.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedia(mediaEntity: MediaEntity)

    @Delete
    suspend fun deleteMedia(mediaEntity: MediaEntity)

    @Query("SELECT EXISTS(SELECT * FROM medias WHERE id = :mediaId)")
    fun statusBookmarkMedia(mediaId: Int): Flow<Boolean>

    @Query("SELECT * FROM medias")
    fun getAllMedia(): PagingSource<Int, MediaEntity>

    @Query("SELECT * FROM medias WHERE type = :type")
    fun getAllMediaByType(type: String): PagingSource<Int, MediaEntity>

    @Query("SELECT * FROM medias WHERE title LIKE :query")
    fun searchMedia(query: String): PagingSource<Int, MediaEntity>

    @Query("SELECT * FROM medias WHERE type = :type AND title LIKE :query")
    fun searchMediaByType(type: String, query: String): PagingSource<Int, MediaEntity>

}