package com.moviles.axoloferiaxml.domain

import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.UserRepository
import okhttp3.MultipartBody

class GetUploadImageUser {
    private val repository = UserRepository()
    suspend operator fun invoke(keystoreHelper: KeystoreHelper, image: MultipartBody.Part, uuid: String): Boolean = repository.uploadImageUser(keystoreHelper, image, uuid)

}