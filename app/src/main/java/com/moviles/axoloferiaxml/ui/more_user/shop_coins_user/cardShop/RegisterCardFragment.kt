package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.maxpilotto.creditcardview.models.CardInput

import com.moviles.axoloferiaxml.databinding.FragmentRegisterCardBinding

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
            registerCard(_binding!!)
        }

        return root
    }

    private fun registerCard(_binding: FragmentRegisterCardBinding) {
        val holder = _binding.holderEditText.text.toString()
        val card = _binding.cardNumberEditText.text.toString()
        val expirationDate = _binding.expirationDateEditText.text.toString()
        
        val (month, year) = getMonthAndYear(expirationDate)
        
        if (month != null && year != null) {
            // here the things work
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