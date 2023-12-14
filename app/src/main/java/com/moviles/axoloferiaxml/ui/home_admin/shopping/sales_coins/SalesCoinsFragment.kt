package com.moviles.axoloferiaxml.ui.home_admin.shopping.sales_coins

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.ui.home_admin.shopping.sales_coins.sales_sumary_coins.SaleSumaryFragment

class SalesCoinsFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private var code = "dwadwad"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_sales_coins, container, false)
        ocultarBarraNavegacion()

        return root
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivity) {
            val tuActividad = activity as MainActivity
            tuActividad.ocultarBarraNavegacion()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                code = it.text
                getUsers(it.text)
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun getUsers(codeR: String) {
        val bundle = Bundle()
        bundle.putString("codigo", codeR)

        val saleSummaryFragment = SaleSumaryFragment()
        saleSummaryFragment.arguments = bundle

        findNavController().navigate(R.id.action_salesCoinsFragment_to_saleSumaryFragment, bundle)
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    fun getCode(): String {
        return code
    }


}