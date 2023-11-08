package com.moviles.axoloferiaxml.core

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class KeystoreHelper(private val context: Context) {

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
    private val keyAlias = "user_token"

    init {
        keyStore.load(null)
    }

    fun saveToken(token: String) {
        val key = generateKeyIfNeeded()

        val cipher = Cipher.getInstance("${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key)

            // Generar un IV aleatorio
            val iv = cipher.iv

            val encryptedToken = cipher.doFinal(token.toByteArray(Charsets.UTF_8))

            // Guardar el IV y el token cifrado en SharedPreferences de manera segura.
            val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
            val encryptedTokenString = Base64.encodeToString(encryptedToken, Base64.DEFAULT)
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("iv", ivString).apply()
            sharedPreferences.edit().putString("encrypted_token", encryptedTokenString).apply()
        } catch (e: Exception) {
            // Manejar la excepción
            e.printStackTrace()
        }
    }

    fun getToken(): String? {
        val key = keyStore.getKey(keyAlias, null) as SecretKey?

        if (key != null) {
            val cipher = Cipher.getInstance("${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")

            try {
                // Recuperar el IV desde SharedPreferences.
                val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                val ivString = sharedPreferences.getString("iv", null)
                val iv = Base64.decode(ivString, Base64.DEFAULT)

                val ivSpec = IvParameterSpec(iv)
                cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)

                // Recuperar la cadena cifrada desde SharedPreferences.
                val encryptedTokenString = sharedPreferences.getString("encrypted_token", null)

                if (encryptedTokenString != null) {
                    val encryptedToken = Base64.decode(encryptedTokenString, Base64.DEFAULT)
                    val decryptedTokenBytes = cipher.doFinal(encryptedToken)
                    val token = String(decryptedTokenBytes, Charsets.UTF_8)
                    return token
                }
            } catch (e: Exception) {
                // Manejar la excepción
                e.printStackTrace()
            }
        }

        return null
    }

    private fun generateKeyIfNeeded(): SecretKey {
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(false)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            return keyGenerator.generateKey()
        } else {
            return keyStore.getKey(keyAlias, null) as SecretKey
        }
    }
}
