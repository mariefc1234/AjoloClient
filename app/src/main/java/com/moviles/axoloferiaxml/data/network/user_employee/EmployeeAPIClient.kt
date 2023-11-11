package com.moviles.axoloferiaxml.data.network.user_employee

import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.net.CacheResponse


interface EmployeeAPIClient {

    @POST("auth/registerAnUser")
    suspend fun registerEmployee(
        @Header("authtoken") token: String,
        @Body params: EmployeeRequest
    ): Response<Employee>
}
