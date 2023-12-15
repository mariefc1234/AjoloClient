package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.StallRepository
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.StallCreate

class UpdateStallUseCase {
    private val repository = StallRepository()

    suspend operator fun invoke(stall: StallCreate, keystoreHelper: KeystoreHelper): GenericResponse? = repository.updateStall(stall, keystoreHelper)
}