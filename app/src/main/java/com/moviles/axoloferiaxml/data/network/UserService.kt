package com.moviles.axoloferiaxml.data.network

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

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

    suspend fun uploadImageUser(token: String, image: MultipartBody.Part): Int? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserAPIClient::class.java).uploadImageUser(token, image)
            if (response.isSuccessful) {
                response.code()
            } else {
                response.code()
            }
        }
    }
}