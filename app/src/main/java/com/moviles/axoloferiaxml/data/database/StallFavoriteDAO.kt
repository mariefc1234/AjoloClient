package com.moviles.axoloferiaxml.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StallFavoriteDAO {
    @Query("SELECT * FROM StallFavorite")
    suspend fun getAllStalls(): List<StallFavorite>

    @Insert
    suspend fun insert(stall: List<StallFavorite>)

    @Query("SELECT * FROM StallFavorite WHERE id = :id")
    suspend fun getStallByID(id: Int): StallFavorite

    @Query("SELECT * FROM StallFavorite WHERE isFavorite = 1")
    suspend fun getFavoriteStalls(): MutableList<StallFavorite>
}