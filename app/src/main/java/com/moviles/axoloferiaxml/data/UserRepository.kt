package com.moviles.axoloferiaxml.data

import android.util.Log
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.data.model.EmployeeProvider
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.data.model.UserAuth
import com.moviles.axoloferiaxml.data.model.UserProvider
import com.moviles.axoloferiaxml.data.network.UserService

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
}