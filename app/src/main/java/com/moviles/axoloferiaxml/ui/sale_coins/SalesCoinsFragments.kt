package com.moviles.axoloferiaxml.ui.sale_coins

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.databinding.FragmentSalesCoinsBinding
import org.json.JSONObject

class SalesCoinsFragments: Fragment() {

    private lateinit var  salesCoinsViewModel: SalesCoinsViewModel
    private var _binding: FragmentSalesCoinsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSalesCoinsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        salesCoinsViewModel = ViewModelProvider(this).get(SalesCoinsViewModel::class.java)
        salesCoinsViewModel.qrResult.observe(viewLifecycleOwner, Observer {
            val stallResult = it ?: return@Observer

            if (stallResult.error != null) {
                showValidateQRFailed(stallResult.error)
            }
            if (stallResult.success != null) {
                loadQrValidated(stallResult.success)
            }
        })
        binding.btnScanner.setOnClickListener { initScanner() }
//        validateQr()
        return root
    }

    private fun initScanner(){
        IntentIntegrator(requireActivity()).initiateScan()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireActivity(), "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(), "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun loadQrValidated(model: User.UserData.UserInfo) {
        //TODO: to implement
    }

    private fun showValidateQRFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }
}

