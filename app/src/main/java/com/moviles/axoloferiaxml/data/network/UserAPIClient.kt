package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPIClient {
    @POST("auth/login")
    suspend fun authenticateUser(
        @Body params: UserAuth
    ): Response<User>


    @GET("users/getByRole/{roleId}")
    suspend fun getUsersByRole(
        @Header("authtoken") token: String,
        @Path("roleId") roleId: Int
    ): Response<Employee>

    @POST("users/image")
    suspend fun uploadImageUser(
        @Header("authtoken") token: String,
        @Body Key: MultipartBody.Part
    ): Response<Int>

}