package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.EmployeeRepository
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeRequest
import java.net.CacheResponse

class GetEmployeeRegisterUseCase {

    private val repository = EmployeeRepository()

    suspend operator fun invoke(keystoreHelper: KeystoreHelper, request: EmployeeRequest ): Employee? =
        repository.registerEmployee(keystoreHelper, request)
}
