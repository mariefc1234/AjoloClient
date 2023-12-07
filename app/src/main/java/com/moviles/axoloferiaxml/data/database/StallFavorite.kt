package com.moviles.axoloferiaxml.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StallFavorite (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val id_stall_type: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val cost: Int,
    val minimun_height_cm: Int,
    val uuidEmployeer: String,
    val isFavorite: Boolean
)