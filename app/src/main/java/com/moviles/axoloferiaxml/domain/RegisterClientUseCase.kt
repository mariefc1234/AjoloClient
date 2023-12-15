package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.data.UserRepository
import com.moviles.axoloferiaxml.data.model.RegisterAuth
import com.moviles.axoloferiaxml.data.model.RegisterUser
import com.moviles.axoloferiaxml.data.model.User

class RegisterClientUseCase {
    private val repository = UserRepository()
    suspend operator fun invoke(user: RegisterAuth): RegisterUser? = repository.registerUser(user)
}