package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPIClient {
    @POST("auth/login")
    suspend fun authenticateUser(
        @Body params: UserAuth
    ): Response<User>
}