package com.moviles.axoloferiaxml.data.network

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.RegisterAuth
import com.moviles.axoloferiaxml.data.model.RegisterUser
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

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

    suspend fun registerUser(user: RegisterAuth): RegisterUser? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserAPIClient::class.java).registerClient(user)
            if(response.isSuccessful) {
                response.body()
            }else{
                null
            }
        }
    }
}