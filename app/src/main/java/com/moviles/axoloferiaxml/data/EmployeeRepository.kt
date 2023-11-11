package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeProvider
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeRequest
import com.moviles.axoloferiaxml.data.network.user_employee.EmployeeService

class EmployeeRepository{

    private val api = EmployeeService()

    suspend fun registerEmployee(keystoreHelper: KeystoreHelper, request: EmployeeRequest): Employee? {
        val token = keystoreHelper.getToken().toString()
        val request = request
        val response = api.registerEmployee(token, request)
        Log.d("aiuda", token)
        Log.d("request", request.toString())
        Log.d("response", response.toString())
        if (response != null) {
            EmployeeProvider.employee = response
        }
        return response
    }
}

