package com.moviles.axoloferiaxml.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StallFavoriteDAO {
    @Query("SELECT * FROM StallFavorite")
    suspend fun getAllStalls(): List<StallFavorite>

    @Insert
    suspend fun insert(stall: StallFavorite)

    @Delete
    suspend fun remove(stall: StallFavorite)
//
//    @Query("DELETE FROM StallFavorite WHERE id = :id")
//    suspend fun removeByID(id: Int)

    @Query("SELECT * FROM StallFavorite WHERE id = :id")
    suspend fun getStallByID(id: Int): StallFavorite

}