package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.pay_card

import RetrofitClientCard
import ShopCard
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.databinding.FragmentPayCardBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.Executor
import co.infinum.goldfinger.Goldfinger;
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

class PayCardFragment : Fragment() {

    private var _binding: FragmentPayCardBinding? = null
    private val binding get() = _binding!!


    private lateinit var goldfinger: Goldfinger


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPayCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        goldfinger = Goldfinger.Builder(requireContext()).build()
        SharedPrefs.init(requireContext())

        binding.edtxSale.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isNotEmpty()) {
                        val enteredValue = it.toString()
                        if (!enteredValue.matches("\\d+".toRegex()) || enteredValue.toInt() < 10) {
                        } else {
                            updateConversion(enteredValue.toInt())
                        }
                    }
                }
            }
        })

        binding.buttonCompleteSale.setOnClickListener {
            val costToBuyText = binding.edtxSale.text.toString()
            val cvvText = binding.edtxCvv.text.toString()

            if (isValidCostToBuy(costToBuyText) && isValidCvv(cvvText)) {
                performSale(costToBuyText.toInt(), cvvText)
            } else {
                Toast.makeText(requireContext(), "Datos no válidos", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun updateConversion(value: Int) {
        val conversionValue = BigDecimal(value * 0.1).setScale(2, RoundingMode.HALF_EVEN)
        binding.conver.text = conversionValue.toString()
    }

    private fun isValidCostToBuy(costToBuy: String): Boolean {
        return costToBuy.matches("\\d+".toRegex()) && costToBuy.toInt() >= 10
    }

    private fun isValidCvv(cvv: String): Boolean {
        return cvv.length == 3
    }

    private fun performSale(costToBuy: Int, cvv: String) {
        Toast.makeText(requireContext(), "Pasaaa", Toast.LENGTH_SHORT).show()
        authenticateWithGoldfinger(costToBuy, cvv)
    }

    private fun authenticateWithGoldfinger(costToBuy: Int, cvv: String) {
        val promptParams = Goldfinger.PromptParams.Builder(this)
            .title("Payment")
            .description("Authenticate Fingerprint to proceed with payment")
            .negativeButtonText("Cancel")
            .allowedAuthenticators(SharedPrefs.getAuthenticatorType())
            .build()

        goldfinger.authenticate(promptParams, object : Goldfinger.Callback {
            override fun onResult(result: Goldfinger.Result) {
                if (result.type() == Goldfinger.Type.SUCCESS) {
                    performSaleAfterBiometricAuth(costToBuy, cvv)
                } else{
                    Toast.makeText(requireContext(), "Error en la autenticación biométrica", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(e: Exception) {
                Toast.makeText(requireContext(), "Awuu", Toast.LENGTH_SHORT).show()
                Log.e("YourTag", "Error in Goldfinger authentication", e)
                performSaleAfterBiometricAuth(costToBuy, cvv)
            }
        })
    }


    private fun performSaleAfterBiometricAuth(costToBuy: Int, cvv: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {

                val shopCard = ShopCard(costToBuy)

                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientCard.webServiceCard.CoinsCardBuy(keystoreHelper, shopCard)

                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        findNavController().navigate(R.id.action_payCardFragment_to_shoppingHistoryUserFragment)
                        Toast.makeText(requireContext(), "Pago Realizado!", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(requireContext(), "Error en el pago", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: HttpException) {
            Log.e("API_CALL_ERROR_POST", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
            Toast.makeText(requireContext(), "Error en el pago", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(requireContext(), "Venta completada con éxito", Toast.LENGTH_SHORT).show()
    }

}
