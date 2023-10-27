package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun authenticateUser(userAuth: UserAuth): User? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserAPIClient::class.java).authenticateUser(userAuth)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}