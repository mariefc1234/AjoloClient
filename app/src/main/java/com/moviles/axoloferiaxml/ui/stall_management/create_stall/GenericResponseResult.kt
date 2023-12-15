package com.moviles.axoloferiaxml.ui.stall_management.create_stall

import com.moviles.axoloferiaxml.data.model.GenericResponse


data class GenericResponseResult(
    val success: GenericResponse? = null,
    val error: Int? = null
)