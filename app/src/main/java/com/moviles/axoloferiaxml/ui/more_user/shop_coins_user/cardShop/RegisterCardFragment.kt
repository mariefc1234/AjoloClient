package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.moviles.axoloferiaxml.databinding.FragmentCardInfoBinding
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
        print(root)
        return root
    }


}