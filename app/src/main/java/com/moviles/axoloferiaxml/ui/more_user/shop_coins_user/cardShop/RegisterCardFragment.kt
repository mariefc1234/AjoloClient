package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop

import CardAdd
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maxpilotto.creditcardview.models.CardInput
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeAdd
import com.moviles.axoloferiaxml.data.network.sales.RetrofitClientSales
import com.moviles.axoloferiaxml.data.network.user_employee.RetrofitClient
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.databinding.FragmentRegisterCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RegisterCardFragment : Fragment() {

    private var _binding: FragmentRegisterCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cardShow =  binding.cardShow
        cardShow.apply {
            pairInput(CardInput.HOLDER, binding.holderEditText)
            pairInput(CardInput.EXPIRY, binding.expirationDateEditText)
            pairInput(CardInput.NUMBER, binding.cardNumberEditText)
        }

        binding.addCardButton.setOnClickListener{
            this.registerCard(_binding!!)
        }

        return root
    }

    private fun registerCard(_binding: FragmentRegisterCardBinding) {
        val expirationDate = _binding.expirationDateEditText.text.toString()
        val (month, year) = getMonthAndYear(expirationDate)

        if (month != null && year != null) {
            val cardAdd = CardAdd("", "", 0, 0) // Assuming cardAdd is an instance of your CardAdd class, declare it here

            cardAdd.cardHolder = _binding.holderEditText.text.toString()
            cardAdd.cardNumber = _binding.cardNumberEditText.text.toString()
            cardAdd.cardExpirationMonth = month
            cardAdd.cardExpirationYear = year

            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                    val call = RetrofitClientCard.webServiceCard.updateCard(keystoreHelper, cardAdd)

                    withContext(Dispatchers.Main) {
                        if (call.isSuccessful) {
                            Toast.makeText(requireContext(), "Tarjeta Registrada!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerCardFragment_to_cardUserFragment)

                        } else {
                            Toast.makeText(requireContext(), "Ingresa una tarjeta válida", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR_POST", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                Toast.makeText(requireContext(), "Ingresa una tarjeta válida", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Put a correct date format please", Toast.LENGTH_SHORT).show()
        }
    }


    fun getMonthAndYear(expirationDate: String): Pair<Int?, Int?> {
        if (expirationDate.length == 6) {
            val month = expirationDate.substring(0, 2).toIntOrNull()
            val year = expirationDate.substring(2, 6).toIntOrNull()

            return Pair(month, year)
        } else {
            return Pair(null, null)
        }
    }
}