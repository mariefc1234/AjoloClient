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

        binding.cardShow.visibility = View.GONE
        binding.loadingSpinner.visibility = View.VISIBLE

        cardViewModel.cardLiveData.observe(viewLifecycleOwner, Observer { card ->
            binding.loadingSpinner.visibility = View.GONE
            if ((card != null) && card.data.cardExist) {
                binding.cardShow.visibility = View.VISIBLE
                binding.btnPay.visibility= View.VISIBLE
                binding.btnUpdateCard.visibility = View.VISIBLE

                binding.cardShow.apply {
                    expiry = "${card.data.card.cardExpirationMonth}${card.data.card.cardExpirationYear}"
                    number = card.data.card.cardNumber
                    holder = card.data.card.cardHolder
                }
                binding.btnPay.setOnClickListener {
                    findNavController().navigate(R.id.action_cardUserFragment_to_payCardFragment)
                }
                binding.btnUpdateCard.setOnClickListener {
                    findNavController().navigate(R.id.action_cardUserFragment_to_updateCardFragment)
                }
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