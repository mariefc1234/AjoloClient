package com.moviles.axoloferiaxml.ui.home_admin.shopping.sales_coins.sales_sumary_coins

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.offers.OfferGet
import com.moviles.axoloferiaxml.data.model.sales.SalePost
import com.moviles.axoloferiaxml.data.model.sales.User
import com.moviles.axoloferiaxml.data.network.sales.RetrofitClientSales
import com.moviles.axoloferiaxml.data.network.user_employee.RetrofitClient
import com.moviles.axoloferiaxml.ui.home_admin.shopping.sales_coins.SalesCoinsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SaleSumaryFragment : Fragment() {
    private lateinit var edtxSale: EditText
    private lateinit var edtxCodePromo: EditText
    private lateinit var saleAmount: TextView
    private lateinit var conver: TextView
    private lateinit var ajoloCoinsTv: TextView
    private lateinit var validateButton: Button
    private var uuid = ""
    private lateinit var statusCodeOffer: TextView
    private lateinit var ajoloCoinsPromoTv : TextView
    private lateinit var finalTotalTv : TextView
    private lateinit var buttonCompleteSale : Button
    private lateinit var coupon : OfferGet
    private lateinit var client : User

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val codigo = arguments?.getString("codigo")
        codigo?.let { getUser(it) }

        val root = inflater.inflate(R.layout.fragment_sale_sumary, container, false)

        edtxSale = root.findViewById(R.id.edtxSale)
        saleAmount = root.findViewById(R.id.saleAmount)
        conver = root.findViewById(R.id.conver)
        ajoloCoinsTv = root.findViewById(R.id.ajoloCoinsTv)

        ajoloCoinsPromoTv = root.findViewById(R.id.ajoloCoinsPromoTv)
        finalTotalTv = root.findViewById(R.id.finalTotalTv)

        // Agregar un TextWatcher al EditText
        edtxSale.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nada
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //nada
            }

            override fun afterTextChanged(s: Editable?) {
                val inputValue = s.toString().toDoubleOrNull() ?: 0.0
                saleAmount.text = inputValue.toString()
                val result = inputValue / 10.0
                conver.text = result.toString()
                ajoloCoinsTv.text = result.toString()
                finalTotalTv.text = inputValue.toString()
            }
        })
        edtxCodePromo = root.findViewById(R.id.edtxCodeOffer)

        validateButton = root.findViewById(R.id.validateCode)

        validateButton.setOnClickListener(){
            edtxCodePromo.text.toString()
            validateCode(edtxCodePromo.text.toString())
        }

        statusCodeOffer = root.findViewById(R.id.statusCodeOffer)


        buttonCompleteSale = root.findViewById(R.id.buttonCompleteSale)

        buttonCompleteSale.setOnClickListener(){
            completeSale()
        }

        return root
    }

    private fun completeSale() {
        finalTotalTv = view?.findViewById(R.id.finalTotalTv)!!
        ajoloCoinsTv = view?.findViewById(R.id.ajoloCoinsTv)!!
        statusCodeOffer = view?.findViewById(R.id.statusCodeOffer)!!
        var couponpost = 0


        if(statusCodeOffer.text != ""){
            couponpost = coupon.data.id_coupon
        }

        val finalTotal = finalTotalTv.text.toString().toDouble().toInt()
        val ajoloCoins = ajoloCoinsTv.text.toString().toDouble().toInt()

        val sale = SalePost(client.data.uuid, couponpost, finalTotal)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientSales.webServiceSales.shopCoins(keystoreHelper, sale)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        val codeInfo = call.body()
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Venta realizada", Toast.LENGTH_LONG).show()
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

    @SuppressLint("ResourceAsColor")
    private fun validateCode(codePromo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientSales.webServiceSales.getCoupon(keystoreHelper, codePromo, uuid)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        val codeInfo = call.body()
                        coupon = call.body()!!
                        if (codeInfo?.data?.valid == true) {
                            statusCodeOffer.setTextColor(R.color.green)
                            statusCodeOffer.text = getString(R.string.status_valid)
                            finalPrice(codeInfo)
                        } else {
                            statusCodeOffer.setTextColor(R.color.red_1)
                            statusCodeOffer.text = getString(R.string.status_invalid)
                            ajoloCoinsPromoTv = view?.findViewById(R.id.ajoloCoinsPromoTv)!!
                            ajoloCoinsPromoTv.text = "N/A"
                            finalTotalTv = view?.findViewById(R.id.finalTotalTv)!!
                            edtxSale = view?.findViewById(R.id.edtxSale)!!
                            val sale = edtxSale.text.toString()
                            finalTotalTv.text = sale
                        }
                    } else {
                        statusCodeOffer.setTextColor(R.color.red_1)
                        statusCodeOffer.text = getString(R.string.status_invalid)
                        ajoloCoinsPromoTv = view?.findViewById(R.id.ajoloCoinsPromoTv)!!
                        ajoloCoinsPromoTv.text = "N/A"
                        finalTotalTv = view?.findViewById(R.id.finalTotalTv)!!
                        edtxSale = view?.findViewById(R.id.edtxSale)!!
                        val sale = edtxSale.text.toString()
                        finalTotalTv.text = sale
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun finalPrice(codeInfo: OfferGet) {
        if (codeInfo.data.idCouponType == "Percentage") {
            edtxSale = view?.findViewById(R.id.edtxSale)!!
            val sale = edtxSale.text.toString()

            try {
                val sale2: Double = sale.toDouble()
                if (sale2 >= codeInfo.data.minimunAmount) {
                    val promo = sale2 * (codeInfo.data.valueCoupon / 100.0)
                    ajoloCoinsPromoTv = view?.findViewById(R.id.ajoloCoinsPromoTv)!!
                    ajoloCoinsPromoTv.text = promo.toString()
                    finalTotalTv = view?.findViewById(R.id.finalTotalTv)!!
                    finalTotalTv.text = (sale2 - promo).toString()
                }
            } catch (e: NumberFormatException) {

                ajoloCoinsPromoTv = view?.findViewById(R.id.ajoloCoinsPromoTv)!!
                ajoloCoinsPromoTv.text = "Invalid input"

                finalTotalTv = view?.findViewById(R.id.finalTotalTv)!!
                finalTotalTv.text = "Invalid input"
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
                        val usernameTextView = view?.findViewById<TextView>(R.id.username)
                        val coinsTextView = view?.findViewById<TextView>(R.id.coins)

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