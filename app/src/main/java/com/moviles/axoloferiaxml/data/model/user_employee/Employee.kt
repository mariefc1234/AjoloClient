package com.moviles.axoloferiaxml.data.model.user_employee

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
    val id_rol: Int
)
