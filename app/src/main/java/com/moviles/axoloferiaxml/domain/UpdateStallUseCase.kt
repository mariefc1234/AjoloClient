package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.StallRepository
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.StallCreate
import com.moviles.axoloferiaxml.data.model.StallUpdate

class UpdateStallUseCase {
    private val repository = StallRepository()

    suspend operator fun invoke(stall: StallUpdate, keystoreHelper: KeystoreHelper): GenericResponse? = repository.updateStall(stall, keystoreHelper)
}