package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.StallRepository
import com.moviles.axoloferiaxml.data.UserRepository
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth

class CreateStallUseCase {
    private val repository = StallRepository()

    suspend operator fun invoke(stall: StallCreate, keystoreHelper: KeystoreHelper): GenericResponse? = repository.createStall(stall, keystoreHelper)
}