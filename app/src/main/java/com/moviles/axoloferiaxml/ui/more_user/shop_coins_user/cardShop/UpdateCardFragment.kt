package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop

import CardAdd
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maxpilotto.creditcardview.models.CardInput
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.databinding.FragmentUpdateCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class updateCardFragment : Fragment() {

    private var _binding: FragmentUpdateCardBinding? = null
    private val binding get() = _binding!!

    private val cardViewModel by viewModels<CardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cardShow.visibility = View.GONE
        binding.loadingSpinner.visibility = View.VISIBLE

        val cardShow = binding.cardShow

        cardViewModel.cardLiveData.observe(viewLifecycleOwner, Observer { card ->
            binding.loadingSpinner.visibility = View.GONE
            binding.cardShow.visibility = View.VISIBLE

            if (card != null) {

                cardShow.apply {
                    pairInput(CardInput.HOLDER, binding.holderEditText)
                    pairInput(CardInput.EXPIRY, binding.expirationDateEditText)
                    pairInput(CardInput.NUMBER, binding.cardNumberEditText)
                }

                binding.holderEditText.setText(card.data.card.cardHolder)
                binding.cardNumberEditText.setText(card.data.card.cardNumber)
                binding.expirationDateEditText.setText("${card.data.card.cardExpirationMonth}${card.data.card.cardExpirationYear}")

                binding.addCardButton.setOnClickListener{
                    this.registerCard(_binding!!)
                }
            }
        })

        cardViewModel.getCard(requireContext())

        return root
    }


    private fun registerCard(_binding: FragmentUpdateCardBinding) {
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
                            Toast.makeText(requireContext(), "Tarjeta Actualizada!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_updateCardFragment_to_cardUserFragment)

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