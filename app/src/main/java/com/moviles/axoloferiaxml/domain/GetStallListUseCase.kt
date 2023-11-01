package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.data.StallRepository
import com.moviles.axoloferiaxml.data.UserRepository
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth

class GetStallListUseCase{
    private val repository = StallRepository()

    suspend operator fun invoke(token: String): Stall? = repository.getStalls(token)
}