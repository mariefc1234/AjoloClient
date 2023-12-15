package com.moviles.axoloferiaxml.ui.home_stall

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.charges.ChargePost
import com.moviles.axoloferiaxml.data.model.sales.SalePost
import com.moviles.axoloferiaxml.data.model.sales.User
import com.moviles.axoloferiaxml.data.network.charge.RetrofitClientCharge
import com.moviles.axoloferiaxml.data.network.sales.RetrofitClientSales
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MakeChargeFragment : Fragment() {

    private lateinit var usernameCharge: TextView
    private lateinit var coinsCharge: TextView
    private lateinit var buttonCompleteCharge: Button
    private lateinit var textEditCoinsCharge : EditText

    private lateinit var client : User
    private var uuid = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val codigo = arguments?.getString("codigo")
        codigo?.let { getUser(it) }
        val root = inflater.inflate(R.layout.fragment_make_charge, container, false)

        usernameCharge = root.findViewById(R.id.usernameCharge)
        coinsCharge = root.findViewById(R.id.coinsCharge)
        buttonCompleteCharge = root.findViewById(R.id.buttonCompleteCharge)
        textEditCoinsCharge = root.findViewById(R.id.textEditCoinsCharge)

        buttonCompleteCharge.setOnClickListener(){
            if (textEditCoinsCharge.text.isBlank()) {
                Toast.makeText(context, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show()
            } else {
                completeCharge()
            }
        }

        return root
    }

    private fun completeCharge() {
        usernameCharge = view?.findViewById(R.id.usernameCharge)!!
        coinsCharge = view?.findViewById(R.id.coinsCharge)!!
        textEditCoinsCharge = view?.findViewById(R.id.textEditCoinsCharge)!!


        val charge = textEditCoinsCharge.text.toString().toDouble().toInt()

        val chargePost = ChargePost(uuid,charge)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientCharge.webServiceCharge.addCharge(keystoreHelper, chargePost)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        val codeInfo = call.body()
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Cargo realizado", Toast.LENGTH_LONG).show()
                    } else {
                        Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")

                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(requireContext(), "ERROR en venta", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error de autenticación: ${e.response()?.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun getUser(codigo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()

                val call = RetrofitClientSales.webServiceSales.getUser(keystoreHelper, codigo)
                if (call.isSuccessful) {
                    val user = call.body()
                    client = call.body()!!
                    Handler(Looper.getMainLooper()).post {
                        val usernameTextView = view?.findViewById<TextView>(R.id.usernameCharge)
                        val coinsTextView = view?.findViewById<TextView>(R.id.coinsCharge)

                        if (usernameTextView != null && coinsTextView != null) {
                            usernameTextView.text = user?.data?.username
                            coinsTextView.text = user?.data?.coins.toString()
                            uuid = user?.data?.uuid.toString()
                        }
                    }

                } else {
                    Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(requireContext(), "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}