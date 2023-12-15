package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper

import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.data.model.EmployeeProvider
import com.moviles.axoloferiaxml.data.model.RegisterAuth
import com.moviles.axoloferiaxml.data.model.RegisterProvider
import com.moviles.axoloferiaxml.data.model.RegisterUser
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import com.moviles.axoloferiaxml.data.model.UserProvider
import com.moviles.axoloferiaxml.data.network.UserService
import okhttp3.MultipartBody

class UserRepository {
    private val api = UserService()

    suspend fun authenticate(userAuth: UserAuth, keystoreHelper: KeystoreHelper): User? {

        val response = api.authenticateUser(userAuth = userAuth)
        if (response != null) {
            UserProvider.user = response
            keystoreHelper.saveToken(response.userData?.token ?: "")
        }
        return response
    }

    suspend fun getUsersByRole(roleId: Int, keystoreHelper: KeystoreHelper): Employee? {
        val token = keystoreHelper.getToken()
        val response = api.getUsersByRole(token ?: "", roleId)
        if (response != null) {
            EmployeeProvider.user = response
        }
        return response
    }

    suspend fun uploadImageUser(keystoreHelper: KeystoreHelper, image: MultipartBody.Part, uuid: String): Boolean{
        val token = keystoreHelper.getToken()
        if (token.isNullOrEmpty() || token.isNullOrBlank()){
            Log.d("error", "el token sigue siendo nulo $token")
        }
        val response = api.uploadImageUser(token ?: "", image = image, uuid)
        if (response != 200) {
            return  false
        }
        return true

    }

    suspend fun registerUser(user: RegisterAuth) : RegisterUser? {
        val response = api.registerUser(user)
        if(response != null) {
            RegisterProvider.user = response
        }
        return response
    }
}