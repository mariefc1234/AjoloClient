package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moviles.axoloferiaxml.databinding.FragmentCardInfoBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.R

class CardInfoFragment : Fragment() {

    private var _binding: FragmentCardInfoBinding? = null
    private val binding get() = _binding!!

    private val cardViewModel by viewModels<CardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loadingSpinner.visibility = View.VISIBLE
        binding.cardCard.visibility = View.GONE

        cardViewModel.cardLiveData.observe(viewLifecycleOwner, Observer { card ->
            binding.loadingSpinner.visibility = View.GONE
            if ((card != null) && card.data.cardExist) {
                binding.cardCard.visibility = View.VISIBLE
                binding.titleCard.text = card.data.card.normalizedName
                binding.tvExpire.text = "Expira el ${card.data.card.cardExpirationMonth}/${card.data.card.cardExpirationYear}"
                binding.tvCardNumber.text = card.data.card.cardNumber
                binding.tvCardHolder.text = card.data.card.cardHolder
            } else {
                binding.tvNoCardWarning.visibility = View.VISIBLE
                binding.btnAddCard.visibility = View.VISIBLE
                binding.btnAddCard.setOnClickListener{
                    findNavController().navigate(R.id.action_cardUserFragment_to_registerCardFragment)
                }
            }
        })

        cardViewModel.getCard(requireContext())

        return root
    }
}