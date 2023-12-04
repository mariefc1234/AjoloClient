package com.moviles.axoloferiaxml.data.network.user_employee

import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeAdd
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeDelete
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeEdit
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeEditResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object AppConstantes {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}
interface WebService {
    @GET("users/employeers")
    suspend fun getUsers(
        @Header("authtoken") token: String?
    ): Response<Employee>

    @POST("auth/registerAnUser")
    suspend fun addUsers(
        @Header("authtoken") token: String?,
        @Body employee: EmployeeAdd
    ): Response<Employee>

    @PUT("users/update/{uuid}")
    suspend fun updateUser(
        @Header("authtoken") token: String?,
        @Path("uuid") idUser: String,
        @Body user: EmployeeEdit
    ): Response<EmployeeEditResponse>

    @DELETE("auth/deleteClient/{uuid}")
    suspend fun borrarUsuario(
        @Header("authtoken") token: String?,
        @Path("uuid") uuid: String
    ): Response<EmployeeDelete>

}

object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}