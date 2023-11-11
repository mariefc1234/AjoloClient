package com.moviles.axoloferiaxml.data.network.user_employee

import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun registerEmployee(token: String, request: EmployeeRequest): Employee? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(EmployeeAPIClient::class.java).registerEmployee(token, request)
            if (response.isSuccessful) {
                response.body()
            } else {
                Employee(
                    status = "Error",
                    message = "Failed to register employee",
                    data = Employee.EmployeeData(
                        userInfo = Employee.EmployeeData.EmployeeInfo(
                            email = "",
                            idRol = 0,
                            username = "",
                            coins = 0,
                            imageUrl = "",
                            languageConfigured = ""
                        ),
                        isRegistered = false
                    )
                )
            }
        }
    }
}