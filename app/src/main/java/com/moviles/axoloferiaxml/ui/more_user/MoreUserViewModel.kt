package com.moviles.axoloferiaxml.ui.more_user

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.domain.GetUploadImageUser
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MoreUserViewModel: ViewModel() {

    private val getUploadImageUser = GetUploadImageUser()

    fun loadImageIntoImageView(imageView: ImageView, imgUrl: String) {
        Picasso.get().load(imgUrl).into(imageView)
    }

    fun uriToMultipartBody(uri: Uri, context: Context): MultipartBody.Part? {
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val mimeType: String? = contentResolver.getType(uri)
            if (mimeType == null) {
                // Manejar el caso en que no se pueda obtener el tipo MIME
                return null
            }
            val file: File = File(uri.path ?: "")
            val fileExtension: String = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())

            val requestBody: RequestBody = RequestBody.create(MediaType.parse(mimeType), file)
            return MultipartBody.Part.createFormData("file", "image.$fileExtension", requestBody)
        } catch (e: IOException) {
            // Manejar la excepción según sea necesario
            return null
        }
    }

    fun bitmapToMultipartBody(bitmap: Bitmap, context: Context): MultipartBody.Part? {
        try {
            val file = File(context.cacheDir, "image.jpg") // Crear un archivo temporal en la caché
            file.createNewFile()

            // Convertir el Bitmap a un archivo
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bitmapData = bos.toByteArray()

            val fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()

            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)

            return MultipartBody.Part.createFormData("file", file.name, requestBody)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }


    suspend fun updateProfileImageUri(uuid: String, image: MultipartBody.Part, context: Context) {
        val keystoreHelper = KeystoreHelper(context)
        val result = getUploadImageUser(keystoreHelper, image, uuid)
        Log.d("IMGURL_VM", "updateProfileImageUri result: $result")
    }

    private var capturedImageUri: Uri? = null


}