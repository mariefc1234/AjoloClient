package com.moviles.axoloferiaxml.data.network

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.core.RetrofitHelper
import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.data.model.RegisterAuth
import com.moviles.axoloferiaxml.data.model.RegisterUser
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
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

    suspend fun getUsersByRole(token: String, roleId: Int): Employee? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserAPIClient::class.java).getUsersByRole(token, roleId)
            if (response.isSuccessful) {
                response.body()
            }else{
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

    
    suspend fun uploadImageUser(token: String, image: MultipartBody.Part, uuid: String): Int? {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(UserAPIClient::class.java).uploadImageUser(token, image, uuid)
                Log.d("RESPUESTA_DEL_SERVIDOR", response.body().toString())
                if (response.isSuccessful) {
                    Log.d("CODIGO_SIisSucce", response.code().toString())
                    return@withContext response.code()
                } else {
                    Log.d("CODIGO_SINOisSuc", response.code().toString())
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("ERROR_DE_RED", "Error en la solicitud de red", e)
                return@withContext null
            }
        }
    }
}