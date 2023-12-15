package com.moviles.axoloferiaxml.ui.home_admin.management.offers_employees

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.offers.OffersGetAllsResponse
import com.moviles.axoloferiaxml.data.model.offers.OffersPost
import com.moviles.axoloferiaxml.data.model.sales.GetSalesResponse
import com.moviles.axoloferiaxml.data.network.offers.RetrofitClientOffers
import com.moviles.axoloferiaxml.data.network.sales.RetrofitClientSales
import com.moviles.axoloferiaxml.data.network.user_employee.RetrofitClient
import com.moviles.axoloferiaxml.databinding.FragmentOffersEmployeesBinding
import com.moviles.axoloferiaxml.databinding.FragmentShoppingHistoryUserBinding
import com.moviles.axoloferiaxml.ui.more_user.shopping_history_user.ShoppingHistoryUserAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class OffersEmployeesFragment : Fragment(), OffersEmployeesAdapter.OnItemClicked {


    lateinit var binding: FragmentOffersEmployeesBinding
    lateinit var adatador: OffersEmployeesAdapter

    var listOfOffers = OffersGetAllsResponse(status = "Success", msg = "")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersEmployeesBinding.inflate(inflater, container, false)

        binding.rvCoupons.layoutManager = LinearLayoutManager(context)
        setupRecyclerView()
        getCoupons()

        ocultarBarraNavegacion()

        binding.circularButton.setOnClickListener {
            showDialog()
        }

        return binding.root
    }

    private fun getCoupons() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientOffers.webServiceOffers.getCoupons(keystoreHelper)
                if (call.isSuccessful) {
                    val sales = call.body()
                    sales?.let {
                        listOfOffers = it
                        withContext(Dispatchers.Main) {
                            setupRecyclerView()
                        }
                    }
                } else {
                    Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adatador = OffersEmployeesAdapter(requireContext(), listOfOffers)
        binding.rvCoupons.adapter = adatador
        adatador.setOnClick(this)
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivity) {
            val tuActividad = activity as MainActivity
            tuActividad.ocultarBarraNavegacion()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_offers, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnRegister = dialogView.findViewById<Button>(R.id.btnRegister)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnRegister.setOnClickListener {

            val etValueCoupon = dialogView.findViewById<EditText>(R.id.etValueCoupon).text.toString()
            val etCodeCoupon = dialogView.findViewById<EditText>(R.id.etCodeCoupon).text.toString()
            val etMinAmounth = dialogView.findViewById<EditText>(R.id.etMinAmounth).text.toString()
            val etUsesPerUser = dialogView.findViewById<EditText>(R.id.etUsesPerUser).text.toString()
            val etTotalUses = dialogView.findViewById<EditText>(R.id.etTotalUses).text.toString()

            if (etValueCoupon.isBlank() || etCodeCoupon.isBlank() || etMinAmounth.isBlank() ||
                etUsesPerUser.isBlank() || etTotalUses.isBlank()) {
                Toast.makeText(context, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show()
            } else {
                runBlocking {
                    val offerPost = OffersPost(
                        idCouponType = 2,
                        valueCoupon = etValueCoupon.toInt(),
                        codeCoupon = etCodeCoupon,
                        minimunAmount = etMinAmounth.toInt(),
                        usesPerUser = etUsesPerUser.toInt(),
                        totalUses = etTotalUses.toInt()
                    )

                    addOffersCoupon(requireContext(), offerPost, dialog)
                }
            }

        }

        dialog.show()
    }

    suspend fun addOffersCoupon(context: Context, offerPost : OffersPost, dialog : Dialog) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientOffers.webServiceOffers.addCoupon(keystoreHelper,offerPost)
                if (call.isSuccessful) {
                    getCoupons()
                    dialog.dismiss()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Registro realizado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "ERROR al registrar", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR_POST", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun mostrarDialogoRegisterEmployee() {

        /*
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Título del diálogo")
            .setMessage("Mensaje del diálogo")
            .setPositiveButton("Aceptar") { dialog, which ->
                // Lógica para manejar el clic en el botón Aceptar
                // Puedes dejar esto en blanco si no necesitas realizar ninguna acción específica
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                // Lógica para manejar el clic en el botón Cancelar
                // Puedes dejar esto en blanco si no necesitas realizar ninguna acción específica
            }
            .show()

         */
    }

    override fun borrarUsuario(id: Int) {
        val keystoreHelper = KeystoreHelper(requireContext()).getToken()
        Log.d("ID", id.toString())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = RetrofitClientOffers.webServiceOffers.deleteCoupons(keystoreHelper,id)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        Toast.makeText(requireContext(), "Eliminado correctamente", Toast.LENGTH_LONG).show()
                        getCoupons()
                    } else {
                        Log.e("API_CALL_DELETE", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "Error al eliminar usuario", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("EXCEPTION", "Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error inesperado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
