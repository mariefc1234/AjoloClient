package com.moviles.axoloferiaxml.ui.more_user

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.ui.login.LoginActivity
import com.moviles.axoloferiaxml.ui.more_admin.MoreAdminViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class MoreUserFragment : Fragment() {

    private lateinit var viewModel: MoreUserViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val cameraActivityResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
        Log.d("MyApp", "Camera result: $result")
        if (result != null) {
            processCapturedImage(result)
        } else {
            // Captura de imagen cancelada o fallida.
            Toast.makeText(requireContext(), "Captura de imagen cancelada o fallida", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido, puedes proceder con la lógica de la cámara.
            openCamera()
        } else {
            // Permiso denegado, puedes manejarlo de acuerdo a tus necesidades.
            Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private val galleryPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openGallery()
        } else {
            Toast.makeText(requireContext(), "Permiso de galería denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            GALLERY_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, puedes acceder al archivo y procesar la imagen
                    openGallery()
                } else {
                    // Permiso denegado, puedes manejarlo de acuerdo a tus necesidades
                    Toast.makeText(requireContext(), "Permiso de galería denegado", Toast.LENGTH_SHORT).show()
                }
            }
            // Otros casos si tienes más permisos
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_more_user, container, false)

        //Funcion Logout


        val logoutButton = root.findViewById<CardView>(R.id.logoutButtonUser)
        logoutButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }

        //Asigna Username
        val textViewUsername = root.findViewById<TextView>(R.id.user_username)
        textViewUsername.text = (requireActivity() as MainActivityUser).getUserName()

        // Funcion para Comprar Ajolocoins
        val shopCoins = root.findViewById<LinearLayout>(R.id.shopCoinsButton)
        shopCoins.setOnClickListener {
            findNavController().navigate(R.id.action_moreUserFragment_to_shopCoinsUserFragment)
        }
        //Funcion Support
        val supportBtn = root.findViewById<LinearLayout>(R.id.btnSupport)
        supportBtn.setOnClickListener {
            findNavController().navigate(R.id.action_moreUserFragment_to_supportUserFragment)
        }

        //Funcion Languaje
        val languajeBtn = root.findViewById<LinearLayout>(R.id.language)
        languajeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_moreUserFragment_to_languageUserFragment4)
        }

        //Funcion shop History
        val historyReviewBtn = root.findViewById<LinearLayout>(R.id.historyReview)
        historyReviewBtn.setOnClickListener {
            findNavController().navigate(R.id.action_moreUserFragment_to_shoppingHistoryUserFragment)
        }

        val imageViewProfile = root.findViewById<ImageView>(R.id.user_image)
        viewModel = ViewModelProvider(this).get(MoreUserViewModel::class.java)
        viewModel.loadImageIntoImageView(imageViewProfile, (requireActivity() as MainActivityUser).getUserImgUrl())

        val editIcon = root.findViewById<ImageView>(R.id.editIcon)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { selectedImageUri ->
                    imageViewProfile.setImageURI(selectedImageUri)
                    val imagePath = getPathFromUri(requireContext(), selectedImageUri)
                    //Log.d("FilePath", "Image Path: $imagePath")
                    val imageFile = File(imagePath)
                    if (imageFile.exists()) {
                        context?.let {
                            //Log.d("SelectedImageUri", selectedImageUri.toString())
                            val imageMultipart: MultipartBody.Part? = viewModel.uriToMultipartBody(selectedImageUri, it)
                            if (imageMultipart != null) {
                                viewModel.viewModelScope.launch {
                                    try {
                                        val userUuid = (requireActivity() as MainActivityUser).getUserUuid()
                                        viewModel.updateProfileImageUri(userUuid, imageMultipart, it)

                                        Toast.makeText(requireContext(), "Imagen actualizada correctamente", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(requireContext(), "Error al actualizar la imagen: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Error al convertir la imagen", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Archivo de imagen no encontrado en la ruta: $imagePath", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        editIcon.setOnClickListener {
            checkCameraPermission()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val tuActividad = activity as MainActivityUser
        tuActividad.mostrarBarraNavegacion()
    }

    fun getPathFromUri(context: Context, uri: Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex: Int = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

    private fun showImageSelectionDialog() {
        val items = arrayOf("Cámara", "Galería")
        AlertDialog.Builder(requireContext())
            .setTitle("Seleccionar imagen")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> checkCameraPermission()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        cameraActivityResultLauncher.launch(null)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
        //val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //intent.addCategory(Intent.CATEGORY_OPENABLE)
        //intent.type = "image/*"
        //resultLauncher.launch(intent)
    }

    private fun processCapturedImage(result: Bitmap) {
        val imageMultipart: MultipartBody.Part? = viewModel.bitmapToMultipartBody(result, requireContext())
        if (imageMultipart != null) {
            viewModel.viewModelScope.launch {
                viewModel.updateProfileImageUri((requireActivity() as MainActivityUser).getUserUuid(), imageMultipart, requireContext())
            }
            val imageViewProfile = requireView().findViewById<ImageView>(R.id.user_image)
            imageViewProfile.setImageBitmap(result)

            Toast.makeText(requireContext(), "Todo bien", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error al convertir la imagen", Toast.LENGTH_SHORT).show()
        }
    }


    private val GALLERY_PERMISSION_REQUEST_CODE = 123

    private fun checkGalleryPermission() {
        /*if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permiso concedido, puedes acceder a la galería.
            openGallery()
        } else {
            // No se tiene el permiso, solicítalo.
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_REQUEST_CODE
            )
        }
         */
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    openGallery()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    Toast.makeText(requireContext(), "Permiso Denegado", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            })
            .check()
    }

    private fun checkGP() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_REQUEST_CODE
            )
        }
    }



    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

}