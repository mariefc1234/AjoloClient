package com.moviles.axoloferiaxml.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moviles.axoloferiaxml.data.model.Stall

@Entity
data class StallFavorite (
    @PrimaryKey
    val id: Int?,
    val id_stall_type: Int,
    val name: String,
    val description: String,
    val image_url: String?,
    val cost: Int,
    val minimun_height_cm: Int,
    val uuidEmployeer: String,
    val enabled: String?,
    val createdAt: String?,
    val updatedAt: String?
)