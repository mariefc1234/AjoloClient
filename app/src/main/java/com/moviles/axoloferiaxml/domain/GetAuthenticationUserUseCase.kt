package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.UserRepository
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth

class GetAuthenticationUserUseCase{
    private val repository = UserRepository()

    suspend operator fun invoke(userAuth: UserAuth, keystoreHelper: KeystoreHelper): User? = repository.authenticate(userAuth, keystoreHelper)
}