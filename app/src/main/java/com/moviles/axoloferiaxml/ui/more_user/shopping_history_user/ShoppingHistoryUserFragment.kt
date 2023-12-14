package com.moviles.axoloferiaxml.ui.more_user.shopping_history_user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.sales.GetSalesResponse
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.network.sales.RetrofitClientSales
import com.moviles.axoloferiaxml.data.network.user_employee.RetrofitClient
import com.moviles.axoloferiaxml.databinding.FragmentEmployeeAdmBinding
import com.moviles.axoloferiaxml.databinding.FragmentShoppingHistoryUserBinding
import com.moviles.axoloferiaxml.ui.home_admin.admin_panel.employees_admin.EmployeeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ShoppingHistoryUserFragment : Fragment() {

    lateinit var binding: FragmentShoppingHistoryUserBinding
    lateinit var adatador: ShoppingHistoryUserAdapter

    var listOfShops = GetSalesResponse(
        status = "Success",
        msg = "",
        data = GetSalesResponse.Data(shoppings = arrayListOf())
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShoppingHistoryUserBinding.inflate(inflater, container, false)

        ocultarBarraNavegacion()

        binding.rvShops.layoutManager = LinearLayoutManager(context)
        setupRecyclerView()
        val uuid = (requireActivity() as MainActivityUser).getUserUuid()
        getShops(uuid)

        
        return binding.root
    }

    private fun getShops(uuid : String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientSales.webServiceSales.getShoppings(keystoreHelper,uuid)
                if (call.isSuccessful) {
                    val sales = call.body()
                    sales?.let {
                        listOfShops = it
                        withContext(Dispatchers.Main) {
                            setupRecyclerView()
                        }
                    }
                } else {
                    Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG).show()
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

    private fun setupRecyclerView() {
        adatador = ShoppingHistoryUserAdapter(requireContext(), listOfShops)
        binding.rvShops.adapter = adatador
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivityUser) {
            val tuActividad = activity as MainActivityUser
            tuActividad.ocultarBarraNavegacion()
        }
    }

}