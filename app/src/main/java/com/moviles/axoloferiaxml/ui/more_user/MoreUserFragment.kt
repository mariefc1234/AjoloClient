package com.moviles.axoloferiaxml.ui.more_user

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.ui.login.LoginActivity
import com.moviles.axoloferiaxml.ui.more_admin.MoreAdminViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

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
        viewModel.loadImageIntoImageView(imageViewProfile)

        val editIcon = root.findViewById<ImageView>(R.id.editIcon)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { selectedImageUri ->
                    imageViewProfile.setImageURI(selectedImageUri)
                    context?.let {
                        val imageMultipart: MultipartBody.Part? = viewModel.uriToMultipartBody(selectedImageUri, it)
                        if (imageMultipart != null) {
                            viewModel.viewModelScope.launch {
                                viewModel.updateProfileImageUri(imageMultipart, it)
                            }
                            Toast.makeText(requireContext(), "Todo bien", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al convertir la imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        editIcon.setOnClickListener {
            showImageSelectionDialog()
            Toast.makeText(requireContext(), "Editar perfil", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val tuActividad = activity as MainActivityUser
        tuActividad.mostrarBarraNavegacion()
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
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private fun processCapturedImage(result: Bitmap) {
        // La imagen ha sido capturada con éxito. Puedes manejar la imagen aquí.
        // La variable 'result' contiene la imagen como un Bitmap.
        val imageMultipart: MultipartBody.Part? = viewModel.bitmapToMultipartBody(result, requireContext())
        if (imageMultipart != null) {
            viewModel.viewModelScope.launch {
                viewModel.updateProfileImageUri(imageMultipart, requireContext())
            }

            val imageViewProfile = requireView().findViewById<ImageView>(R.id.user_image)
            imageViewProfile.setImageBitmap(result)

            Toast.makeText(requireContext(), "Todo bien", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error al convertir la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCameraPermission() {
        Log.d("MyApp", "Checking camera permission")
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permiso ya concedido, puedes proceder con la lógica de la cámara.
                openCamera()
            }
            else -> {
                // Solicitar permiso de cámara utilizando el nuevo enfoque de ActivityResultContracts.
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

}