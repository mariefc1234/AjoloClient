package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.StallRepository
import com.moviles.axoloferiaxml.data.model.GenericResponse

class DeleteStallUseCase {
    private val repository = StallRepository()

    suspend operator fun invoke(keystoreHelper: KeystoreHelper, stallId: Int): GenericResponse? = repository.deleteStalls(keystoreHelper, stallId)
}