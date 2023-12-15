package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.UserRepository
import com.moviles.axoloferiaxml.data.model.Employee

class GetUsersByRoleListUseCase {

    private val repository = UserRepository()

    suspend operator fun invoke(roleId: Int, keystoreHelper: KeystoreHelper): Employee? = repository.getUsersByRole(roleId, keystoreHelper)
}