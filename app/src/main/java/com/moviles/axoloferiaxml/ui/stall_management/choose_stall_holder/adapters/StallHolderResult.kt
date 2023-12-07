package com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder.adapters

import com.moviles.axoloferiaxml.data.model.Employee


data class StallHolderResult (
    val success: Employee? = null,
    val error: Int? = null
)